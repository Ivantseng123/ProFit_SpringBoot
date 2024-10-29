package com.ProFit.controller.transactions.frontend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ProFit.model.dto.coursesDTO.CourseOrderDTO;
import com.ProFit.model.dto.servicesDTO.ServiceOrdersDTO;
import com.ProFit.model.dto.transactionDTO.JobOrderDTO;
import com.ProFit.model.dto.transactionDTO.UserTransactionDTO;
import com.ProFit.model.dto.usersDTO.UsersDTO;
import com.ProFit.service.courseService.CourseOrderService;
import com.ProFit.service.eventService.EventOrderService;
import com.ProFit.service.serviceService.ServiceOrdersService;
import com.ProFit.service.transactionService.JobOrderService;
import com.ProFit.service.transactionService.UserTransactionService;
import com.ProFit.service.userService.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AllOrderController {

	@Autowired
	private UserService userService;

	@Autowired
	private JobOrderService jobOrderService;
	
	@Autowired
	private CourseOrderService courseOrderService;
	
	@Autowired
	private EventOrderService eventOrderService;
	
	@Autowired
	private UserTransactionService userTransactionService;
	
	@Autowired
	private ServiceOrdersService serviceOrdersService;

	@GetMapping("/allOrder")
	public String showWalletPage(HttpSession session, Model model) {
		UsersDTO usersDTO = (UsersDTO) session.getAttribute("CurrentUser");
		if (usersDTO == null || usersDTO.getUserId() == null) {
			return "redirect:/user/profile";
		}
		Integer userId = usersDTO.getUserId();

		Integer userBalance = userService.getUserBalanceById(userId);
		model.addAttribute("userBalance", userBalance);

		return "transactionVIEW/frontend/allOrder";
	}

	// 透過AJAX獲取JobOrder資料
	@GetMapping("/allOrder/jobOrders")
	@ResponseBody
	public List<JobOrderDTO> getAllJobOrders(HttpSession session) {
		UsersDTO usersDTO = (UsersDTO) session.getAttribute("CurrentUser");
		if (usersDTO == null || usersDTO.getUserId() == null) {
			return null;
		}

		List<JobOrderDTO> orders = jobOrderService.getAllOrdersAsDTO();
	    return orders;
	}
	
	// 獲取所有 ServiceOrder 資料
	@GetMapping("/allOrder/serviceOrders")
	@ResponseBody
	public List<ServiceOrdersDTO> getAllServiceOrders(HttpSession session) {
	    UsersDTO usersDTO = (UsersDTO) session.getAttribute("CurrentUser");
	    if (usersDTO == null || usersDTO.getUserId() == null) {
	        return null;
	    }

	    // 根據 caseownerId 過濾訂單
	    List<ServiceOrdersDTO> serviceOrders = serviceOrdersService.getServiceOrdersByCaseownerId(usersDTO.getUserId());
	    return serviceOrders;
	}

	
	// 獲取所有 CourseOrder 資料
	@GetMapping("/allOrder/courseOrders")
	@ResponseBody
	public List<CourseOrderDTO> getAllCourseOrders(HttpSession session) {
	    UsersDTO usersDTO = (UsersDTO) session.getAttribute("CurrentUser");
	    if (usersDTO == null || usersDTO.getUserId() == null) {
	        return null;
	    }

	    Integer userId = usersDTO.getUserId();
	    return courseOrderService.getOrdersByUserId(userId);
	}
	
	// 新增刪除訂單的 API
	@PostMapping("/allOrder/deleteOrder")
	@ResponseBody
	public String deleteOrder(@RequestParam String orderId, HttpSession session) {
	    UsersDTO usersDTO = (UsersDTO) session.getAttribute("CurrentUser");
	    if (usersDTO == null || usersDTO.getUserId() == null) {
	        return "unauthorized";
	    }

	    try {
	        courseOrderService.deleteCourseOrderById(orderId);
	        return "success";
	    } catch (Exception e) {
	        return "error";
	    }
	}
	
	//更新訂單狀態
	@PostMapping("/allOrder/updateOrderStatus")
	@ResponseBody
	public String updateOrderStatus(@RequestParam String orderId, @RequestParam String status, HttpSession session) {
	    UsersDTO usersDTO = (UsersDTO) session.getAttribute("CurrentUser");
	    if (usersDTO == null || usersDTO.getUserId() == null) {
	        return "unauthorized";
	    }

	    try {
	        // 檢查 status，根據不同狀態進行相應操作
	        if ("Pending".equals(status)) {
	            // 將狀態從 Pending 更新為 "已付款"
	            courseOrderService.updateOrderStatusById(orderId, "paid");

	            // 為付款方新增交易紀錄
	            UserTransactionDTO transactionDTO = new UserTransactionDTO();
	            transactionDTO.setUserId(usersDTO.getUserId());
	            transactionDTO.setTransactionRole("payer"); 
	            transactionDTO.setTransactionType("payment"); 
	            String orderType = getOrderTypeByOrderId(orderId);
	            transactionDTO.setOrderType(orderType);
	            transactionDTO.setOrderId(orderId);
	            Double orderAmount = getOrderAmountByOrderId(orderId);
	            transactionDTO.setTotalAmount(orderAmount);
	            transactionDTO.setTransactionStatus("completed");
	            transactionDTO.setPaymentMethod("ECPAY");
	            userTransactionService.insertTransaction(transactionDTO);

	        } else if ("Completed".equals(status)) {
	            // 將狀態從 "已付款" 更新為 "Completed"
	            courseOrderService.updateOrderStatusById(orderId, "Completed");

	            // 為課程創建者新增一筆「存入」的交易紀錄
	            Integer creatorUserId = courseOrderService.getCreatorUserIdByOrderId(orderId); // 創建者的 ID
	            Double originalAmount = getOrderAmountByOrderId(orderId);
	            Double finalAmount = originalAmount * 0.95; // 扣除 5% 手續費

	            UserTransactionDTO transactionDTO = new UserTransactionDTO();
	            transactionDTO.setUserId(creatorUserId);
	            transactionDTO.setTransactionRole("receiver");
	            transactionDTO.setTransactionType("deposit");
	            String orderType = getOrderTypeByOrderId(orderId);
	            transactionDTO.setOrderType(orderType);
	            transactionDTO.setOrderId(orderId);
	            transactionDTO.setTotalAmount(finalAmount);
	            transactionDTO.setTransactionStatus("completed");
	            transactionDTO.setPaymentMethod("deposit");

	            userTransactionService.insertTransaction(transactionDTO);
	            
	            userService.updateUserBalance(creatorUserId, finalAmount);
	        }

	        return "success";
	    } catch (Exception e) {
	        return "error";
	    }
	}
	
	// 輔助方法 - 取得訂單類型
    private String getOrderTypeByOrderId(String orderId) {
        if (orderId.startsWith("J")) {
            return "job";
        } else if (orderId.startsWith("C")) {
            return "course";
        } else if (orderId.startsWith("E")) {
            return "event";
        }
        return "unknown"; // 如果無法匹配訂單類型
    }

    // 輔助方法 - 取得訂單金額並移除小數點
    private Double getOrderAmountByOrderId(String orderId) {
        Double amount = 0.0;
        if (orderId.startsWith("J")) {
            amount = jobOrderService.getOrderAmountById(orderId); // 假設有這樣的方法
        } else if (orderId.startsWith("C")) {
            amount = courseOrderService.getOrderAmountById(orderId);
        } 
        return (double) amount.intValue(); // 回傳去除小數點的金額
    }
    
    
}
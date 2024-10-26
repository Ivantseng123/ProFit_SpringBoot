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
import com.ProFit.model.dto.transactionDTO.JobOrderDTO;
import com.ProFit.model.dto.usersDTO.UsersDTO;
import com.ProFit.service.courseService.CourseOrderService;
import com.ProFit.service.eventService.EventOrderService;
import com.ProFit.service.transactionService.JobOrderService;
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
	
	// 獲取所有 CourseOrder 資料
	@GetMapping("/allOrder/courseOrders")
	@ResponseBody
	public List<CourseOrderDTO> getAllCourseOrders(HttpSession session) {
	    UsersDTO usersDTO = (UsersDTO) session.getAttribute("CurrentUser");
	    if (usersDTO == null || usersDTO.getUserId() == null) {
	        return null;
	    }

	    List<CourseOrderDTO> courseOrders = courseOrderService.searchAllCourseOrders();
	    return courseOrders;
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

}
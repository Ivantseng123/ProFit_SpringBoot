package com.ProFit.controller.transactions.frontend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ProFit.model.dto.transactionDTO.JobOrderDTO;
import com.ProFit.model.dto.usersDTO.UsersDTO;
import com.ProFit.service.transactionService.JobOrderService;
import com.ProFit.service.userService.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AllOrderController {

	@Autowired
	private UserService userService;

	@Autowired
	private JobOrderService jobOrderService;

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
			return null; // 如果使用者未登入，返回null
		}

		List<JobOrderDTO> orders = jobOrderService.getAllOrdersAsDTO();
	    System.out.println("Orders fetched: " + orders); // 用來檢查是否有正確返回資料
	    return orders;
	}
}

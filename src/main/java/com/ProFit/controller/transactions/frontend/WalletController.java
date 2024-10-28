package com.ProFit.controller.transactions.frontend;

import com.ProFit.model.dto.coursesDTO.CourseOrderDTO;
import com.ProFit.model.dto.transactionDTO.InvoiceDTO;
import com.ProFit.model.dto.transactionDTO.UserTransactionDTO;
import com.ProFit.model.dto.usersDTO.UsersDTO;
import com.ProFit.service.courseService.CourseOrderService;
import com.ProFit.service.transactionService.InvoiceService;
import com.ProFit.service.transactionService.UserTransactionService;
import com.ProFit.service.userService.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Controller
public class WalletController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserTransactionService userTransactionService;

	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	private CourseOrderService courseOrderService;

	@GetMapping("/wallet")
	public String showWalletPage(HttpSession session, Model model) {
		UsersDTO usersDTO = (UsersDTO) session.getAttribute("CurrentUser");
		if (usersDTO == null || usersDTO.getUserId() == null) {
			return "redirect:/user/profile";
		}
		Integer userId = usersDTO.getUserId();

		Integer userBalance = userService.getUserBalanceById(userId);
		model.addAttribute("userBalance", userBalance);

		return "transactionVIEW/frontend/wallet";
	}

	// 類型篩選返回 JSON
	@GetMapping("/wallet/filter")
	@ResponseBody
	public List<UserTransactionDTO> filterTransactions(@RequestParam("type") String type, HttpSession session) {
		UsersDTO usersDTO = (UsersDTO) session.getAttribute("CurrentUser");
		if (usersDTO == null || usersDTO.getUserId() == null) {
			return Collections.emptyList();
		}
		Integer userId = usersDTO.getUserId();

		List<UserTransactionDTO> transactions;
		switch (type) {
		case "deposit":
			transactions = userTransactionService.getTransactionsByType(userId, "deposit");
			break;
		case "withdrawal":
			transactions = userTransactionService.getTransactionsByType(userId, "withdrawal");
			break;
		default:
			transactions = userTransactionService.getAllTransactionsByUserId(userId);
			break;
		}

		return transactions;
	}

	// 獲取所有 CourseOrder 資料
	@GetMapping("/wallet/courseOrders")
	@ResponseBody
	public List<CourseOrderDTO> getAllCourseOrders(HttpSession session) {
		UsersDTO usersDTO = (UsersDTO) session.getAttribute("CurrentUser");
		if (usersDTO == null || usersDTO.getUserId() == null) {
			return null;
		}

		List<CourseOrderDTO> courseOrders = courseOrderService.searchAllCourseOrders();
		return courseOrders;
	}

	// 日期篩選返回 JSON
	@GetMapping("/wallet/filterDate")
	@ResponseBody
	public List<UserTransactionDTO> filterTransactionsByDate(@RequestParam("startDate") LocalDate startDate,
			@RequestParam("endDate") LocalDate endDate, HttpSession session) {

		UsersDTO usersDTO = (UsersDTO) session.getAttribute("CurrentUser");
		if (usersDTO == null || usersDTO.getUserId() == null) {
			return Collections.emptyList();
		}
		Integer userId = usersDTO.getUserId();

		List<UserTransactionDTO> transactions = userTransactionService.getTransactionsByCompletionDateRange(userId,
				startDate, endDate);
		return transactions;
	}

	@GetMapping("/wallet/invoices")
	@ResponseBody
	public List<InvoiceDTO> getInvoices(HttpSession session) {
		UsersDTO usersDTO = (UsersDTO) session.getAttribute("CurrentUser");
		if (usersDTO == null || usersDTO.getUserId() == null) {
			return Collections.emptyList();
		}

		// 根據用戶ID獲取發票記錄
		return invoiceService.getInvoicesByUserId(usersDTO.getUserId());
	}
}

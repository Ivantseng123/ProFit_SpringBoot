package com.ProFit.controller.transactions.frontend;

import com.ProFit.model.dto.usersDTO.UsersDTO;
import com.ProFit.service.transactionService.UserTransactionService;
import com.ProFit.service.userService.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TransactionController {

    @Autowired
    private UserTransactionService userTransactionService;

    @Autowired
    private UserService userService;

    // 顯示進帳管理頁面
    @GetMapping("/transaction")
    public String showIncomeOverview(HttpSession session, Model model) {
        UsersDTO usersDTO = (UsersDTO) session.getAttribute("CurrentUser");
        if (usersDTO == null || usersDTO.getUserId() == null) {
            return "redirect:/login";
        }
        Integer userId = usersDTO.getUserId();

        // 獲取用戶餘額
        Integer userBalance = userService.getUserBalanceById(userId);
        model.addAttribute("userBalance", userBalance);

        // 獲取撥款數據 (交易類型為取出且狀態為等待)
        model.addAttribute("pendingTransactions", userTransactionService.getPendingTransactionsByUserId(userId));

        // 獲取已撥款數據 (交易類型為取出且狀態為完成)
        model.addAttribute("paidTransactions", userTransactionService.getPaidTransactionsByUserId(userId));

        // 設定假設帳號
        model.addAttribute("accountNumber", "****5610");

        return "transactionVIEW/frontend/userTransactions"; // 返回模板頁面名稱
    }
    
}

package com.ProFit.controller.transactions.frontend;

import com.ProFit.model.dto.transactionDTO.UserTransactionDTO;
import com.ProFit.model.dto.usersDTO.UsersDTO;
import com.ProFit.service.transactionService.UserTransactionService;
import com.ProFit.service.userService.UserService;
import jakarta.servlet.http.HttpSession;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        if (userBalance != null) {
            model.addAttribute("userBalance", userBalance);
        } else {
            model.addAttribute("userBalance", 0); // 默認值為0
        }
        System.out.println("User Balance: " + userBalance);
        
        // 計算即將撥款金額並轉換為整數
        Integer pendingAmount = (int) Math.floor(userTransactionService.calculatePendingAmount(userId));
        model.addAttribute("pendingAmount", pendingAmount);

        // 計算已撥款金額並轉換為整數
        Integer paidAmount = (int) Math.floor(userTransactionService.calculatePaidAmount(userId));
        model.addAttribute("paidAmount", paidAmount);
        
        // 獲取撥款數據 (交易類型為取出且狀態為等待)
        List<UserTransactionDTO> pendingTransactions = userTransactionService.getPendingTransactionsByUserId(userId);
        model.addAttribute("pendingTransactions", pendingTransactions);
        
        // 設定假設帳號
        model.addAttribute("accountNumber", "****5610");

        return "transactionVIEW/frontend/userTransactions"; // 返回模板頁面名稱
    }
    
    @GetMapping("/transaction/getPendingTransactions")
    @ResponseBody
    public List<UserTransactionDTO> getPendingTransactions(HttpSession session) {
        UsersDTO usersDTO = (UsersDTO) session.getAttribute("CurrentUser");
        if (usersDTO == null || usersDTO.getUserId() == null) {
            return Collections.emptyList();
        }
        Integer userId = usersDTO.getUserId();
        return userTransactionService.getPendingTransactionsByUserId(userId);
    }

    
}

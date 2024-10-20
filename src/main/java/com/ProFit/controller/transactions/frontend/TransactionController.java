package com.ProFit.controller.transactions.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/transaction")
public class TransactionController {

    // 顯示進帳管理頁面，這裡傳遞靜態的假設數據
    @GetMapping("")
    public String showIncomeOverview(Model model) {
        // 假設初始數據
        double pendingAmount = 0.0;  // 靜態設置即將撥款的金額
        double paidAmount = 1963.0;  // 靜態設置已撥款的金額
        String accountNumber = "****5610";  // 靜態設置帳號

        // 將數據傳遞到模板
        model.addAttribute("pendingAmount", pendingAmount);
        model.addAttribute("paidAmount", paidAmount);
        model.addAttribute("accountNumber", accountNumber);

        return "transactionVIEW/frontend/userTransactions"; // 返回前端模板頁面名稱
    }
}

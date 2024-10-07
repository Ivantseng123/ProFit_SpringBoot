package com.ProFit.controller.transactions;

import com.ProFit.model.bean.transactionBean.UserTransactionBean;
import com.ProFit.service.transactionService.UserTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/userTransactions")
public class UserTransactionController {

    @Autowired
    private UserTransactionService transactionService;

    @GetMapping
    public String viewTransactions(Model model) {
        return "transactionVIEW/userTransactions";
    }

    @GetMapping("/data")
    @ResponseBody
    public List<UserTransactionBean> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/search")
    @ResponseBody
    public List<UserTransactionBean> filterTransactions(
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) String transactionType,
            @RequestParam(required = false) String transactionStatus,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) throws java.text.ParseException {

        // 定義日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime startTimestamp = null;
        LocalDateTime endTimestamp = null;

        // 如果 startDate 和 endDate 不為空，則解析為 LocalDateTime
        if (startDate != null && !startDate.isEmpty()) {
            startTimestamp = LocalDateTime.parse(startDate, formatter);
        }
        if (endDate != null && !endDate.isEmpty()) {
            endTimestamp = LocalDateTime.parse(endDate, formatter);
        }

        // 返回篩選結果
        return transactionService.getTransactionsByFilters(userId, transactionType, transactionStatus, startTimestamp, endTimestamp);
    }



    @PostMapping("/insert")
    public String insertTransaction(@ModelAttribute UserTransactionBean transaction) {
        transaction.setCreatedAt(LocalDateTime.now());
        transactionService.insertTransaction(transaction);
        return "redirect:/userTransactions";
    }

    @PostMapping("/update")
    public String updateTransaction(@ModelAttribute UserTransactionBean transaction) {
        System.out.println("接收到更新請求, 交易ID: " + transaction.getTransactionId());
        System.out.println("更新的交易狀態: " + transaction.getTransactionStatus());

        transactionService.updateTransaction(transaction);
        return "redirect:/userTransactions";
    }


    @PostMapping("/delete")
    public String deleteTransaction(@RequestParam String transactionId) {
        transactionService.deleteTransaction(transactionId);
        return "redirect:/userTransactions";
    }
}
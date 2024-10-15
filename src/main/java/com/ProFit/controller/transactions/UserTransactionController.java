package com.ProFit.controller.transactions;

import com.ProFit.model.dto.transactionDTO.UserTransactionDTO;
import com.ProFit.service.transactionService.UserTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/userTransactions")
public class UserTransactionController {

    @Autowired
    private UserTransactionService transactionService;

    // 顯示交易記錄頁面
    @GetMapping
    public String viewTransactions(Model model) {
        return "transactionVIEW/userTransactions";
    }

    // 獲取所有交易數據，使用 DTO
    @GetMapping("/data")
    @ResponseBody
    public List<UserTransactionDTO> getAllTransactions() {
        return transactionService.getAllTransactions().stream()
                .map(transactionService::convertToDTO)
                .collect(Collectors.toList());
    }

    // 根據條件篩選交易
    @GetMapping("/search")
    @ResponseBody
    public List<UserTransactionDTO> filterTransactions(
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) String transactionType,
            @RequestParam(required = false) String transactionStatus,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime startTimestamp = null;
        LocalDateTime endTimestamp = null;

        if (startDate != null && !startDate.isEmpty()) {
            startTimestamp = LocalDateTime.parse(startDate, formatter);
        }
        if (endDate != null && !endDate.isEmpty()) {
            endTimestamp = LocalDateTime.parse(endDate, formatter);
        }

        return transactionService.getTransactionsByFilters(userId, transactionType, transactionStatus, startTimestamp, endTimestamp)
                .stream()
                .map(transactionService::convertToDTO)
                .collect(Collectors.toList());
    }

    // 新增交易
    @PostMapping("/insert")
    public String insertTransaction(@ModelAttribute UserTransactionDTO transactionDTO) {
        transactionService.insertTransaction(transactionDTO);
        return "redirect:/userTransactions";
    }

    // 更新交易
    @PostMapping("/update")
    public String updateTransaction(@ModelAttribute UserTransactionDTO transactionDTO) {
        transactionService.updateTransaction(transactionDTO);
        return "redirect:/userTransactions";
    }

    // 刪除交易
    @PostMapping("/delete")
    public String deleteTransaction(@RequestParam String transactionId) {
        transactionService.deleteTransaction(transactionId);
        return "redirect:/userTransactions";
    }
}

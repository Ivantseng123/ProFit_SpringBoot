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
    
    @GetMapping("search/{userId}")
    @ResponseBody  // 加上這個標註來返回 JSON 而不是頁面
    public List<UserTransactionDTO> getUserTransactions(@PathVariable("userId") Integer userId) {
        // 根據 userId 查詢該用戶的所有交易
        return transactionService.getTransactionsByFilters(userId, null, null, null, null)
                .stream()
                .map(transactionService::convertToDTO)
                .collect(Collectors.toList());
    }



    // 獲取所有交易數據，使用 DTO
    @GetMapping("/data")
    @ResponseBody
    public List<UserTransactionDTO> getAllTransactions() {
        return transactionService.getAllTransactions().stream()
                .map(transactionService::convertToDTO)  // 將實體轉換為 DTO
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
            @RequestParam(required = false) String endDate) throws java.text.ParseException {

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
                .map(transactionService::convertToDTO)  // 將結果轉換為 DTO
                .collect(Collectors.toList());
    }

    // 新增交易
    @PostMapping("/insert")
    public String insertTransaction(@ModelAttribute UserTransactionDTO transactionDTO) {
        transactionService.insertTransaction(transactionService.convertToEntity(transactionDTO));  // 將 DTO 轉換為實體
        return "redirect:/userTransactions";
    }

    // 更新交易
    @PostMapping("/update")
    public String updateTransaction(@ModelAttribute UserTransactionDTO transactionDTO) {
        transactionService.updateTransaction(transactionService.convertToEntity(transactionDTO));  // 將 DTO 轉換為實體
        return "redirect:/userTransactions";
    }

    // 刪除交易
    @PostMapping("/delete")
    @ResponseBody
    public boolean deleteTransaction(@RequestParam String transactionId) {
        transactionService.deleteTransaction(transactionId);
        return true;
    }
}
package com.ProFit.controller.transactions.backend;

import com.ProFit.model.dto.usersDTO.UsersDTO;
import com.ProFit.model.dto.transactionDTO.UserTransactionDTO;
import com.ProFit.service.transactionService.UserTransactionService;
import com.ProFit.service.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class AllTransactionController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserTransactionService transactionService;

    // 顯示交易管理頁面
    @GetMapping("/allTransactions")
    public String showAllTransactions(Model model) {
        // 獲取用戶信息
        List<UsersDTO> users = userService.getAllUserInfo()
                                          .stream()
                                          .map(UsersDTO::new)
                                          .collect(Collectors.toList());
        model.addAttribute("users", users);

        // 獲取訂單類型的統計信息
        Map<String, Object> orderStatistics = transactionService.getOrderStatistics();

        // 確保所有收入值非空，並賦予預設值0
        double courseOrderIncome = (double) orderStatistics.getOrDefault("courseOrderIncome", 0.0);
        double jobOrderIncome = (double) orderStatistics.getOrDefault("jobOrderIncome", 0.0);
        double eventOrderIncome = (double) orderStatistics.getOrDefault("eventOrderIncome", 0.0);
        double serviceOrderIncome = (double) orderStatistics.getOrDefault("serviceOrderIncome", 0.0); 
        double totalIncome = courseOrderIncome + jobOrderIncome + eventOrderIncome + serviceOrderIncome;

        // 防止 totalIncome 為零的情況，避免百分比計算出錯
        if (totalIncome == 0) {
            totalIncome = 1;  // 設置為 1 避免除以 0
        }

        // 計算並格式化各類收入百分比
        String formattedCoursePercentage = String.format("%.2f", (courseOrderIncome / totalIncome) * 100);
        String formattedJobPercentage = String.format("%.2f", (jobOrderIncome / totalIncome) * 100);
        String formattedEventPercentage = String.format("%.2f", (eventOrderIncome / totalIncome) * 100);
        String formattedServicePercentage = String.format("%.2f", (serviceOrderIncome / totalIncome) * 100);

        // 添加數據到 orderStatistics Map
        orderStatistics.put("formattedCoursePercentage", formattedCoursePercentage);
        orderStatistics.put("formattedJobPercentage", formattedJobPercentage);
        orderStatistics.put("formattedEventPercentage", formattedEventPercentage);
        orderStatistics.put("formattedServicePercentage", formattedServicePercentage);

        model.addAttribute("orderStatistics", orderStatistics);

        // 本週放款用戶列表，轉換為 DTO
        List<UserTransactionDTO> releaseTransactions = transactionService.getPendingReleaseUsers()
                .stream()
                .map(UserTransactionDTO::new)
                .collect(Collectors.toList());
        model.addAttribute("releaseUsers", releaseTransactions);

        return "transactionVIEW/backend/allTransactions"; 
    }


    
    @PostMapping("/releaseTransactions")
    @ResponseBody
    public String releaseTransactions() {
        // 更新所有待處理的取款交易狀態為已完成
        transactionService.updatePendingReleaseTransactions();
        return "success";
    } 
}

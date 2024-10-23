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

        // 格式化各類收入百分比
        double courseOrderIncome = (double) orderStatistics.get("courseOrderIncome");
        double jobOrderIncome = (double) orderStatistics.get("jobOrderIncome");
        double eventOrderIncome = (double) orderStatistics.get("eventOrderIncome");
        double totalIncome = (double) orderStatistics.get("totalIncome");

        String formattedCoursePercentage = String.format("%.2f", (courseOrderIncome / totalIncome) * 100);
        String formattedJobPercentage = String.format("%.2f", (jobOrderIncome / totalIncome) * 100);
        String formattedEventPercentage = String.format("%.2f", (eventOrderIncome / totalIncome) * 100);

        orderStatistics.put("formattedCoursePercentage", formattedCoursePercentage);
        orderStatistics.put("formattedJobPercentage", formattedJobPercentage);
        orderStatistics.put("formattedEventPercentage", formattedEventPercentage);

        model.addAttribute("orderStatistics", orderStatistics);

        // 本週放款用戶列表，將交易轉換為 DTO
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

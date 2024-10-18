package com.ProFit.controller.transactions;

import com.ProFit.model.dto.usersDTO.UsersDTO;
import com.ProFit.service.transactionService.UserTransactionService;
import com.ProFit.service.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class AllTransactionController {

	@Autowired
    private UserTransactionService transactionService;
	
    @Autowired
    private UserService userService;

    // 顯示交易管理頁面
    @GetMapping("/allTransactions")
    public String showAllTransactions(Model model) {
        // 使用 getAllUserInfo() 而不是 getAllUsers()
        List<UsersDTO> users = userService.getAllUserInfo()
                                          .stream()
                                          .map(user -> new UsersDTO(user))  // 將 Users 轉換為 UsersDTO
                                          .collect(Collectors.toList());

        // 將 DTO 列表傳遞給前端
        model.addAttribute("users", users);

        // 返回模板頁面
        return "transactionVIEW/allTransactions";  // 確保這裡對應你的 Thymeleaf 模板路徑
    }
    
 // 獲取每月的 payment 交易收入
    @GetMapping("/ProFit/orders/monthlyIncome")
    @ResponseBody
    public Map<String, Double> getMonthlyPaymentIncome() {
        return transactionService.getMonthlyPaymentIncome();
    }

}

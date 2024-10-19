package com.ProFit.controller.transactions.backend;

import com.ProFit.model.dto.usersDTO.UsersDTO;
import com.ProFit.service.transactionService.UserTransactionService;
import com.ProFit.service.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AllTransactionController {

	
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
        return "transactionVIEW/backend/allTransactions";  // 確保這裡對應你的 Thymeleaf 模板路徑
    }
    
}

package com.ProFit.controller.transactions.frontend;

import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.service.userService.UserService;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WalletController {

    @Autowired
    private UserService userService;

    // 映射到顯示錢包頁面的路徑
    @GetMapping("/wallet")
    public String showWalletPage(HttpSession session, Model model) {
        // 從 session 中獲取 userId
        Integer userId = (Integer) session.getAttribute("userId");

        // 如果 session 中沒有 userId，則重定向到登入頁面
        if (userId == null) {
            return "redirect:/login";
        }

        // 根據 userId 獲取用戶資料
        Users user = userService.getUserInfoByID(userId);
        
        // 獲取使用者的餘額
        if (user != null) {
            Integer userBalance = user.getUserBalance();
            model.addAttribute("userBalance", userBalance);
        } else {
            model.addAttribute("userBalance", 0); // 若找不到用戶，設置餘額為 0
        }

        return "transactionVIEW/frontend/wallet"; // 返回視圖
    }
}

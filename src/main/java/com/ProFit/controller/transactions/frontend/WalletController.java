package com.ProFit.controller.transactions.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WalletController {

    // 映射到顯示錢包頁面的路徑
    @GetMapping("/wallet")
    public String showWalletPage() {
        // 返回 Thymeleaf 模板的名稱 (wallet.html)
        return "transactionVIEW/frontend/wallet";
    }
}

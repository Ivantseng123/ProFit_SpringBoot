package com.ProFit.controller.homepage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Homepage {

    @GetMapping("/homepage")
    public String homepage() {
        return "model/frontend/homepage";
    }

}

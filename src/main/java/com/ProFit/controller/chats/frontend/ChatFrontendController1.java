package com.ProFit.controller.chats.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/c/chat")
public class ChatFrontendController1 {

    @GetMapping("")
    public String getMethodName() {
        return "chatVIEW/frontend/chatRoomVIEW";
    }
    
}

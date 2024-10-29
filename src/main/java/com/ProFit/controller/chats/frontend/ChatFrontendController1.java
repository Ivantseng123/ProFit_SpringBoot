package com.ProFit.controller.chats.frontend;

import java.util.*;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ProFit.model.dto.chatsDTO.ChatDTO;
import com.ProFit.model.dto.chatsDTO.ChatUserDTO;
import com.ProFit.model.dto.chatsDTO.MessageDTO;
import com.ProFit.model.dto.usersDTO.UsersDTO;
import com.ProFit.service.chatService.ChatService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/c/chat")
public class ChatFrontendController1 {

    @Autowired
    private ChatService chatService;

    @GetMapping("")
    public String getMethodName(HttpSession session, Model model) {

        UsersDTO currentUser = (UsersDTO) session.getAttribute("CurrentUser");

        if (currentUser == null) {
            // 未登入，重定向到登入頁面
            return "redirect:/user/profile";
        }

        // 將用戶信息添加到模型中
        model.addAttribute("currentUser", currentUser);
        return "chatVIEW/frontend/chatRoomVIEW";
    }

    /**
     * 獲取聊天用戶列表
     */
    @GetMapping("/api/users")
    @ResponseBody
    public ResponseEntity<List<ChatUserDTO>> getChatUsers(HttpSession session) {
        UsersDTO currentUser = (UsersDTO) session.getAttribute("CurrentUser");
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        List<ChatUserDTO> users = chatService.getCurrentUserChatUserList(currentUser.getUserId());
        return ResponseEntity.ok(users);
    }

    /**
     * 創建或獲取聊天室
     */
    @PostMapping("/api/create")
    @ResponseBody
    public ResponseEntity<ChatDTO> createChat(@RequestBody Map<String, Integer> request, 
                                            HttpSession session) {
        UsersDTO currentUser = (UsersDTO) session.getAttribute("CurrentUser");
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Integer userId1 = request.get("userId1");
        Integer userId2 = request.get("userId2");
        
        try {
            ChatDTO chat = chatService.createOrGetChat(null, userId1, userId2);
            return ResponseEntity.ok(chat);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 獲取聊天記錄
     */
    @GetMapping("/api/{chatId}/messages")
    @ResponseBody
    public ResponseEntity<List<MessageDTO>> getChatMessages(@PathVariable Integer chatId,
                                                          HttpSession session) {
        UsersDTO currentUser = (UsersDTO) session.getAttribute("CurrentUser");
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        try {
            List<MessageDTO> messages = chatService.getChatMessages(chatId);
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * 發送消息
     */
    @PostMapping("/api/{chatId}/messages")
    @ResponseBody
    public ResponseEntity<MessageDTO> sendMessage(@PathVariable Integer chatId,
                                                @RequestBody MessageDTO messageDTO,
                                                HttpSession session) {
        UsersDTO currentUser = (UsersDTO) session.getAttribute("CurrentUser");
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        try {
            messageDTO.setChatId(chatId);
            messageDTO.setSenderId(currentUser.getUserId());
            MessageDTO savedMessage = chatService.saveMessage(messageDTO);
            return ResponseEntity.ok(savedMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

package com.ProFit.controller.chats.frontend;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
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
import com.ProFit.model.dto.servicesDTO.ServicesDTO;
import com.ProFit.model.dto.usersDTO.UsersDTO;
import com.ProFit.service.chatService.ChatService;
import com.ProFit.service.serviceService.ServiceService;

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

    // 案主從服務頁面建立(/查)聊天室 (currentUserId, freelancerId, serviceId)
    @GetMapping("/add")
    public String createOrGetChatRoom(HttpSession session, @RequestParam Integer serviceId,
            @RequestParam Integer freelancerId, Model model) {

        UsersDTO currentUser = (UsersDTO) session.getAttribute("CurrentUser");

        System.out.println(currentUser);

        if (currentUser == null) {
            // 未登入，重定向到登入頁面
            return "redirect:/user/profile";
        }
        // 將用戶信息添加到模型中
        model.addAttribute("currentUser", currentUser);

        try {
            chatService.createOrGetChat(serviceId, freelancerId, currentUser.getUserId());
            return "chatVIEW/frontend/chatRoomVIEW";
        } catch (Exception e) {
            System.err.println(e);
            return "chatVIEW/frontend/chatRoomVIEW";
        }

    }

    /**
     * 獲取聊天用戶列表 (我是案主時)
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
     * 獲取聊天用戶列表 (我是接案人時, 從我已有的服務獲取聊天用戶)
     */
    @GetMapping("/api/users/service/{serviceId}")
    @ResponseBody
    public ResponseEntity<List<ChatUserDTO>> getChatUsersFromService(HttpSession session,
            @PathVariable Integer serviceId) {
        UsersDTO currentUser = (UsersDTO) session.getAttribute("CurrentUser");
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<ChatUserDTO> users = chatService.getCurrentFreelancerChatUserList(currentUser.getUserId(), serviceId);
        return ResponseEntity.ok(users);
    }

    /**
     * 獲取用戶的服務列表
     * 
     * @param userId  要查詢的用戶ID
     * @param session HTTPsession
     * @return 服務列表
     */
    @GetMapping("/api/user/{userId}/services")
    @ResponseBody
    public ResponseEntity<Page<ServicesDTO>> getUserServices(@PathVariable Integer userId,
            HttpSession session) {
        // 檢查當前用戶是否登入
        UsersDTO currentUser = (UsersDTO) session.getAttribute("CurrentUser");
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            // 調用服務層獲取用戶服務列表
            PageRequest pgb = PageRequest.of(0, 20, Sort.Direction.DESC, "serviceUpdateDate");

            Page<ServicesDTO> services = chatService.getUserServices(userId, pgb);
            return ResponseEntity.ok(services);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 獲取最新的聊天室
     */
    @GetMapping("/api/latest")
    @ResponseBody
    public ResponseEntity<Page<ChatDTO>> latestChat(HttpSession session) {
        UsersDTO currentUser = (UsersDTO) session.getAttribute("CurrentUser");
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Integer userId1 = request.get("userId1");
        // Integer userId2 = request.get("userId2");
        // Integer serviceId = request.get("serviceId");

        PageRequest pgb = PageRequest.of(0, 1, Sort.Direction.DESC, "lastMessageAt");

        try {
            Page<ChatDTO> chat = chatService.getCurrentUserChatUserList(currentUser.getUserId(), pgb);
            return ResponseEntity.ok(chat);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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
        Integer serviceId = request.get("serviceId");

        try {
            ChatDTO chat = chatService.createOrGetChat(serviceId, userId1, userId2);
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

package com.ProFit.controller.chats.frontend;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.ProFit.model.dto.chatsDTO.MessageDTO;
import com.ProFit.service.chatService.ChatService;

@Controller
public class ChatWebSocketController {

  @Autowired
  private ChatService chatService;

  /**
   * 處理發送消息的請求
   * 
   * @param messageDTO 消息內容
   * @param principal  當前用戶信息
   * @return 保存後的消息
   */
  @MessageMapping("/chat.sendMessage")
  @SendTo("/topic/messages")
  public MessageDTO sendMessage(@Payload MessageDTO messageDTO,
      Principal principal) {
    return chatService.saveMessage(messageDTO);
  }

  /**
   * 處理發送服務申請相關的消息
   */
  @MessageMapping("/chat.serviceApplication")
  @SendTo("/topic/messages")
  public MessageDTO sendServiceApplication(@Payload MessageDTO messageDTO) {
    // 添加服務申請相關的標記
    messageDTO.setContent("[SERVICE_APPLICATION]" + messageDTO.getContent());
    return chatService.saveMessage(messageDTO);
  }

  /**
   * 處理用戶加入聊天室的請求
   * 
   * @param chatId    聊天室ID
   * @param principal 當前用戶信息
   * @return 加入提示訊息
   */
  @MessageMapping("/chat.join")
  @SendTo("/topic/chat/{chatId}")
  public String joinChat(@DestinationVariable Integer chatId,
      Principal principal) {
    return principal.getName() + " joined the chat";
  }

  /**
   * 獲取特定聊天室的消息歷史
   */
  @MessageMapping("/chat.history")
  public List<MessageDTO> getChatHistory(@Payload Integer chatId) {
    return chatService.getChatMessages(chatId);
  }

}

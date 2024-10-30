package com.ProFit.controller.chats.frontend;

import java.security.Principal;
import java.util.List;
import java.util.Date;
import java.time.LocalDateTime;

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
  @MessageMapping("/chat.sendMessage/{chatId}")
  @SendTo("/topic/chat/{chatId}") // 修改：改為發送到特定聊天室
  public MessageDTO sendMessage(@Payload MessageDTO messageDTO,
      @DestinationVariable Integer chatId,
      Principal principal) {
    // 設置消息的聊天室ID
    messageDTO.setChatId(chatId);
    return chatService.saveMessage(messageDTO);
  }

  /**
   * 處理發送服務申請相關的消息
   */
  @MessageMapping("/chat.serviceApplication/{chatId}")
  @SendTo("/topic/chat/{chatId}") // 修改：改為發送到特定聊天室
  public MessageDTO sendServiceApplication(@Payload MessageDTO messageDTO,
      @DestinationVariable Integer chatId) {
    messageDTO.setContent("[SERVICE_APPLICATION]" + messageDTO.getContent());
    messageDTO.setChatId(chatId);
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
  public MessageDTO joinChat(@DestinationVariable Integer chatId,
      Principal principal) {
    MessageDTO joinMessage = new MessageDTO();
    joinMessage.setChatId(chatId);
    joinMessage.setContent(principal.getName() + " 加入聊天室");
    joinMessage.setSentAt(LocalDateTime.now());
    return joinMessage;
  }

  /**
   * 獲取特定聊天室的消息歷史
   */
  @MessageMapping("/chat.history")
  public List<MessageDTO> getChatHistory(@Payload Integer chatId) {
    return chatService.getChatMessages(chatId);
  }

}

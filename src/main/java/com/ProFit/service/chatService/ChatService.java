package com.ProFit.service.chatService;

import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ProFit.model.bean.chatsBean.ChatBean;
import com.ProFit.model.bean.chatsBean.MessageBean;
import com.ProFit.model.bean.servicesBean.ServiceBean;
import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.dao.chatsCRUD.ChatRepository;
import com.ProFit.model.dao.chatsCRUD.MessageRepository;
import com.ProFit.model.dao.servicesCRUD.ServiceRepository;
import com.ProFit.model.dto.chatsDTO.ChatDTO;
import com.ProFit.model.dto.chatsDTO.ChatUserDTO;
import com.ProFit.model.dto.chatsDTO.MessageDTO;
import com.ProFit.model.dto.servicesDTO.ServicesDTO;
import com.ProFit.model.dto.usersDTO.UsersDTO;

@Service
public class ChatService {

  @Autowired
  private ChatRepository chatRepository;

  @Autowired
  private MessageRepository messageRepository;

  @Autowired
  private ServiceRepository serviceRepository;

  /**
   * 保存新消息並更新聊天室資訊
   * 
   * @param messageDTO 消息資料傳輸物件
   * @return 保存後的消息DTO
   */
  @Transactional // 確保事務的一致性
  public MessageDTO saveMessage(MessageDTO messageDTO) {
    // 創建新的消息實體
    MessageBean messageBean = new MessageBean();
    messageBean.setChatId(messageDTO.getChatId());
    messageBean.setSenderId(messageDTO.getSenderId());
    messageBean.setContent(messageDTO.getContent());
    messageBean.setSentAt(LocalDateTime.now()); // 設置發送時間為當前時間
    messageBean.setRead(false); // 初始設置為未讀

    // 保存消息
    MessageBean savedMessage = messageRepository.save(messageBean);

    // 更新聊天室的最後消息時間
    ChatBean chat = chatRepository.findById(messageDTO.getChatId())
        .orElseThrow(() -> new RuntimeException("Chat not found"));
    chat.setLastMessageAt(LocalDateTime.now());
    chatRepository.save(chat);

    return MessageDTO.fromEntity(savedMessage);
  }

  /**
   * 獲取特定聊天室的所有消息
   * 
   * @param chatId 聊天室ID
   * @return 消息列表
   */
  public List<MessageDTO> getChatMessages(Integer chatId) {
    ChatBean chat = chatRepository.findById(chatId)
        .orElseThrow(() -> new RuntimeException("Chat not found"));

    return chat.getMessages().stream()
        .map(MessageDTO::fromEntity)
        .collect(Collectors.toList());
  }

  /**
   * 創建新的聊天室或獲取現有聊天室
   * 
   * @param serviceId    服務ID
   * @param freelancerId 接案者ID
   * @param caseOwnerId  案主ID
   * @return 聊天室DTO
   */
  @Transactional
  public ChatDTO createOrGetChat(Integer serviceId, Integer freelancerId, Integer caseOwnerId) {
    // 查找現有聊天室
    ChatBean chat = chatRepository.findByUserId1AndUserId2AndServiceId(
        freelancerId, caseOwnerId, serviceId);

    // 如果不存在則創建新的
    if (chat == null) {
      chat = new ChatBean(serviceId, freelancerId, caseOwnerId,
          LocalDateTime.now(), LocalDateTime.now(), 1);
      chat = chatRepository.save(chat);
    }
    // System.out.println(chat);

    return ChatDTO.fromEntity(chat);
  }

  // 查詢 caseOwner 有的聊天室
  public Page<ChatDTO> getCurrentUserChatUserList(Integer caseOwnerId, Pageable pageable) {
    Page<ChatBean> byUserId2 = chatRepository.findByUserId2(caseOwnerId, pageable);

    Page<ChatDTO> chatDTOPage = byUserId2.map(ChatDTO::fromEntity); // 使用方法引用進行映射

    return chatDTOPage;
  }

  // 查 跟caseOwner 有聊天室的 user(freelancer)
  public List<ChatUserDTO> getCurrentUserChatUserList(Integer caseOwnerId) {
    List<ChatUserDTO> usersByUserId2 = chatRepository.findUsersByUserId2(caseOwnerId);

    return usersByUserId2;
  }

  // 返回一個user有的服務
  public Page<ServicesDTO> getUserServices(Integer userId, Pageable pageable) {
    Page<ServiceBean> services = serviceRepository.findByUserId(userId, pageable);
    // 將實體轉換為DTO
    return services.map(ServicesDTO::fromEntity);
  }

}

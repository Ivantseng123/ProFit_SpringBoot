package com.ProFit.model.dto.chatsDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Comparator;
import java.util.Collections;
import java.util.stream.Collectors;

import com.ProFit.model.bean.chatsBean.ChatBean;
import com.ProFit.model.bean.servicesBean.ServiceBean;
import com.ProFit.model.dto.servicesDTO.ServicesDTO;
import com.ProFit.model.dto.usersDTO.UsersDTO;

public class ChatDTO {

  private Integer chatId;
  private Integer serviceId;
  private Integer userId1;
  private Integer userId2;
  private LocalDateTime createAt;
  private LocalDateTime lastMessageAt;
  private Integer status;
  //
  private UsersDTO freelancer; // userId1
  private UsersDTO caseowner; // userId2
  private ServicesDTO service;
  private List<MessageDTO> messages;

  // 无参构造函数
  public ChatDTO() {
  }

  // 全参数构造函数
  public ChatDTO(Integer chatId, Integer serviceId, Integer userId1, Integer userId2, LocalDateTime createAt,
      LocalDateTime lastMessageAt, Integer status, UsersDTO freelancer, UsersDTO caseowner, ServicesDTO service,
      List<MessageDTO> messages) {
    this.chatId = chatId;
    this.serviceId = serviceId;
    this.userId1 = userId1;
    this.userId2 = userId2;
    this.createAt = createAt;
    this.lastMessageAt = lastMessageAt;
    this.status = status;
    this.freelancer = freelancer;
    this.caseowner = caseowner;
    this.service = service;
    this.messages = messages;
  }

  // 静态工厂方法
  public static ChatDTO fromEntity(ChatBean chatBean) {
    if (chatBean == null)
      return null;

    ChatDTO chatDTO = new ChatDTO();
    chatDTO.setChatId(chatBean.getChatId());
    chatDTO.setServiceId(chatBean.getServiceId());
    chatDTO.setUserId1(chatBean.getUserId1());
    chatDTO.setUserId2(chatBean.getUserId2());
    chatDTO.setCreateAt(chatBean.getCreateAt());
    chatDTO.setLastMessageAt(chatBean.getLastMessageAt());
    chatDTO.setStatus(chatBean.getStatus());

    //
    if (chatBean.getUserId1() != null) {
      chatDTO.setFreelancer(new UsersDTO(chatBean.getUser1()));
    }
    if (chatBean.getUserId2() != null) {
      chatDTO.setCaseowner(new UsersDTO(chatBean.getUser2()));
    }
    if (chatBean.getService() != null) {
      chatDTO.setService(ServicesDTO.fromEntity(chatBean.getService()));
    }
    if (chatBean.getMessages() != null) {
      chatDTO.setMessages(chatBean.getMessages().stream().map(MessageDTO::fromEntity).collect(Collectors.toList()));
    }

    return chatDTO;
  }

  /**
   * 僅轉換聊天室的基本資訊，不包含訊息列表
   * 
   * @param chatBean 聊天室實體
   * @return ChatDTO 只包含基本資訊的DTO
   */
  public static ChatDTO fromEntityWithoutMessages(ChatBean chatBean) {
    if (chatBean == null)
      return null;

    ChatDTO chatDTO = new ChatDTO();
    chatDTO.setChatId(chatBean.getChatId());
    chatDTO.setServiceId(chatBean.getServiceId());
    chatDTO.setUserId1(chatBean.getUserId1());
    chatDTO.setUserId2(chatBean.getUserId2());
    chatDTO.setCreateAt(chatBean.getCreateAt());
    chatDTO.setLastMessageAt(chatBean.getLastMessageAt());
    chatDTO.setStatus(chatBean.getStatus());

    //
    if (chatBean.getUserId1() != null) {
      chatDTO.setFreelancer(new UsersDTO(chatBean.getUser1()));
    }
    if (chatBean.getUserId2() != null) {
      chatDTO.setCaseowner(new UsersDTO(chatBean.getUser2()));
    }
    if (chatBean.getService() != null) {
      chatDTO.setService(ServicesDTO.fromEntity(chatBean.getService()));
    }

    return chatDTO;
  }

  /**
   * 轉換聊天室資訊，只包含最新的N條訊息
   * 
   * @param chatBean 聊天室實體
   * @param limit    要載入的訊息數量
   * @return ChatDTO 包含最新N條訊息的DTO
   */
  public static ChatDTO fromEntityWithLatestMessages(ChatBean chatBean, int limit) {
    if (chatBean == null)
      return null;

    ChatDTO chatDTO = new ChatDTO();

    List<MessageDTO> latestMessages = null;
    if (chatBean.getMessages() != null) {
      latestMessages = chatBean.getMessages().stream()
          .sorted((m1, m2) -> m2.getSentAt().compareTo(m1.getSentAt())) // 按時間降序排序
          .limit(limit) // 只取前N條
          .map(MessageDTO::fromEntity)
          .collect(Collectors.toList());
    }
    chatDTO.setMessages(latestMessages);

    chatDTO.setChatId(chatBean.getChatId());
    chatDTO.setServiceId(chatBean.getServiceId());
    chatDTO.setUserId1(chatBean.getUserId1());
    chatDTO.setUserId2(chatBean.getUserId2());
    chatDTO.setCreateAt(chatBean.getCreateAt());
    chatDTO.setLastMessageAt(chatBean.getLastMessageAt());
    chatDTO.setStatus(chatBean.getStatus());

    //
    if (chatBean.getUserId1() != null) {
      chatDTO.setFreelancer(new UsersDTO(chatBean.getUser1()));
    }
    if (chatBean.getUserId2() != null) {
      chatDTO.setCaseowner(new UsersDTO(chatBean.getUser2()));
    }
    if (chatBean.getService() != null) {
      chatDTO.setService(ServicesDTO.fromEntity(chatBean.getService()));
    }

    return chatDTO;
  }

  /**
   * 分頁載入指定聊天室的訊息
   * 
   * @param chatBean 聊天室實體
   * @param page     頁碼（從0開始）
   * @param size     每頁大小
   * @return 分頁後的訊息列表
   */
  public static List<MessageDTO> fromEntityPagedMessages(ChatBean chatBean, int page, int size) {
    if (chatBean == null || chatBean.getMessages() == null) {
      return Collections.emptyList();
    }

    return chatBean.getMessages().stream()
        .sorted((m1, m2) -> m2.getSentAt().compareTo(m1.getSentAt())) // 按時間降序排序
        .skip((long) page * size) // 跳過前面的頁數
        .limit(size) // 取指定數量
        .map(MessageDTO::fromEntity)
        .collect(Collectors.toList());
  }

  /**
   * 建議添加一個用於計算總頁數的輔助方法
   */
  public static int calculateTotalPages(ChatBean chatBean, int pageSize) {
    if (chatBean == null || chatBean.getMessages() == null) {
      return 0;
    }
    return (int) Math.ceil((double) chatBean.getMessages().size() / pageSize);
  }

  // Getter 和 Setter 方法
  public Integer getChatId() {
    return chatId;
  }

  public void setChatId(Integer chatId) {
    this.chatId = chatId;
  }

  public Integer getServiceId() {
    return serviceId;
  }

  public void setServiceId(Integer serviceId) {
    this.serviceId = serviceId;
  }

  public Integer getUserId1() {
    return userId1;
  }

  public void setUserId1(Integer userId1) {
    this.userId1 = userId1;
  }

  public Integer getUserId2() {
    return userId2;
  }

  public void setUserId2(Integer userId2) {
    this.userId2 = userId2;
  }

  public LocalDateTime getCreateAt() {
    return createAt;
  }

  public void setCreateAt(LocalDateTime createAt) {
    this.createAt = createAt;
  }

  public LocalDateTime getLastMessageAt() {
    return lastMessageAt;
  }

  public void setLastMessageAt(LocalDateTime lastMessageAt) {
    this.lastMessageAt = lastMessageAt;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public UsersDTO getFreelancer() {
    return freelancer;
  }

  public void setFreelancer(UsersDTO freelancer) {
    this.freelancer = freelancer;
  }

  public UsersDTO getCaseowner() {
    return caseowner;
  }

  public void setCaseowner(UsersDTO caseowner) {
    this.caseowner = caseowner;
  }

  public ServicesDTO getService() {
    return service;
  }

  public void setService(ServicesDTO service) {
    this.service = service;
  }

  public List<MessageDTO> getMessages() {
    return messages;
  }

  public void setMessages(List<MessageDTO> messages) {
    this.messages = messages;
  }

  @Override
  public String toString() {
    return "ChatDTO [chatId=" + chatId + ", serviceId=" + serviceId + ", userId1=" + userId1 + ", userId2=" + userId2
        + ", createAt=" + createAt + ", lastMessageAt=" + lastMessageAt + ", status=" + status + ", service=" + service
        + ", messages=" + messages + "]";
  }

}

package com.ProFit.model.dto.chatsDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.ProFit.model.bean.chatsBean.ChatBean;

public class ChatDTO {

  private Integer chatId;
  private Integer serviceId;
  private Integer userId1;
  private Integer userId2;
  private LocalDateTime createAt;
  private LocalDateTime lastMessageAt;
  private Integer status;
  private List<MessageDTO> messages;

  // 无参构造函数
  public ChatDTO() {
  }

  // 全参数构造函数
  public ChatDTO(Integer chatId, Integer serviceId, Integer userId1, Integer userId2, LocalDateTime createAt,
      LocalDateTime lastMessageAt, Integer status, List<MessageDTO> messages) {
    this.chatId = chatId;
    this.serviceId = serviceId;
    this.userId1 = userId1;
    this.userId2 = userId2;
    this.createAt = createAt;
    this.lastMessageAt = lastMessageAt;
    this.status = status;
    this.messages = messages;
  }

  // 静态工厂方法
  public static ChatDTO fromEntity(ChatBean chatBean) {
    if (chatBean == null)
      return null;

    return new ChatDTO(
        chatBean.getChatId(),
        chatBean.getServiceId(),
        chatBean.getUserId1(),
        chatBean.getUserId2(),
        chatBean.getCreateAt(),
        chatBean.getLastMessageAt(),
        chatBean.getStatus(),
        chatBean.getMessages() != null
            ? chatBean.getMessages().stream().map(MessageDTO::fromEntity).collect(Collectors.toList())
            : null);
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

  public List<MessageDTO> getMessages() {
    return messages;
  }

  public void setMessages(List<MessageDTO> messages) {
    this.messages = messages;
  }

  @Override
  public String toString() {
    return "ChatDTO{" +
        "chatId=" + chatId +
        ", serviceId=" + serviceId +
        ", userId1=" + userId1 +
        ", userId2=" + userId2 +
        ", createAt=" + createAt +
        ", lastMessageAt=" + lastMessageAt +
        ", status=" + status +
        ", messages=" + messages +
        '}';
  }

}

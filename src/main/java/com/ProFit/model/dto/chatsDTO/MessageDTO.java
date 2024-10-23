package com.ProFit.model.dto.chatsDTO;

import java.time.LocalDateTime;

import com.ProFit.model.bean.chatsBean.MessageBean;

public class MessageDTO {

  private Integer messageId;
  private Integer chatId;
  private Integer senderId;
  private String content;
  private LocalDateTime sentAt;
  private boolean isRead;

  // 无参构造函数
  public MessageDTO() {
  }

  // 全参数构造函数
  public MessageDTO(Integer messageId, Integer chatId, Integer senderId, String content, LocalDateTime sentAt,
      boolean isRead) {
    this.messageId = messageId;
    this.chatId = chatId;
    this.senderId = senderId;
    this.content = content;
    this.sentAt = sentAt;
    this.isRead = isRead;
  }

  // 静态工厂方法
  public static MessageDTO fromEntity(MessageBean messageBean) {
    if (messageBean == null)
      return null;

    return new MessageDTO(
        messageBean.getMessageId(),
        messageBean.getChatId(),
        messageBean.getSenderId(),
        messageBean.getContent(),
        messageBean.getSentAt(),
        messageBean.isRead());
  }

  // Getter 和 Setter 方法
  public Integer getMessageId() {
    return messageId;
  }

  public void setMessageId(Integer messageId) {
    this.messageId = messageId;
  }

  public Integer getChatId() {
    return chatId;
  }

  public void setChatId(Integer chatId) {
    this.chatId = chatId;
  }

  public Integer getSenderId() {
    return senderId;
  }

  public void setSenderId(Integer senderId) {
    this.senderId = senderId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public LocalDateTime getSentAt() {
    return sentAt;
  }

  public void setSentAt(LocalDateTime sentAt) {
    this.sentAt = sentAt;
  }

  public boolean isRead() {
    return isRead;
  }

  public void setRead(boolean isRead) {
    this.isRead = isRead;
  }

  @Override
  public String toString() {
    return "MessageDTO{" +
        "messageId=" + messageId +
        ", chatId=" + chatId +
        ", senderId=" + senderId +
        ", content='" + content + '\'' +
        ", sentAt=" + sentAt +
        ", isRead=" + isRead +
        '}';
  }
}

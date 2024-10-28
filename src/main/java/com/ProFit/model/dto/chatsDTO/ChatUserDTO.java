package com.ProFit.model.dto.chatsDTO;

import java.time.LocalDateTime;

public class ChatUserDTO {
  private Integer userId;
  private String userName;
  private String userEmail;
  private String userPictureURL;
  private LocalDateTime lastChatTime;

  public ChatUserDTO() {
    super();
  }

  public ChatUserDTO(Integer userId, String userName, String userEmail, String userPictureURL,
      LocalDateTime lastChatTime) {
    this.userId = userId;
    this.userName = userName;
    this.userEmail = userEmail;
    this.userPictureURL = userPictureURL;
    this.lastChatTime = lastChatTime;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  public String getUserPictureURL() {
    return userPictureURL;
  }

  public void setUserPictureURL(String userPictureURL) {
    this.userPictureURL = userPictureURL;
  }

  public LocalDateTime getLastChatTime() {
    return lastChatTime;
  }

  public void setLastChatTime(LocalDateTime lastChatTime) {
    this.lastChatTime = lastChatTime;
  }

}

package com.ProFit.model.dto.servicesDTO;

import java.time.LocalDateTime;

import com.ProFit.model.bean.servicesBean.ServiceApplicationBean;
import com.ProFit.model.dto.chatsDTO.ChatDTO;
import com.ProFit.model.dto.usersDTO.UsersDTO;

public class ServiceApplicationsDTO {

  private Integer serviceApplicationId;
  private Integer caseownerId;
  private Integer freelancerId;
  private Integer serviceId;
  private Integer chatId;
  private String serviceApplicationTitle;
  private String serviceApplicationSubitem;
  private Integer serviceApplicationPrice;
  private Integer serviceApplicationAmount;
  private String serviceApplicationUnit;
  private String serviceApplicationContent;
  private String appendixUrl;
  private Integer status;
  private String serviceApplicationMission;
  private LocalDateTime serviceApplicationDoneDate;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  // 關聯實體的dto
  private UsersDTO caseowner;
  private UsersDTO freelancer;
  private ServicesDTO service;

  public ServiceApplicationsDTO() {
  }

  // 全参数构造函数
  public ServiceApplicationsDTO(Integer serviceApplicationId, Integer caseownerId, Integer freelancerId,
      Integer serviceId, Integer chatId, String serviceApplicationTitle,
      String serviceApplicationSubitem, Integer serviceApplicationPrice,
      Integer serviceApplicationAmount, String serviceApplicationUnit,
      String serviceApplicationContent, String appendixUrl, Integer status,
      String serviceApplicationMission, LocalDateTime serviceApplicationDoneDate,
      LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.serviceApplicationId = serviceApplicationId;
    this.caseownerId = caseownerId;
    this.freelancerId = freelancerId;
    this.serviceId = serviceId;
    this.chatId = chatId;
    this.serviceApplicationTitle = serviceApplicationTitle;
    this.serviceApplicationSubitem = serviceApplicationSubitem;
    this.serviceApplicationPrice = serviceApplicationPrice;
    this.serviceApplicationAmount = serviceApplicationAmount;
    this.serviceApplicationUnit = serviceApplicationUnit;
    this.serviceApplicationContent = serviceApplicationContent;
    this.appendixUrl = appendixUrl;
    this.status = status;
    this.serviceApplicationMission = serviceApplicationMission;
    this.serviceApplicationDoneDate = serviceApplicationDoneDate;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  // 静态工厂方法
  public static ServiceApplicationsDTO fromEntity(ServiceApplicationBean serviceApplicationBean) {
    if (serviceApplicationBean == null)
      return null;

    ServiceApplicationsDTO dto = new ServiceApplicationsDTO();
    dto.setServiceApplicationId(serviceApplicationBean.getServiceApplicationId());
    dto.setCaseownerId(serviceApplicationBean.getCaseownerId());
    dto.setFreelancerId(serviceApplicationBean.getFreelancerId());
    dto.setServiceId(serviceApplicationBean.getServiceId());
    dto.setChatId(serviceApplicationBean.getChatId());
    dto.setServiceApplicationTitle(serviceApplicationBean.getServiceApplicationTitle());
    dto.setServiceApplicationSubitem(serviceApplicationBean.getServiceApplicationSubitem());
    dto.setServiceApplicationPrice(serviceApplicationBean.getServiceApplicationPrice());
    dto.setServiceApplicationAmount(serviceApplicationBean.getServiceApplicationAmount());
    dto.setServiceApplicationUnit(serviceApplicationBean.getServiceApplicationUnit());
    dto.setServiceApplicationContent(serviceApplicationBean.getServiceApplicationContent());
    dto.setAppendixUrl(serviceApplicationBean.getAppendixUrl());
    dto.setStatus(serviceApplicationBean.getStatus());
    dto.setServiceApplicationMission(serviceApplicationBean.getServiceApplicationMission());
    dto.setServiceApplicationDoneDate(serviceApplicationBean.getServiceApplicationDoneDate());
    dto.setCreatedAt(serviceApplicationBean.getCreatedAt());
    dto.setUpdatedAt(serviceApplicationBean.getUpdatedAt());

    if (serviceApplicationBean.getCaseowner() != null) {
      dto.setCaseowner(new UsersDTO(serviceApplicationBean.getCaseowner()));
    }
    if (serviceApplicationBean.getFreelancer() != null) {
      dto.setFreelancer(new UsersDTO(serviceApplicationBean.getFreelancer()));
    }
    if (serviceApplicationBean.getService() != null) {
      dto.setService(ServicesDTO.fromEntity(serviceApplicationBean.getService()));
    }

    return dto;
  }

  public Integer getServiceApplicationId() {
    return serviceApplicationId;
  }

  public void setServiceApplicationId(Integer serviceApplicationId) {
    this.serviceApplicationId = serviceApplicationId;
  }

  public Integer getCaseownerId() {
    return caseownerId;
  }

  public void setCaseownerId(Integer caseownerId) {
    this.caseownerId = caseownerId;
  }

  public Integer getFreelancerId() {
    return freelancerId;
  }

  public void setFreelancerId(Integer freelancerId) {
    this.freelancerId = freelancerId;
  }

  public Integer getServiceId() {
    return serviceId;
  }

  public void setServiceId(Integer serviceId) {
    this.serviceId = serviceId;
  }

  public Integer getChatId() {
    return chatId;
  }

  public void setChatId(Integer chatId) {
    this.chatId = chatId;
  }

  public String getServiceApplicationTitle() {
    return serviceApplicationTitle;
  }

  public void setServiceApplicationTitle(String serviceApplicationTitle) {
    this.serviceApplicationTitle = serviceApplicationTitle;
  }

  public String getServiceApplicationSubitem() {
    return serviceApplicationSubitem;
  }

  public void setServiceApplicationSubitem(String serviceApplicationSubitem) {
    this.serviceApplicationSubitem = serviceApplicationSubitem;
  }

  public Integer getServiceApplicationPrice() {
    return serviceApplicationPrice;
  }

  public void setServiceApplicationPrice(Integer serviceApplicationPrice) {
    this.serviceApplicationPrice = serviceApplicationPrice;
  }

  public Integer getServiceApplicationAmount() {
    return serviceApplicationAmount;
  }

  public void setServiceApplicationAmount(Integer serviceApplicationAmount) {
    this.serviceApplicationAmount = serviceApplicationAmount;
  }

  public String getServiceApplicationUnit() {
    return serviceApplicationUnit;
  }

  public void setServiceApplicationUnit(String serviceApplicationUnit) {
    this.serviceApplicationUnit = serviceApplicationUnit;
  }

  public String getServiceApplicationContent() {
    return serviceApplicationContent;
  }

  public void setServiceApplicationContent(String serviceApplicationContent) {
    this.serviceApplicationContent = serviceApplicationContent;
  }

  public String getAppendixUrl() {
    return appendixUrl;
  }

  public void setAppendixUrl(String appendixUrl) {
    this.appendixUrl = appendixUrl;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getServiceApplicationMission() {
    return serviceApplicationMission;
  }

  public void setServiceApplicationMission(String serviceApplicationMission) {
    this.serviceApplicationMission = serviceApplicationMission;
  }

  public LocalDateTime getServiceApplicationDoneDate() {
    return serviceApplicationDoneDate;
  }

  public void setServiceApplicationDoneDate(LocalDateTime serviceApplicationDoneDate) {
    this.serviceApplicationDoneDate = serviceApplicationDoneDate;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public UsersDTO getCaseowner() {
    return caseowner;
  }

  public void setCaseowner(UsersDTO caseowner) {
    this.caseowner = caseowner;
  }

  public UsersDTO getFreelancer() {
    return freelancer;
  }

  public void setFreelancer(UsersDTO freelancer) {
    this.freelancer = freelancer;
  }

  public ServicesDTO getService() {
    return service;
  }

  public void setService(ServicesDTO service) {
    this.service = service;
  }

  @Override
  public String toString() {
    return "ServiceApplicationsDTO{" +
        "serviceApplicationId=" + serviceApplicationId +
        ", caseownerId=" + caseownerId +
        ", freelancerId=" + freelancerId +
        ", serviceId=" + serviceId +
        ", chatId=" + chatId +
        ", serviceApplicationTitle='" + serviceApplicationTitle + '\'' +
        ", serviceApplicationSubitem='" + serviceApplicationSubitem + '\'' +
        ", serviceApplicationPrice=" + serviceApplicationPrice +
        ", serviceApplicationAmount=" + serviceApplicationAmount +
        ", serviceApplicationUnit='" + serviceApplicationUnit + '\'' +
        ", serviceApplicationContent='" + serviceApplicationContent + '\'' +
        ", appendixUrl='" + appendixUrl + '\'' +
        ", status=" + status +
        ", serviceApplicationMission='" + serviceApplicationMission + '\'' +
        ", serviceApplicationDoneDate=" + serviceApplicationDoneDate +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        '}';
  }
}

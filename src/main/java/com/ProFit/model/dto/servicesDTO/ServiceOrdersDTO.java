package com.ProFit.model.dto.servicesDTO;

import java.time.LocalDateTime;

import com.ProFit.model.bean.servicesBean.ServiceOrderBean;

public class ServiceOrdersDTO {

  private String serviceOrderId;
  private Integer serviceApplicationId;
  private Integer serviceOrderPayById;
  private LocalDateTime serviceOrderDate;
  private String status;
  private String serviceOrderNote;
  private String paymentMethod;
  private Integer taxId;
  private Integer serviceOrderAmount;
  private LocalDateTime createdAt;

  // 无参构造函数
  public ServiceOrdersDTO() {
  }

  // 全参数构造函数
  public ServiceOrdersDTO(String serviceOrderId, Integer serviceApplicationId, Integer serviceOrderPayById,
      LocalDateTime serviceOrderDate, String status, String serviceOrderNote, String paymentMethod, Integer taxId,
      Integer serviceOrderAmount, LocalDateTime createdAt) {
    this.serviceOrderId = serviceOrderId;
    this.serviceApplicationId = serviceApplicationId;
    this.serviceOrderPayById = serviceOrderPayById;
    this.serviceOrderDate = serviceOrderDate;
    this.status = status;
    this.serviceOrderNote = serviceOrderNote;
    this.paymentMethod = paymentMethod;
    this.taxId = taxId;
    this.serviceOrderAmount = serviceOrderAmount;
    this.createdAt = createdAt;
  }
  

  //添加訂單詳情裡面要抓取的欄位資料
  public ServiceOrdersDTO(ServiceOrderBean order) {
	    if (order != null) {
	        this.serviceOrderId = order.getServiceOrderId();
	        this.serviceApplicationId = order.getServiceApplicationId();
	        this.serviceOrderPayById = order.getServiceOrderPayById();
	        this.serviceOrderDate = order.getServiceOrderDate();
	        this.status = order.getStatus();
	        this.serviceOrderNote = order.getServiceOrderNote();
	        this.paymentMethod = order.getPaymentMethod();
	        this.taxId = order.getTaxId();
	        this.serviceOrderAmount = order.getServiceOrderAmount();
	        this.createdAt = order.getCreatedAt();
	    }
	}


// 静态工厂方法
  public static ServiceOrdersDTO fromEntity(ServiceOrderBean serviceOrderBean) {
    if (serviceOrderBean == null)
      return null;

    ServiceOrdersDTO dto = new ServiceOrdersDTO();
    dto.setServiceOrderId(serviceOrderBean.getServiceOrderId());
    dto.setServiceApplicationId(serviceOrderBean.getServiceApplicationId());
    dto.setServiceOrderPayById(serviceOrderBean.getServiceOrderPayById());
    dto.setServiceOrderDate(serviceOrderBean.getServiceOrderDate());
    dto.setStatus(serviceOrderBean.getStatus());
    dto.setServiceOrderNote(serviceOrderBean.getServiceOrderNote());
    dto.setPaymentMethod(serviceOrderBean.getPaymentMethod());
    dto.setTaxId(serviceOrderBean.getTaxId());
    dto.setServiceOrderAmount(serviceOrderBean.getServiceOrderAmount());
    dto.setCreatedAt(serviceOrderBean.getCreatedAt());

    return dto;
  }

  public String getServiceOrderId() {
    return serviceOrderId;
  }

  public void setServiceOrderId(String serviceOrderId) {
    this.serviceOrderId = serviceOrderId;
  }

  public Integer getServiceApplicationId() {
    return serviceApplicationId;
  }

  public void setServiceApplicationId(Integer serviceApplicationId) {
    this.serviceApplicationId = serviceApplicationId;
  }

  public Integer getServiceOrderPayById() {
    return serviceOrderPayById;
  }

  public void setServiceOrderPayById(Integer serviceOrderPayById) {
    this.serviceOrderPayById = serviceOrderPayById;
  }

  public LocalDateTime getServiceOrderDate() {
    return serviceOrderDate;
  }

  public void setServiceOrderDate(LocalDateTime serviceOrderDate) {
    this.serviceOrderDate = serviceOrderDate;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getServiceOrderNote() {
    return serviceOrderNote;
  }

  public void setServiceOrderNote(String serviceOrderNote) {
    this.serviceOrderNote = serviceOrderNote;
  }

  public String getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  public Integer getTaxId() {
    return taxId;
  }

  public void setTaxId(Integer taxId) {
    this.taxId = taxId;
  }

  public Integer getServiceOrderAmount() {
    return serviceOrderAmount;
  }

  public void setServiceOrderAmount(Integer serviceOrderAmount) {
    this.serviceOrderAmount = serviceOrderAmount;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public String toString() {
    return "ServiceOrdersDTO{" +
        "serviceOrderId='" + serviceOrderId + '\'' +
        ", serviceApplicationId=" + serviceApplicationId +
        ", serviceOrderPayById=" + serviceOrderPayById +
        ", serviceOrderDate=" + serviceOrderDate +
        ", status='" + status + '\'' +
        ", serviceOrderNote='" + serviceOrderNote + '\'' +
        ", paymentMethod='" + paymentMethod + '\'' +
        ", taxId=" + taxId +
        ", serviceOrderAmount=" + serviceOrderAmount +
        ", createdAt=" + createdAt +
        '}';
  }

}

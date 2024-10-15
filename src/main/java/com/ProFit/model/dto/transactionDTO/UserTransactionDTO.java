package com.ProFit.model.dto.transactionDTO;

import java.time.LocalDateTime;

import com.ProFit.model.bean.transactionBean.UserTransactionBean;

public class UserTransactionDTO {

    private String transactionId;  // 交易ID
    private Integer userId;  // 用戶ID
    private String transactionRole;  // 交易角色
    private String transactionType;  // 交易類型
    private String orderId;  // 訂單ID
    private Double totalAmount;  // 交易金額
    private Double platformFee;  // 平台費用
    private Double targetIncome;  // 實際支付給接收方的金額
    private String transactionStatus;  // 交易狀態
    private String paymentMethod;  // 支付方式
    private String referenceId;  // 第三方支付平台參考ID
    private LocalDateTime createdAt;  // 交易創建時間
    private LocalDateTime completionAt;  // 交易完成時間

    // Constructors

    public UserTransactionDTO() {
        // 無參構造函數
    }

    public UserTransactionDTO(UserTransactionBean transaction) {
        this.transactionId = transaction.getTransactionId();
        this.userId = transaction.getUserId();
        this.transactionRole = transaction.getTransactionRole();
        this.transactionType = transaction.getTransactionType();
        this.orderId = transaction.getOrderId();
        this.totalAmount = transaction.getTotalAmount();
        this.platformFee = transaction.getPlatformFee();
        this.targetIncome = transaction.getTargetIncome();
        this.transactionStatus = transaction.getTransactionStatus();
        this.paymentMethod = transaction.getPaymentMethod();
        this.referenceId = transaction.getReferenceId();
        this.createdAt = transaction.getCreatedAt();
        this.completionAt = transaction.getCompletionAt();
    }

    // Getters and Setters

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTransactionRole() {
        return transactionRole;
    }

    public void setTransactionRole(String transactionRole) {
        this.transactionRole = transactionRole;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getPlatformFee() {
        return platformFee;
    }

    public void setPlatformFee(Double platformFee) {
        this.platformFee = platformFee;
    }

    public Double getTargetIncome() {
        return targetIncome;
    }

    public void setTargetIncome(Double targetIncome) {
        this.targetIncome = targetIncome;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCompletionAt() {
        return completionAt;
    }

    public void setCompletionAt(LocalDateTime completionAt) {
        this.completionAt = completionAt;
    }
}

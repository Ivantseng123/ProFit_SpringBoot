package com.ProFit.model.bean.transactionBean;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_transactions")
public class UserTransactionBean {

    @Id
    @Column(name = "transaction_id", updatable = false, nullable = false, length = 50)
    private String transactionId;  // 交易ID

    @Column(name = "user_id", nullable = false)
    private int userId;  // 用戶ID

    @Column(name = "transaction_role", nullable = false, length = 10)
    private String transactionRole;  // 交易角色 (sender/receiver)

    @Column(name = "transaction_type", nullable = false, length = 10)
    private String transactionType;  // 交易類型 (deposit/withdrawal/payment/refund)

    @Column(name = "order_id", length = 50)
    private String orderId;  // 訂單ID

    @Column(name = "total_amount", nullable = false)
    private double totalAmount;  // 交易金額

    @Column(name = "platform_fee", nullable = false)
    private double platformFee;  // 平台費用

    @Column(name = "target_income")
    private Double targetIncome;  // 實際支付給接收方的金額

    @Column(name = "transaction_status", nullable = false, length = 10)
    private String transactionStatus;  // 交易狀態 (pending/completed/failed)

    @Column(name = "payment_method", nullable = false, length = 20)
    private String paymentMethod;  // 支付方式

    @Column(name = "reference_id", length = 100)
    private String referenceId;  // 第三方支付平台參考ID

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;  // 交易創建時間

    @Column(name = "completion_at")
    private LocalDateTime completionAt;  // 交易完成時間

    // Constructors
    public UserTransactionBean() {
        // 無參構造函數
    }

    public UserTransactionBean(int userId, String transactionRole, String transactionType, String orderId, double totalAmount,
                               double platformFee, Double targetIncome, String transactionStatus, String paymentMethod, String referenceId) {
        this.userId = userId;
        this.transactionRole = transactionRole;
        this.transactionType = transactionType;
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.platformFee = platformFee;
        this.targetIncome = targetIncome;
        this.transactionStatus = transactionStatus;
        this.paymentMethod = paymentMethod;
        this.referenceId = referenceId;
        this.createdAt = LocalDateTime.now();  // 默認當前時間
    }

    @PrePersist
    public void prePersist() {
        if (this.transactionId == null) {
            this.transactionId = UUID.randomUUID().toString();  // 自動生成交易ID
        }
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();  // 默認設置創建時間
        }
    }

    // Getters and Setters
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getPlatformFee() {
        return platformFee;
    }

    public void setPlatformFee(double platformFee) {
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

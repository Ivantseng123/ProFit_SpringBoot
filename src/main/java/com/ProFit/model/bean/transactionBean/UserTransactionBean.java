package com.ProFit.model.bean.transactionBean;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_transactions")
public class UserTransactionBean {

    @Id
    @Column(name = "transaction_id", updatable = false, nullable = false, length = 36)
    private String transactionId;  // 使用 UUID

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "transaction_type", nullable = false)
    private String transactionType;

    @Column(name = "transaction_amount", nullable = false)
    private int transactionAmount;

    @Column(name = "transaction_status", nullable = false)
    private String transactionStatus;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "completion_at")
    private LocalDateTime completionAt;

    public UserTransactionBean() {
    }

    public UserTransactionBean(int userId, String transactionType, int transactionAmount, String transactionStatus) {
        this.userId = userId;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.transactionStatus = transactionStatus;
        this.createdAt = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        if (this.transactionId == null) {
            this.transactionId = UUID.randomUUID().toString();
        }
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
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

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public int getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(int transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
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

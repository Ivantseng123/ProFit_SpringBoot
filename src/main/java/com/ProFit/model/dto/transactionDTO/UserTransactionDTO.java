package com.ProFit.model.dto.transactionDTO;

import java.time.LocalDateTime;

public class UserTransactionDTO {

    private String transactionId;
    private Integer userId;
    private String transactionType;
    private Integer transactionAmount;
    private String transactionStatus;
    private LocalDateTime createdAt;
    private LocalDateTime completionAt;

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

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Integer getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Integer transactionAmount) {
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

package com.ProFit.model.dto.transactionDTO;

import java.time.LocalDateTime;

public class InvoiceDTO {

    private String invoiceNumber;
    private String transactionId;  // 關聯的交易ID
    private int invoiceAmount;
    private LocalDateTime issuedDate;
    private String invoiceStatus;

    // Constructors
    public InvoiceDTO() {
    }

    public InvoiceDTO(String invoiceNumber, String transactionId, int invoiceAmount, LocalDateTime issuedDate, String invoiceStatus) {
        this.invoiceNumber = invoiceNumber;
        this.transactionId = transactionId;
        this.invoiceAmount = invoiceAmount;
        this.issuedDate = issuedDate;
        this.invoiceStatus = invoiceStatus;
    }

    // Getters and Setters
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public int getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(int invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public LocalDateTime getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(LocalDateTime issuedDate) {
        this.issuedDate = issuedDate;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }
}

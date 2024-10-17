package com.ProFit.model.dto.transactionDTO;

import java.time.LocalDateTime;

public class InvoiceDTO {

    private String invoiceNumber;
    private String transactionId;
    private String jobOrderId;
    private String courseOrderId;
    private String eventOrderId;
    private int invoiceAmount;
    private LocalDateTime issuedDate;
    private String invoiceStatus;

    // Getter 和 Setter 方法
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

    public String getJobOrderId() {
        return jobOrderId;
    }

    public void setJobOrderId(String jobOrderId) {
        this.jobOrderId = jobOrderId;
    }

    public String getCourseOrderId() {
        return courseOrderId;
    }

    public void setCourseOrderId(String courseOrderId) {
        this.courseOrderId = courseOrderId;
    }

    public String getEventOrderId() {
        return eventOrderId;
    }

    public void setEventOrderId(String eventOrderId) {
        this.eventOrderId = eventOrderId;
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

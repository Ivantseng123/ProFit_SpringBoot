package com.ProFit.model.dto.transactionDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JobOrderDTO {

    private String jobOrdersId;
    private int jobApplicationId;
    private LocalDateTime jobOrderDate;
    private String jobOrderStatus;
    private String jobNotes;
    private int jobAmount;
    private String jobOrderPaymentMethod;
    private Integer jobOrderTaxID;

    // Getter 和 Setter 方法
    public String getJobOrdersId() {
        return jobOrdersId;
    }

    public void setJobOrdersId(String jobOrdersId) {
        this.jobOrdersId = jobOrdersId;
    }

    public int getJobApplicationId() {
        return jobApplicationId;
    }

    public void setJobApplicationId(int jobApplicationId) {
        this.jobApplicationId = jobApplicationId;
    }

    public LocalDateTime getJobOrderDate() {
        return jobOrderDate;
    }

    public void setJobOrderDate(LocalDateTime jobOrderDate) {
        this.jobOrderDate = jobOrderDate;
    }

    public String getJobOrderStatus() {
        return jobOrderStatus;
    }

    public void setJobOrderStatus(String jobOrderStatus) {
        this.jobOrderStatus = jobOrderStatus;
    }

    public String getJobNotes() {
        return jobNotes;
    }

    public void setJobNotes(String jobNotes) {
        this.jobNotes = jobNotes;
    }

    public int getJobAmount() {
        return jobAmount;
    }

    public void setJobAmount(int jobAmount) {
        this.jobAmount = jobAmount;
    }
    
    public String getJobOrderPaymentMethod() {
        return jobOrderPaymentMethod;
    }

    public void setJobOrderPaymentMethod(String jobOrderPaymentMethod) {
        this.jobOrderPaymentMethod = jobOrderPaymentMethod;
    }

    public Integer getJobOrderTaxID() {
        return jobOrderTaxID;
    }

    public void setJobOrderTaxID(Integer jobOrderTaxID) {
        this.jobOrderTaxID = jobOrderTaxID;
    }

    // 格式化日期方法
    public String getFormattedJobOrderDate() {
        if (jobOrderDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return jobOrderDate.format(formatter);
        }
        return "";
    }
}

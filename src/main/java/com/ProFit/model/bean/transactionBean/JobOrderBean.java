package com.ProFit.model.bean.transactionBean;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Table(name = "job_orders")
public class JobOrderBean {

    @Id
    @Column(name = "job_orders_id", nullable = false, unique = true)
    private String jobOrdersId;

    @Column(name = "job_application_id", nullable = false)
    private int jobApplicationId;

    @Column(name = "job_order_date", nullable = false)
    private LocalDateTime jobOrderDate;

    @Column(name = "job_order_status", nullable = false)
    private String jobOrderStatus;

    @Column(name = "job_notes")
    private String jobNotes;

    @Column(name = "job_amount", nullable = false)
    private int jobAmount;

 
    // 無參構造函數
    public JobOrderBean() {
        
    }

    // 全參構造函數
    public JobOrderBean(int jobApplicationId, String jobOrderStatus, String jobNotes, int jobAmount) {
        this.jobOrdersId = UUID.randomUUID().toString();  // 自動生成 UUID
        this.jobApplicationId = jobApplicationId;
        this.jobOrderDate = LocalDateTime.now();  // 使用當前時間作為訂單日期
        this.jobOrderStatus = jobOrderStatus;
        this.jobNotes = jobNotes;
        this.jobAmount = jobAmount;
    }


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

   
}

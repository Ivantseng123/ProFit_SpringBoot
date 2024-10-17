package com.ProFit.model.dto.jobsDTO;

import com.ProFit.model.bean.jobsBean.JobsApplication;
import java.util.Date;

//適合一般的 CRUD 操作。如果之後需要更複雜的功能，再考慮拆分成 Request/Response DTO。
public class JobsApplicationDTO {
    // 核心欄位
    private Integer jobsApplicationId;
    private Integer jobsId;               // 職缺ID
    private String jobTitle;              // 職缺名稱
    private Integer applicantId;          // 應徵者ID
    private String applicantName;         // 應徵者姓名
    private Date jobsApplicationDate;     // 申請日期
    private Byte jobsApplicationStatus;   // 申請狀態
    private Boolean hasContract;          // 是否有合約檔案， 移除了 Blob 直接操作，改用 boolean 表示是否有檔案

    // 建構子
    public JobsApplicationDTO() {
        super();
    }

    // getter/setter
    public Integer getJobsApplicationId() {
        return jobsApplicationId;
    }

    public void setJobsApplicationId(Integer jobsApplicationId) {
        this.jobsApplicationId = jobsApplicationId;
    }

    public Integer getJobsId() {
        return jobsId;
    }

    public void setJobsId(Integer jobsId) {
        this.jobsId = jobsId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Integer getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Integer applicantId) {
        this.applicantId = applicantId;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public Date getJobsApplicationDate() {
        return jobsApplicationDate;
    }

    public void setJobsApplicationDate(Date jobsApplicationDate) {
        this.jobsApplicationDate = jobsApplicationDate;
    }

    public Byte getJobsApplicationStatus() {
        return jobsApplicationStatus;
    }

    public void setJobsApplicationStatus(Byte jobsApplicationStatus) {
        this.jobsApplicationStatus = jobsApplicationStatus;
    }

    public Boolean getHasContract() {
        return hasContract;
    }
    public void setHasContract(Boolean hasContract) {
        this.hasContract = hasContract;
    }

//    // 從 Entity 轉換為 DTO 的靜態方法
//    public static JobsApplicationDTO fromEntity(JobsApplication entity) {
//        JobsApplicationDTO dto = new JobsApplicationDTO();
//
//        dto.setJobsApplicationId(entity.getJobsApplicationId());
//        dto.setJobsApplicationDate(entity.getJobsApplicationDate());
//        dto.setJobsApplicationStatus(entity.getJobsApplicationStatus());
//        dto.setHasContract(entity.getJobsApplicationContract() != null);
//
//        // 安全地獲取職缺資訊
//        if (entity.getPoster() != null) {
//            dto.setJobsId(entity.getPoster().getJobsId());
//            dto.setJobTitle(entity.getPoster().getJobTitle());  // 假設 Jobs 實體有 getJobTitle 方法
//        }
//
//        // 安全地獲取應徵者資訊
//        if (entity.getApplicant() != null) {
//            dto.setApplicantId(entity.getApplicant().getUserId());  // 假設 Users 實體有 getUserId 方法
//            dto.setApplicantName(entity.getApplicant().getName());  // 假設 Users 實體有 getName 方法
//        }
//
//        return dto;
//    }
//
//    // 從 DTO 轉換為 Entity 的方法（如果需要的話）
//    public JobsApplication toEntity() {
//        JobsApplication entity = new JobsApplication();
//        entity.setJobsApplicationId(this.jobsApplicationId);
//        entity.setJobsApplicationStatus(this.jobsApplicationStatus);
//        entity.setJobsApplicationDate(this.jobsApplicationDate);
//        // 注意：poster 和 applicant 需要在 service 層設置
//        return entity;
//    }
}
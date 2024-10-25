package com.ProFit.model.dto.jobsDTO;

import com.ProFit.model.bean.jobsBean.JobsApplication;
import com.ProFit.model.bean.jobsBean.Jobs;
import java.sql.Blob;
import java.sql.Date;
import java.util.List;

/**
 * 求職申請DTO類
 * 用於前後端數據傳輸
 */
public class JobsApplicationDTO {

    // 基本屬性
    private int jobsApplicationId;                    // 申請編號
    private Integer jobsApplicationJobsId;            // 職缺ID
    private Integer jobsApplicationMemberId;          // 應徵者ID
    private Date jobsApplicationDate;                 // 申請日期
    private Byte jobsApplicationStatus;               // 申請狀態
    private Blob jobsApplicationContract;             // 合約文件
//    private List<JobsApplicationProjectDTO> projects; // 相關專案列表

    // 預設建構子
    public JobsApplicationDTO() {
        super();
    }

    // 從實體轉換為DTO的靜態方法
    public static JobsApplicationDTO fromEntity(JobsApplication entity) {
        JobsApplicationDTO dto = new JobsApplicationDTO();
        dto.setJobsApplicationId(entity.getJobsApplicationId());

        // 設置職缺ID
        if (entity.getJobs() != null) {
            dto.setJobsApplicationJobsId(entity.getJobs().getJobsId()); // 因為Jobs類中的getter方法就是getJobsId()
        }

        // 設置應徵者ID
        if (entity.getApplicant() != null) {
            dto.setJobsApplicationMemberId(entity.getApplicant().getUserId());
        }

        dto.setJobsApplicationDate(entity.getJobsApplicationDate());
        dto.setJobsApplicationStatus(entity.getJobsApplicationStatus());
//        dto.setJobsApplicationContract(entity.getJobsApplicationContract());

        // 轉換專案列表
//        if (entity.getProjects() != null) {
//            dto.setProjects(entity.getProjects().stream()
//                    .map(JobsApplicationProjectDTO::fromEntity)
//                    .collect(java.util.stream.Collectors.toList()));
//        }

        return dto;
    }

    // Getter 和 Setter 方法
    public int getJobsApplicationId() {
        return jobsApplicationId;
    }

    public void setJobsApplicationId(int jobsApplicationId) {
        this.jobsApplicationId = jobsApplicationId;
    }

    public Integer getJobsApplicationJobsId() {
        return jobsApplicationJobsId;
    }

    public void setJobsApplicationJobsId(Integer jobsApplicationJobsId) {
        this.jobsApplicationJobsId = jobsApplicationJobsId;
    }

    public Integer getJobsApplicationMemberId() {
        return jobsApplicationMemberId;
    }

    public void setJobsApplicationMemberId(Integer jobsApplicationMemberId) {
        this.jobsApplicationMemberId = jobsApplicationMemberId;
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

    public Blob getJobsApplicationContract() {
        return jobsApplicationContract;
    }

    public void setJobsApplicationContract(Blob jobsApplicationContract) {
        this.jobsApplicationContract = jobsApplicationContract;
    }

//    public List<JobsApplicationProjectDTO> getProjects() {
//        return projects;
//    }
//
//    public void setProjects(List<JobsApplicationProjectDTO> projects) {
//        this.projects = projects;
//    }
}
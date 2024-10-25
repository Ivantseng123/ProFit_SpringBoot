package com.ProFit.model.dto.jobsDTO;

import com.ProFit.model.bean.jobsBean.JobsApplicationProject;
import com.ProFit.model.bean.jobsBean.JobsApplication;

public class JobsApplicationProjectDTO {
    private Integer jobsApplicationProjectId;
    private Integer jobsApplicationId; // 只存ID而不是整個物件
    private Byte jobsApplicationStatus;
    private String jobsProject;
    private Integer jobsAmount;

    // 空參建構子
    public JobsApplicationProjectDTO() {
        super();
    }

    // 完整建構子
    public JobsApplicationProjectDTO(Integer jobsApplicationProjectId,
                                     Integer jobsApplicationId,
                                     Byte jobsApplicationStatus,
                                     String jobsProject,
                                     Integer jobsAmount) {
        this.jobsApplicationProjectId = jobsApplicationProjectId;
        this.jobsApplicationId = jobsApplicationId;
        this.jobsApplicationStatus = jobsApplicationStatus;
        this.jobsProject = jobsProject;
        this.jobsAmount = jobsAmount;
    }

    // 從Entity轉換到DTO的靜態方法
    public static JobsApplicationProjectDTO fromEntity(JobsApplicationProject entity) {
        JobsApplicationProjectDTO dto = new JobsApplicationProjectDTO();
        dto.setJobsApplicationProjectId(entity.getJobsApplicationProjectId());

        // 從JobsApplication實體中獲取ID
        if (entity.getJobsApplication() != null) {
            dto.setJobsApplicationId(entity.getJobsApplication().getJobsApplicationId());
        }

        dto.setJobsApplicationStatus(entity.getJobsApplicationStatus());
        dto.setJobsProject(entity.getJobsProject());
        dto.setJobsAmount(entity.getJobsAmount());

        return dto;
    }

    // Getters and Setters
    public Integer getJobsApplicationProjectId() {
        return jobsApplicationProjectId;
    }

    public void setJobsApplicationProjectId(Integer jobsApplicationProjectId) {
        this.jobsApplicationProjectId = jobsApplicationProjectId;
    }

    public Integer getJobsApplicationId() {
        return jobsApplicationId;
    }

    public void setJobsApplicationId(Integer jobsApplicationId) {
        this.jobsApplicationId = jobsApplicationId;
    }

    public Byte getJobsApplicationStatus() {
        return jobsApplicationStatus;
    }

    public void setJobsApplicationStatus(Byte jobsApplicationStatus) {
        this.jobsApplicationStatus = jobsApplicationStatus;
    }

    public String getJobsProject() {
        return jobsProject;
    }

    public void setJobsProject(String jobsProject) {
        this.jobsProject = jobsProject;
    }

    public Integer getJobsAmount() {
        return jobsAmount;
    }

    public void setJobsAmount(Integer jobsAmount) {
        this.jobsAmount = jobsAmount;
    }
}
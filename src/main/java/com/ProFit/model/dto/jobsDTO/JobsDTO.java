package com.ProFit.model.dto.jobsDTO;

import java.util.Date;

public class JobsDTO {
    private Integer jobsId;
    private Integer userId; // Assuming we only need the ID of the user, not the whole object
    private String jobsRequiredSkills;
    private String jobsTitle;
    private Date jobsPostingDate;
    private Date jobsApplicationDeadline;
    private String jobsDescription;
    private String jobsLocation;
    private Byte jobsStatus;
    private Integer jobsMaxSalary;
    private Integer jobsMinSalary;
    private String jobsWorktime;
    private Integer jobsNumberOfOpenings;

    // 無參建構子
    public JobsDTO() {}

    // 帶參Getters and Setters
    public Integer getJobsId() {
        return jobsId;
    }

    public void setJobsId(Integer jobsId) {
        this.jobsId = jobsId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getJobsRequiredSkills() {
        return jobsRequiredSkills;
    }

    public void setJobsRequiredSkills(String jobsRequiredSkills) {
        this.jobsRequiredSkills = jobsRequiredSkills;
    }

    public String getJobsTitle() {
        return jobsTitle;
    }

    public void setJobsTitle(String jobsTitle) {
        this.jobsTitle = jobsTitle;
    }

    public Date getJobsPostingDate() {
        return jobsPostingDate;
    }

    public void setJobsPostingDate(Date jobsPostingDate) {
        this.jobsPostingDate = jobsPostingDate;
    }

    public Date getJobsApplicationDeadline() {
        return jobsApplicationDeadline;
    }

    public void setJobsApplicationDeadline(Date jobsApplicationDeadline) {
        this.jobsApplicationDeadline = jobsApplicationDeadline;
    }

    public String getJobsDescription() {
        return jobsDescription;
    }

    public void setJobsDescription(String jobsDescription) {
        this.jobsDescription = jobsDescription;
    }

    public String getJobsLocation() {
        return jobsLocation;
    }

    public void setJobsLocation(String jobsLocation) {
        this.jobsLocation = jobsLocation;
    }

    public Byte getJobsStatus() {
        return jobsStatus;
    }

    public void setJobsStatus(Byte jobsStatus) {
        this.jobsStatus = jobsStatus;
    }

    public Integer getJobsMaxSalary() {
        return jobsMaxSalary;
    }

    public void setJobsMaxSalary(Integer jobsMaxSalary) {
        this.jobsMaxSalary = jobsMaxSalary;
    }

    public Integer getJobsMinSalary() {
        return jobsMinSalary;
    }

    public void setJobsMinSalary(Integer jobsMinSalary) {
        this.jobsMinSalary = jobsMinSalary;
    }

    public String getJobsWorktime() {
        return jobsWorktime;
    }

    public void setJobsWorktime(String jobsWorktime) {
        this.jobsWorktime = jobsWorktime;
    }

    public Integer getJobsNumberOfOpenings() {
        return jobsNumberOfOpenings;
    }

    public void setJobsNumberOfOpenings(Integer jobsNumberOfOpenings) {
        this.jobsNumberOfOpenings = jobsNumberOfOpenings;
    }

    // toString method
    @Override
    public String toString() {
        return "JobsDTO{" +
                "jobsId=" + jobsId +
                ", userId=" + userId +
                ", jobsRequiredSkills='" + jobsRequiredSkills + '\'' +
                ", jobsTitle='" + jobsTitle + '\'' +
                ", jobsPostingDate=" + jobsPostingDate +
                ", jobsApplicationDeadline=" + jobsApplicationDeadline +
                ", jobsDescription='" + jobsDescription + '\'' +
                ", jobsLocation='" + jobsLocation + '\'' +
                ", jobsStatus=" + jobsStatus +
                ", jobsMaxSalary=" + jobsMaxSalary +
                ", jobsMinSalary=" + jobsMinSalary +
                ", jobsWorktime='" + jobsWorktime + '\'' +
                ", jobsNumberOfOpenings=" + jobsNumberOfOpenings +
                '}';
    }
}
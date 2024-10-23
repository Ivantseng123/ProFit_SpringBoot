package com.ProFit.model.dto.jobsDTO;

import com.ProFit.model.bean.jobsBean.Jobs;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import com.ProFit.model.dto.majorsDTO.MajorDTO;

public class JobsDTO {
    private Integer jobsId;
    private Integer jobsUserId;
    private String jobsTitle;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date jobsPostingDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date jobsApplicationDeadline;

    private String jobsDescription;
    private String jobsLocation;
    private Byte jobsStatus;
    private Integer jobsMaxSalary;
    private Integer jobsMinSalary;
    private String jobsWorktime;
    private Integer jobsNumberOfOpenings;
    private String companyName; // From Users entity
    private List<MajorDTO> majors;

    public JobsDTO() {
        super();
    }

    // Full constructor
    public JobsDTO(Integer jobsId, Integer jobsUserId, String jobsTitle, Date jobsPostingDate,
                   Date jobsApplicationDeadline, String jobsDescription, String jobsLocation,
                   Byte jobsStatus, Integer jobsMaxSalary, Integer jobsMinSalary,
                   String jobsWorktime, Integer jobsNumberOfOpenings, String companyName,
                   List<MajorDTO> majors) {
        this.jobsId = jobsId;
        this.jobsUserId = jobsUserId;
        this.jobsTitle = jobsTitle;
        this.jobsPostingDate = jobsPostingDate;
        this.jobsApplicationDeadline = jobsApplicationDeadline;
        this.jobsDescription = jobsDescription;
        this.jobsLocation = jobsLocation;
        this.jobsStatus = jobsStatus;
        this.jobsMaxSalary = jobsMaxSalary;
        this.jobsMinSalary = jobsMinSalary;
        this.jobsWorktime = jobsWorktime;
        this.jobsNumberOfOpenings = jobsNumberOfOpenings;
        this.companyName = companyName;
        this.majors = majors;
    }

    // Static method to convert from Jobs entity to JobsDTO
    public static JobsDTO fromEntity(Jobs jobs) {
        JobsDTO dto = new JobsDTO();
        dto.setJobsId(jobs.getJobsId());
        dto.setJobsUserId(jobs.getUsers() != null ? jobs.getUsers().getUserId() : null);
        dto.setJobsTitle(jobs.getJobsTitle());
        dto.setJobsPostingDate(jobs.getJobsPostingDate());
        dto.setJobsApplicationDeadline(jobs.getJobsApplicationDeadline());
        dto.setJobsDescription(jobs.getJobsDescription());
        dto.setJobsLocation(jobs.getJobsLocation());
        dto.setJobsStatus(jobs.getJobsStatus());
        dto.setJobsMaxSalary(jobs.getJobsMaxSalary());
        dto.setJobsMinSalary(jobs.getJobsMinSalary());
        dto.setJobsWorktime(jobs.getJobsWorktime());
        dto.setJobsNumberOfOpenings(jobs.getJobsNumberOfOpenings());

        // Set company name from Users entity if available
        if (jobs.getUsers() != null) {
            dto.setCompanyName(jobs.getUsers().getUserName());
        }

        // Convert and set majors if available
        if (jobs.getMajors() != null) {
            dto.setMajors(jobs.getMajors().stream()
                    .map(MajorDTO::fromEntity)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    // Getters and Setters
    public Integer getJobsId() {
        return jobsId;
    }

    public void setJobsId(Integer jobsId) {
        this.jobsId = jobsId;
    }

    public Integer getJobsUserId() {
        return jobsUserId;
    }

    public void setJobsUserId(Integer jobsUserId) {
        this.jobsUserId = jobsUserId;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<MajorDTO> getMajors() {
        return majors;
    }

    public void setMajors(List<MajorDTO> majors) {
        this.majors = majors;
    }
}
package com.ProFit.model.bean.jobsBean;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


//資料型態對應：
//INT: Java 的 Integer
//NVARCHAR: Java 的 String
//DATETIME: Java 的 Date
//TINYINT: Java 的 Byte (假設 TINYINT 用於表示狀態)


@Entity
@Table(name = "jobs_application_project")
public class JobsApplicationProject {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jobs_application_project_id")
    private int jobsApplicationProjectId;


    @ManyToOne(fetch = FetchType.LAZY)//FK,一個申請對應到一個申請項目
    @JoinColumn(name = "jobs_application_id")
    private JobsApplication jobsApplication;


    //一個狀態對應到一個申請項目
    @Column(name = "jobs_application_status")
    private Byte jobsApplicationStatus = 0;

    @Column(name = "jobs_project")
    private String jobsProject;

    @Column(name = "jobs_amount")
    private Integer jobsAmount;

    @Column(name = "jobs_contract")
    private String jobsContract;


    public JobsApplicationProject() {
        super();
    }


    public int getJobsApplicationProjectId() {
        return jobsApplicationProjectId;
    }


    public void setJobsApplicationProjectId(int jobsApplicationProjectId) {
        this.jobsApplicationProjectId = jobsApplicationProjectId;
    }


    public JobsApplication getJobsApplication() {
        return jobsApplication;
    }


    public void setJobsApplication(JobsApplication jobsApplication) {
        this.jobsApplication = jobsApplication;

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

    public String getJobsContract() {
        return jobsContract;
    }

    public void setJobsContract(String jobsContract) {
        this.jobsContract = jobsContract;
    }
}

package com.ProFit.model.bean.jobsBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ProFit.model.bean.usersBean.Users;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
//	資料型態對應：
//	INT: Java 的 Integer
//	NVARCHAR: Java 的 String
//	DATETIME: Java 的 Date
//	TINYINT: Java 的 Byte (假設 TINYINT 用於表示狀態)
//	VARBINARY(MAX)
//	如果需要對資料進行隨機存取，byte[] 是更好的選擇。
//	如果只需要逐個位元組地讀取資料，InputStream 是一個不錯的選擇。
//	如果需要直接與資料庫進行互動，Blob 是最適合的選擇。
import org.springframework.format.annotation.DateTimeFormat;

//@Setter
//@NoArgsConstructor
///@AllArgsConstructor
//@Getter
@Entity
@Table(name = "jobs_Application")
public class JobsApplication implements java.io.Serializable{

	
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  @Column(name = "jobs_application_id")
	  private int jobsApplicationId;


	  @ManyToOne(fetch = FetchType.LAZY)//FK，對Jobs表，一個職缺對應到多個申請
	  @JoinColumn(name = "jobs_application_jobs_id")
	  private Jobs jobs;//不確定是不是Jobs

	  @ManyToOne(fetch = FetchType.LAZY)//FK，對USER表，一個應徵者對應到多個申請
	  @JoinColumn(name = "jobs_application_member_id")//改名為jobs_application_user_id
	  private Users applicant;

	  @Temporal(TemporalType.DATE)
	  @DateTimeFormat(pattern = "yyyy-MM-dd")
	  @JsonFormat(pattern = "yyyy-MM-dd")
	  @Column(name = "jobs_application_date",insertable = false,updatable = false)
	  private Date jobsApplicationDate;

	  @Column(name = "jobs_application_status")
	  private Byte jobsApplicationStatus=0;


	  @Column(name = "jobs_application_resume")
	  private String jobsApplicationResume;


	// cascade = CascadeType.ALL會同步更新刪除
	@OneToMany(mappedBy = "jobsApplication", cascade = CascadeType.ALL)
	private List<JobsApplicationProject> projects = new ArrayList<>();

    //無參建構子
	public JobsApplication() {
		super();
	}


	public int getJobsApplicationId() {
		return jobsApplicationId;
	}

	public void setJobsApplicationId(int jobsApplicationId) {
		this.jobsApplicationId = jobsApplicationId;
	}

	public Jobs getJobs() {
		return jobs;
	}

	public void setJobs(Jobs jobs) {
		this.jobs = jobs;
	}

	public Users getApplicant() {
		return applicant;
	}

	public void setApplicant(Users applicant) {
		this.applicant = applicant;
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

	public String getJobsApplicationResume() {
		return jobsApplicationResume;
	}

	public void setJobsApplicationResume(String jobsApplicationResume) {
		this.jobsApplicationResume = jobsApplicationResume;
	}

//	public String getJobsApplicationContract() {
//		return jobsApplicationContract;
//	}
//
//	public void setJobsApplicationResume(String jobsApplicationContract) {
//		this.jobsApplicationContract = jobsApplicationContract;
//	}

	public List<JobsApplicationProject> getProjects() {
		return projects;
	}

	public void setProjects(List<JobsApplicationProject> projects) {
		this.projects = projects;
	}
}

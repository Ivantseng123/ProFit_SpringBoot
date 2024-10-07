package com.ProFit.model.bean.coursesBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

import com.ProFit.model.bean.majorsBean.MajorBean;
import com.ProFit.model.bean.usersBean.Users;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name="courses")
public class CourseBean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id @Column(name="course_id")
	private String courseId;

	@Column(name="course_name")
	private String courseName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="course_create_user_id",insertable = false,updatable = false)
	private Users courseCreater;

	@Column(name="course_create_user_id")
	private Integer courseCreateUserId;
	
	@Column(name="course_coverPictureURL")
	private String courseCoverPictureURL;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "course",cascade = CascadeType.ALL)
	private List<CourseModuleBean> courseModules;
	
	@Column(name="course_category")
	private String courseCategory;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="course_category", insertable = false,updatable = false)
	private MajorBean major;

	@Column(name = "course_information")
	private String courseInformation;

	@Column(name="course_description")
	private String courseDescription;

	@Column(name="course_enrollment_date")
	private LocalDate courseEnrollmentDate;

	@Column(name="course_start_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime courseStartDate;

	@Column(name="course_end_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime courseEndDate;

	@Column(name="course_price")
	private Integer coursePrice;

	@Column(name="course_status")
	private String courseStatus;

	public CourseBean() {
		super();
	}
	
	
	public CourseBean(String courseId, String courseName, Integer courseCreateUserId, String courseCategory,
			String courseInformation, String courseDescription, LocalDate courseEnrollmentDate, LocalDateTime courseStartDate,
			LocalDateTime courseEndDate, Integer coursePrice, String courseStatus) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
		this.courseCreateUserId = courseCreateUserId;
		this.courseCategory = courseCategory;
		this.courseInformation = courseInformation;
		this.courseDescription = courseDescription;
		this.courseEnrollmentDate = courseEnrollmentDate;
		this.courseStartDate = courseStartDate;
		this.courseEndDate = courseEndDate;
		this.coursePrice = coursePrice;
		this.courseStatus = courseStatus;
	}
	
	
	// for create course
	public CourseBean(String courseName, Integer courseCreateUserId, String courseCategory, String courseInformation,
			String courseDescription, LocalDate courseEnrollmentDate, LocalDateTime courseStartDate, LocalDateTime courseEndDate,
			Integer coursePrice, String courseStatus) {
		super();
		this.courseName = courseName;
		this.courseCreateUserId = courseCreateUserId;
		this.courseCategory = courseCategory;
		this.courseInformation = courseInformation;
		this.courseDescription = courseDescription;
		this.courseEnrollmentDate = courseEnrollmentDate;
		this.courseStartDate = courseStartDate;
		this.courseEndDate = courseEndDate;
		this.coursePrice = coursePrice;
		this.courseStatus = courseStatus;
	}


	public String getCourseId() {
		return courseId;
	}


	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}


	public String getCourseName() {
		return courseName;
	}


	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}


	public Users getCourseCreater() {
		return courseCreater;
	}


	public void setCourseCreater(Users courseCreater) {
		this.courseCreater = courseCreater;
	}


	public Integer getCourseCreateUserId() {
		return courseCreateUserId;
	}


	public void setCourseCreateUserId(Integer courseCreateUserId) {
		this.courseCreateUserId = courseCreateUserId;
	}


	public String getCourseCoverPictureURL() {
		return courseCoverPictureURL;
	}


	public void setCourseCoverPictureURL(String courseCoverPictureURL) {
		this.courseCoverPictureURL = courseCoverPictureURL;
	}


	public List<CourseModuleBean> getCourseModules() {
		return courseModules;
	}


	public void setCourseModules(List<CourseModuleBean> courseModules) {
		this.courseModules = courseModules;
	}


	public String getCourseCategory() {
		return courseCategory;
	}


	public void setCourseCategory(String courseCategory) {
		this.courseCategory = courseCategory;
	}


	public MajorBean getMajor() {
		return major;
	}


	public void setMajor(MajorBean major) {
		this.major = major;
	}


	public String getCourseInformation() {
		return courseInformation;
	}


	public void setCourseInformation(String courseInformation) {
		this.courseInformation = courseInformation;
	}


	public String getCourseDescription() {
		return courseDescription;
	}


	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}


	public LocalDate getCourseEnrollmentDate() {
		return courseEnrollmentDate;
	}


	public void setCourseEnrollmentDate(LocalDate courseEnrollmentDate) {
		this.courseEnrollmentDate = courseEnrollmentDate;
	}


	public LocalDateTime getCourseStartDate() {
		return courseStartDate;
	}


	public void setCourseStartDate(LocalDateTime courseStartDate) {
		this.courseStartDate = courseStartDate;
	}


	public LocalDateTime getCourseEndDate() {
		return courseEndDate;
	}


	public void setCourseEndDate(LocalDateTime courseEndDate) {
		this.courseEndDate = courseEndDate;
	}


	public Integer getCoursePrice() {
		return coursePrice;
	}


	public void setCoursePrice(Integer coursePrice) {
		this.coursePrice = coursePrice;
	}


	public String getCourseStatus() {
		return courseStatus;
	}


	public void setCourseStatus(String courseStatus) {
		this.courseStatus = courseStatus;
	}
	
	

}
package com.ProFit.model.dto.coursesDTO;

import com.ProFit.model.bean.coursesBean.CourseBean;

public class CoursesDTO {

	private String courseId;
	private String courseName;
	private String courseCreaterId;
	private String courseCreaterName;
	private String courseCreaterPictureURL;
	private Integer courseCategoryId;
	private String courseCategoryName;
	private Integer courseModuleNumber;
	private String courseInformation;
	private String courseCoverPictureURL;
	private String courseDescription;
	private String courseEnrollmentDate;
	private String courseStartDate;
	private String courseEndDate;
	private Integer coursePrice;
	private String courseStatus;

	public CoursesDTO() {
		super();
	}

	public CoursesDTO(CourseBean courseBean) {
		this.courseId = courseBean.getCourseId();
		this.courseName = courseBean.getCourseName();
		this.courseCreaterId = courseBean.getCourseCreater().getUserId().toString();
		this.courseCreaterName = courseBean.getCourseCreater().getUserName();
		this.courseCreaterPictureURL = courseBean.getCourseCreater().getUserPictureURL();
		this.courseCategoryId = courseBean.getMajorCategory().getMajorCategoryId();
		this.courseCategoryName = courseBean.getMajorCategory().getCategoryName();
		this.courseModuleNumber = courseBean.getCourseModules().size();
		this.courseInformation = courseBean.getCourseInformation();
		this.courseCoverPictureURL = courseBean.getCourseCoverPictureURL();
		this.courseDescription = courseBean.getCourseDescription();
		this.courseEnrollmentDate = courseBean.getCourseEnrollmentDate().toString();
		this.courseStartDate = courseBean.getCourseStartDate().toString();
		this.courseEndDate = courseBean.getCourseEndDate().toString();
		this.coursePrice = courseBean.getCoursePrice();
		this.courseStatus = courseBean.getCourseStatus();
	}

	public Integer getCourseModuleNumber() {
		return courseModuleNumber;
	}

	public String getCourseCoverPictureURL() {
		return courseCoverPictureURL;
	}

	public void setCourseCoverPictureURL(String courseCoverPictureURL) {
		this.courseCoverPictureURL = courseCoverPictureURL;
	}

	public void setCourseModuleNumber(Integer courseModuleNumber) {
		this.courseModuleNumber = courseModuleNumber;
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

	public String getCourseCreaterId() {
		return courseCreaterId;
	}

	public void setCourseCreaterId(String courseCreaterId) {
		this.courseCreaterId = courseCreaterId;
	}

	public String getCourseCreaterName() {
		return courseCreaterName;
	}

	public void setCourseCreaterName(String courseCreaterName) {
		this.courseCreaterName = courseCreaterName;
	}

	public Integer getCourseCategoryId() {
		return courseCategoryId;
	}

	public void setCourseCategoryId(Integer courseCategoryId) {
		this.courseCategoryId = courseCategoryId;
	}

	public String getCourseCategoryName() {
		return courseCategoryName;
	}

	public void setCourseCategoryName(String courseCategoryName) {
		this.courseCategoryName = courseCategoryName;
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

	public String getCourseEnrollmentDate() {
		return courseEnrollmentDate;
	}

	public void setCourseEnrollmentDate(String courseEnrollmentDate) {
		this.courseEnrollmentDate = courseEnrollmentDate;
	}

	public String getCourseStartDate() {
		return courseStartDate;
	}

	public void setCourseStartDate(String courseStartDate) {
		this.courseStartDate = courseStartDate;
	}

	public String getCourseEndDate() {
		return courseEndDate;
	}

	public void setCourseEndDate(String courseEndDate) {
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

	public String getCourseCreaterPictureURL() {
		return courseCreaterPictureURL;
	}

	public void setCourseCreaterPictureURL(String courseCreaterPictureURL) {
		this.courseCreaterPictureURL = courseCreaterPictureURL;
	}
}
package com.ProFit.model.dto.coursesDTO;

import java.time.LocalDateTime;

import com.ProFit.model.bean.coursesBean.CourseOrderBean;

public class CourseOrderDTO {
	
	private String courseOrderId;
	private Integer courseOrderPrice;
	private String courseId;
	private String courseName;
	private Integer coursePrice;
	private String courseCoverPictureURL;
	private Integer studentId;
	private String studentName;
	private Integer studentBalance;
	private Integer courseCreaterBalance;
	private Integer courseCreaterId;
	private String courseCreaterName;
	private LocalDateTime courseOrderCreateDate;
	private String courseOrderRemark;
	private String courseOrderStatus;
	
	public CourseOrderDTO() {
		super();
	}

	public CourseOrderDTO(CourseOrderBean courseOrder) {
		super();
		this.courseOrderId = courseOrder.getCourseOrderId();
		this.courseOrderPrice = courseOrder.getCourseOrderPrice();
		this.courseId = courseOrder.getCourse().getCourseId();
		this.courseName = courseOrder.getCourse().getCourseName();
		this.coursePrice = courseOrder.getCourse().getCoursePrice();
		this.courseCoverPictureURL = courseOrder.getCourse().getCourseCoverPictureURL();
		this.studentId = courseOrder.getStudentId();
		this.studentName = courseOrder.getStudnt().getUserName();
		this.studentBalance = courseOrder.getStudnt().getUserBalance();
		this.courseCreaterBalance = courseOrder.getCourse().getCourseCreater().getUserBalance();
		this.courseCreaterId = courseOrder.getCourse().getCourseCreateUserId();
		this.courseCreaterName = courseOrder.getCourse().getCourseCreater().getUserName();
		this.courseOrderCreateDate = courseOrder.getCourseOrderCreateDate();
		this.courseOrderRemark = courseOrder.getCourseOrderRemark();
		this.courseOrderStatus = courseOrder.getCourseOrderStatus();
	}

	public String getCourseOrderId() {
		return courseOrderId;
	}

	public void setCourseOrderId(String courseOrderId) {
		this.courseOrderId = courseOrderId;
	}

	public Integer getCourseOrderPrice() {
		return courseOrderPrice;
	}

	public void setCourseOrderPrice(Integer courseOrderPrice) {
		this.courseOrderPrice = courseOrderPrice;
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

	public Integer getCoursePrice() {
		return coursePrice;
	}

	public void setCoursePrice(Integer coursePrice) {
		this.coursePrice = coursePrice;
	}

	public String getCourseCoverPictureURL() {
		return courseCoverPictureURL;
	}

	public void setCourseCoverPictureURL(String courseCoverPictureURL) {
		this.courseCoverPictureURL = courseCoverPictureURL;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public Integer getStudentBalance() {
		return studentBalance;
	}

	public void setStudentBalance(Integer studentBalance) {
		this.studentBalance = studentBalance;
	}

	public Integer getCourseCreaterBalance() {
		return courseCreaterBalance;
	}

	public void setCourseCreaterBalance(Integer courseCreaterBalance) {
		this.courseCreaterBalance = courseCreaterBalance;
	}

	public Integer getCourseCreaterId() {
		return courseCreaterId;
	}

	public void setCourseCreaterId(Integer courseCreaterId) {
		this.courseCreaterId = courseCreaterId;
	}

	public String getCourseCreaterName() {
		return courseCreaterName;
	}

	public void setCourseCreaterName(String courseCreaterName) {
		this.courseCreaterName = courseCreaterName;
	}

	public LocalDateTime getCourseOrderCreateDate() {
		return courseOrderCreateDate;
	}

	public void setCourseOrderCreateDate(LocalDateTime courseOrderCreateDate) {
		this.courseOrderCreateDate = courseOrderCreateDate;
	}

	public String getCourseOrderRemark() {
		return courseOrderRemark;
	}

	public void setCourseOrderRemark(String courseOrderRemark) {
		this.courseOrderRemark = courseOrderRemark;
	}

	public String getCourseOrderStatus() {
		return courseOrderStatus;
	}

	public void setCourseOrderStatus(String courseOrderStatus) {
		this.courseOrderStatus = courseOrderStatus;
	}
	
	
	
}

package com.ProFit.model.bean.coursesBean;

import java.time.LocalDateTime;

import com.ProFit.model.bean.usersBean.Users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "course_order")
public class CourseOrderBean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "course_order_id")
	private String courseOrderId;

	@Column(name = "course_id")
	private String courseId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id", insertable = false, updatable = false)
	private CourseBean course;

	@Column(name = "student_id")
	private Integer studentId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id", insertable = false, updatable = false)
	private Users student;

	@Column(name = "course_order_price")
	private Integer courseOrderPrice;

	@Column(name = "course_order_create_date")
	private LocalDateTime courseOrderCreateDate;

	@Column(name = "course_order_remark")
	private String courseOrderRemark;

	@Column(name = "course_order_status")
	private String courseOrderStatus;

	@Column(name = "course_order_payment_method")
	private String courseOrderPaymentMethod;

	@Column(name = "course_order_taxID")
	private Integer courseOrderTaxID;

	public CourseOrderBean() {
		super();
	}

	public CourseOrderBean(String courseOrderId, String courseId, Integer studentId, Integer courseOrderPrice,
			LocalDateTime courseOrderCreateDate, String courseOrderRemark, String courseOrderStatus) {
		super();
		this.courseOrderId = courseOrderId;
		this.courseId = courseId;
		this.studentId = studentId;
		this.courseOrderPrice = courseOrderPrice;
		this.courseOrderCreateDate = courseOrderCreateDate;
		this.courseOrderRemark = courseOrderRemark;
		this.courseOrderStatus = courseOrderStatus;
	}

	public Users getStudent() {
		return student;
	}

	public void setStudent(Users student) {
		this.student = student;
	}

	public String getCourseOrderPaymentMethod() {
		return courseOrderPaymentMethod;
	}

	public void setCourseOrderPaymentMethod(String courseOrderPaymentMethod) {
		this.courseOrderPaymentMethod = courseOrderPaymentMethod;
	}

	public Integer getCourseOrderTaxID() {
		return courseOrderTaxID;
	}

	public void setCourseOrderTaxID(Integer courseOrderTaxID) {
		this.courseOrderTaxID = courseOrderTaxID;
	}

	public String getCourseOrderStatus() {
		return courseOrderStatus;
	}

	public void setCourseOrderStatus(String courseOrderStatus) {
		this.courseOrderStatus = courseOrderStatus;
	}

	public String getCourseOrderRemark() {
		return courseOrderRemark;
	}

	public void setCourseOrderRemark(String courseOrderRemark) {
		this.courseOrderRemark = courseOrderRemark;
	}

	public CourseBean getCourse() {
		return course;
	}

	public void setCourse(CourseBean course) {
		this.course = course;
	}

	public Users getStudnt() {
		return student;
	}

	public void setStudnt(Users student) {
		this.student = student;
	}

	public String getCourseOrderId() {
		return courseOrderId;
	}

	public void setCourseOrderId(String courseOrderId) {
		this.courseOrderId = courseOrderId;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Integer getCourseOrderPrice() {
		return courseOrderPrice;
	}

	public void setCourseOrderPrice(Integer courseOrderPrice) {
		this.courseOrderPrice = courseOrderPrice;
	}

	public LocalDateTime getCourseOrderCreateDate() {
		return courseOrderCreateDate;
	}

	public void setCourseOrderCreateDate(LocalDateTime courseOrderCreateDate) {
		this.courseOrderCreateDate = courseOrderCreateDate;
	}

}

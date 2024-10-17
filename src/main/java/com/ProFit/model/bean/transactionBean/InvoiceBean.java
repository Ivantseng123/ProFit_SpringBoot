package com.ProFit.model.bean.transactionBean;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoices")
public class InvoiceBean {

	@Id
	@Column(name = "invoice_number")
	private String invoiceNumber;

	@Column(name = "transaction_id")
	private String transactionId;

	@Column(name = "job_order_id")
	private String jobOrderId;

	@Column(name = "course_order_id")
	private String courseOrderId;

	@Column(name = "event_order_id")
	private String eventOrderId;

	@Column(name = "invoice_amount")
	private int invoiceAmount;

	@Column(name = "issued_date")
	private LocalDateTime issuedDate;

	@Column(name = "invoice_status")
	private String invoiceStatus;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "transaction_id", insertable = false, updatable = false)
	private UserTransactionBean userTransactionBean;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "job_order_id", insertable = false, updatable = false)
	private JobOrderBean jobOrderBean;

//    
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="course_order_id", insertable = false, updatable = false)
//    private CourseOrderBean courseOrderBean;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="event_order_id", insertable = false, updatable = false)
//    private EventOrderBean eventOrderBean;

	// Getters 和 Setters
	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getJobOrderId() {
		return jobOrderId;
	}

	public void setJobOrderId(String jobOrderId) {
		this.jobOrderId = jobOrderId;
	}

	public String getCourseOrderId() {
		return courseOrderId;
	}

	public void setCourseOrderId(String courseOrderId) {
		this.courseOrderId = courseOrderId;
	}

	public String getEventOrderId() {
		return eventOrderId;
	}

	public void setEventOrderId(String eventOrderId) {
		this.eventOrderId = eventOrderId;
	}

	public int getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(int invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public LocalDateTime getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(LocalDateTime issuedDate) {
		this.issuedDate = issuedDate;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public UserTransactionBean getUserTransactionBean() {
		return userTransactionBean;
	}

	public void setUserTransactionBean(UserTransactionBean userTransactionBean) {
		this.userTransactionBean = userTransactionBean;
	}

	public JobOrderBean getJobOrderBean() {
		return jobOrderBean;
	}

	public void setJobOrderBean(JobOrderBean jobOrderBean) {
		this.jobOrderBean = jobOrderBean;
	}

//    public CourseOrderBean getCourseOrderBean() {
//        return courseOrderBean;
//    }
//
//    public void setCourseOrderBean(CourseOrderBean courseOrderBean) {
//        this.courseOrderBean = courseOrderBean;
//    }
//
//    public EventOrderBean getEventOrderBean() {
//        return eventOrderBean;
//    }
//
//    public void setEventOrderBean(EventOrderBean eventOrderBean) {
//        this.eventOrderBean = eventOrderBean;
//    }
}

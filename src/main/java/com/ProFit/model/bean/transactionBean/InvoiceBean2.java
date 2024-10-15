//package com.ProFit.model.bean.transactionBean;
//
//import jakarta.persistence.*;
//import java.time.LocalDateTime;
//
//import com.ProFit.model.dto.transactionDTO.InvoiceDTO;
//
//@Entity
//@Table(name = "invoices")
//public class InvoiceBean2 {
//
//	@Id
//	@Column(name = "invoice_number")
//	private String invoiceNumber;
//
//	@Column(name = "transaction_id")
//	private String transactionId;
//
//	@Column(name = "job_order_id")
//	private String jobOrderId;
//
//	@Column(name = "course_order_id")
//	private String courseOrderId;
//
//	@Column(name = "event_order_id")
//	private String eventOrderId;
//
//	@Column(name = "invoice_amount")
//	private int invoiceAmount;
//
//	@Column(name = "issued_date")
//	private LocalDateTime issuedDate;
//
//	@Column(name = "invoice_status")
//	private String invoiceStatus;
//
//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "transaction_id", insertable = false, updatable = false)
//	private UserTransactionBean userTransactionBean;
//
//
//	 public InvoiceBean2(InvoiceDTO dto) {
//	        this.invoiceNumber = dto.getInvoiceNumber();
//	        this.transactionId = dto.getTransactionId();
//	        this.jobOrderId = dto.getJobOrderId();
//	        this.courseOrderId = dto.getCourseOrderId();
//	        this.eventOrderId = dto.getEventOrderId();
//	        this.invoiceAmount = dto.getInvoiceAmount();
//	        this.issuedDate = dto.getIssuedDate();
//	        this.invoiceStatus = dto.getInvoiceStatus();
//	    }
//	 public InvoiceBean2() {
//	    }
//	// Getters 和 Setters
//	public String getInvoiceNumber() {
//		return invoiceNumber;
//	}
//
//	public void setInvoiceNumber(String invoiceNumber) {
//		this.invoiceNumber = invoiceNumber;
//	}
//
//	public String getTransactionId() {
//		return transactionId;
//	}
//
//	public void setTransactionId(String transactionId) {
//		this.transactionId = transactionId;
//	}
//
//	public String getJobOrderId() {
//		return jobOrderId;
//	}
//
//	public void setJobOrderId(String jobOrderId) {
//		this.jobOrderId = jobOrderId;
//	}
//
//	public String getCourseOrderId() {
//		return courseOrderId;
//	}
//
//	public void setCourseOrderId(String courseOrderId) {
//		this.courseOrderId = courseOrderId;
//	}
//
//	public String getEventOrderId() {
//		return eventOrderId;
//	}
//
//	public void setEventOrderId(String eventOrderId) {
//		this.eventOrderId = eventOrderId;
//	}
//
//	public int getInvoiceAmount() {
//		return invoiceAmount;
//	}
//
//	public void setInvoiceAmount(int invoiceAmount) {
//		this.invoiceAmount = invoiceAmount;
//	}
//
//	public LocalDateTime getIssuedDate() {
//		return issuedDate;
//	}
//
//	public void setIssuedDate(LocalDateTime issuedDate) {
//		this.issuedDate = issuedDate;
//	}
//
//	public String getInvoiceStatus() {
//		return invoiceStatus;
//	}
//
//	public void setInvoiceStatus(String invoiceStatus) {
//		this.invoiceStatus = invoiceStatus;
//	}
//
//	public UserTransactionBean getUserTransactionBean() {
//		return userTransactionBean;
//	}
//
//	public void setUserTransactionBean(UserTransactionBean userTransactionBean) {
//		this.userTransactionBean = userTransactionBean;
//	}
//
//	
//}

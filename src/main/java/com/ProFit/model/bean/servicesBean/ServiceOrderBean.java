package com.ProFit.model.bean.servicesBean;

import java.time.LocalDateTime;

import com.ProFit.model.bean.usersBean.Users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="service_order")
public class ServiceOrderBean implements java.io.Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "service_order_id")
    private String serviceOrderId;

    @Column(name="service_application_id")
	private Integer serviceApplicationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_application_id", insertable = false, updatable = false)
    private ServiceApplicationBean serviceApplication;

    @Column (name = "service_order_payby")
    private Integer serviceOrderPayById;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "service_order_payby",insertable = false,updatable = false)
    private Users serviceOrderPayBy;

    @Column(name = "service_order_date")
    private LocalDateTime serviceOrderDate;

    @Column(name = "service_order_status")
    private String status;

    @Column(name = "service_order_notes")
    private String serviceOrderNote;

    @Column(name = "service_order_payment_method")
    private String paymentMethod;

    @Column(name = "service_order_taxID")
    private Integer taxId;

    @Column(name = "service_order_amount")
    private Integer serviceOrderAmount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    //建構子
    public ServiceOrderBean() {
    }

    public ServiceOrderBean(String serviceOrderId, Integer serviceApplicationId, Integer serviceOrderPayById,
            LocalDateTime serviceOrderDate, String status, String serviceOrderNote, String paymentMethod, Integer taxId,
            Integer serviceOrderAmount) {
        this.serviceOrderId = serviceOrderId;
        this.serviceApplicationId = serviceApplicationId;
        this.serviceOrderPayById = serviceOrderPayById;
        this.serviceOrderDate = serviceOrderDate;
        this.status = status;
        this.serviceOrderNote = serviceOrderNote;
        this.paymentMethod = paymentMethod;
        this.taxId = taxId;
        this.serviceOrderAmount = serviceOrderAmount;
    }

    @Override
    public String toString() {
        return "ServiceOrderBean [serviceOrderId=" + serviceOrderId + ", serviceApplicationId=" + serviceApplicationId
                + ", serviceOrderPayById=" + serviceOrderPayById + ", serviceOrderDate=" + serviceOrderDate
                + ", status=" + status + ", serviceOrderNote=" + serviceOrderNote + ", paymentMethod=" + paymentMethod
                + ", taxId=" + taxId + ", serviceOrderAmount=" + serviceOrderAmount + ", createdAt=" + createdAt + "]";
    }

    
}

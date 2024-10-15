package com.ProFit.model.bean.transactionBean;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoices")
public class InvoiceBean {

    @Id
    @Column(name = "invoice_number", length = 50, nullable = false)
    private String invoiceNumber;  // 發票號碼

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", referencedColumnName = "transaction_id")  // 與交易表的關聯
    private UserTransactionBean userTransactionBean;

    @Column(name = "invoice_amount", nullable = false)
    private int invoiceAmount;  // 發票金額

    @Column(name = "issued_date", nullable = false)
    private LocalDateTime issuedDate;  // 發票開具日期

    @Column(name = "invoice_status", nullable = false, length = 10)
    private String invoiceStatus;  // 發票狀態

    // Getters 和 Setters

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public UserTransactionBean getUserTransactionBean() {
        return userTransactionBean;
    }

    public void setUserTransactionBean(UserTransactionBean userTransactionBean) {
        this.userTransactionBean = userTransactionBean;
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
}

package com.ProFit.model.dao.transactionCRUD;

import com.ProFit.model.bean.transactionBean.InvoiceBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceDAORepository extends JpaRepository<InvoiceBean, String> {

    // 查詢所有發票，按日期排序
    List<InvoiceBean> findAllByOrderByIssuedDateDesc();

    // 根據 Transaction ID 查詢發票
    List<InvoiceBean> findByTransactionId(String transactionId);

    // 查詢所有有 Transaction ID 的發票
    List<InvoiceBean> findAllByTransactionIdIsNotNull();

    // 根據 Job Order ID 查詢發票
    List<InvoiceBean> findByJobOrderId(String jobOrderId);

    // 查詢所有有 Job Order ID 的發票
    List<InvoiceBean> findAllByJobOrderIdIsNotNull();

    // 根據 Course Order ID 查詢發票
    List<InvoiceBean> findByCourseOrderId(String courseOrderId);

    // 查詢所有有 Course Order ID 的發票
    List<InvoiceBean> findAllByCourseOrderIdIsNotNull();

    // 根據 Event Order ID 查詢發票
    List<InvoiceBean> findByEventOrderId(String eventOrderId);

    // 查詢所有有 Event Order ID 的發票
    List<InvoiceBean> findAllByEventOrderIdIsNotNull();

	List<InvoiceBean> findByInvoiceNumberAndInvoiceStatus(String invoiceNumber, String invoiceStatus);

	List<InvoiceBean> findByInvoiceNumber(String invoiceNumber);

	List<InvoiceBean> findByInvoiceStatus(String invoiceStatus);
}


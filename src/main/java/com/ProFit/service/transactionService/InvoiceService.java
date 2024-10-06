package com.ProFit.service.transactionService;

import com.ProFit.model.bean.transactionBean.InvoiceBean;
import com.ProFit.model.dao.transactionCRUD.InvoiceDAORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceDAORepository invoiceRepository;

    // 查詢所有發票
    @Transactional(readOnly = true)
    public List<InvoiceBean> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    // 根據發票號碼查詢發票
    @Transactional(readOnly = true)
    public InvoiceBean getInvoiceById(String invoiceNumber) {
        return invoiceRepository.findById(invoiceNumber).orElse(null);
    }

    // 根據條件查詢發票
    @Transactional(readOnly = true)
    public List<InvoiceBean> searchInvoices(String invoiceNumber, String invoiceStatus, String idType, String idValue) {
        // 根據不同的條件調用對應的查詢方法
        if (invoiceNumber != null && !invoiceNumber.isEmpty() && invoiceStatus != null && !invoiceStatus.isEmpty()) {
            // 查詢發票號碼和發票狀態
            return invoiceRepository.findByInvoiceNumberAndInvoiceStatus(invoiceNumber, invoiceStatus);
        } else if (invoiceNumber != null && !invoiceNumber.isEmpty()) {
            // 只查詢發票號碼
            return invoiceRepository.findByInvoiceNumber(invoiceNumber);
        } else if (invoiceStatus != null && !invoiceStatus.isEmpty()) {
            // 只查詢發票狀態
            return invoiceRepository.findByInvoiceStatus(invoiceStatus);
        } else if (idType != null && !idType.isEmpty() && idValue != null && !idValue.isEmpty()) {
            // 根據 idType 查詢對應的訂單ID類型
            switch (idType) {
                case "transaction_id":
                    return invoiceRepository.findByTransactionId(idValue);
                case "job_order_id":
                    return invoiceRepository.findByJobOrderId(idValue);
                case "course_order_id":
                    return invoiceRepository.findByCourseOrderId(idValue);
                case "event_order_id":
                    return invoiceRepository.findByEventOrderId(idValue);
            }
        }
        // 返回所有發票
        return invoiceRepository.findAllByOrderByIssuedDateDesc();
    }


    // 新增發票
    @Transactional
    public boolean insertInvoice(InvoiceBean invoice) {
        try {
            invoice.setIssuedDate(LocalDateTime.now()); // 設置當前日期
            invoiceRepository.save(invoice);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 更新發票
    @Transactional
    public boolean updateInvoice(InvoiceBean invoice) {
        try {
            if (invoiceRepository.existsById(invoice.getInvoiceNumber())) {
                invoiceRepository.save(invoice); // 更新已存在的發票
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 刪除發票
    @Transactional
    public boolean deleteInvoice(String invoiceNumber) {
        try {
            if (invoiceRepository.existsById(invoiceNumber)) {
                invoiceRepository.deleteById(invoiceNumber);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

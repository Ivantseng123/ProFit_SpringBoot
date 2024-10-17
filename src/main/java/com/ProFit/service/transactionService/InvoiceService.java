package com.ProFit.service.transactionService;

import com.ProFit.model.bean.transactionBean.InvoiceBean;
import com.ProFit.model.dto.transactionDTO.InvoiceDTO;
import com.ProFit.model.dao.transactionCRUD.InvoiceDAORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceDAORepository invoiceRepository;

    // 查詢所有發票，並轉換為 DTO
    @Transactional(readOnly = true)
    public List<InvoiceDTO> getAllInvoices() {
        return invoiceRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    // 根據發票號碼查詢發票
    @Transactional(readOnly = true)
    public InvoiceDTO getInvoiceById(String invoiceNumber) {
        InvoiceBean invoiceBean = invoiceRepository.findById(invoiceNumber).orElse(null);
        return invoiceBean != null ? convertToDTO(invoiceBean) : null;
    }

    // 根據條件查詢發票
    @Transactional(readOnly = true)
    public List<InvoiceDTO> searchInvoices(String invoiceNumber, String invoiceStatus, String idType, String idValue) {
        List<InvoiceBean> invoices;
        if (invoiceNumber != null && !invoiceNumber.isEmpty() && invoiceStatus != null && !invoiceStatus.isEmpty()) {
            invoices = invoiceRepository.findByInvoiceNumberAndInvoiceStatus(invoiceNumber, invoiceStatus);
        } else if (invoiceNumber != null && !invoiceNumber.isEmpty()) {
            invoices = invoiceRepository.findByInvoiceNumber(invoiceNumber);
        } else if (invoiceStatus != null && !invoiceStatus.isEmpty()) {
            invoices = invoiceRepository.findByInvoiceStatus(invoiceStatus);
        } else if (idType != null && !idType.isEmpty() && idValue != null && !idValue.isEmpty()) {
            switch (idType) {
                case "transaction_id":
                    invoices = invoiceRepository.findByTransactionId(idValue);
                    break;
                case "job_order_id":
                    invoices = invoiceRepository.findByJobOrderId(idValue);
                    break;
                case "course_order_id":
                    invoices = invoiceRepository.findByCourseOrderId(idValue);
                    break;
                case "event_order_id":
                    invoices = invoiceRepository.findByEventOrderId(idValue);
                    break;
                default:
                    invoices = invoiceRepository.findAllByOrderByIssuedDateDesc();
            }
        } else {
            invoices = invoiceRepository.findAllByOrderByIssuedDateDesc();
        }
        return invoices.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // 新增發票
    @Transactional
    public boolean insertInvoice(InvoiceDTO invoiceDTO) {
        try {
            InvoiceBean invoice = convertToEntity(invoiceDTO);
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
    public boolean updateInvoice(InvoiceDTO invoiceDTO) {
        try {
            if (invoiceRepository.existsById(invoiceDTO.getInvoiceNumber())) {
                InvoiceBean invoice = convertToEntity(invoiceDTO);
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

    // 將實體轉換為 DTO
    private InvoiceDTO convertToDTO(InvoiceBean invoiceBean) {
        InvoiceDTO dto = new InvoiceDTO();
        dto.setInvoiceNumber(invoiceBean.getInvoiceNumber());
        dto.setTransactionId(invoiceBean.getTransactionId());
        dto.setJobOrderId(invoiceBean.getJobOrderId());
        dto.setCourseOrderId(invoiceBean.getCourseOrderId());
        dto.setEventOrderId(invoiceBean.getEventOrderId());
        dto.setInvoiceAmount(invoiceBean.getInvoiceAmount());
        dto.setIssuedDate(invoiceBean.getIssuedDate());
        dto.setInvoiceStatus(invoiceBean.getInvoiceStatus());
        return dto;
    }

    // 將 DTO 轉換為實體
    private InvoiceBean convertToEntity(InvoiceDTO dto) {
        InvoiceBean invoice = new InvoiceBean();
        invoice.setInvoiceNumber(dto.getInvoiceNumber());
        invoice.setTransactionId(dto.getTransactionId());
        invoice.setJobOrderId(dto.getJobOrderId());
        invoice.setCourseOrderId(dto.getCourseOrderId());
        invoice.setEventOrderId(dto.getEventOrderId());
        invoice.setInvoiceAmount(dto.getInvoiceAmount());
        invoice.setIssuedDate(dto.getIssuedDate());
        invoice.setInvoiceStatus(dto.getInvoiceStatus());
        return invoice;
    }
}

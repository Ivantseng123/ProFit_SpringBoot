package com.ProFit.service.transactionService;

import com.ProFit.model.bean.transactionBean.InvoiceBean;
import com.ProFit.model.bean.transactionBean.UserTransactionBean;
import com.ProFit.model.dto.transactionDTO.InvoiceDTO;
import com.ProFit.model.dao.transactionCRUD.InvoiceDAORepository;
import com.ProFit.model.dao.transactionCRUD.UserTransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

	@Autowired
	private InvoiceDAORepository invoiceRepository;
	
	 @Autowired
	private UserTransactionRepository transactionRepository;

	// 查詢所有發票
	public List<InvoiceDTO> getAllInvoices() {
		return invoiceRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	// 根據發票號碼查詢
	public InvoiceDTO getInvoiceById(String invoiceNumber) {
		InvoiceBean invoiceBean = invoiceRepository.findById(invoiceNumber).orElse(null);
		return invoiceBean != null ? convertToDTO(invoiceBean) : null;
	}

	// 根據條件查詢發票
	public List<InvoiceDTO> searchInvoices(String invoiceNumber, String invoiceStatus, String transactionId) {
		List<InvoiceBean> invoices;
		if (invoiceNumber != null && !invoiceNumber.isEmpty()) {
			invoices = invoiceRepository.findByInvoiceNumber(invoiceNumber);
		} else if (invoiceStatus != null && !invoiceStatus.isEmpty()) {
			invoices = invoiceRepository.findByInvoiceStatus(invoiceStatus);
		} else if (transactionId != null && !transactionId.isEmpty()) {
			invoices = invoiceRepository.findByUserTransactionBean_TransactionId(transactionId);
		} else {
			invoices = invoiceRepository.findAllByOrderByIssuedDateDesc();
		}
		return invoices.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	// 新增發票
	public boolean insertInvoice(InvoiceDTO invoiceDTO) {
		try {
			InvoiceBean invoice = convertToEntity(invoiceDTO);
			invoice.setIssuedDate(LocalDateTime.now()); // 設置當前日期
			invoiceRepository.save(invoice);
			return true;
		} catch (Exception e) {
			System.out.println("發票生成失敗：" + e.getMessage());
			e.printStackTrace(); // 打印具體錯誤信息以便排查
			return false;
		}
	}

	// 更新發票
	public boolean updateInvoice(InvoiceDTO invoiceDTO) {
		try {
			if (invoiceRepository.existsById(invoiceDTO.getInvoiceNumber())) {
				InvoiceBean invoice = convertToEntity(invoiceDTO);
				invoiceRepository.save(invoice);
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 刪除發票
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
		String transactionId = null;

		// 檢查 userTransactionBean 是否為 null
		if (invoiceBean.getUserTransactionBean() != null) {
			transactionId = invoiceBean.getUserTransactionBean().getTransactionId();
		}

		return new InvoiceDTO(invoiceBean.getInvoiceNumber(), transactionId, // 這裡可能為 null
				invoiceBean.getInvoiceAmount(), invoiceBean.getIssuedDate(), invoiceBean.getInvoiceStatus());
	}

	// 將 DTO 轉換為實體
	private InvoiceBean convertToEntity(InvoiceDTO dto) {
	    InvoiceBean invoice = new InvoiceBean();
	    invoice.setInvoiceNumber(dto.getInvoiceNumber());
	    invoice.setInvoiceAmount(dto.getInvoiceAmount());
	    invoice.setIssuedDate(dto.getIssuedDate());
	    invoice.setInvoiceStatus(dto.getInvoiceStatus());

	    // 確保 transactionId 正確設置
	    if (dto.getTransactionId() != null) {
	        UserTransactionBean transaction = transactionRepository.findById(dto.getTransactionId())
	            .orElseThrow(() -> new RuntimeException("Transaction not found for ID: " + dto.getTransactionId()));
	        invoice.setUserTransactionBean(transaction); // 關聯交易ID
	    }

	    return invoice;
	}

	
	// 根據用戶ID查詢發票
	public List<InvoiceDTO> getInvoicesByUserId(Integer userId) {
	    List<InvoiceDTO> invoices = invoiceRepository.findInvoicesByUserId(userId);
	    System.out.println("獲取到的發票數量：" + invoices.size()); // 打印發票數量
	    return invoices;
	}
}

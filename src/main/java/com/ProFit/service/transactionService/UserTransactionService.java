package com.ProFit.service.transactionService;

import com.ProFit.model.bean.transactionBean.UserTransactionBean;
import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.dao.transactionCRUD.UserTransactionRepository;
import com.ProFit.model.dto.transactionDTO.InvoiceDTO;
import com.ProFit.model.dto.transactionDTO.UserTransactionDTO;
import com.ProFit.service.userService.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserTransactionService {

	@Autowired
	private UserTransactionRepository transactionRepository;

	@Autowired
	private UserService userService;
	
	@Autowired
	private InvoiceService invoiceService;

	// 獲取所有交易
	public List<UserTransactionBean> getAllTransactions() {
		return transactionRepository.findAll();
	}

	// 根據條件篩選交易
	public List<UserTransactionBean> getTransactionsByFilters(Integer userId, String transactionType,
			String transactionStatus, LocalDateTime startTimestamp, LocalDateTime endTimestamp) {
		if (userId != null) {
			return transactionRepository.findByUserId(userId);
		} else if (transactionType != null && !transactionType.isEmpty()) {
			return transactionRepository.findByTransactionType(transactionType);
		} else if (transactionStatus != null && !transactionStatus.isEmpty()) {
			return transactionRepository.findByTransactionStatus(transactionStatus);
		} else if (startTimestamp != null && endTimestamp != null) {
			return transactionRepository.findByCreatedAtBetween(startTimestamp, endTimestamp);
		}
		return transactionRepository.findAll();
	}

	// 插入交易
	public void insertTransaction(UserTransactionDTO transactionDTO) {
		UserTransactionBean transaction = new UserTransactionBean(transactionDTO.getUserId(),
				transactionDTO.getTransactionRole(), transactionDTO.getTransactionType(), transactionDTO.getOrderId(),
				transactionDTO.getTotalAmount(), transactionDTO.getPlatformFee(), transactionDTO.getTargetIncome(),
				transactionDTO.getTransactionStatus(), transactionDTO.getPaymentMethod(),
				transactionDTO.getReferenceId());

		// 更新用戶餘額
		Users user = userService.getUserInfoByID(transactionDTO.getUserId());
		if (user != null) {
			Integer totalAmount = transactionDTO.getTargetIncome().intValue(); // 假設 totalAmount 是整數

			// 根據交易類型調整餘額，例如存入增加餘額，取出減少餘額
			if ("deposit".equals(transactionDTO.getTransactionType())) {
				user.setUserBalance(user.getUserBalance() + totalAmount);
			} else if ("withdrawal".equals(transactionDTO.getTransactionType())) {
				user.setUserBalance(user.getUserBalance() - totalAmount);
			} 
			// 保存更新後的用戶信息
			userService.updateUserBalance(user);
		}

		// 保存交易
		transactionRepository.save(transaction);
		
		// 檢查交易類型是否為 'payment'，並生成發票
        if ("payment".equals(transactionDTO.getTransactionType())) {
        	// 確保在保存交易之後，能拿到 transactionId
            transactionDTO.setTransactionId(transaction.getTransactionId());
            generateInvoice(transactionDTO);
        }
	}
	
	
	//生成發票
	private void generateInvoice(UserTransactionDTO transactionDTO) {
	    InvoiceDTO invoiceDTO = new InvoiceDTO();
	    invoiceDTO.setInvoiceNumber(generateInvoiceNumber());  // 生成一個唯一的發票號
	    invoiceDTO.setInvoiceAmount(transactionDTO.getTotalAmount().intValue());  // 使用交易總金額作為發票金額
	    invoiceDTO.setInvoiceStatus("open");  // 設置發票狀態
	    invoiceDTO.setTransactionId(transactionDTO.getTransactionId());  // 設置對應的交易 ID

	    // 調用 InvoiceService 生成發票
	    invoiceService.insertInvoice(invoiceDTO);
	}

	// 生成唯一發票號的簡單方法
	private String generateInvoiceNumber() {
	    return "INV-" + System.currentTimeMillis();
	}
	
	// 更新交易
	public void updateTransaction(UserTransactionDTO transactionDTO) {
		// 根據 transactionId 查找原有的交易記錄
		UserTransactionBean existingTransaction = transactionRepository.findById(transactionDTO.getTransactionId())
				.orElseThrow(() -> new RuntimeException("交易記錄不存在，無法更新"));

		// 更新用戶餘額
		Users user = userService.getUserInfoByID(transactionDTO.getUserId());
		if (user != null) {
			// 舊的 target_income 和新的 target_income
			Double oldIncome = existingTransaction.getTargetIncome();
			Double newIncome = transactionDTO.getTargetIncome();

			// 確保 oldIncome 和 newIncome 都不為 null，如果為 null，設置為 0
			int oldIncomeInt = oldIncome != null ? oldIncome.intValue() : 0;
			int newIncomeInt = newIncome != null ? newIncome.intValue() : 0;
			int difference = newIncomeInt - oldIncomeInt;

			// 根據交易類型更新餘額
			if ("deposit".equals(transactionDTO.getTransactionType())) {
				user.setUserBalance(user.getUserBalance() + difference);
			} else if ("withdrawal".equals(transactionDTO.getTransactionType())) {
				user.setUserBalance(user.getUserBalance() - difference);
			}

			// 保存更新後的用戶信息
			userService.updateUserBalance(user);
		}

		// 更新交易記錄
		existingTransaction.setTransactionRole(transactionDTO.getTransactionRole());
		existingTransaction.setTransactionType(transactionDTO.getTransactionType());
		existingTransaction.setTotalAmount(transactionDTO.getTotalAmount());
		existingTransaction.setPlatformFee(transactionDTO.getPlatformFee());
		existingTransaction.setTargetIncome(transactionDTO.getTargetIncome());
		existingTransaction.setTransactionStatus(transactionDTO.getTransactionStatus());
		existingTransaction.setPaymentMethod(transactionDTO.getPaymentMethod());
		existingTransaction.setReferenceId(transactionDTO.getReferenceId());

		// 如果狀態變更為已完成，則設置完成時間
		if ("completed".equals(transactionDTO.getTransactionStatus())
				&& existingTransaction.getCompletionAt() == null) {
			existingTransaction.setCompletionAt(LocalDateTime.now());
		}

		// 保存更新後的交易記錄
		transactionRepository.save(existingTransaction);

	}

	// 刪除交易
	public void deleteTransaction(String transactionId) {
		// 查找交易記錄
		UserTransactionBean transaction = transactionRepository.findById(transactionId)
				.orElseThrow(() -> new RuntimeException("交易記錄不存在，無法刪除"));

		// 獲取交易對應的用戶
		Users user = userService.getUserInfoByID(transaction.getUserId());
		if (user != null) {
			Integer targetIncome = transaction.getTargetIncome().intValue(); // 假設 targetIncome 是整數

			// 根據交易類型調整用戶餘額，取消交易的影響
			if ("deposit".equals(transaction.getTransactionType())) {
				user.setUserBalance(user.getUserBalance() - targetIncome);
			} else if ("withdrawal".equals(transaction.getTransactionType())
					|| "payment".equals(transaction.getTransactionType())) {
				user.setUserBalance(user.getUserBalance() + targetIncome);
			}

			// 保存更新後的用戶信息
			userService.updateUserBalance(user);
		}

		// 刪除交易
		transactionRepository.deleteById(transactionId);
	}

	// 實體轉換為 DTO
	public UserTransactionDTO convertToDTO(UserTransactionBean transaction) {
		return new UserTransactionDTO(transaction);
	}

	public String getUserAccountNumber() {
		// TODO Auto-generated method stub
		return null;
	}

	public double calculatePaidAmount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double calculatePendingAmount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void saveUserAccountNumber(String accountNumber) {
		// TODO Auto-generated method stub
		
	}

}

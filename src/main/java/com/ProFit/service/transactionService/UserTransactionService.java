package com.ProFit.service.transactionService;

import com.ProFit.model.bean.transactionBean.UserTransactionBean;
import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.dao.transactionCRUD.UserTransactionRepository;
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

    // 獲取所有交易
    public List<UserTransactionBean> getAllTransactions() {
        return transactionRepository.findAll();
    }

    // 根據條件篩選交易
    public List<UserTransactionBean> getTransactionsByFilters(
            Integer userId, String transactionType, String transactionStatus, LocalDateTime startTimestamp, LocalDateTime endTimestamp) {
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
        UserTransactionBean transaction = new UserTransactionBean(
            transactionDTO.getUserId(),
            transactionDTO.getTransactionRole(),
            transactionDTO.getTransactionType(),
            transactionDTO.getOrderId(),
            transactionDTO.getTotalAmount(),
            transactionDTO.getPlatformFee(),
            transactionDTO.getTargetIncome(),
            transactionDTO.getTransactionStatus(),
            transactionDTO.getPaymentMethod(),
            transactionDTO.getReferenceId()
        );

        // 更新用戶餘額
        Users user = userService.getUserInfoByID(transactionDTO.getUserId());
        if (user != null) {
            Integer totalAmount = transactionDTO.getTotalAmount().intValue();  // 假設 totalAmount 是整數

            // 根據交易類型調整餘額，例如存入增加餘額，取出減少餘額
            if ("deposit".equals(transactionDTO.getTransactionType())) {
                user.setUserBalance(user.getUserBalance() + totalAmount);
            } else if ("withdrawal".equals(transactionDTO.getTransactionType())) {
                user.setUserBalance(user.getUserBalance() - totalAmount);
            } else if ("payment".equals(transactionDTO.getTransactionType())) {
                user.setUserBalance(user.getUserBalance() - totalAmount);
            }
            
            // 保存更新後的用戶信息
            userService.updateUserBalance(user);
        }

        // 保存交易
        transactionRepository.save(transaction);
    }


 // 更新交易
    public void updateTransaction(UserTransactionDTO transactionDTO) {
        // 根據 transactionId 查找原有的交易記錄
        UserTransactionBean existingTransaction = transactionRepository.findById(transactionDTO.getTransactionId())
            .orElseThrow(() -> new RuntimeException("交易記錄不存在，無法更新"));

        // 更新用戶餘額
     // 更新用戶餘額
        Users user = userService.getUserInfoByID(transactionDTO.getUserId());
        if (user != null) {
            // 如果是 Double 類型，可以檢查是否為 null
            Double oldAmount = existingTransaction.getTotalAmount();
            Double newAmount = transactionDTO.getTotalAmount();

            // 確保 oldAmount 和 newAmount 都不為 null，如果為 null，設置為 0
            int oldAmountInt = oldAmount != null ? oldAmount.intValue() : 0;
            int newAmountInt = newAmount != null ? newAmount.intValue() : 0;
            int difference = newAmountInt - oldAmountInt;

            // 根據交易類型更新餘額
            if ("deposit".equals(transactionDTO.getTransactionType())) {
                user.setUserBalance(user.getUserBalance() + difference);
            } else if ("withdrawal".equals(transactionDTO.getTransactionType()) || 
                       "payment".equals(transactionDTO.getTransactionType())) {
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
        if ("completed".equals(transactionDTO.getTransactionStatus()) && existingTransaction.getCompletionAt() == null) {
            existingTransaction.setCompletionAt(LocalDateTime.now());
        }

        // 保存更新後的交易記錄
        transactionRepository.save(existingTransaction);

    }



    // 刪除交易
    public void deleteTransaction(String transactionId) {
        transactionRepository.deleteById(transactionId);
    }

    // DTO 轉換為實體
    public UserTransactionBean convertToEntity(UserTransactionDTO dto) {
        UserTransactionBean transaction = new UserTransactionBean();
        transaction.setTransactionId(dto.getTransactionId());
        transaction.setUserId(dto.getUserId());
        transaction.setTransactionType(dto.getTransactionType());
        transaction.setTransactionAmount(dto.getTransactionAmount());
        transaction.setTransactionStatus(dto.getTransactionStatus());
        transaction.setCreatedAt(dto.getCreatedAt());
        transaction.setCompletionAt(dto.getCompletionAt());
        return transaction;
    }

    // 實體轉換為 DTO
    public UserTransactionDTO convertToDTO(UserTransactionBean transaction) {
        UserTransactionDTO dto = new UserTransactionDTO();
        dto.setTransactionId(transaction.getTransactionId());
        dto.setUserId(transaction.getUserId());
        dto.setTransactionType(transaction.getTransactionType());
        dto.setTransactionAmount(transaction.getTransactionAmount());
        dto.setTransactionStatus(transaction.getTransactionStatus());
        dto.setCreatedAt(transaction.getCreatedAt());
        dto.setCompletionAt(transaction.getCompletionAt());
        return dto;
    }
}

package com.ProFit.service.transactionService;

import com.ProFit.model.bean.transactionBean.UserTransactionBean;
import com.ProFit.model.dao.transactionCRUD.UserTransactionRepository;
import com.ProFit.model.dto.transactionDTO.UserTransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserTransactionService {

    @Autowired
    private UserTransactionRepository transactionRepository;

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
    public void insertTransaction(UserTransactionBean transaction) {
        transactionRepository.save(transaction);
    }

    // 更新交易
    public void updateTransaction(UserTransactionBean transaction) {
        if ("completed".equals(transaction.getTransactionStatus()) && transaction.getCompletionAt() == null) {
            transaction.setCompletionAt(LocalDateTime.now());
        }
        transactionRepository.save(transaction);
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

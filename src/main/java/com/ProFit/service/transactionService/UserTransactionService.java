package com.ProFit.service.transactionService;

import com.ProFit.model.bean.transactionBean.UserTransactionBean;
import com.ProFit.model.dao.transactionCRUD.UserTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserTransactionService {

    @Autowired
    private UserTransactionRepository transactionRepository;

    public List<UserTransactionBean> getAllTransactions() {
        return transactionRepository.findAll();
    }

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



    public void insertTransaction(UserTransactionBean transaction) {
        transactionRepository.save(transaction);
    }

    public void updateTransaction(UserTransactionBean transaction) {
        // 當交易狀態為 "completed" 時，自動填入完成時間
        if ("completed".equals(transaction.getTransactionStatus()) && transaction.getCompletionAt() == null) {
            transaction.setCompletionAt(LocalDateTime.now());
        }
        transactionRepository.save(transaction);
    }

    public void deleteTransaction(String transactionId) {
        transactionRepository.deleteById(transactionId);
    }

    public UserTransactionBean getTransactionById(String transactionId) {
        return transactionRepository.findById(transactionId).orElse(null);
    }
}

package com.ProFit.model.dao.transactionCRUD;

import com.ProFit.model.bean.transactionBean.UserTransactionBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserTransactionRepository extends JpaRepository<UserTransactionBean, String> {

    // 根據用戶ID查詢交易
    List<UserTransactionBean> findByUserId(Integer userId);

    // 根據交易類型查詢
    List<UserTransactionBean> findByTransactionType(String transactionType);

    // 根據交易狀態查詢
    List<UserTransactionBean> findByTransactionStatus(String transactionStatus);

    // 根據創建時間範圍查詢
    List<UserTransactionBean> findByCreatedAtBetween(LocalDateTime startTimestamp, LocalDateTime endTimestamp);

    // 根據交易類型和交易狀態查詢
    List<UserTransactionBean> findByTransactionTypeAndTransactionStatus(String transactionType, String transactionStatus);

    // 根據用戶ID和交易類型查詢
    List<UserTransactionBean> findByUserIdAndTransactionType(Integer userId, String transactionType);

    // 根據用戶ID和創建時間範圍查詢
    List<UserTransactionBean> findByUserIdAndCreatedAtBetween(Integer userId, LocalDateTime startTimestamp, LocalDateTime endTimestamp);

    // 根據用戶ID和完成時間範圍查詢
    List<UserTransactionBean> findByUserIdAndCompletionAtBetween(Integer userId, LocalDateTime startTimestamp, LocalDateTime endTimestamp);

    // 根據用戶ID和交易狀態查詢
    List<UserTransactionBean> findByUserIdAndTransactionStatus(Integer userId, String transactionStatus);

    // 根據用戶ID、交易類型和交易狀態查詢
    List<UserTransactionBean> findByUserIdAndTransactionTypeAndTransactionStatus(Integer userId, String transactionType, String transactionStatus);

    // 根據交易ID查詢交易
    Optional<UserTransactionBean> findById(String transactionId);
}

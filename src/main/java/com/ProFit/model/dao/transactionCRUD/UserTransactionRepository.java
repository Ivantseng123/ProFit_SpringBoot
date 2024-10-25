package com.ProFit.model.dao.transactionCRUD;

import com.ProFit.model.bean.transactionBean.UserTransactionBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserTransactionRepository extends JpaRepository<UserTransactionBean, String> {
    List<UserTransactionBean> findByUserId(Integer userId);
    List<UserTransactionBean> findByTransactionType(String transactionType);
    List<UserTransactionBean> findByTransactionStatus(String transactionStatus);
    List<UserTransactionBean> findByCreatedAtBetween(LocalDateTime startTimestamp, LocalDateTime endTimestamp);
	List<UserTransactionBean> findByTransactionTypeAndTransactionStatus(String string, String string2);
	Collection<UserTransactionBean> findByUserIdAndTransactionType(Integer userId, String transactionType);
	List<UserTransactionBean> findByUserIdAndCreatedAtBetween(Integer userId, LocalDateTime atStartOfDay,
			LocalDateTime atTime);
	List<UserTransactionBean> findByUserIdAndCompletionAtBetween(Integer userId, LocalDateTime atStartOfDay,
			LocalDateTime atTime);
	Collection<UserTransactionBean> findByUserIdAndTransactionStatus(Integer userId, String string);
	Collection<UserTransactionBean> findByUserIdAndTransactionTypeAndTransactionStatus(Integer userId, String string,
			String string2);
	Optional<UserTransactionBean> findById(String transactionId);
}

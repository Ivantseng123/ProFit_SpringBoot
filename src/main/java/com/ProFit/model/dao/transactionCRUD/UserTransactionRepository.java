	package com.ProFit.model.dao.transactionCRUD;
	
	import com.ProFit.model.bean.transactionBean.UserTransactionBean;
	import org.springframework.data.jpa.repository.JpaRepository;
	import org.springframework.stereotype.Repository;
	
	import java.time.LocalDateTime;
	import java.util.List;
	
	@Repository
	public interface UserTransactionRepository extends JpaRepository<UserTransactionBean, String> {
	    List<UserTransactionBean> findByUserId(Integer userId);
	    List<UserTransactionBean> findByTransactionType(String transactionType);
	    List<UserTransactionBean> findByTransactionStatus(String transactionStatus);
	    List<UserTransactionBean> findByCreatedAtBetween(LocalDateTime startTimestamp, LocalDateTime endTimestamp);
	}

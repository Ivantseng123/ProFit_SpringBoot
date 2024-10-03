package com.ProFit.model.dao.usersCRUD;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ProFit.model.bean.usersBean.Pwd_reset_tokens;

public interface PwdResetTokenRepository extends JpaRepository<Pwd_reset_tokens, Integer> {
	
	@Query("SELECT p FROM Pwd_reset_tokens p WHERE p.user.userEmail LIKE %?1%")
	Page<Pwd_reset_tokens> findByUserEmailContaining(String userEmail, Pageable pageable);
}
	
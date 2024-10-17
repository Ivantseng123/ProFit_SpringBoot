package com.ProFit.model.dao.usersCRUD;

import java.util.Optional;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ProFit.model.bean.usersBean.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {
	
	Optional<Users> findByVerificationCode(String verificationCode);
	
	Optional<Users> findByUserEmailAndUserPasswordHash(String userEmail,String userPasswordHash);
	
	Optional<Users> findByUserEmail(String userEmail);
	
//	@Query("from Users where name = ?1")
//	List<Users> findUserByEmail(String userEmail);
	
	@Query("SELECT u FROM Users u WHERE (u.userName LIKE %:search% OR u.userEmail LIKE %:search%) AND u.userIdentity = :userIdentity")
	Page<Users> findBySearchAndUserIdentity(@Param("search") String search, @Param("userIdentity") Integer userIdentity, Pageable pageable);


	Page<Users> findByUserNameContainingOrUserEmailContaining(String userName, String userEmail, Pageable pageable);
	
	Page<Users> findByUserIdentity(Integer userIdentity, Pageable pageable);

	//用來更新用戶餘額
	
}

package com.ProFit.model.dao.usersCRUD;

import java.util.Optional;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ProFit.model.bean.usersBean.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {
	
	Optional<Users> findByVerificationCode(String verificationCode);
	
	Optional<Users> findByUserEmailAndUserPasswordHash(String userEmail,String userPasswordHash);
	
	Optional<Users> findByUserEmail(String userEmail);
	
//	@Query("from Users where name = ?1")
//	List<Users> findUserByEmail(String userEmail);
	
	Page<Users> findByUserNameContainingOrUserEmailContaining(String userName, String userEmail, Pageable pageable);

	
}

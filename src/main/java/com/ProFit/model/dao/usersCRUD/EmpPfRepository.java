package com.ProFit.model.dao.usersCRUD;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.ProFit.model.bean.usersBean.Employer_profile;
import com.ProFit.model.bean.usersBean.Users;

import java.util.List;
import java.util.Optional;


public interface EmpPfRepository extends JpaRepository<Employer_profile, Integer> {
	
	@Query("SELECT e FROM Employer_profile e WHERE e.user.userEmail LIKE %?1%OR e.companyName LIKE %?2%")
	Page<Employer_profile> findByUserEmailOrCompanyNameContaining(String userEmail, String companyName, Pageable pageable);
	
	Employer_profile findByUserId(int userId);
	
}

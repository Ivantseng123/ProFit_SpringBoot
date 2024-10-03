package com.ProFit.model.dao.usersCRUD;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.ProFit.model.bean.usersBean.Employer_application;

public interface EmpApplRepository extends JpaRepository<Employer_application, Integer> {

	@Query("SELECT e FROM Employer_application e WHERE e.user.userEmail LIKE %?1%OR e.companyName LIKE %?2%")
	Page<Employer_application> findByUserEmailOrCompanyNameContaining(String userEmail, String companyName, Pageable pageable);
}

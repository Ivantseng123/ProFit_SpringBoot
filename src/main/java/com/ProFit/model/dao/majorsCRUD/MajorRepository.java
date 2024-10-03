package com.ProFit.model.dao.majorsCRUD;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ProFit.model.bean.majorsBean.MajorBean;

public interface MajorRepository extends JpaRepository<MajorBean, Integer> {

	List<MajorBean> findByMajorCategoryId(Integer majorCategoryId);
	
	@Query("SELECT m FROM MajorBean m WHERE m.majorName LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<MajorBean> findByMajorNameContainingIgnoreCase(String keyword);
}

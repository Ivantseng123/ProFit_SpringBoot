package com.ProFit.model.dao.majorsCRUD;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ProFit.model.bean.majorsBean.MajorBean;

public interface MajorRepository extends JpaRepository<MajorBean, Integer> {

	List<MajorBean> findByMajorCategoryId(Integer majorCategoryId);
}

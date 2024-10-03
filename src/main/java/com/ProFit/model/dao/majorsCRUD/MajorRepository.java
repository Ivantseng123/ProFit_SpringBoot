package com.ProFit.model.dao.majorsCRUD;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ProFit.model.bean.majorsBean.MajorBean;

public interface MajorRepository extends JpaRepository<MajorBean, Integer> {

}

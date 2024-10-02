package com.ProFit.model.dao.majorsCRUD;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ProFit.model.bean.majorsBean.MajorCategoryBean;

public interface MajorCategoryRepsitory  extends JpaRepository<MajorCategoryBean,Integer>{

}

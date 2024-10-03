package com.ProFit.model.dao.majorsCRUD;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ProFit.model.bean.majorsBean.MajorCategoryBean;

public interface MajorCategoryRepository  extends JpaRepository<MajorCategoryBean,Integer>{

}

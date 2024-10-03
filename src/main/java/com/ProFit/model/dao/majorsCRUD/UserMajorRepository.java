package com.ProFit.model.dao.majorsCRUD;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ProFit.model.bean.majorsBean.UserMajorBean;
import com.ProFit.model.bean.majorsBean.UserMajorPK;

public interface UserMajorRepository extends JpaRepository<UserMajorBean, UserMajorPK> {

}

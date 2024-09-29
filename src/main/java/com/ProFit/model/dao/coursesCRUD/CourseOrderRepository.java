package com.ProFit.model.dao.coursesCRUD;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ProFit.model.bean.coursesBean.CourseOrderBean;

public interface CourseOrderRepository extends JpaRepository<CourseOrderBean, String> {

}

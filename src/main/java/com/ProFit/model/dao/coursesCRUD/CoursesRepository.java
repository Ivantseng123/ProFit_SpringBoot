package com.ProFit.model.dao.coursesCRUD;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ProFit.model.bean.coursesBean.CourseBean;

public interface CoursesRepository extends JpaRepository<CourseBean, String> {

}

package com.ProFit.model.dao.coursesCRUD;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ProFit.model.bean.coursesBean.CourseLessonBean;

public interface CourseLessonRepository extends JpaRepository<CourseLessonBean, Integer> {

}

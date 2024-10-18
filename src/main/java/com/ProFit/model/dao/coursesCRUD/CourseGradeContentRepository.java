package com.ProFit.model.dao.coursesCRUD;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.ProFit.model.bean.coursesBean.CourseGradeContentBean;

public interface CourseGradeContentRepository extends JpaRepository<CourseGradeContentBean, Integer> {

        @Query("SELECT c FROM CourseGradeContentBean c WHERE " +
                        "(:courseId IS NULL OR c.courseId = :courseId)")
        Page<CourseGradeContentBean> findByCourseId(
                        @Param("courseId") String courseId,
                        Pageable pageable);
}

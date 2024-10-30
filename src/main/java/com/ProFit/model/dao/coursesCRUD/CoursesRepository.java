package com.ProFit.model.dao.coursesCRUD;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ProFit.model.bean.coursesBean.CourseBean;
import com.ProFit.model.bean.coursesBean.CourseGradeContentBean;

public interface CoursesRepository extends JpaRepository<CourseBean, String> {

        @Query("SELECT c FROM CourseBean c JOIN c.courseCreater u WHERE " +
                        "(:courseName IS NULL OR c.courseName LIKE %:courseName%) AND " +
                        "(:userName IS NULL OR u.userName LIKE %:userName%) AND " +
                        "(:status IS NULL OR :status = '' OR c.courseStatus = :status) AND " +
                        "(:userId IS NULL OR c.courseCreateUserId = :userId) AND " +
                        "(:category IS NULL OR c.courseCategory = :category)")
        Page<CourseBean> searchCoursesPage(
                        @Param("courseName") String courseName,
                        @Param("userName") String userName,
                        @Param("status") String status,
                        @Param("userId") Integer userId,
                        @Param("category") Integer category,
                        Pageable pageable);

        // 依照類別計算課程數量
        @Query("SELECT m.categoryName, COUNT(c) FROM CourseBean c JOIN MajorCategoryBean m ON c.courseCategory = m.majorCategoryId GROUP BY m.categoryName")
        List<Object[]> getCourseCountByCategoryName();

}

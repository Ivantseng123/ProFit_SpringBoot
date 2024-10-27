package com.ProFit.model.dao.coursesCRUD;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.ProFit.model.bean.coursesBean.CourseOrderBean;
import java.util.List;

public interface CourseOrderRepository extends JpaRepository<CourseOrderBean, String> {

    @Query("SELECT co from CourseOrderBean co WHERE" +
            "(:courseId IS NULL OR co.courseId LIKE %:courseId%) AND" +
            "(:studentId IS NULL OR co.studentId = :studentId) AND" +
            "(:courseOrderStatus IS NULL OR co.courseOrderStatus LIKE %:courseOrderStatus%)")
    List<CourseOrderBean> searchCourseOrders(
            @Param("courseId") String courseId,
            @Param("studentId") Integer studentId,
            @Param("courseOrderStatus") String courseOrderStatus);

    //訂單查詢訂單金額
     @Query("SELECT c.courseOrderPrice FROM CourseOrderBean c WHERE c.courseOrderId = :orderId")
     Integer findOrderAmountById(@Param("orderId") String orderId);
    
}

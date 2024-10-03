package com.ProFit.model.dao.transactionCRUD;

import com.ProFit.model.bean.transactionBean.JobOrderBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface JobOrderRepository extends JpaRepository<JobOrderBean, String> {

    // 根據職缺申請ID查詢
    List<JobOrderBean> findByJobApplicationId(Integer jobApplicationId);

    // 根據狀態查詢
    List<JobOrderBean> findByJobOrderStatus(String jobOrderStatus);

    // 根據日期範圍查詢
    List<JobOrderBean> findByJobOrderDateBetween(Timestamp startDate, Timestamp endDate);

    // 自定義查詢條件：職缺ID + 日期範圍 + 狀態
    List<JobOrderBean> findByJobApplicationIdAndJobOrderDateBetweenAndJobOrderStatus(Integer jobApplicationId, Timestamp startDate, Timestamp endDate, String jobOrderStatus);
}

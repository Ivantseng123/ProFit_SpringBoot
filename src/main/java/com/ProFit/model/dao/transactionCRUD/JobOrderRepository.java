package com.ProFit.model.dao.transactionCRUD;

import com.ProFit.model.bean.transactionBean.JobOrderBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface JobOrderRepository extends JpaRepository<JobOrderBean, String> {

    List<JobOrderBean> findByJobApplicationId(Integer jobApplicationId);

    List<JobOrderBean> findByJobOrderStatus(String jobOrderStatus);

    List<JobOrderBean> findByJobOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<JobOrderBean> findByJobApplicationIdAndJobOrderDateBetweenAndJobOrderStatus(Integer jobApplicationId, LocalDateTime startDate, LocalDateTime endDate, String jobOrderStatus);
}

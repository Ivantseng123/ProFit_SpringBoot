package com.ProFit.service.transactionService;

import com.ProFit.model.bean.transactionBean.JobOrderBean;
import com.ProFit.model.dao.transactionCRUD.JobOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
public class JobOrderService {

    @Autowired
    private JobOrderRepository jobOrderRepository;

    @Transactional(readOnly = true)
    public List<JobOrderBean> getAllOrders() {
        return jobOrderRepository.findAll();
    }

    @Transactional
    public void insertOrder(JobOrderBean order) {
        jobOrderRepository.save(order);
    }

    @Transactional
    public void updateOrder(JobOrderBean order) {
        jobOrderRepository.save(order);
    }

    @Transactional
    public void deleteOrder(String jobOrdersId) {
        jobOrderRepository.deleteById(jobOrdersId);
    }

    @Transactional(readOnly = true)
    public JobOrderBean getOrderById(String jobOrdersId) {
        return jobOrderRepository.findById(jobOrdersId).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<JobOrderBean> searchOrdersByCriteria(Integer jobApplicationId, Timestamp startDate, Timestamp endDate, String jobOrderStatus) {
        return jobOrderRepository.findByJobApplicationIdAndJobOrderDateBetweenAndJobOrderStatus(jobApplicationId, startDate, endDate, jobOrderStatus);
    }
}

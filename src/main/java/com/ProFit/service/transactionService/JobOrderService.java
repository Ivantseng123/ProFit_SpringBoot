package com.ProFit.service.transactionService;

import com.ProFit.model.bean.transactionBean.JobOrderBean;
import com.ProFit.model.dao.transactionCRUD.JobOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class JobOrderService {

    @Autowired
    private JobOrderRepository jobOrderRepository;

    // 查詢所有訂單
    @Transactional(readOnly = true)
    public List<JobOrderBean> getAllOrders() {
        return jobOrderRepository.findAll();
    }

    // 根據ID獲取訂單
    @Transactional(readOnly = true)
    public JobOrderBean getOrderById(String jobOrdersId) {
        return jobOrderRepository.findById(jobOrdersId).orElse(null); 
    }

    // 根據條件篩選訂單
    @Transactional(readOnly = true)
    public List<JobOrderBean> searchOrdersByCriteria(Integer jobApplicationId, LocalDateTime startDate, LocalDateTime endDate, String jobOrderStatus) {
        if (jobApplicationId != null && startDate != null && endDate != null && jobOrderStatus != null) {
            // 根據職缺ID、日期範圍和狀態篩選
            return jobOrderRepository.findByJobApplicationIdAndJobOrderDateBetweenAndJobOrderStatus(jobApplicationId, startDate, endDate, jobOrderStatus);
        } else if (jobApplicationId != null) {
            // 根據職缺申請ID篩選
            return jobOrderRepository.findByJobApplicationId(jobApplicationId);
        } else if (startDate != null && endDate != null) {
            // 根據日期範圍篩選
            return jobOrderRepository.findByJobOrderDateBetween(startDate, endDate);
        } else if (jobOrderStatus != null) {
            // 根據狀態篩選
            return jobOrderRepository.findByJobOrderStatus(jobOrderStatus);
        }
        return jobOrderRepository.findAll();  // 如果沒有篩選條件，則返回所有訂單
    }
    
    // 新增訂單
    @Transactional
    public void insertOrder(JobOrderBean order) {
        if (order.getJobOrdersId() == null || order.getJobOrdersId().isEmpty()) {
            order.setJobOrdersId(UUID.randomUUID().toString());  // 自動生成UUID
        }
        order.setJobOrderDate(LocalDateTime.now());  // 使用 LocalDateTime.now() 設置當前時間
        jobOrderRepository.save(order);
    }

    // 更新訂單
    @Transactional
    public void updateOrder(JobOrderBean order) {
        if (order.getJobOrderDate() == null) {
            order.setJobOrderDate(LocalDateTime.now()); // 如果沒有設置日期，則設置為當前時間
        }
        jobOrderRepository.save(order);  // 更新訂單
    }


    // 刪除訂單
    @Transactional
    public void deleteOrder(String jobOrdersId) {
        jobOrderRepository.deleteById(jobOrdersId);  // 根據ID刪除
    }

   
}

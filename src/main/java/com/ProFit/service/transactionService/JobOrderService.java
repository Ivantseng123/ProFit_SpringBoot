package com.ProFit.service.transactionService;

import com.ProFit.model.dto.transactionDTO.JobOrderDTO;
import com.ProFit.model.bean.transactionBean.JobOrderBean;
import com.ProFit.model.dao.transactionCRUD.JobOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class JobOrderService {

    @Autowired
    private JobOrderRepository jobOrderRepository;

    @Transactional(readOnly = true)
    public List<JobOrderDTO> getAllOrdersAsDTO() {
        return jobOrderRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<JobOrderDTO> searchOrdersByCriteriaDTO(Integer jobApplicationId, LocalDateTime startDate, LocalDateTime endDate, String jobOrderStatus) {
        List<JobOrderBean> result;
        if (jobApplicationId != null && startDate != null && endDate != null && jobOrderStatus != null) {
            result = jobOrderRepository.findByJobApplicationIdAndJobOrderDateBetweenAndJobOrderStatus(jobApplicationId, startDate, endDate, jobOrderStatus);
        } else if (jobApplicationId != null) {
            result = jobOrderRepository.findByJobApplicationId(jobApplicationId);
        } else if (startDate != null && endDate != null) {
            result = jobOrderRepository.findByJobOrderDateBetween(startDate, endDate);
        } else if (jobOrderStatus != null) {
            result = jobOrderRepository.findByJobOrderStatus(jobOrderStatus);
        } else {
            result = jobOrderRepository.findAll();
        }
        return result.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional
    public void insertOrderFromDTO(JobOrderDTO jobOrderDTO) {
        JobOrderBean jobOrderBean = convertToEntity(jobOrderDTO);
        
        // 確保在插入時自動生成 jobOrdersId
        if (jobOrderBean.getJobOrdersId() == null || jobOrderBean.getJobOrdersId().isEmpty()) {
            jobOrderBean.setJobOrdersId(UUID.randomUUID().toString());  // 自動生成 UUID 作為主鍵
        }

        jobOrderBean.setJobOrderDate(LocalDateTime.now());  // 設置當前時間
        jobOrderRepository.save(jobOrderBean);
    }

    @Transactional
    public void updateOrderFromDTO(JobOrderDTO jobOrderDTO) {
        JobOrderBean jobOrderBean = convertToEntity(jobOrderDTO);

        // 更新時不需生成新 UUID，但確保有 jobOrderDate
        if (jobOrderBean.getJobOrderDate() == null) {
            jobOrderBean.setJobOrderDate(LocalDateTime.now());
        }

        jobOrderRepository.save(jobOrderBean);
    }

    @Transactional
    public void deleteOrder(String jobOrdersId) {
        jobOrderRepository.deleteById(jobOrdersId);
    }

    // 將實體轉換為 DTO
    private JobOrderDTO convertToDTO(JobOrderBean jobOrderBean) {
        JobOrderDTO dto = new JobOrderDTO();
        dto.setJobOrdersId(jobOrderBean.getJobOrdersId());
        dto.setJobApplicationId(jobOrderBean.getJobApplicationId());
        dto.setJobOrderDate(jobOrderBean.getJobOrderDate());
        dto.setJobOrderStatus(jobOrderBean.getJobOrderStatus());
        dto.setJobNotes(jobOrderBean.getJobNotes());
        dto.setJobAmount(jobOrderBean.getJobAmount());
        return dto;
    }

    // 將 DTO 轉換為實體
    private JobOrderBean convertToEntity(JobOrderDTO jobOrderDTO) {
        JobOrderBean entity = new JobOrderBean();
        entity.setJobOrdersId(jobOrderDTO.getJobOrdersId());
        entity.setJobApplicationId(jobOrderDTO.getJobApplicationId());
        entity.setJobOrderDate(jobOrderDTO.getJobOrderDate());
        entity.setJobOrderStatus(jobOrderDTO.getJobOrderStatus());
        entity.setJobNotes(jobOrderDTO.getJobNotes());
        entity.setJobAmount(jobOrderDTO.getJobAmount());
        return entity;
    }
}

package com.ProFit.service.transactionService;

import com.ProFit.model.dto.transactionDTO.JobDetailsDTO;
import com.ProFit.model.dto.transactionDTO.JobOrderDTO;
import com.ProFit.model.bean.jobsBean.Jobs;
import com.ProFit.model.bean.jobsBean.JobsApplication;
import com.ProFit.model.bean.jobsBean.JobsApplicationProject;
import com.ProFit.model.bean.transactionBean.JobOrderBean;
import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.dao.transactionCRUD.JobOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class JobOrderService {

	@Autowired
    private JobOrderRepository jobOrderRepository;

    public List<JobOrderDTO> getAllOrdersAsDTO() {
        return jobOrderRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

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

    public void insertOrderFromDTO(JobOrderDTO jobOrderDTO) {
        JobOrderBean jobOrderBean = convertToEntity(jobOrderDTO);
        if (jobOrderBean.getJobOrdersId() == null || jobOrderBean.getJobOrdersId().isEmpty()) {
            jobOrderBean.setJobOrdersId(UUID.randomUUID().toString());
        }
        jobOrderBean.setJobOrderDate(LocalDateTime.now());
        jobOrderRepository.save(jobOrderBean);
    }

    public void updateOrderFromDTO(JobOrderDTO jobOrderDTO) {
        JobOrderBean jobOrderBean = convertToEntity(jobOrderDTO);
        if (jobOrderBean.getJobOrderDate() == null) {
            jobOrderBean.setJobOrderDate(LocalDateTime.now());
        }
        jobOrderRepository.save(jobOrderBean);
    }

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
        dto.setJobOrderPaymentMethod(jobOrderBean.getJobOrderPaymentMethod());
        dto.setJobOrderTaxID(jobOrderBean.getJobOrderTaxID());
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
        entity.setJobOrderPaymentMethod(jobOrderDTO.getJobOrderPaymentMethod());
        entity.setJobOrderTaxID(jobOrderDTO.getJobOrderTaxID());
        return entity;
    }

	public Double getOrderAmountById(String orderId) {
		// TODO Auto-generated method stub
		return null;
	}

}

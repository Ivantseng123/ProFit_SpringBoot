package com.ProFit.model.dao.servicesCRUD;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ProFit.model.bean.servicesBean.ServiceApplicationBean;
import com.ProFit.model.bean.servicesBean.ServiceBean;
import java.util.List;


public interface ServiceApplicationRepository extends JpaRepository<ServiceApplicationBean, Integer> {

    // 根據 服務id 查詢(分頁)
    Page<ServiceApplicationBean> findByServiceId(Integer serviceId, Pageable pageable);

    // 根據 委託狀態 查詢（分頁）
    Page<ServiceApplicationBean> findByStatus(Integer status, Pageable pageable);

    // 根據 caseownerId 查詢（分頁）
    Page<ServiceApplicationBean> findByCaseownerId(Integer caseownerId, Pageable pageable);

    // 根據 freelancerId 查詢（分頁）
    Page<ServiceApplicationBean> findByFreelancerId(Integer freelancerId, Pageable pageable);

    

}
package com.ProFit.model.dao.servicesCRUD;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ProFit.model.bean.servicesBean.ServiceApplicationBean;
import com.ProFit.model.bean.servicesBean.ServiceBean;

public interface ServiceApplicationRepository extends JpaRepository<ServiceApplicationBean, Integer>{

    // 根據服務id查詢(分頁)
    Page<ServiceApplicationBean> findByServiceId(Integer serviceId, Pageable pageable);

//    // 根據委託狀態查詢（分頁）
//	Page<ServiceBean> findByServiceStatus(Integer status, Pageable pageable);

    

}

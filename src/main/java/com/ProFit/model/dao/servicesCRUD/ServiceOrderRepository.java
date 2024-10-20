package com.ProFit.model.dao.servicesCRUD;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ProFit.model.bean.servicesBean.ServiceOrderBean;

public interface ServiceOrderRepository extends JpaRepository<ServiceOrderBean, String>{

}

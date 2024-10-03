package com.ProFit.model.dao.servicesCRUD;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ProFit.model.bean.servicesBean.ServiceBean;

public interface ServiceRepository extends JpaRepository<ServiceBean, Integer> {

}

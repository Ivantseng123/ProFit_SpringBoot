package com.ProFit.model.dao.eventsCRUD;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ProFit.model.bean.eventsBean.EventsBean;

@Repository
public interface EventOrderBeanDAO extends JpaRepository<EventsBean, String> {

}
package com.ProFit.model.dao.eventsCRUD;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ProFit.model.bean.eventsBean.EventHostBean;
import com.ProFit.model.bean.eventsBean.EventHostIdBean;

@Repository
public interface EventHostDAO extends JpaRepository<EventHostBean, EventHostIdBean> {

    List<EventHostBean> findByEvent_EventId(String eventId);

    List<EventHostBean> findByEventHost_UserId(int eventHostId);

}
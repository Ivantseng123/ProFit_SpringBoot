package com.ProFit.model.dao.eventsCRUD;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ProFit.model.bean.eventsBean.EventsBean;

@Repository
public interface EventsDAO extends JpaRepository<EventsBean, String> {

    @Query(value="SELECT top(1) * FROM events ORDER BY event_id DESC", nativeQuery = true)
    EventsBean findMaxEventId();
    
    List<EventsBean> findByEventNameContaining(String eventName);
    
    List<EventsBean> findByIsEventActive(int isEventActive);
    
    List<EventsBean> findByEventCategory(int eventCategory);
    
    List<EventsBean> findByEventMajorId(int eventMajor);
    
    
}
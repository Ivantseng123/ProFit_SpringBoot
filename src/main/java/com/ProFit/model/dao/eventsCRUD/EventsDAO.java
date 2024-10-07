package com.ProFit.model.dao.eventsCRUD;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ProFit.model.bean.eventsBean.EventsBean;

@Repository
public interface EventsDAO extends JpaRepository<EventsBean, String> {

    @Query(value="SELECT top(?1) * FROM events ORDER BY event_id DESC", nativeQuery = true)
    String findMaxEventId();

}
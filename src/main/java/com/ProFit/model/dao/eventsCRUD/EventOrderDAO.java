package com.ProFit.model.dao.eventsCRUD;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ProFit.model.bean.eventsBean.EventOrderBean;

@Repository
public interface EventOrderDAO extends JpaRepository<EventOrderBean, String> {

	@Query(value = "SELECT top(1) * FROM event_order ORDER BY event_order_id DESC", nativeQuery = true)
	EventOrderBean findMaxEventOrderId();

	List<EventOrderBean> findByIsEventOrderActive(Boolean isEventOrderActive);

	List<EventOrderBean> findByEventParticipantId(int eventParticipantId);

	List<EventOrderBean> findByEventId(String eventId);

}
package com.ProFit.model.dao.eventsCRUD;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ProFit.model.bean.eventsBean.EventOrderBean;
import com.ProFit.model.bean.eventsBean.EventsBean;
import com.ProFit.model.bean.usersBean.Users;

@Repository
public interface EventOrderDAO extends JpaRepository<EventOrderBean, String> {

	@Query(value = "SELECT top(1) * FROM event_order ORDER BY event_order_id DESC", nativeQuery = true)
	EventOrderBean findMaxEventOrderId();

	List<EventOrderBean> findByIsEventOrderActive(Boolean isEventOrderActive);

	List<EventsBean> findByEventParticipantId(int eventParticipantId);

	List<Users> findByEventId(String eventId);

}
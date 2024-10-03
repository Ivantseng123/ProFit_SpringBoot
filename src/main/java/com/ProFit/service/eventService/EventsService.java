package com.ProFit.service.eventService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ProFit.model.bean.eventsBean.EventsBean;
import com.ProFit.model.dao.eventsCRUD.EventsDAO;

@Service
@Transactional
public class EventsService {

    @Autowired
    private EventsDAO eventsDAO;

    public List<EventsBean> selectAllEvents() {
        return eventsDAO.findAll();
    }

    public EventsBean selectEventById(String eventId) {
        return eventsDAO.findById(eventId).orElse(null);
    }

    public String saveEvent(EventsBean event) {
        eventsDAO.save(event);
        return event.getEventId();
    }

    public String deleteEvent(String eventId) {
        eventsDAO.deleteById(eventId);
        return eventId;
    }
}

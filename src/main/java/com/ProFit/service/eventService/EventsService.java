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
        if (eventsDAO.existsById(event.getEventId())==false) {
            String newEventId = generateNewEventId();
            event.setEventId(newEventId);
        }
        eventsDAO.save(event);
        return event.getEventId();
    }

    public String deleteEvent(String eventId) {
        eventsDAO.deleteById(eventId);
        return eventId;
    }

    private String generateNewEventId() {
        String maxEventId = eventsDAO.findMaxEventId(); // 此方法需在 DAO 中實現
        int newId = (maxEventId != null) ? Integer.parseInt(maxEventId.replace("EV", "")) + 1 : 1;
        return String.format("EV%03d", newId);
    }
}

package com.ProFit.service.eventService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ProFit.model.bean.eventsBean.EventsBean;
import com.ProFit.model.dto.eventsDTO.EventsDTO;
import com.ProFit.model.dao.eventsCRUD.EventsDAO;
import com.ProFit.model.dao.majorsCRUD.MajorRepository;

@Service
@Transactional
public class EventsService {

    @Autowired
    private EventsDAO eventsDAO;
    
    @Autowired
    private MajorRepository majorRepository;

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

    public EventsDTO convertToDTO(EventsBean event) {
        if (event == null) {
            return null;
        }
        EventsDTO dto = new EventsDTO();
        dto.setEventId(event.getEventId());
        dto.setEventName(event.getEventName());
        dto.setIsEventActive(event.getIsEventActive());
        dto.setEventMajorId(event.getEventMajor() != null ? event.getEventMajor().getMajorId() : null);
        dto.setEventStartDate(event.getEventStartDate());
        dto.setEventEndDate(event.getEventEndDate());
        dto.setEventPartStartDate(event.getEventPartStartDate());
        dto.setEventPartEndDate(event.getEventPartEndDate());
        dto.setEventAmount(event.getEventAmount());
        dto.setEventLocation(event.getEventLocation());
        dto.setEventParticipantMaximum(event.getEventParticipantMaximum());
        dto.setEventDescription(event.getEventDescription());
        dto.setEventNote(event.getEventNote());
        return dto;
    }

    public EventsBean convertToEntity(EventsDTO eventDTO) {
        EventsBean event = new EventsBean();
        event.setEventId(eventDTO.getEventId());
        event.setEventName(eventDTO.getEventName());
        event.setIsEventActive(eventDTO.getIsEventActive());
        event.setEventMajor(majorRepository.findById(eventDTO.getEventMajorId()).get());
        event.setEventStartDate(eventDTO.getEventStartDate());
        event.setEventEndDate(eventDTO.getEventEndDate());
        event.setEventPartStartDate(eventDTO.getEventPartStartDate());
        event.setEventPartEndDate(eventDTO.getEventPartEndDate());
        event.setEventAmount(eventDTO.getEventAmount());
        event.setEventLocation(eventDTO.getEventLocation());
        event.setEventParticipantMaximum(eventDTO.getEventParticipantMaximum());
        event.setEventDescription(eventDTO.getEventDescription());
        event.setEventNote(eventDTO.getEventNote());
        return event;
    }
    
    
    private String generateNewEventId() {
    	String maxEventId = eventsDAO.findMaxEventId();
    	int newId = (maxEventId != null) ? Integer.parseInt(maxEventId.replace("EV", "")) + 1 : 1;
    	return String.format("EV%03d", newId);
    }
}

package com.ProFit.service.eventService;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ProFit.model.bean.eventsBean.EventsBean;
import com.ProFit.model.dto.eventsDTO.EventsDTO;
import com.ProFit.model.dao.eventsCRUD.EventsDAO;

@Service
@Transactional
public class EventsService implements IEventsService {

    @Autowired
    private EventsDAO eventsDAO;

    // 搜尋全部活動
    @Override
    public List<EventsBean> selectAllEvents() {
        return eventsDAO.findAllByOrderByEventIdDesc();
    }

    // 依照ID搜尋活動
    @Override
    public EventsBean selectEventById(String eventId) {
        return eventsDAO.findById(eventId).orElse(null);
    }

    // 依照完整名稱搜尋活動
    @Override
    public EventsBean selectEventByFullName(String eventName) {
    	return eventsDAO.findByEventName(eventName);
    }

    // 依照名稱搜尋活動
    @Override
    public List<EventsBean> selectEventByName(String eventName) {
        return eventsDAO.findByEventNameContaining(eventName);
    }

    // 依照狀態搜尋活動
    @Override
    public List<EventsBean> selectEventByStatus(int isEventActive) {
        return eventsDAO.findByIsEventActive(isEventActive);
    }

    // 依照類別搜尋活動
    @Override
    public List<EventsBean> selectEventByCategory(int eventCategory) {
        return eventsDAO.findByEventCategory(eventCategory);
    }

    // 依照專業搜尋活動
    @Override
    public List<EventsBean> selectEventByMajor(int eventMajor) {
        return eventsDAO.findByEventMajorId(eventMajor);
    }

    // 保存活動
    @Override
    public String saveEvent(EventsBean event) {
        if (eventsDAO.existsById(event.getEventId()) == false) {
            String newEventId = generateNewEventId();
            event.setEventId(newEventId);
            event.setEventPublishDate(LocalDateTime.now());
        }
        eventsDAO.save(event);
        return event.getEventId();
    }

    // 刪除活動
    @Override
    public String deleteEvent(String eventId) {
        eventsDAO.deleteById(eventId);
        return eventId;
    }

    // 將實體轉換成DTO
    @Override
    public EventsDTO convertToDTO(EventsBean event) {
        if (event == null) {
            return null;
        }
        EventsDTO dto = new EventsDTO();
        dto.setEventId(event.getEventId());
        dto.setEventName(event.getEventName());
        dto.setIsEventActive(event.getIsEventActive());
        dto.setEventCategory(event.getEventCategory());
        if(event.getEventMajor()!=null) {
        	dto.setEventMajorId(event.getEventMajor().getMajorId());
        	dto.setEventMajorName(event.getEventMajor().getMajorName());
        	dto.setEventMajorCategoryId(event.getEventMajor().getMajorCategory().getMajorCategoryId());
        	dto.setEventMajorCategoryName(event.getEventMajor().getMajorCategory().getCategoryName());        	
        }
        dto.setEventPublishDate(event.getEventPublishDate());
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

    // 將DTO轉換成實體
    @Override
    public EventsBean convertToBean(EventsDTO eventDTO) {
        EventsBean event = new EventsBean();
        event.setEventId(eventDTO.getEventId());
        event.setEventName(eventDTO.getEventName());
        event.setIsEventActive(eventDTO.getIsEventActive());
        event.setEventCategory(eventDTO.getEventCategory());
        event.setEventMajorId(eventDTO.getEventMajorId());
        event.setEventPublishDate(eventDTO.getEventPublishDate());
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
        String maxEventId = eventsDAO.findMaxEventId().getEventId();
        int newId = (maxEventId != null) ? Integer.parseInt(maxEventId.replace("EV", "")) + 1 : 1;
        return String.format("EV%03d", newId);
    }
}

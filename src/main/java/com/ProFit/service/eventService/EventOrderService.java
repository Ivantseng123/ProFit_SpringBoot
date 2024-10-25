package com.ProFit.service.eventService;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.bean.eventsBean.EventsBean;
import com.ProFit.model.bean.eventsBean.EventOrderBean;
import com.ProFit.model.dto.eventsDTO.EventOrderDTO;
import com.ProFit.model.dao.eventsCRUD.EventOrderDAO;

@Service
@Transactional
public class EventOrderService {

    @Autowired
    private EventOrderDAO eventOrderDAO;
    
    //搜尋全部訂單
    public List<EventOrderBean> selectAllEvents() {
        return eventOrderDAO.findAll();
    }

    //依照ID搜尋訂單
    public EventOrderBean selectOrderById(String eventOrderId) {
    	return eventOrderDAO.findById(eventOrderId).orElse(null);
    }
    
    //依照狀態搜尋訂單
    public List<EventOrderBean> selectOrderByStatus(int isEventOrderActive) {
    	return eventOrderDAO.findByIsEventOrderActive(isEventOrderActive);
    }
    
    //依照參加者搜尋活動
    public List<EventsBean> selectEventByParticipant(int eventParticipantId) {
    	return eventOrderDAO.findByEventParticipantId(eventParticipantId);
    }
    
    //依照活動搜尋參加者
    public List<Users> selectParticipantByEvent(String eventId) {
    	return eventOrderDAO.findByEventId(eventId);
    }

    //保存活動
    public String saveOrder(EventOrderBean order) {
        if (eventOrderDAO.existsById(order.getEventOrderId())==false) {
            String newOrderId = generateNewEventOrderId();
            order.setEventOrderId(newOrderId);
            order.setEventParticipantDate(LocalDateTime.now());
        }
        System.out.println(order);
        eventOrderDAO.save(order);
        return order.getEventOrderId();
    }

    //刪除活動
    public String deleteOrder(String eventOrderId) {
    	eventOrderDAO.deleteById(eventOrderId);
        return eventOrderId;
    }
    
    //將實體轉換成DTO
    public EventOrderDTO convertToDTO(EventOrderBean order) {
        if (order == null) {
            return null;
        }
        EventOrderDTO dto = new EventOrderDTO();
        dto.setEventOrderId(order.getEventOrderId());
        dto.setEventOrderAmount(order.getEventOrderAmount());
        dto.setEventParticipantDate(order.getEventParticipantDate());
        dto.setEventParticipantNote(order.getEventParticipantNote());
        dto.setEventOrderActive(order.isEventOrderActive());
        if (order.getEvent() != null) {
            dto.setEventId(order.getEvent().getEventId());
        }
        if (order.getParticipant() != null) {
            dto.setParticipantId(order.getParticipant().getUserId());
        }

        return dto;
    }

    //將DTO轉換成實體
    public EventOrderBean convertToBean(EventOrderDTO orderDTO) {
        EventOrderBean order = new EventOrderBean();
        order.setEventOrderId(orderDTO.getEventOrderId());
        order.setEventOrderAmount(orderDTO.getEventOrderAmount());
        order.setEventParticipantDate(orderDTO.getEventParticipantDate());
        order.setEventParticipantNote(orderDTO.getEventParticipantNote());
        order.setEventOrderActive(orderDTO.isEventOrderActive());
        order.setEventId(orderDTO.getEventId());
        order.setEventParticipantId(orderDTO.getParticipantId());
        return order;
    }
    
    
    private String generateNewEventOrderId() {
    	String maxEventOrderId = eventOrderDAO.findMaxEventOrderId().getEventOrderId();
    	int newId = (maxEventOrderId != null) ? Integer.parseInt(maxEventOrderId.replace("EO", "")) + 1 : 1;
    	return String.format("EO%03d", newId);
    }
}

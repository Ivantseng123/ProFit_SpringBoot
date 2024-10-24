package com.ProFit.service.eventService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ProFit.model.bean.eventsBean.EventsBean;
import com.ProFit.model.bean.eventsBean.EventHostBean;
import com.ProFit.model.bean.eventsBean.EventHostIdBean;
import com.ProFit.model.dao.eventsCRUD.EventHostDAO;
import com.ProFit.model.dto.eventsDTO.EventHostDTO;
import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.service.userService.IUserService;

@Service
@Transactional
public class EventHostService implements IEventHostService {

   @Autowired
   private EventHostDAO eventHostDAO;

   @Autowired
   private IEventsService eventsService;

   @Autowired
   private IUserService userService;

   // 搜尋全部活動主辦
   @Override
   public List<EventHostBean> selectAllEventHost() {
      return eventHostDAO.findAll();
   }

   // 依照id搜尋活動主辦
   @Override
   public EventHostBean selectByEventHostId(EventHostIdBean id) {
      return eventHostDAO.findById(id).orElse(null);
   }

   // 依照主辦搜尋活動
   @Override
   public List<EventHostBean> selectByHost(Integer eventHostId) {
      return eventHostDAO.findByEventHost_UserId(eventHostId);
   }

   // 依照活動搜尋主辦
   @Override
   public List<EventHostBean> selectByEvent(String eventId) {
      return eventHostDAO.findByEvent_EventId(eventId);
   }

   // 保存活動主辦
   @Override
   public EventHostBean saveEventHost(EventHostBean eventHost) {
      return eventHostDAO.save(eventHost);
   }

   // 刪除活動主辦
   @Override
   public void deleteEventHost(EventHostIdBean id) {
      eventHostDAO.deleteById(id);
   }

   // 將實體轉換成DTO
   @Override
   public EventHostDTO convertToDTO(EventHostBean eventHost) {
      EventHostDTO dto = new EventHostDTO();
      dto.setEventId(eventHost.getEvent().getEventId());
      dto.setEventName(eventHost.getEvent().getEventName());
      dto.setEventHostId(eventHost.getEventHost().getUserId());
      dto.setEventHostName(eventHost.getEventHost().getUserName());
      return dto;
   }

   // 將DTO轉換成實體
   @Override
   public EventHostBean convertToBean(EventHostDTO dto) {
      EventsBean event = eventsService.selectEventById(dto.getEventId());
      Users host = userService.getUserInfoByID(dto.getEventHostId());
      return new EventHostBean(event, host);
   }

}

package com.ProFit.service.eventService;

import java.util.List;

import com.ProFit.model.bean.eventsBean.EventHostBean;
import com.ProFit.model.bean.eventsBean.EventHostIdBean;
import com.ProFit.model.dto.eventsDTO.EventHostDTO;
import com.ProFit.model.dto.eventsDTO.EventsDTO;

public interface IEventHostService {

	// 搜尋全部活動主辦
	List<EventHostBean> selectAllEventHost();

	// 依照id搜尋活動主辦
	EventHostBean selectByEventHostId(EventHostIdBean id);

	// 依照主辦搜尋活動
	List<EventHostBean> selectByHost(Integer eventHostId);

	// 依照活動搜尋主辦
	List<EventHostBean> selectByEvent(String eventId);

	// 保存活動主辦
	EventHostBean saveEventHost(EventHostBean eventHost);

	// 刪除活動主辦
	void deleteEventHost(EventHostIdBean id);

	// 將實體轉換成DTO
	EventHostDTO convertToDTO(EventHostBean eventHost);

	// 將DTO轉換成實體
	EventHostBean convertToBean(EventHostDTO dto);

	// 轉換成event
	EventsDTO convertToEvents(EventHostBean eventHost);

}
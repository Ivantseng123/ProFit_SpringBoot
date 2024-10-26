package com.ProFit.service.eventService;

import java.util.List;

import com.ProFit.model.bean.eventsBean.EventsBean;
import com.ProFit.model.dto.eventsDTO.EventsDTO;

public interface IEventsService {

	// 搜尋全部活動
	List<EventsBean> selectAllEvents();

	// 依照ID搜尋活動
	EventsBean selectEventById(String eventId);

	// 依照完整名稱搜尋活動
	EventsBean selectEventByFullName(String eventName);

	// 依照名稱搜尋活動
	List<EventsBean> selectEventByName(String eventName);

	// 依照狀態搜尋活動
	List<EventsBean> selectEventByStatus(int isEventActive);

	// 依照類別搜尋活動
	List<EventsBean> selectEventByCategory(int eventCategory);

	// 依照專業搜尋活動
	List<EventsBean> selectEventByMajor(int eventMajor);

	// 保存活動
	String saveEvent(EventsBean event);

	// 刪除活動
	String deleteEvent(String eventId);

	// 將實體轉換成DTO
	EventsDTO convertToDTO(EventsBean event);

	// 將DTO轉換成實體
	EventsBean convertToBean(EventsDTO eventDTO);

}
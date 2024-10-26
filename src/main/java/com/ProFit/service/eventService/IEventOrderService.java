package com.ProFit.service.eventService;

import java.util.List;

import com.ProFit.model.bean.eventsBean.EventOrderBean;
import com.ProFit.model.dto.eventsDTO.EventOrderDTO;

public interface IEventOrderService {

	// 搜尋全部訂單
	List<EventOrderBean> selectAllOrders();

	// 依照ID搜尋訂單
	EventOrderBean selectOrderById(String eventOrderId);

	// 依照狀態搜尋訂單
	List<EventOrderBean> selectOrderByStatus(Boolean isEventOrderActive);

	// 依照參加者搜尋活動
	List<EventOrderBean> selectEventByParticipant(int eventParticipantId);

	// 依照活動搜尋參加者
	List<EventOrderBean> selectParticipantByEvent(String eventId);

	// 保存活動
	String saveOrder(EventOrderBean order);

	// 刪除活動
	String deleteOrder(String eventOrderId);

	// 將實體轉換成DTO
	EventOrderDTO convertToDTO(EventOrderBean order);

	// 將DTO轉換成實體
	EventOrderBean convertToBean(EventOrderDTO orderDTO);

}
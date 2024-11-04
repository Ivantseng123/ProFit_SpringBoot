package com.ProFit.controller.events.frontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ProFit.model.bean.eventsBean.EventOrderBean;
import com.ProFit.model.dto.eventsDTO.EventOrderDTO;
import com.ProFit.model.dto.eventsDTO.EventsDTO;
import com.ProFit.service.eventService.IEventOrderService;
import com.ProFit.service.eventService.IEventsService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/f/events/order")
public class EventOrderControllerF {
	
	@Autowired
	private IEventsService eventsService;

    @Autowired
    private IEventOrderService eventOrderService;
    
    // 主頁面，列出所有訂單
//    @GetMapping
//    public String listOrder(Model model) {
//        return "eventsVIEW/backend/eventOrder";
//    }

    // 新增訂單
    @GetMapping("/new")
    public String newOrder(@RequestParam String eventId, Model model) {
        EventOrderDTO order = new EventOrderDTO();
        EventsDTO event = eventsService.convertToDTO(eventsService.selectEventById(eventId));
        model.addAttribute("event", event);
        model.addAttribute("order", order);
        return "eventsVIEW/frontend/eventOrderFormF";
    }

//    // 編輯訂單
//    @GetMapping("/edit")
//    public String editOrder(@RequestParam String eventOrderId, Model model) {
//        EventOrderBean eventOrderBean = eventOrderService.selectOrderById(eventOrderId);
//        EventOrderDTO order = eventOrderService.convertToDTO(eventOrderBean);
//        model.addAttribute("order", order);
//        return "eventsVIEW/backend/eventOrderForm";
//    }
//
//    // 檢視訂單
//    @GetMapping("/view")
//    public String viewOrder(@RequestParam String eventOrderId, Model model) {
//        EventOrderBean eventOrderBean = eventOrderService.selectOrderById(eventOrderId);
//        EventOrderDTO order = eventOrderService.convertToDTO(eventOrderBean);
//        model.addAttribute("order", order);
//        return "eventsVIEW/backend/eventOrderForm";
//    }

    // 搜尋訂單
    @GetMapping("/search") @ResponseBody
    public List<EventOrderDTO> searchOrder(@RequestParam(required = false) Boolean eventOrderStatus,
    									@RequestParam(required = false) String eventId,
							    		@RequestParam(required = false) Integer eventParticipantId) {
        List<EventOrderBean> eventOrderList;
        
        System.out.println("eventOrderStatus: " + eventOrderStatus + " eventId: " + eventId + " eventParticipantId: " + eventParticipantId);

        if (eventOrderStatus != null) {
             eventOrderList = eventOrderService.selectOrderByStatus(eventOrderStatus);
        } else if (eventId != null && !eventId.isEmpty()) {
        	eventOrderList = eventOrderService.selectParticipantByEvent(eventId);
        } else if (eventParticipantId != null) {
             eventOrderList = eventOrderService.selectEventByParticipant(eventParticipantId);
        } else {
            eventOrderList = eventOrderService.selectAllOrders();
        }
        
        List<EventOrderDTO> eventOrder = eventOrderList.stream().map(eventOrderService::convertToDTO).collect(Collectors.toList());
        return eventOrder;
    }

//    // 刪除訂單
//    @GetMapping("/delete")
//    public String deleteOrder(@RequestParam String eventOrderId) {
//        eventOrderService.deleteOrder(eventOrderId);
//        return "redirect:/events/order";
//    }

    // 儲存訂單
    @PostMapping("/save")
    public ResponseEntity<String> saveOrder(@RequestBody EventOrderDTO eventOrderDTO) {
        EventOrderBean order = eventOrderService.convertToBean(eventOrderDTO);
        eventOrderService.saveOrder(order);
        return ResponseEntity.ok("/ProFit/f/events");
    }
    
}

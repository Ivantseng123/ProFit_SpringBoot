package com.ProFit.controller.events.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ProFit.model.bean.eventsBean.EventOrderBean;
import com.ProFit.model.dto.eventsDTO.EventOrderDTO;
import com.ProFit.service.eventService.IEventOrderService;


import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/events/order")
public class EventOrderController {

    @Autowired
    private IEventOrderService eventOrderService;
    
    // 主頁面，列出所有訂單
    @GetMapping
    public String listOrder(Model model) {
        return "eventOrderVIEW/backend/eventOrder";
    }

    // 新增訂單
    @GetMapping("/new")
    public String newOrder(Model model) {
        EventOrderDTO order = new EventOrderDTO();
        model.addAttribute("order", order);
        return "eventOrderVIEW/backend/eventOrderForm";
    }

    // 編輯訂單
    @GetMapping("/edit")
    public String editOrder(@RequestParam String eventOrderId, Model model) {
        EventOrderBean eventOrderBean = eventOrderService.selectOrderById(eventOrderId);
        EventOrderDTO order = eventOrderService.convertToDTO(eventOrderBean);
        model.addAttribute("order", order);
        return "eventOrderVIEW/backend/eventOrderForm";
    }

    // 檢視訂單
    @GetMapping("/view")
    public String viewOrder(@RequestParam String eventOrderId, Model model) {
        EventOrderBean eventOrderBean = eventOrderService.selectOrderById(eventOrderId);
        EventOrderDTO order = eventOrderService.convertToDTO(eventOrderBean);
        model.addAttribute("order", order);
        return "eventOrderVIEW/backend/eventOrderForm";
    }

    // 搜尋訂單
    @GetMapping("/search") @ResponseBody
    public List<EventOrderDTO> searchOrder(@RequestParam(required = false) Boolean eventOrderStatus,
							    		@RequestParam(required = false) Integer eventParticipantId,
							    		@RequestParam(required = false) String eventId) {
        List<EventOrderBean> eventOrderList;
        
        System.out.println("eventOrderStatus: " + eventOrderStatus + " eventParticipantId: " + eventParticipantId + " eventId: " + eventId);

        if (eventOrderStatus != null) {
             eventOrderList = eventOrderService.selectOrderByStatus(eventOrderStatus);
         } else if (eventParticipantId != null) {
             eventOrderList = eventOrderService.selectEventByParticipant(eventParticipantId);
         } else if (eventId != null && !eventId.isEmpty()) {
             eventOrderList = eventOrderService.selectParticipantByEvent(eventId);
        } else {
            eventOrderList = eventOrderService.selectAllOrders();
        }
        
        List<EventOrderDTO> eventOrder = eventOrderList.stream().map(eventOrderService::convertToDTO).collect(Collectors.toList());
        return eventOrder;
    }

    // 刪除訂單
    @GetMapping("/delete")
    public String deleteOrder(@RequestParam String eventOrderId) {
        eventOrderService.deleteOrder(eventOrderId);
        return "redirect:/events/order";
    }

    // 儲存訂單
    @PostMapping("/save")
    public ResponseEntity<String> saveOrder(@RequestBody EventOrderDTO eventOrderDTO) {
        EventOrderBean order = eventOrderService.convertToBean(eventOrderDTO);
        eventOrderService.saveOrder(order);
        return ResponseEntity.ok("/ProFit/eventOrder");
    }
    
}

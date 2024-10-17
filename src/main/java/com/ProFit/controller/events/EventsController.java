package com.ProFit.controller.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ProFit.model.bean.eventsBean.EventsBean;
import com.ProFit.model.dto.eventsDTO.EventsDTO;
import com.ProFit.service.eventService.EventsService;
import com.ProFit.service.majorService.IMajorCategoryService;
import com.ProFit.service.majorService.IMajorService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/events")
public class EventsController {

    @Autowired
    private EventsService eventsService;
    
//    @Autowired
//    private IMajorService majorService;
    
//    @Autowired
//    private IMajorCategoryService majorCategoryService;
    
//    List<MajorDTO> majors = majorService.findAllMajors();

    //主頁面，列出所有活動
    @GetMapping
    public String listEvents(Model model) {
        List<EventsBean> eventsList = eventsService.selectAllEvents();
        List<EventsDTO> events = eventsList.stream().map(eventsService::convertToDTO).collect(Collectors.toList());
        model.addAttribute("events", events);
        return "eventsVIEW/events";
    }

    //新增活動
    @GetMapping("/new")
    public String newEvent(Model model) {
        EventsDTO event = new EventsDTO();
        model.addAttribute("event", event);
        return "eventsVIEW/eventForm";
    }

    //編輯活動
    @GetMapping("/edit")
    public String editEvent(@RequestParam String eventId, Model model) {
    	EventsBean eventBean = eventsService.selectEventById(eventId);
    	EventsDTO event = eventsService.convertToDTO(eventBean) ;		
    	model.addAttribute("event", event);
    	return "eventsVIEW/eventForm";
    }
    
    //檢視活動
    @GetMapping("/view")
    public String viewEvent(@RequestParam String eventId, Model model) {
        EventsBean eventBean = eventsService.selectEventById(eventId);
        EventsDTO event = eventsService.convertToDTO(eventBean) ;		
        model.addAttribute("event", event);
        return "eventsVIEW/eventForm";
    }
    
    //搜尋活動
//    @GetMapping("/search")
//    public List<EventsBean> searchEvents(@RequestParam String eventName) {
//    	
//    	if (eventName != null && !eventName.isEmpty()) {
//            return eventsService.selectEventByName(eventName);
//        } else if (eventStatus != null) {
//            return eventsService.selectEventByStatus(eventStatus);
//        }
//
//        return eventsService.selectAllEvents();
//     }

    //刪除活動
    @GetMapping("/delete")
    public String deleteEvent(@RequestParam String eventId) {
        eventsService.deleteEvent(eventId);
        return "redirect:/events";
    }

    //儲存活動
    @PostMapping("/save")
    public ResponseEntity<String> saveEvent(@RequestBody EventsDTO eventsDTO) {
        EventsBean event = eventsService.convertToEntity(eventsDTO);
        eventsService.saveEvent(event);
        return ResponseEntity.ok("/ProFit/events");
    }
}

package com.ProFit.controller.events.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ProFit.model.bean.eventsBean.EventHostBean;
import com.ProFit.model.bean.eventsBean.EventHostIdBean;
import com.ProFit.model.bean.eventsBean.EventsBean;
import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.dto.eventsDTO.EventHostDTO;
import com.ProFit.service.eventService.EventsService;
import com.ProFit.service.eventService.IEventHostService;
import com.ProFit.service.eventService.IEventsService;
import com.ProFit.service.userService.IUserService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/events/host")
public class EventHostController {

    @Autowired
    private IEventHostService eventHostService;
    
    @Autowired
    private IEventsService eventsService;
    
    @Autowired
    private IUserService userService;

    // 主頁面，列出所有活動
    @GetMapping
    public String listEventHost(Model model) {
        return "eventsVIEW/backend/eventHost";
    }

    // 新增活動
    @GetMapping("/new")
    public String newEventHost(Model model) {
        EventHostDTO eventHost = new EventHostDTO();
        model.addAttribute("eventHost", eventHost);
        return "eventsVIEW/backend/eventHostForm";
    }

    // 搜尋活動
    @GetMapping("/search")
    @ResponseBody
    public List<EventHostDTO> searchEventHost(@RequestParam(required = false) String eventId,
            @RequestParam(required = false) Integer userId) {
        List<EventHostBean> eventHostList;

        System.out.println("eventId: " + eventId + " userId: " + userId);

        if (eventId != null && !eventId.isEmpty()) {
            eventHostList = eventHostService.selectByEvent(eventId);
        } else if (userId != null) {
            eventHostList = eventHostService.selectByHost(userId);
        } else {
            eventHostList = eventHostService.selectAllEventHost();
        }

        List<EventHostDTO> eventHost = eventHostList.stream().map(eventHostService::convertToDTO)
                .collect(Collectors.toList());
        return eventHost;
    }

    // 刪除活動
    @GetMapping("/delete")
    public String deleteEventHost(@RequestParam String eventId, @RequestParam Integer hostId) {
        EventHostIdBean id = new EventHostIdBean(eventId, hostId);
        eventHostService.deleteEventHost(id);
        return "redirect:/events";
    }

    // 儲存活動
    @PostMapping("/save")
    public ResponseEntity<String> saveEventHost(@RequestBody EventHostDTO eventHostDTO) {
    	EventsBean event = eventsService.selectEventById(eventHostDTO.getEventId());
    	Users host = userService.getUserInfoByID(eventHostDTO.getEventHostId());
        EventHostBean eventHost = new EventHostBean(event, host);
        eventHostService.saveEventHost(eventHost);
        return ResponseEntity.ok("/ProFit/events");
    }
}

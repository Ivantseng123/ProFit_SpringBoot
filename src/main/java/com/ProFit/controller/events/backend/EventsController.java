package com.ProFit.controller.events.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;

import com.ProFit.model.bean.eventsBean.EventsBean;
import com.ProFit.model.dto.eventsDTO.EventsDTO;
import com.ProFit.model.dto.majorsDTO.MajorDTO;
import com.ProFit.service.eventService.IEventsService;
import com.ProFit.service.majorService.IMajorService;
//import com.ProFit.service.utilsService.FirebaseStorageService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/events")
public class EventsController {

    @Autowired
    private IEventsService eventsService;

    @Autowired
    private IMajorService majorService;

//    @Autowired
//    private FirebaseStorageService firebaseStorageService;


    // 主頁面，列出所有活動
    @GetMapping
    public String listEvents(Model model) {
        List<MajorDTO> majors = majorService.findAllMajors();
        model.addAttribute("majors", majors);
        return "eventsVIEW/backend/events";
    }

    // 新增活動
    @GetMapping("/new")
    public String newEvent(Model model) {
        EventsDTO event = new EventsDTO();
        model.addAttribute("event", event);
        return "eventsVIEW/backend/eventForm";
    }

    // 編輯活動
    @GetMapping("/edit")
    public String editEvent(@RequestParam String eventId, Model model) {
        EventsBean eventBean = eventsService.selectEventById(eventId);
        EventsDTO event = eventsService.convertToDTO(eventBean);
        model.addAttribute("event", event);
        return "eventsVIEW/backend/eventForm";
    }

    // 檢視活動
    @GetMapping("/view")
    public String viewEvent(@RequestParam String eventId, Model model) {
        EventsBean eventBean = eventsService.selectEventById(eventId);
        EventsDTO event = eventsService.convertToDTO(eventBean);
        model.addAttribute("event", event);
        return "eventsVIEW/backend/eventForm";
    }

    // 搜尋活動
    @GetMapping("/search")
    @ResponseBody
    public List<EventsDTO> searchEvents(@RequestParam(required = false) String eventName,
            @RequestParam(required = false) Integer eventStatus,
            @RequestParam(required = false) Integer eventCategory,
            @RequestParam(required = false) Integer eventMajor) {
        List<EventsBean> eventsList;

        if (eventName != null && !eventName.isEmpty()) {
            eventsList = eventsService.selectEventByName(eventName);
        } else if (eventStatus != null) {
            eventsList = eventsService.selectEventByStatus(eventStatus);
        } else if (eventCategory != null) {
            eventsList = eventsService.selectEventByCategory(eventCategory);
        } else if (eventMajor != null) {
            eventsList = eventsService.selectEventByMajor(eventMajor);
        } else {
            eventsList = eventsService.selectAllEvents();
        }

        List<EventsDTO> events = eventsList.stream().map(eventsService::convertToDTO).collect(Collectors.toList());
        return events;
    }

    // 刪除活動
    @GetMapping("/delete")
    public String deleteEvent(@RequestParam String eventId) {
        eventsService.deleteEvent(eventId);
        return "redirect:/events";
    }
    
    // 儲存活動
    @PostMapping("/save")
    public ResponseEntity<String> saveEvent(@RequestBody EventsDTO eventsDTO) {
    EventsBean event = eventsService.convertToBean(eventsDTO);
    eventsService.saveEvent(event);
    return ResponseEntity.ok("/ProFit/events");
    }
//    @PostMapping("/save")
//    @ResponseBody
//    public ResponseEntity<String> saveEvent(@RequestParam String eventId,
//            @RequestParam String eventName,
//            @RequestParam int isEventActive,
//            @RequestParam int eventCategory,
//            @RequestParam int eventMajorId,
//            @RequestParam LocalDateTime eventPublishDate,
//            @RequestParam LocalDateTime eventStartDate,
//            @RequestParam LocalDateTime eventEndDate,
//            @RequestParam LocalDateTime eventPartStartDate,
//            @RequestParam LocalDateTime eventPartEndDate,
//            @RequestParam int eventAmount,
//            @RequestParam String eventLocation,
//            @RequestParam int eventParticipantMaximum,
//            @RequestParam String eventDescription,
//            @RequestParam(required = false) String eventNote,
//            @RequestPart(required = false) MultipartFile eventFile) {
//
//        // 處理檔案上傳
//        String eventFileUrl = null;
//        try {
//        	if (eventFile != null && !eventFile.isEmpty()) {
//                eventFileUrl = firebaseStorageService.uploadFile(eventFile);
//        	} else {
//				eventFileUrl = eventNote;
//			}
//        } catch (IOException e) {
//        	e.printStackTrace();
//        }
//        EventsDTO eventsDTO = new EventsDTO(eventId, eventName, isEventActive, eventCategory, eventMajorId, eventPublishDate, eventStartDate, eventEndDate, eventPartStartDate, eventPartEndDate, eventAmount, eventLocation, eventParticipantMaximum, eventDescription, eventFileUrl);
//
//        EventsBean event = eventsService.convertToBean(eventsDTO);
//        eventsService.saveEvent(event);
//
//        return ResponseEntity.ok("/ProFit/events");
//    }
}

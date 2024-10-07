package com.ProFit.controller.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ProFit.model.bean.eventsBean.EventsBean;
import com.ProFit.service.eventService.EventsService;

import java.util.List;

@Controller
@RequestMapping("/events")
public class EventsController {

    @Autowired
    private EventsService eventsService;

    @GetMapping
    public String listEvents(Model model) {
        List<EventsBean> events = eventsService.selectAllEvents();
        model.addAttribute("events", events);
        return "eventsVIEW/events";
    }

    @GetMapping("/new")
    public String newEvent(Model model) {
        model.addAttribute("event", new EventsBean());
        return "eventsVIEW/eventForm";
    }

    @GetMapping("/edit")
    public String editEvent(@RequestParam String eventId, Model model) {
        EventsBean event = eventsService.selectEventById(eventId);
        model.addAttribute("event", event);
        return "eventsVIEW/eventForm";
    }

    @GetMapping("/delete")
    public String deleteEvent(@RequestParam String eventId) {
        eventsService.deleteEvent(eventId);
        return "redirect:/events";
    }

    @PostMapping
    public String saveEvent(@ModelAttribute EventsBean event) {
        eventsService.saveEvent(event);
        return "redirect:/events";
    }
}

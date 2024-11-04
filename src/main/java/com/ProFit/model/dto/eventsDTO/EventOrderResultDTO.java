package com.ProFit.model.dto.eventsDTO;

import java.util.List;

public class EventOrderResultDTO {
	private List<EventsDTO> events;
    private List<EventOrderDTO> eventOrders;

    public EventOrderResultDTO(List<EventsDTO> events, List<EventOrderDTO> eventOrders) {
        this.events = events;
        this.eventOrders = eventOrders;
    }

    public List<EventsDTO> getEvents() {
        return events;
    }

    public void setEvents(List<EventsDTO> events) {
        this.events = events;
    }

    public List<EventOrderDTO> getEventOrders() {
        return eventOrders;
    }

    public void setEventOrders(List<EventOrderDTO> eventOrders) {
        this.eventOrders = eventOrders;
    }
}

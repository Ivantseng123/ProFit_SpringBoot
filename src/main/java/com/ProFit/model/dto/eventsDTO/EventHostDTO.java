package com.ProFit.model.dto.eventsDTO;

import java.io.Serializable;

public class EventHostDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String eventId;
    private int eventHostId;

    public EventHostDTO() {
    }

    public EventHostDTO(String eventId, int eventHostId) {
        this.eventId = eventId;
        this.eventHostId = eventHostId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public int getEventHostId() {
        return eventHostId;
    }

    public void setEventHostId(int eventHostId) {
        this.eventHostId = eventHostId;
    }

    @Override
    public String toString() {
        return "EventHostDTO [eventId=" + eventId + ", eventHostId=" + eventHostId + "]";
    }
}
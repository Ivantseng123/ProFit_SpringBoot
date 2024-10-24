package com.ProFit.model.dto.eventsDTO;

import java.io.Serializable;

public class EventHostDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String eventId;
    private String eventName;
    private int eventHostId;
    private String eventHostName;
    
    
	public EventHostDTO() {
	}

	public EventHostDTO(String eventId, String eventName, int eventHostId, String eventHostName) {
		super();
		this.eventId = eventId;
		this.eventName = eventName;
		this.eventHostId = eventHostId;
		this.eventHostName = eventHostName;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public int getEventHostId() {
		return eventHostId;
	}

	public void setEventHostId(int eventHostId) {
		this.eventHostId = eventHostId;
	}

	public String getEventHostName() {
		return eventHostName;
	}

	public void setEventHostName(String eventHostName) {
		this.eventHostName = eventHostName;
	}

	@Override
	public String toString() {
		return "EventHostDTO [eventId=" + eventId + ", eventName=" + eventName + ", eventHostId=" + eventHostId
				+ ", eventHostName=" + eventHostName + "]";
	}

    
	
}
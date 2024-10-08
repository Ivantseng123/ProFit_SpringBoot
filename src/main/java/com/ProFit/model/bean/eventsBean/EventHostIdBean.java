package com.ProFit.model.bean.eventsBean;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

// 活動主辦表格的複合ID
@Embeddable
public class EventHostIdBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String eventId;
    private int eventHostId;

    public EventHostIdBean() {
    }

    public EventHostIdBean(String eventId, int eventHostId) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventHostIdBean)) return false;
        EventHostIdBean that = (EventHostIdBean) o;
        return eventHostId == that.eventHostId && eventId.equals(that.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, eventHostId);
    }
}

package com.ProFit.model.bean.eventsBean;

import java.io.Serializable;

import jakarta.persistence.*;

import com.ProFit.model.bean.usersBean.Users;

@Entity
@Table(name = "event_host")
public class EventHostBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private EventHostIdBean id;

    @ManyToOne
    @MapsId("eventId")
    @JoinColumn(name = "event_id", referencedColumnName = "event_id", nullable = false)
    private EventsBean event;

    @ManyToOne
    @MapsId("eventHostId")
    @JoinColumn(name = "event_host_id", referencedColumnName = "user_id", nullable = false)
    private Users eventHost;

    public EventHostBean() {
    }

    public EventHostBean(EventsBean event, Users eventHost) {
        this.event = event;
        this.eventHost = eventHost;
        this.id = new EventHostIdBean(event.getEventId(), eventHost.getUserId());
    }

    public EventsBean getEvent() {
        return event;
    }

    public void setEvent(EventsBean event) {
        this.event = event;
        this.id.setEventId(event.getEventId());
    }

    public Users getEventHost() {
        return eventHost;
    }

    public void setEventHost(Users eventHost) {
        this.eventHost = eventHost;
        this.id.setEventHostId(eventHost.getUserId());
    }

    @Override
    public String toString() {
        return "EventHostBean [eventId=" + this.event.getEventId() + ", eventHostId=" + this.eventHost.getUserId()
                + "]";
    }

}

package com.ProFit.model.bean.eventsBean;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.ProFit.model.bean.usersBean.Users;

import jakarta.persistence.*;

@Entity
@Table(name = "event_order")
public class EventOrderBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id 
	private String eventOrderId;

	@Column(name = "event_order_amount")
	private int eventOrderAmount;

	@Column(name = "is_event_order_active")
	private boolean isEventOrderActive;

	@Column(name = "event_participant_date")
	private LocalDateTime eventParticipantDate;

	@Column(name = "event_participant_note")
	private String eventParticipantNote;

	@ManyToOne
	@JoinColumn(name = "event_id", referencedColumnName = "event_id", nullable = false)
	private EventsBean event;

	@ManyToOne
	@JoinColumn(name = "event_participant_id", referencedColumnName = "user_id", nullable = false)
	private Users participant;

	public EventOrderBean() {
	}

	public EventOrderBean(String eventOrderId, int eventOrderAmount, boolean isEventOrderActive, String eventId,
			int eventParticipantId, LocalDateTime eventParticipantDate, String eventParticipantNote) {
		super();
		this.eventOrderId = eventOrderId;
		this.eventOrderAmount = eventOrderAmount;
		this.isEventOrderActive = isEventOrderActive;
		this.eventParticipantDate = eventParticipantDate;
		this.eventParticipantNote = eventParticipantNote;
	}

	public String getEventOrderId() {
		return eventOrderId;
	}

	public void setEventOrderId(String eventOrderId) {
		this.eventOrderId = eventOrderId;
	}

	public int getEventOrderAmount() {
		return eventOrderAmount;
	}

	public void setEventOrderAmount(int eventOrderAmount) {
		this.eventOrderAmount = eventOrderAmount;
	}

	public boolean isEventOrderActive() {
		return isEventOrderActive;
	}

	public void setEventOrderActive(boolean isEventOrderActive) {
		this.isEventOrderActive = isEventOrderActive;
	}

	public EventsBean getEvent() {
		return event;
	}

	public Users getEventParticipant() {
		return participant;
	}

	public LocalDateTime getEventParticipantDate() {
		return eventParticipantDate;
	}

	public void setEventParticipantDate(LocalDateTime eventParticipantDate) {
		this.eventParticipantDate = eventParticipantDate;
	}

	public String getEventParticipantNote() {
		return eventParticipantNote;
	}

	public void setEventParticipantNote(String eventParticipantNote) {
		this.eventParticipantNote = eventParticipantNote;
	}

	@Override
	public String toString() {
		return "EventOrderBean [eventOrderId=" + eventOrderId + ", eventOrderAmount=" + eventOrderAmount
				+ ", isEventOrderActive=" + isEventOrderActive + ", eventId=" + this.event.getEventId() + ", eventParticipantId="
				+ this.participant.getUserId() + ", eventParticipantDate=" + eventParticipantDate + ", eventParticipantNote="
				+ eventParticipantNote + "]";
	}

}

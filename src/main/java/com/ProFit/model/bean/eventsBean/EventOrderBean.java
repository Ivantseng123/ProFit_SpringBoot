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

//	@ManyToOne
//	@JoinColumn(name = "event_id", referencedColumnName = "event_id", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "event_id", insertable = false, updatable = false)
	private EventsBean event;
	
	@Column(name = "event_id")
	private String eventId;

//	@ManyToOne
//	@JoinColumn(name = "event_participant_id", referencedColumnName = "user_id", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "event_participant_id", insertable = false, updatable = false)
	private Users participant;
	
	@Column(name = "event_major")
	private Integer eventParticipantId;

	public EventOrderBean() {
	}

	public EventOrderBean(String eventOrderId, int eventOrderAmount, boolean isEventOrderActive,
			LocalDateTime eventParticipantDate, String eventParticipantNote, EventsBean event, Users participant) {
		super();
		this.eventOrderId = eventOrderId;
		this.eventOrderAmount = eventOrderAmount;
		this.isEventOrderActive = isEventOrderActive;
		this.eventParticipantDate = eventParticipantDate;
		this.eventParticipantNote = eventParticipantNote;
		this.event = event;
		this.participant = participant;
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

	public EventsBean getEvent() {
		return event;
	}

	public void setEvent(EventsBean event) {
		this.event = event;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public Users getParticipant() {
		return participant;
	}

	public void setParticipant(Users participant) {
		this.participant = participant;
	}

	public Integer getEventParticipantId() {
		return eventParticipantId;
	}

	public void setEventParticipantId(Integer eventParticipantId) {
		this.eventParticipantId = eventParticipantId;
	}

	@Override
	public String toString() {
		return "EventOrderBean [eventOrderId=" + eventOrderId + ", eventOrderAmount=" + eventOrderAmount
				+ ", isEventOrderActive=" + isEventOrderActive + ", eventParticipantDate=" + eventParticipantDate
				+ ", eventParticipantNote=" + eventParticipantNote + ", event=" + event + ", eventId=" + eventId
				+ ", participant=" + participant + ", eventParticipantId=" + eventParticipantId + "]";
	}

	

}

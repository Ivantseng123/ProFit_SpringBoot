package com.ProFit.model.dto.eventsDTO;

import java.io.Serializable;
import java.time.LocalDateTime;

public class EventOrderDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String eventOrderId;
    private int eventOrderAmount;
    private boolean isEventOrderActive;
    private LocalDateTime eventParticipantDate;
    private String eventParticipantNote;
    private String eventId;
    private String eventName;
    private int participantId;
    private String participantName;

    public EventOrderDTO() {
    }

	public EventOrderDTO(String eventOrderId, int eventOrderAmount, boolean isEventOrderActive,
			LocalDateTime eventParticipantDate, String eventParticipantNote, String eventId, String eventName,
			int participantId, String participantName) {
		super();
		this.eventOrderId = eventOrderId;
		this.eventOrderAmount = eventOrderAmount;
		this.isEventOrderActive = isEventOrderActive;
		this.eventParticipantDate = eventParticipantDate;
		this.eventParticipantNote = eventParticipantNote;
		this.eventId = eventId;
		this.eventName = eventName;
		this.participantId = participantId;
		this.participantName = participantName;
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

	public int getParticipantId() {
		return participantId;
	}

	public void setParticipantId(int participantId) {
		this.participantId = participantId;
	}

	public String getParticipantName() {
		return participantName;
	}

	public void setParticipantName(String participantName) {
		this.participantName = participantName;
	}

	@Override
	public String toString() {
		return "EventOrderDTO [eventOrderId=" + eventOrderId + ", eventOrderAmount=" + eventOrderAmount
				+ ", isEventOrderActive=" + isEventOrderActive + ", eventParticipantDate=" + eventParticipantDate
				+ ", eventParticipantNote=" + eventParticipantNote + ", eventId=" + eventId + ", eventName=" + eventName
				+ ", participantId=" + participantId + ", participantName=" + participantName + "]";
	}

    

}

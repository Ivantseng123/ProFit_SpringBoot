package com.ProFit.model.dto.eventsDTO;

import java.time.LocalDateTime;

public class EventsDTO {
	private String eventId;
	private String eventName;
	private int isEventActive;
	private int eventCategory;
	private int eventMajorId;
	private LocalDateTime eventPublishDate;
	private LocalDateTime eventStartDate;
	private LocalDateTime eventEndDate;
	private LocalDateTime eventPartStartDate;
	private LocalDateTime eventPartEndDate;
	private int eventAmount;
	private String eventLocation;
	private int eventParticipantMaximum;
	private String eventDescription;
	private String eventNote;

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

	public int getIsEventActive() {
		return isEventActive;
	}

	public void setIsEventActive(int isEventActive) {
		this.isEventActive = isEventActive;
	}

	public int getEventCategory() {
		return eventCategory;
	}

	public void setEventCategory(int eventCategory) {
		this.eventCategory = eventCategory;
	}

	public int getEventMajorId() {
		return eventMajorId;
	}

	public void setEventMajorId(int eventMajorId) {
		this.eventMajorId = eventMajorId;
	}

	public LocalDateTime getEventPublishDate() {
		return eventPublishDate;
	}

	public void setEventPublishDate(LocalDateTime eventPublishDate) {
		this.eventPublishDate = eventPublishDate;
	}

	public LocalDateTime getEventStartDate() {
		return eventStartDate;
	}

	public void setEventStartDate(LocalDateTime eventStartDate) {
		this.eventStartDate = eventStartDate;
	}

	public LocalDateTime getEventEndDate() {
		return eventEndDate;
	}

	public void setEventEndDate(LocalDateTime eventEndDate) {
		this.eventEndDate = eventEndDate;
	}

	public LocalDateTime getEventPartStartDate() {
		return eventPartStartDate;
	}

	public void setEventPartStartDate(LocalDateTime eventPartStartDate) {
		this.eventPartStartDate = eventPartStartDate;
	}

	public LocalDateTime getEventPartEndDate() {
		return eventPartEndDate;
	}

	public void setEventPartEndDate(LocalDateTime eventPartEndDate) {
		this.eventPartEndDate = eventPartEndDate;
	}

	public int getEventAmount() {
		return eventAmount;
	}

	public void setEventAmount(int eventAmount) {
		this.eventAmount = eventAmount;
	}

	public String getEventLocation() {
		return eventLocation;
	}

	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}

	public int getEventParticipantMaximum() {
		return eventParticipantMaximum;
	}

	public void setEventParticipantMaximum(int eventParticipantMaximum) {
		this.eventParticipantMaximum = eventParticipantMaximum;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	public String getEventNote() {
		return eventNote;
	}

	public void setEventNote(String eventNote) {
		this.eventNote = eventNote;
	}

}

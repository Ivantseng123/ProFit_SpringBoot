package com.ProFit.model.dto.eventsDTO;

import java.time.LocalDateTime;

public class EventsDTO {
	private String eventId;
	private String eventName;
	private int isEventActive;
	private int eventCategory;
	private int eventMajorId;
	private String eventMajorName;
	private int eventMajorCategoryId;
	private String eventMajorCategoryName;	
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
	private int hostId;

	public EventsDTO() {
	}
	
	public EventsDTO(String eventId, String eventName, int isEventActive, int eventCategory, int eventMajorId,
			LocalDateTime eventPublishDate, LocalDateTime eventStartDate, LocalDateTime eventEndDate,
			LocalDateTime eventPartStartDate, LocalDateTime eventPartEndDate, int eventAmount, String eventLocation,
			int eventParticipantMaximum, String eventDescription, String eventNote) {
		super();
		this.eventId = eventId;
		this.eventName = eventName;
		this.isEventActive = isEventActive;
		this.eventCategory = eventCategory;
		this.eventMajorId = eventMajorId;
		this.eventPublishDate = eventPublishDate;
		this.eventStartDate = eventStartDate;
		this.eventEndDate = eventEndDate;
		this.eventPartStartDate = eventPartStartDate;
		this.eventPartEndDate = eventPartEndDate;
		this.eventAmount = eventAmount;
		this.eventLocation = eventLocation;
		this.eventParticipantMaximum = eventParticipantMaximum;
		this.eventDescription = eventDescription;
		this.eventNote = eventNote;
	}

	public EventsDTO(String eventId, String eventName, int isEventActive, int eventCategory, int eventMajorId, String eventMajorName,
			int eventMajorCategoryId, String eventMajorCategoryName, LocalDateTime eventPublishDate,
			LocalDateTime eventStartDate, LocalDateTime eventEndDate, LocalDateTime eventPartStartDate,
			LocalDateTime eventPartEndDate, int eventAmount, String eventLocation, int eventParticipantMaximum,
			String eventDescription, String eventNote, int hostId) {
		super();
		this.eventId = eventId;
		this.eventName = eventName;
		this.isEventActive = isEventActive;
		this.eventCategory = eventCategory;
		this.eventMajorId = eventMajorId;
		this.eventMajorName = eventMajorName;
		this.eventMajorCategoryId = eventMajorCategoryId;
		this.eventMajorCategoryName = eventMajorCategoryName;
		this.eventPublishDate = eventPublishDate;
		this.eventStartDate = eventStartDate;
		this.eventEndDate = eventEndDate;
		this.eventPartStartDate = eventPartStartDate;
		this.eventPartEndDate = eventPartEndDate;
		this.eventAmount = eventAmount;
		this.eventLocation = eventLocation;
		this.eventParticipantMaximum = eventParticipantMaximum;
		this.eventDescription = eventDescription;
		this.eventNote = eventNote;
		this.hostId = hostId;
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

	public String getEventMajorName() {
		return eventMajorName;
	}

	public void setEventMajorName(String eventMajorName) {
		this.eventMajorName = eventMajorName;
	}

	public int getEventMajorCategoryId() {
		return eventMajorCategoryId;
	}

	public void setEventMajorCategoryId(int eventMajorCategoryId) {
		this.eventMajorCategoryId = eventMajorCategoryId;
	}

	public String getEventMajorCategoryName() {
		return eventMajorCategoryName;
	}

	public void setEventMajorCategoryName(String eventMajorCategoryName) {
		this.eventMajorCategoryName = eventMajorCategoryName;
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

	public int getHostId() {
		return hostId;
	}

	public void setHostId(int hostId) {
		this.hostId = hostId;
	}
	
	

}

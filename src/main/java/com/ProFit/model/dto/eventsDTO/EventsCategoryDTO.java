package com.ProFit.model.dto.eventsDTO;

public class EventsCategoryDTO {
	
	private int majorCategoryId;
	private String majorCategoryName;
	private int eventsNumber;
	
	public EventsCategoryDTO() {
		super();
	}

	public EventsCategoryDTO(int majorCategoryId, String majorCategoryName, int eventsNumber) {
		super();
		this.majorCategoryId = majorCategoryId;
		this.majorCategoryName = majorCategoryName;
		this.eventsNumber = eventsNumber;
	}

	public int getMajorCategoryId() {
		return majorCategoryId;
	}

	public void setMajorCategoryId(int majorCategoryId) {
		this.majorCategoryId = majorCategoryId;
	}

	public String getMajorCategoryName() {
		return majorCategoryName;
	}

	public void setMajorCategoryName(String majorCategoryName) {
		this.majorCategoryName = majorCategoryName;
	}

	public int getEventsNumber() {
		return eventsNumber;
	}

	public void setEventsNumber(int eventsNumber) {
		this.eventsNumber = eventsNumber;
	}
	
	
	

}

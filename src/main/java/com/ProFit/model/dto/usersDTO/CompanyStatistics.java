package com.ProFit.model.dto.usersDTO;


public class CompanyStatistics {
	
	private String category;
	private long count;

	public CompanyStatistics(String identity, long count) {
		this.category = identity;
		this.count = count;
	}

	public String getCategory() {
		return category;
	}

	public long getCount() {
		return count;
	}
}

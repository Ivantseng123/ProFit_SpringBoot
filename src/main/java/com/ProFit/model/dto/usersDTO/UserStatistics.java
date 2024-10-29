package com.ProFit.model.dto.usersDTO;


public class UserStatistics {
	
	private String identity;
	private long count;

	public UserStatistics(String identity, long count) {
		this.identity = identity;
		this.count = count;
	}

	public String getIdentity() {
		return identity;
	}

	public long getCount() {
		return count;
	}
}

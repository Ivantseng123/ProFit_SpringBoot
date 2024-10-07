package com.ProFit.model.bean.majorsBean;

import java.io.Serializable;
import java.util.Objects;

import com.ProFit.model.bean.usersBean.Users;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class UserMajorPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@JoinColumn(name = "user_id") // Users的@Id變數名稱
	private Integer userId;

	@JoinColumn(name = "major_id") // MajorBean的@Id變數名稱
	private Integer majorId;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof UserMajorPK))
			return false;
		UserMajorPK that = (UserMajorPK) o;
		return Objects.equals(userId, that.userId) && Objects.equals(majorId, that.majorId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId, majorId);
	}

	public UserMajorPK() {
	}

	public UserMajorPK(Integer userId, Integer majorId) {
		this.userId = userId;
		this.majorId = majorId;
	}

	
	// getter setter
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getMajorId() {
		return majorId;
	}

	public void setMajorId(Integer majorId) {
		this.majorId = majorId;
	}

	@Override
	public String toString() {
		return "UserMajorPK [userId=" + userId + ", majorId=" + majorId + "]";
	}

	
}

package com.ProFit.model.bean.majorsBean;

import com.ProFit.model.bean.usersBean.Users;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_major")
public class UserMajorBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UserMajorPK id;

	@ManyToOne
	@MapsId("userId")
	@JoinColumn(name = "user_id")
	private Users user;

	@ManyToOne
    @MapsId("majorId")
    @JoinColumn(name = "major_id")
    private MajorBean major;
	
	// 構造函數
	public UserMajorBean() {
	}

	public UserMajorBean(UserMajorPK id) {
		this.id = id;
	}

	public UserMajorPK getId() {
		return id;
	}

	public void setId(UserMajorPK id) {
		this.id = id;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public MajorBean getMajor() {
		return major;
	}

	public void setMajor(MajorBean major) {
		this.major = major;
	}

	@Override
	public String toString() {
		return "UserMajorBean [id=" + id + ", user=" + user + ", major=" + major + "]";
	}

}

package com.ProFit.model.bean.majorsBean;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.ProFit.model.bean.jobsBean.Jobs;
import com.ProFit.model.bean.usersBean.Users;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "major")
public class MajorBean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "major_id")
	private Integer majorId; // 主鍵

	@Column(name = "major_name")
	private String majorName; // 專業名稱

	@Column(name = "major_category_id")
	private Integer majorCategoryId; // 專業_類別ID

	@Column(name = "major_description")
	private String majorDescription; // 專業描述

	// 多對多關係，中介表user_major
	// 由users表成為關係的所有者，定義了 @JoinTable, 單方向管理這段關係
	// MajorBean 成為被動方，表明 Users 實體是關係的所有者。
	// 避免了重複定義 user_major 表
	@ManyToMany(mappedBy = "majors")
	private Set<Users> users = new LinkedHashSet<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "major_category_id", insertable = false, updatable = false)
	private MajorCategoryBean majorCategory;

	public MajorBean() {
		super();
	}

	// 沒帶id建構子
	public MajorBean(String majorName, Integer majorCategoryId, String majorDescription) {
		super();
		this.majorName = majorName;
		this.majorCategoryId = majorCategoryId;
		this.majorDescription = majorDescription;
	}

	// 有帶id建構子
	public MajorBean(Integer majorId, String majorName, Integer majorCategoryId, String majorDescription) {
		super();
		this.majorId = majorId;
		this.majorName = majorName;
		this.majorCategoryId = majorCategoryId;
		this.majorDescription = majorDescription;
	}

	public Integer getMajorId() {
		return majorId;
	}

	public void setMajorId(Integer majorId) {
		this.majorId = majorId;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	public Integer getMajorCategoryId() {
		return majorCategoryId;
	}

	public void setMajorCategoryId(Integer majorCategoryId) {
		this.majorCategoryId = majorCategoryId;
	}

	public String getMajorDescription() {
		return majorDescription;
	}

	public void setMajorDescription(String majorDescription) {
		this.majorDescription = majorDescription;
	}

	public Set<Users> getUsers() {
		return users;
	}

	public void setUsers(Set<Users> users) {
		this.users = users;
	}

	public MajorCategoryBean getMajorCategory() {
		return majorCategory;
	}

	public void setMajorCategory(MajorCategoryBean majorCategory) {
		this.majorCategory = majorCategory;
	}

	@Override
	public String toString() {
		return "MajorBean [majorId=" + majorId + ", majorName=" + majorName + ", majorCategoryId=" + majorCategoryId
				+ ", majorDescription=" + majorDescription + ", majorCategory=" + majorCategory + "]";
	}

}

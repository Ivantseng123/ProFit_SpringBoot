package com.ProFit.model.bean.majorsBean;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ProFit.model.bean.coursesBean.CourseBean;
import com.ProFit.model.bean.jobsBean.Jobs;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "major_category")
public class MajorCategoryBean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "major_category_id")
	private Integer majorCategoryId; // 主鍵，專業_類別ID

	@Column(name = "category_name")
	private String categoryName; // 類別名稱

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "majorCategory", cascade = CascadeType.ALL)
	private Set<MajorBean> majors = new HashSet<MajorBean>(0);

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "majorCategory")
	private List<CourseBean> courseList;

	public MajorCategoryBean() {
		super();
	}

	// 帶id建構子
	public MajorCategoryBean(Integer majorCategoryId, String categoryName) {
		super();
		this.majorCategoryId = majorCategoryId;
		this.categoryName = categoryName;
	}

	public List<CourseBean> getCourseList() {
		return courseList;
	}

	public void setCourseList(List<CourseBean> courseList) {
		this.courseList = courseList;
	}

	public Integer getMajorCategoryId() {
		return majorCategoryId;
	}

	public void setMajorCategoryId(Integer majorCategoryId) {
		this.majorCategoryId = majorCategoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Set<MajorBean> getMajors() {
		return majors;
	}

	public void setMajors(Set<MajorBean> majors) {
		this.majors = majors;
	}

	@Override
	public String toString() {
		return "MajorCategoryBean [majorCategoryId=" + majorCategoryId + ", categoryName=" + categoryName + "]";
	}

}

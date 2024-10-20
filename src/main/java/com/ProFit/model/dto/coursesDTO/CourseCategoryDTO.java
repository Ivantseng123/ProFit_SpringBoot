package com.ProFit.model.dto.coursesDTO;

import com.ProFit.model.bean.majorsBean.MajorCategoryBean;

public class CourseCategoryDTO {

	private String categoryName;
	private Integer majorCategoryId;
	private Integer courseNumber;

	public CourseCategoryDTO() {

	}

	public CourseCategoryDTO(MajorCategoryBean majorCategoryBean) {
		this.majorCategoryId = majorCategoryBean.getMajorCategoryId();
		this.categoryName = majorCategoryBean.getCategoryName();
		this.courseNumber = majorCategoryBean.getCourseList().size();
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getMajorCategoryId() {
		return majorCategoryId;
	}

	public void setMajorCategoryId(Integer majorCategoryId) {
		this.majorCategoryId = majorCategoryId;
	}

	public Integer getCourseNumber() {
		return courseNumber;
	}

	public void setCourseNumber(Integer courseNumber) {
		this.courseNumber = courseNumber;
	}

}

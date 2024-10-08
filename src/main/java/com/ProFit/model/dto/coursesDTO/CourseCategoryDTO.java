package com.ProFit.model.dto.coursesDTO;

import com.ProFit.model.bean.majorsBean.MajorCategoryBean;

public class CourseCategoryDTO {
	
	private String categoryName;
	private Integer majorCategoryId;
	
	
	public CourseCategoryDTO() {
		
	}
	public CourseCategoryDTO(MajorCategoryBean majorCategoryBean) {
		this.majorCategoryId = majorCategoryBean.getMajorCategoryId();
		this.categoryName = majorCategoryBean.getCategoryName();
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
	
}

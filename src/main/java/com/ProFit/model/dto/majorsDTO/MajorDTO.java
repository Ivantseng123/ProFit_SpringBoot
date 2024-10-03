package com.ProFit.model.dto.majorsDTO;

import com.ProFit.model.bean.majorsBean.MajorBean;

public class MajorDTO {
	private Integer majorId;
	private String majorName;
	private Integer majorCategoryId;
	private String categoryName;
	private String majorDescription;

	public MajorDTO() {
		super();
	}

	public MajorDTO(Integer majorId, String majorName, Integer majorCategoryId, String categoryName,
			String majorDescription) {
		this.majorId = majorId;
		this.majorName = majorName;
		this.majorCategoryId = majorCategoryId;
		this.categoryName = categoryName;
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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getMajorDescription() {
		return majorDescription;
	}

	public void setMajorDescription(String majorDescription) {
		this.majorDescription = majorDescription;
	}

	 // 從 MajorBean 創建 MajorDTO 的靜態方法
	public static MajorDTO fromEntity(MajorBean majorBean) {
		MajorDTO dto = new MajorDTO();
		dto.setMajorId(majorBean.getMajorId());
		dto.setMajorName(majorBean.getMajorName());
		dto.setMajorCategoryId(majorBean.getMajorCategoryId());
        dto.setMajorDescription(majorBean.getMajorDescription());
		
		// 安全地獲取類別名稱
		if (majorBean.getMajorCategory() != null) {
			dto.setMajorCategoryId(majorBean.getMajorCategory().getMajorCategoryId());
			dto.setCategoryName(majorBean.getMajorCategory().getCategoryName());
		}
		return dto;
	}
}
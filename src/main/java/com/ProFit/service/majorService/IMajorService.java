package com.ProFit.service.majorService;

import java.util.List;

import com.ProFit.model.bean.majorsBean.MajorBean;
import com.ProFit.model.dto.majorsDTO.MajorDTO;


public interface IMajorService {

	// 新增 Major
	MajorBean insertMajor(MajorBean major);

	// 更新 Major
	MajorBean updateMajor(MajorBean major);

	// 删除 Major(by majorid)
	boolean deleteMajor(int majorId);

	// 查找 Major(by majorid)
	MajorDTO findMajorById(int majorId);

	// 查找所有 Major
	List<MajorDTO> findAllMajors();

	// 根據 majorCategoryid 查找 Majors (
	List<MajorDTO> findMajorsByCategoryId(int majorCategoryId);

	// 根據MajorName 模糊搜尋Majors
	List<MajorDTO> findMajorsByMajorName(String name);

}
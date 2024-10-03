package com.ProFit.service.majorService;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ProFit.model.bean.majorsBean.MajorCategoryBean;


public interface IMajorCategoryService {

	// 新增專業類別
	MajorCategoryBean insertMajorCategory(MajorCategoryBean majorCategory);

	// 更新專業類別
	MajorCategoryBean updateMajorCategory(MajorCategoryBean majorCategory);

	// 刪除專業類別
	boolean deleteMajorCategory(int majorCategoryId);

	// 查詢所有專業類別
	List<MajorCategoryBean> findAllMajorCategories();

	// 根據ID查詢專業類別
	MajorCategoryBean findMajorCategoryById(int majorCategoryId);

	// 分頁顯示所有查詢, 15筆一頁
	Page<MajorCategoryBean> findMajorCategoryByPage(Integer pageNmuber);

}
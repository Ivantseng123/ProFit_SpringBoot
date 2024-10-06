package com.ProFit.service.majorService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ProFit.model.bean.majorsBean.UserMajorBean;
import com.ProFit.model.bean.majorsBean.UserMajorPK;
import com.ProFit.model.dto.majorsDTO.PageResponse;

public interface IUserMajorService {

	// (新增)添加用戶-專業關聯
	UserMajorBean addUserMajor(Integer userId, Integer majorId);

	// 删除 用戶-專業關聯(by userId & majorId)
	void deleteUserMajor(Integer userId, Integer majorId);

	// 檢查用戶-專業關聯是否存在(根據user、Major查找單一 UserMajor)
	boolean existsUserMajor(Integer userId, Integer majorId);

	// 根據用戶ID分頁獲取所有關聯的專業
	PageResponse<UserMajorBean> getUserMajorsByUserId(Integer userId, int page, int size, String sortBy,
			boolean ascending);

	// 根據專業ID分頁獲取所有關聯的用戶
	PageResponse<UserMajorBean> getUserMajorsByMajorId(Integer majorId, int page, int size, String sortBy,
			boolean ascending);

	// 獲取特定的用戶-專業關聯
	UserMajorBean getUserMajor(Integer userId, Integer majorId);

	// 分頁獲取所有用戶-專業關聯
	PageResponse<UserMajorBean> getAllUserMajors(int page, int size, String sortBy, boolean ascending);

}
package com.ProFit.model.dao.servicesCRUD;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ProFit.model.bean.majorsBean.UserMajorBean;
import com.ProFit.model.bean.servicesBean.ServiceBean;

public interface ServiceRepository extends JpaRepository<ServiceBean, Integer> {

	// 分頁查詢所有服務
	Page<ServiceBean> findAll(Pageable pageable);

	// 根據 UserMajor 分頁查找所有相關的服務
	Page<ServiceBean> findByUserMajor(UserMajorBean userMajor, Pageable pageable);

	// 根據服務標題模糊搜索（分頁）
	Page<ServiceBean> findByServiceTitleContaining(String title, Pageable pageable);

	// 根據價格範圍查詢服務（分頁）
	Page<ServiceBean> findByServicePriceBetween(Integer minPrice, Integer maxPrice, Pageable pageable);

	// 根據服務狀態查詢（分頁）
	Page<ServiceBean> findByServiceStatus(Integer status, Pageable pageable);

	// 根據用戶ID查詢服務（分頁）
	Page<ServiceBean> findByUserId(Integer userId, Pageable pageable);

	// 根據用戶freelancer_identity查詢服務（分頁）
	@Query("SELECT s FROM ServiceBean s WHERE s.userMajor.user.freelancerIdentity = :identity")
	Page<ServiceBean> findByUserFreelancerIdentity(@Param("identity") String identity, Pageable pageable);

	// 根據用戶freelancer_exprience查詢服務（分頁）
	@Query("SELECT s FROM ServiceBean s WHERE s.userMajor.user.freelancerExprience = :experience")
	Page<ServiceBean> findByUserFreelancerExperience(@Param("experience") String experience, Pageable pageable);

	// 根據用戶freelancer_location_prefer查詢服務（分頁）
	@Query("SELECT s FROM ServiceBean s WHERE s.userMajor.user.freelancerLocationPrefer = :location")
	Page<ServiceBean> findByUserFreelancerLocationPrefer(@Param("location") String location, Pageable pageable);

	// 根據專業ID查詢服務（分頁）
	Page<ServiceBean> findByMajorId(Integer majorId, Pageable pageable);

	// 根據 MajorCategory ID 查詢服務（分頁）
	@Query("SELECT s FROM ServiceBean s WHERE s.userMajor.major.majorCategory.majorCategoryId = :categoryId")
	Page<ServiceBean> findByMajorCategoryId(@Param("categoryId") Integer categoryId, Pageable pageable);

	// 根據用戶ID和專業ID查詢服務（分頁）
	Page<ServiceBean> findByUserIdAndMajorId(Integer userId, Integer majorId, Pageable pageable);

	// 根據服務ID刪除服務


}

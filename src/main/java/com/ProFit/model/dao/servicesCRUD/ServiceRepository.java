package com.ProFit.model.dao.servicesCRUD;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ProFit.model.bean.majorsBean.UserMajorBean;
import com.ProFit.model.bean.servicesBean.ServiceBean;
import java.util.List;

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

//	----------------------------------------------------------------------
	// 前台查尋功能，status為 1 時才能讓其他會員查詢到

	// 根據專業ID查詢開放的服務（分頁）
	@Query(value = "SELECT * FROM service s WHERE service_status = :status AND major_id = :majorId", nativeQuery = true)
	Page<ServiceBean> findServiceByMajorIdAndStatus(@Param("status") Integer serviceStatus,
			@Param("majorId") Integer majorId, Pageable pageable);

	// 根據專業ID查詢開放的服務（分頁）
	@Query(value = "SELECT * FROM service s WHERE service_status = :status AND service_price >= :minPric AND service_price <= :maxPric", nativeQuery = true)
	Page<ServiceBean> findServiceByPriceRangeAndStatus(@Param("status")Integer serviceStatus, Integer minPric, Integer maxPric,
			Pageable pageable);

	// 根據專業類別查詢服務

	// 根據 多條件 查詢服務
	@Query("SELECT s FROM ServiceBean s JOIN s.userMajor u WHERE " +
            "(:serviceTitle IS NULL OR s.serviceTitle  LIKE %:serviceTitle%) AND " +
            "(:userName IS NULL OR u.user.userName LIKE %:userName%) AND " +
            "(:status IS NULL OR s.serviceStatus  = :status) AND " +
            "(:userId IS NULL OR u.user.userId  = :userId) AND " +
            "(:majorIdList IS NULL OR u.major.majorId IN :majorIdList) AND " + 
			"(:majorCategoryId IS NULL OR u.major.majorCategoryId = :majorCategoryId)")
	Page<ServiceBean> searchServicePage(
		@Param("serviceTitle") String serviceTitle,
        @Param("userName") String userName,
        @Param("status") Integer status,
        @Param("userId") Integer userId,
        @Param("majorIdList") List<Integer> majorIdList,   // majorIdList： MajorId 列表，使用 IN 语句，匹配多个MajorId。
		@Param("majorCategoryId") Integer majorCategoryId,
        Pageable pageable
	);
}

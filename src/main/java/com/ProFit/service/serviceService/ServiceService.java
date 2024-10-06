package com.ProFit.service.serviceService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ProFit.model.bean.majorsBean.MajorBean;
import com.ProFit.model.bean.majorsBean.UserMajorBean;
import com.ProFit.model.bean.majorsBean.UserMajorPK;
import com.ProFit.model.bean.servicesBean.ServiceBean;
import com.ProFit.model.dao.servicesCRUD.ServiceRepository;
import com.ProFit.model.dto.majorsDTO.PageResponse;

@Service
@Transactional
public class ServiceService {

	@Autowired
	private ServiceRepository serviceRepo;

	// 創建 Pageable物件 的 private方法 (需在controller設定默認值,不然很危)
	private Pageable createPageable(int page, int size, String sortBy, boolean ascending) {
		// 根據 sortBy 和 ascending 創建排序參數 sort，允許客戶端指定排序字段和順序。
		Sort sort = Sort.by(ascending ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
		// 根據 page, size, sort 建立 Pageable物件 並回傳
		return PageRequest.of(page, size, sort);
	}

	// 新增服務
	public ServiceBean addService(ServiceBean service) {
		return serviceRepo.save(service);
	}

	// 根據服務ID刪除服務
	public void deleteService(Integer serviceId) {
		serviceRepo.deleteById(serviceId);
	}

	// 修改(更新)服務
	public ServiceBean updateMajor(ServiceBean newService) {
		Optional<ServiceBean> optional = serviceRepo.findById(newService.getServiceId());
		if (optional.isPresent()) {
			ServiceBean oldService = optional.get();
			oldService.setServiceTitle(newService.getServiceTitle());
			oldService.setServiceContent(newService.getServiceContent());
			oldService.setServicePrice(newService.getServicePrice());
			oldService.setServiceUnitName(newService.getServiceUnitName());
			oldService.setServiceDuration(newService.getServiceDuration());
			oldService.setServiceCreateDate(newService.getServiceCreateDate());
			oldService.setServiceUpdateDate(newService.getServiceUpdateDate());
			oldService.setServicePictureURL1(newService.getServicePictureURL1());
			oldService.setServicePictureURL2(newService.getServicePictureURL2());
			oldService.setServicePictureURL3(newService.getServicePictureURL3());
			oldService.setServiceStatus(newService.getServiceStatus());

			return oldService;
		}
		return null;
	}

	// 分頁查詢所有服務
	public PageResponse<ServiceBean> getAllServices(int page, int size, String sortBy, boolean ascending) {
		Pageable pageable = createPageable(page, size, sortBy, ascending);
		Page<ServiceBean> servicePage = serviceRepo.findAll(pageable);
		// 獲取用戶專業關聯的方法 of，使用自己寫的 PageResponse 傳入 Page 並返回分頁結果(response物件)。
		return PageResponse.of(servicePage);
	}

	// 根據 UserMajor 分頁查找所有相關的服務
	public PageResponse<ServiceBean> getServicesByUserMajor(UserMajorBean userMajor, int page, int size, String sortBy,
			boolean ascending) {
		Pageable pageable = createPageable(page, size, sortBy, ascending);
		Page<ServiceBean> servicePage = serviceRepo.findByUserMajor(userMajor, pageable);
		return PageResponse.of(servicePage);
	}

	// 根據服務標題模糊搜索（分頁）
	public PageResponse<ServiceBean> getServicesServiceTitleContaining(String title, int page, int size, String sortBy,
			boolean ascending) {
		Pageable pageable = createPageable(page, size, sortBy, ascending);
		Page<ServiceBean> servicePage = serviceRepo.findByServiceTitleContaining(title, pageable);
		return PageResponse.of(servicePage);
	}
	
	// 根據價格範圍查詢服務（分頁）

	// 根據服務狀態查詢（分頁

	// 根據用戶ID查詢服務（分頁

	// 根據用戶freelancer_identity查詢服務（分頁）

	// 根據用戶freelancer_exprience查詢服務（分頁）

	// 根據用戶freelancer_location_prefer查詢服務（分頁

	// 根據專業ID查詢服務（分頁

	// 根據 MajorCategory ID 查詢服務（分頁

	// 根據用戶ID和專業ID查詢服務（分頁）

}

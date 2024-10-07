//package com.ProFit.service.serviceService;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.ProFit.model.bean.majorsBean.MajorBean;
//import com.ProFit.model.bean.majorsBean.UserMajorBean;
//import com.ProFit.model.bean.majorsBean.UserMajorPK;
//import com.ProFit.model.bean.servicesBean.ServiceBean;
//import com.ProFit.model.dao.servicesCRUD.ServiceRepository;
//import com.ProFit.model.dto.majorsDTO.PageResponse;
//import com.ProFit.model.dto.servicesDTO.ServicesDTO;
//
//@Service
//@Transactional
//public class ServiceService {
//
//	@Autowired
//	private ServiceRepository serviceRepo;
//
//	// 輔助方法：創建 Pageable物件 的 private方法 (需在controller設定默認值,不然很危)
//	private Pageable createPageable(int page, int size, String sortBy, boolean ascending) {
//		// 根據 sortBy 和 ascending 創建排序參數 sort，允許客戶端指定排序字段和順序。
//		Sort sort = Sort.by(ascending ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
//		// 根據 page, size, sort 建立 Pageable物件 並回傳
//		return PageRequest.of(page, size, sort);
//	}
//
//	// 輔助方法：將 Page<ServiceBean> 轉換為 PageResponse<ServiceDTO>
//	private PageResponse<ServicesDTO> convertToPageResponse(Page<ServiceBean> page) {
//		return new PageResponse<>(page.getContent().stream().map(ServicesDTO::fromEntity).collect(Collectors.toList()),
//				page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages(), page.isLast(),
//				page.isFirst());
//	}
//
//	// 輔助方法：用 DTO 更新 Entity
//	private void updateEntityFromDTO(ServiceBean entity, ServicesDTO dto) {
//		entity.setServiceId(dto.getServiceId());
//		entity.setServiceTitle(dto.getServiceTitle());
//		entity.setServiceContent(dto.getServiceContent());
//		entity.setServicePrice(dto.getServicePrice());
//		entity.setServiceUnitName(dto.getServiceUnitName());
//		entity.setServiceDuration(dto.getServiceDuration());
//		entity.setServiceCreateDate(dto.getServiceCreateDate());
//		entity.setServiceUpdateDate(dto.getServiceUpdateDate());
//		entity.setServicePictureURL1(dto.getServicePictureURL1());
//		entity.setServicePictureURL2(dto.getServicePictureURL2());
//		entity.setServicePictureURL3(dto.getServicePictureURL3());
//		entity.setServiceStatus(dto.getServiceStatus());
//		// 注意：這裡需要處理 UserMajor 的轉換
//	}
//
//	// 輔助方法：將 DTO 轉換為 Entity
//	private ServiceBean convertToEntity(ServicesDTO dto) {
//		ServiceBean entity = new ServiceBean();
//		updateEntityFromDTO(entity, dto);
//		return entity;
//	}
//
//	// 新增服務
//	public ServicesDTO addService(ServicesDTO serviceDTO) {
//		ServiceBean serviceBean = convertToEntity(serviceDTO);
//		ServiceBean savedBean = serviceRepo.save(serviceBean);
//		return ServicesDTO.fromEntity(savedBean);
//	}
//
//	// 根據服務ID刪除服務
//	public void deleteService(Integer serviceId) {
//		serviceRepo.deleteById(serviceId);
//	}
//
//	// 修改(更新)服務
//	public ServicesDTO updateService(ServicesDTO serviceDTO) {
//		Optional<ServiceBean> optional = serviceRepo.findById(serviceDTO.getServiceId());
//		if (optional.isPresent()) {
//			ServiceBean serviceBean = optional.get();
//			updateEntityFromDTO(serviceBean, serviceDTO);
//			ServiceBean updatedBean = serviceRepo.save(serviceBean);
//			return ServicesDTO.fromEntity(updatedBean);
//		}
//		return null;
//	}
//
//	// 分頁查詢所有服務
//	public PageResponse<ServicesDTO> getAllServices(int page, int size, String sortBy, boolean ascending) {
//		Pageable pageable = createPageable(page, size, sortBy, ascending);
//		Page<ServiceBean> servicePage = serviceRepo.findAll(pageable);
//		return convertToPageResponse(servicePage);
//	}
//
//	// 根據 UserMajor 分頁查找所有相關的服務
//	public PageResponse<ServicesDTO> getServicesByUserMajor(UserMajorBean userMajor, int page, int size, String sortBy,
//			boolean ascending) {
//		Pageable pageable = createPageable(page, size, sortBy, ascending);
//		Page<ServiceBean> servicePage = serviceRepo.findByUserMajor(userMajor, pageable);
//		return convertToPageResponse(servicePage);
//	}
//
//	// 根據服務標題模糊搜索（分頁）
//	public PageResponse<ServicesDTO> getServicesServiceTitleContaining(String title, int page, int size, String sortBy,
//            boolean ascending) {
//        Pageable pageable = createPageable(page, size, sortBy, ascending);
//        Page<ServiceBean> servicePage = serviceRepo.findByServiceTitleContaining(title, pageable);
//        return convertToPageResponse(servicePage);
//    }
//
//	// 根據價格範圍查詢服務（分頁）
//
//	// 根據服務狀態查詢（分頁
//
//	// 根據用戶ID查詢服務（分頁
//
//	// 根據用戶freelancer_identity查詢服務（分頁）
//
//	// 根據用戶freelancer_exprience查詢服務（分頁）
//
//	// 根據用戶freelancer_location_prefer查詢服務（分頁
//
//	// 根據專業ID查詢服務（分頁
//
//	// 根據 MajorCategory ID 查詢服務（分頁
//
//	// 根據用戶ID和專業ID查詢服務（分頁）
//
//}

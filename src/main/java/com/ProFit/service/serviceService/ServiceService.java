package com.ProFit.service.serviceService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ProFit.model.bean.majorsBean.MajorBean;
import com.ProFit.model.bean.majorsBean.UserMajorBean;
import com.ProFit.model.bean.majorsBean.UserMajorPK;
import com.ProFit.model.bean.servicesBean.ServiceBean;
import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.dao.majorsCRUD.MajorRepository;
import com.ProFit.model.dao.majorsCRUD.UserMajorRepository;
import com.ProFit.model.dao.servicesCRUD.ServiceRepository;
import com.ProFit.model.dao.usersCRUD.UsersRepository;
import com.ProFit.model.dto.majorsDTO.PageResponse;
import com.ProFit.model.dto.majorsDTO.UserMajorDTO;
import com.ProFit.model.dto.servicesDTO.ServicesDTO;

@Service
@Transactional
public class ServiceService {

	@Autowired
	private ServiceRepository serviceRepo;

	@Autowired
	private UsersRepository usersRepo;

	@Autowired
	private MajorRepository majorRepo;

	@Autowired
	private UserMajorRepository userMajorRepo;

	// 輔助方法：創建 Pageable物件 的 private方法 (需在controller設定默認值,不然很危)
	private Pageable createPageable(int page, int size, String sortBy, boolean ascending) {
		// 根據 sortBy 和 ascending 創建排序參數 sort，允許客戶端指定排序字段和順序。
		Sort sort = Sort.by(ascending ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
		// 根據 page, size, sort 建立 Pageable物件 並回傳
		return PageRequest.of(page, size, sort);
	}

	// 輔助方法：將 Page<ServiceBean> 轉換為 PageResponse<ServiceDTO>
	private PageResponse<ServicesDTO> convertToPageResponse(Page<ServiceBean> page) {
		List<ServicesDTO> dtos = page.getContent().stream().map(ServicesDTO::fromEntity).collect(Collectors.toList());

		return new PageResponse<>(dtos, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages(),
				page.isLast(), page.isFirst());
	}

	// 輔助方法：用 DTO 更新 Entity
	private void updateEntityFromDTO(ServiceBean entity, ServicesDTO dto) {
		// 更新基本字段
		entity.setServiceId(dto.getServiceId());
		entity.setServiceTitle(dto.getServiceTitle());
		entity.setServiceContent(dto.getServiceContent());
		entity.setServicePrice(dto.getServicePrice());
		entity.setServiceUnitName(dto.getServiceUnitName());
		entity.setServiceDuration(dto.getServiceDuration());
		entity.setServiceCreateDate(dto.getServiceCreateDate());
		entity.setServiceUpdateDate(dto.getServiceUpdateDate());
		entity.setServicePictureURL1(dto.getServicePictureURL1());
		entity.setServicePictureURL2(dto.getServicePictureURL2());
		entity.setServicePictureURL3(dto.getServicePictureURL3());
		entity.setServiceStatus(dto.getServiceStatus());

		// 處理 UserMajor
		if (dto.getUserMajor() != null) {
			UserMajorDTO userMajorDTO = dto.getUserMajor();
			UserMajorPK userMajorPK = new UserMajorPK(userMajorDTO.getUserId(), userMajorDTO.getMajorId());

			// 嘗試從數據庫獲取現有的 UserMajorBean
			Optional<UserMajorBean> optional = userMajorRepo.findById(userMajorPK);

			if (optional.isPresent()) { // 如果存在，保存 UserMajorBean
				entity.setUserMajor(optional.get());
			} else if (optional.isEmpty()) { // 如果不存在，創建一個新的 UserMajorBean
				UserMajorBean newUserMajor = new UserMajorBean();
				newUserMajor.setId(userMajorPK);

				// 加載 User
				Users user = usersRepo.findById(userMajorDTO.getUserId())
						.orElseThrow(() -> new RuntimeException("User not found"));
				newUserMajor.setUser(user);

				// 加載 Major
				MajorBean major = majorRepo.findById(userMajorDTO.getMajorId())
						.orElseThrow(() -> new RuntimeException("Major not found"));
				newUserMajor.setMajor(major);

				// 保存新的 UserMajorBean
				UserMajorBean save = userMajorRepo.save(newUserMajor);
				entity.setUserMajor(save);
			}
		}
	}

	// 輔助方法：將 DTO 轉換為 Entity
	private ServiceBean convertToEntity(ServicesDTO dto) {
		ServiceBean entity = new ServiceBean();
		updateEntityFromDTO(entity, dto);
		return entity;
	}

	// 新增服務
	public ServicesDTO addService(ServicesDTO serviceDTO) {
		ServiceBean serviceBean = new ServiceBean();
		updateEntityFromDTO(serviceBean, serviceDTO);
		ServiceBean savedBean = serviceRepo.save(serviceBean);
		return ServicesDTO.fromEntity(savedBean);
	}

	// 新增服務
	public ServicesDTO addService(ServiceBean serviceBean, Integer userId, Integer majorId) {

		UserMajorPK userMajorPK = new UserMajorPK(userId, majorId);
		Optional<UserMajorBean> optional = userMajorRepo.findById(userMajorPK);

		if (optional.isPresent()) {
			UserMajorBean userMajorBean = optional.get();
			serviceBean.setUserMajor(userMajorBean);
			ServiceBean savedBean = serviceRepo.save(serviceBean);
			return ServicesDTO.fromEntity(savedBean);
		}

		return null;
	}

	// 根據服務ID刪除服務
	public void deleteService(Integer serviceId) {
		serviceRepo.deleteById(serviceId);
	}

	// 修改(更新)服務
	public ServicesDTO updateService(ServicesDTO serviceDTO) {
		System.out.println("aa ");
		Optional<ServiceBean> optional = serviceRepo.findById(serviceDTO.getServiceId());
		if (optional.isPresent()) {
			ServiceBean serviceBean = optional.get();

			String oldPictureURL1 = serviceBean.getServicePictureURL1();
			String oldPictureURL2 = serviceBean.getServicePictureURL2();
			String oldPictureURL3 = serviceBean.getServicePictureURL3();
			LocalDateTime oldserviceCreateDate = serviceBean.getServiceCreateDate();

			updateEntityFromDTO(serviceBean, serviceDTO);
			if (serviceBean.getServicePictureURL1() == null) {
				serviceBean.setServicePictureURL1(oldPictureURL1);
			}
			if (serviceBean.getServicePictureURL2() == null) {
				serviceBean.setServicePictureURL2(oldPictureURL2);
			}
			if (serviceBean.getServicePictureURL3() == null) {
				serviceBean.setServicePictureURL3(oldPictureURL3);
			}
			if (serviceBean.getServiceCreateDate() == null) {
				serviceBean.setServiceCreateDate(oldserviceCreateDate);
			}
			System.out.println(serviceBean);
			ServiceBean updatedBean = serviceRepo.save(serviceBean);
			return ServicesDTO.fromEntity(updatedBean);
		}
		return null;
	}

	// 搜尋單筆的方法
	public ServicesDTO getServiceById(Integer serviceId) {
		Optional<ServiceBean> optional = serviceRepo.findById(serviceId);
		if (optional.isPresent()) {
			ServiceBean serviceBean = optional.get();
			return ServicesDTO.fromEntity(serviceBean);
		}
		return null;
	}

	// 分頁查詢所有服務
	public PageResponse<ServicesDTO> getAllServices(int page, int size, String sortBy, boolean ascending) {
		Pageable pageable = createPageable(page, size, sortBy, ascending);
		Page<ServiceBean> servicePage = serviceRepo.findAll(pageable);
		return convertToPageResponse(servicePage);
	}

	// 根據 UserMajor 分頁查找所有相關的服務
	public PageResponse<ServicesDTO> getServicesByUserMajor(UserMajorDTO userMajorDTO, int page, int size,
			String sortBy, boolean ascending) {
		UserMajorBean userMajorBean = new UserMajorBean();
		userMajorBean.setId(new UserMajorPK(userMajorDTO.getUserId(), userMajorDTO.getMajorId()));

		Pageable pageable = createPageable(page, size, sortBy, ascending);
		Page<ServiceBean> servicePage = serviceRepo.findByUserMajor(userMajorBean, pageable);
		return convertToPageResponse(servicePage);
	}

	// 根據服務標題模糊搜索（分頁）
	public PageResponse<ServicesDTO> getServicesServiceTitleContaining(String title, int page, int size, String sortBy,
			boolean ascending) {
		Pageable pageable = createPageable(page, size, sortBy, ascending);
		Page<ServiceBean> servicePage = serviceRepo.findByServiceTitleContaining(title, pageable);
		return convertToPageResponse(servicePage);
	}

	// 根據價格範圍查詢服務（分頁）
	public PageResponse<ServicesDTO> getServicesByPriceRange(Integer minPrice, Integer maxPrice, int page, int size,
			String sortBy, boolean ascending) {
		Pageable pageable = createPageable(page, size, sortBy, ascending);
		Page<ServiceBean> servicePage = serviceRepo.findByServicePriceBetween(minPrice, maxPrice, pageable);
		return convertToPageResponse(servicePage);
	}

	// 根據服務狀態查詢（分頁）
	public PageResponse<ServicesDTO> getServicesByStatus(Integer status, int page, int size, String sortBy,
			boolean ascending) {
		Pageable pageable = createPageable(page, size, sortBy, ascending);
		Page<ServiceBean> servicePage = serviceRepo.findByServiceStatus(status, pageable);
		return convertToPageResponse(servicePage);
	}

	// 根據用戶ID查詢服務（分頁）
	public PageResponse<ServicesDTO> getServicesByUserId(Integer userId, int page, int size, String sortBy,
			boolean ascending) {
		Pageable pageable = createPageable(page, size, sortBy, ascending);
		Page<ServiceBean> servicePage = serviceRepo.findByUserId(userId, pageable);
		return convertToPageResponse(servicePage);
	}

	// 根據用戶freelancer_identity查詢服務（分頁）
	public PageResponse<ServicesDTO> getServicesByFreelancerIdentity(String identity, int page, int size, String sortBy,
			boolean ascending) {
		Pageable pageable = createPageable(page, size, sortBy, ascending);
		Page<ServiceBean> servicePage = serviceRepo.findByUserFreelancerIdentity(identity, pageable);
		return convertToPageResponse(servicePage);
	}

	// 根據用戶freelancer_exprience查詢服務（分頁）
	public PageResponse<ServicesDTO> getServicesByFreelancerExperience(String experience, int page, int size,
			String sortBy, boolean ascending) {
		Pageable pageable = createPageable(page, size, sortBy, ascending);
		Page<ServiceBean> servicePage = serviceRepo.findByUserFreelancerExperience(experience, pageable);
		return convertToPageResponse(servicePage);
	}

	// 根據用戶freelancer_location_prefer查詢服務（分頁）
	public PageResponse<ServicesDTO> getServicesByFreelancerLocationPrefer(String location, int page, int size,
			String sortBy, boolean ascending) {
		Pageable pageable = createPageable(page, size, sortBy, ascending);
		Page<ServiceBean> servicePage = serviceRepo.findByUserFreelancerLocationPrefer(location, pageable);
		return convertToPageResponse(servicePage);
	}

	// 根據專業ID查詢服務（分頁）
	public PageResponse<ServicesDTO> getServicesByMajorId(Integer majorId, int page, int size, String sortBy,
			boolean ascending) {
		Pageable pageable = createPageable(page, size, sortBy, ascending);
		Page<ServiceBean> servicePage = serviceRepo.findByMajorId(majorId, pageable);
		return convertToPageResponse(servicePage);
	}

	// 根據 MajorCategory ID 查詢服務（分頁）
	public PageResponse<ServicesDTO> getServicesByMajorCategoryId(Integer categoryId, int page, int size, String sortBy,
			boolean ascending) {
		Pageable pageable = createPageable(page, size, sortBy, ascending);
		Page<ServiceBean> servicePage = serviceRepo.findByMajorCategoryId(categoryId, pageable);
		return convertToPageResponse(servicePage);
	}

	// 根據用戶ID和專業ID查詢服務（分頁）
	public PageResponse<ServicesDTO> getServicesByUserIdAndMajorId(Integer userId, Integer majorId, int page, int size,
			String sortBy, boolean ascending) {
		Pageable pageable = createPageable(page, size, sortBy, ascending);
		Page<ServiceBean> servicePage = serviceRepo.findByUserIdAndMajorId(userId, majorId, pageable);
		return convertToPageResponse(servicePage);
	}



	//----------------------新增的好用查詢----------
	// 分頁 多條件 查詢服務
	public PageResponse<ServicesDTO> searchServicePage(String serviceTitle,
			String userName,
			Integer status,
			Integer userId,
			List<Integer> majorIdList,
			Integer majorCategoryId,
			int page, int size,
			String sortBy, boolean ascending) {


		Pageable pageable = createPageable(page, size, sortBy, ascending);
		Page<ServiceBean> searchServicePage = serviceRepo.searchServicePage(serviceTitle, userName, status, userId, majorIdList, majorCategoryId, pageable);

		PageResponse<ServicesDTO> serviceDTOPage = convertToPageResponse(searchServicePage);

		return serviceDTOPage;
	}
}

package com.ProFit.controller.services.backend;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ProFit.model.bean.majorsBean.MajorBean;
import com.ProFit.model.bean.majorsBean.UserMajorBean;
import com.ProFit.model.bean.majorsBean.UserMajorPK;
import com.ProFit.model.bean.servicesBean.ServiceBean;
import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.dto.majorsDTO.PageResponse;
import com.ProFit.model.dto.majorsDTO.UserMajorDTO;
import com.ProFit.model.dto.servicesDTO.ServicesDTO;
import com.ProFit.service.serviceService.ServiceService;
import com.ProFit.service.utilsService.FirebaseStorageService;

@Controller
@RequestMapping("/service")
public class ServiceController {

	@Autowired
	private ServiceService serviceService;

	@Autowired
	private FirebaseStorageService firebaseStorageService;

	// 跳轉到主頁面
	@GetMapping("/")
	public String listServices() {
		return "servicesVIEW/ServiceMainPage"; // 返回主頁面的視圖名稱
	}

	// 跳轉到新增頁面
	@GetMapping("/create")
	public String addServices() {
		return "servicesVIEW/createServiceView"; // 返回主頁面的視圖名稱
	}

	// 跳轉到修改頁面
	@GetMapping("/edit/{serviceId}")
	public String editServices(@PathVariable Integer serviceId, Model model) {
		model.addAttribute("serviceId", serviceId);
		return "servicesVIEW/UpdateServiceView"; // 返回主頁面的視圖名稱
	}

	// 搜尋全部的方法
	@GetMapping("/api/list")
	@ResponseBody
	public ResponseEntity<PageResponse<ServicesDTO>> getAllServices(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "serviceId") String sortBy,
			@RequestParam(defaultValue = "true") boolean ascending) {
		PageResponse<ServicesDTO> response = serviceService.getAllServices(page, size, sortBy, ascending);
		return ResponseEntity.ok(response);
	}

	// 搜尋單筆的方法
	@GetMapping("/api/{serviceId}")
	@ResponseBody
	public ResponseEntity<ServicesDTO> getServiceById(@PathVariable Integer serviceId) {
		ServicesDTO service = serviceService.getServiceById(serviceId);
		if (service != null) {
			return ResponseEntity.ok(service);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// 新增服務的方法
	@PostMapping("/api")
	@ResponseBody
	public ResponseEntity<Map<String, String>> addService(@RequestParam("serviceTitle") String serviceTitle,
			@RequestParam("serviceContent") String serviceContent, @RequestParam("servicePrice") Integer servicePrice,
			@RequestParam("serviceUnitName") String serviceUnitName,
			@RequestParam("serviceDuration") Double serviceDuration,
			@RequestParam("serviceStatus") Integer serviceStatus, @RequestParam("userId") Integer userId,
			@RequestParam("majorId") Integer majorId, @RequestPart(required = false) MultipartFile servicePictureURL1,
			@RequestPart(required = false) MultipartFile servicePictureURL2,
			@RequestPart(required = false) MultipartFile servicePictureURL3) {

		try {
			ServiceBean serviceBean = new ServiceBean();
			serviceBean.setServiceTitle(serviceTitle);
			serviceBean.setServiceContent(serviceContent);
			serviceBean.setServicePrice(servicePrice);
			serviceBean.setServiceUnitName(serviceUnitName);
			serviceBean.setServiceDuration(serviceDuration);
			serviceBean.setServiceStatus(serviceStatus);
			serviceBean.setServiceCreateDate(LocalDateTime.now());
			serviceBean.setServiceUpdateDate(LocalDateTime.now());

			// 處理圖片上傳
			if (servicePictureURL1 != null && !servicePictureURL1.isEmpty()) {
				String photoURL = firebaseStorageService.uploadFile(servicePictureURL1);
				serviceBean.setServicePictureURL1(photoURL);
			}
			if (servicePictureURL2 != null && !servicePictureURL2.isEmpty()) {
				String photoURL = firebaseStorageService.uploadFile(servicePictureURL2);
				serviceBean.setServicePictureURL2(photoURL);
			}
			if (servicePictureURL3 != null && !servicePictureURL3.isEmpty()) {
				String photoURL = firebaseStorageService.uploadFile(servicePictureURL3);
				serviceBean.setServicePictureURL3(photoURL);
			}

			// 新增服務
			ServicesDTO createdService = serviceService.addService(serviceBean, userId, majorId);

			// 返回 JSON 響應
			Map<String, String> response = new HashMap<>();
			response.put("message", "OK");
			response.put("serviceId", createdService.getServiceId().toString());
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("error", e.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 更新服務的方法
	@PutMapping("/api/{serviceId}")
	@ResponseBody
	public ResponseEntity<Map<String, String>> updateService(@PathVariable Integer serviceId,
			@RequestParam("serviceTitle") String serviceTitle, @RequestParam("serviceContent") String serviceContent,
			@RequestParam("servicePrice") Integer servicePrice, @RequestParam("serviceUnitName") String serviceUnitName,
			@RequestParam("serviceDuration") Double serviceDuration,
			@RequestParam("serviceStatus") Integer serviceStatus, @RequestParam("userId") Integer userId,
			@RequestParam("majorId") Integer majorId, @RequestPart(required = false) MultipartFile servicePictureURL1,
			@RequestPart(required = false) MultipartFile servicePictureURL2,
			@RequestPart(required = false) MultipartFile servicePictureURL3) {

		System.out.println("gg");

		try {
			ServicesDTO serviceDTO = new ServicesDTO();
			serviceDTO.setServiceId(serviceId);
			serviceDTO.setServiceTitle(serviceTitle);
			serviceDTO.setServiceContent(serviceContent);
			serviceDTO.setServicePrice(servicePrice);
			serviceDTO.setServiceUnitName(serviceUnitName);
			serviceDTO.setServiceDuration(serviceDuration);
			serviceDTO.setServiceStatus(serviceStatus);
			serviceDTO.setServiceUpdateDate(LocalDateTime.now());

			// 處理圖片上傳
			if (servicePictureURL1 != null && !servicePictureURL1.isEmpty()) {
				String photoURL = firebaseStorageService.uploadFile(servicePictureURL1);
				serviceDTO.setServicePictureURL1(photoURL);
			}
			if (servicePictureURL2 != null && !servicePictureURL2.isEmpty()) {
				String photoURL = firebaseStorageService.uploadFile(servicePictureURL2);
				serviceDTO.setServicePictureURL2(photoURL);
			}
			if (servicePictureURL3 != null && !servicePictureURL3.isEmpty()) {
				String photoURL = firebaseStorageService.uploadFile(servicePictureURL3);
				serviceDTO.setServicePictureURL3(photoURL);
			}

			// System.out.println(serviceDTO);
			// 更新服務
			ServicesDTO updatedService = serviceService.updateService(serviceDTO);
			// System.out.println(updatedService);

			if (updatedService != null) {
				Map<String, String> response = new HashMap<>();
				response.put("message", "OK");
				response.put("serviceId", updatedService.getServiceId().toString());
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (IOException e) {
			e.printStackTrace();
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("error", e.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 刪除服務的方法
	@DeleteMapping("/api/{serviceId}")
	@ResponseBody
	public ResponseEntity<Void> deleteService(@PathVariable Integer serviceId) {
		serviceService.deleteService(serviceId);
		return ResponseEntity.noContent().build();
	}

	// 根據價格範圍查詢服務
	@GetMapping("/api/price-range")
	@ResponseBody
	public ResponseEntity<PageResponse<ServicesDTO>> getServicesByPriceRange(@RequestParam Integer minPrice,
			@RequestParam Integer maxPrice, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "serviceId") String sortBy,
			@RequestParam(defaultValue = "true") boolean ascending) {
		PageResponse<ServicesDTO> response = serviceService.getServicesByPriceRange(minPrice, maxPrice, page, size,
				sortBy, ascending);
		return ResponseEntity.ok(response);
	}

	// 根據服務狀態查詢
	@GetMapping("/api/status/{status}")
	@ResponseBody
	public ResponseEntity<PageResponse<ServicesDTO>> getServicesByStatus(@PathVariable Integer status,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "serviceId") String sortBy,
			@RequestParam(defaultValue = "true") boolean ascending) {
		PageResponse<ServicesDTO> response = serviceService.getServicesByStatus(status, page, size, sortBy, ascending);
		return ResponseEntity.ok(response);
	}

	// 根據 UserMajor 分頁查找所有相關的服務
	@GetMapping("/api/userMajor/{userId}/{majorId}")
	@ResponseBody
	public ResponseEntity<PageResponse<ServicesDTO>> getServicesByUserMajor(@PathVariable Integer userId,
			@PathVariable Integer majorId, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "serviceId") String sortBy,
			@RequestParam(defaultValue = "true") boolean ascending) {

		UserMajorDTO userMajorDTO = new UserMajorDTO();
		userMajorDTO.setUserId(userId);
		userMajorDTO.setMajorId(majorId);

		PageResponse<ServicesDTO> response = serviceService.getServicesByUserMajor(userMajorDTO, page, size, sortBy,
				ascending);
		return ResponseEntity.ok(response);
	}

	// 根據標題查詢服務
	@GetMapping("/api/search")
	@ResponseBody
	public ResponseEntity<PageResponse<ServicesDTO>> searchServicesByTitle(@RequestParam String title,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "serviceId") String sortBy,
			@RequestParam(defaultValue = "true") boolean ascending) {

		PageResponse<ServicesDTO> response = serviceService.getServicesServiceTitleContaining(title, page, size, sortBy,
				ascending);
		return ResponseEntity.ok(response);
	}

	// ...
}
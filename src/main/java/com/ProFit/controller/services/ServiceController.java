package com.ProFit.controller.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ProFit.model.bean.majorsBean.MajorBean;
import com.ProFit.model.bean.majorsBean.UserMajorBean;
import com.ProFit.model.bean.majorsBean.UserMajorPK;
import com.ProFit.model.bean.servicesBean.ServiceBean;
import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.dto.majorsDTO.PageResponse;
import com.ProFit.model.dto.servicesDTO.ServicesDTO;
import com.ProFit.service.serviceService.ServiceService;

@Controller
@RequestMapping("/service")
public class ServiceController {

	@Autowired
	private ServiceService serviceService;

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
	public ResponseEntity<ServicesDTO> addService(@RequestBody ServicesDTO serviceDTO) {
		ServicesDTO createdService = serviceService.addService(serviceDTO);
		return new ResponseEntity<>(createdService, HttpStatus.CREATED);
	}

	// 更新服務的方法
	@PutMapping("/api/{serviceId}")
	@ResponseBody
	public ResponseEntity<ServicesDTO> updateService(@PathVariable Integer serviceId,
			@RequestBody ServicesDTO serviceDTO) {
		serviceDTO.setServiceId(serviceId);
		ServicesDTO updatedService = serviceService.updateService(serviceDTO);
		if (updatedService != null) {
			return ResponseEntity.ok(updatedService);
		} else {
			return ResponseEntity.notFound().build();
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

	// ...
}
package com.ProFit.controller.services.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ProFit.model.bean.servicesBean.ServiceApplicationBean;
import com.ProFit.model.dto.servicesDTO.ServiceApplicationsDTO;
import com.ProFit.service.serviceService.ServiceApplicationService;

@Controller
@RequestMapping("/a/serviceApplications")
public class ServiceApplicationController {

  @Autowired
  private ServiceApplicationService serviceApplicationService;

  // 返回主頁視圖
  @GetMapping
  public String showServiceApplicationsPage() {
    // 這裡可以添加一些需要提供視圖的數據
    return "servicesVIEW/backend/serviceApplications";
  }

  // 創建新的服務委託
  @PostMapping
  @ResponseBody
  public ResponseEntity<ServiceApplicationsDTO> createServiceApplication(
      @RequestBody ServiceApplicationBean serviceApplication) {
    ServiceApplicationsDTO createdApplication = serviceApplicationService.createServiceApplication(serviceApplication);
    return ResponseEntity.ok(createdApplication);
  }

  // 更新服務委託
  @PutMapping("/api/{id}")
  @ResponseBody
  public ResponseEntity<ServiceApplicationsDTO> updateServiceApplication(@PathVariable Integer id,
      @RequestBody ServiceApplicationBean serviceApplication) {
    serviceApplication.setServiceApplicationId(id);
    ServiceApplicationsDTO updatedApplication = serviceApplicationService.updateServiceApplication(serviceApplication);
    return ResponseEntity.ok(updatedApplication);
  }

  // 删除服務委託
  @DeleteMapping("/api/{id}")
  @ResponseBody
  public ResponseEntity<Void> deleteServiceApplication(@PathVariable Integer id) {
    serviceApplicationService.deleteServiceApplication(id);
    return ResponseEntity.ok().build();
  }

  // 更改狀態
  @PutMapping("/api/{id}/status")
  @ResponseBody
  public ResponseEntity<Boolean> updateStatus(@PathVariable Integer id, @RequestParam Integer status) {
    boolean updated = serviceApplicationService.updateStatus(id, status);
    return ResponseEntity.ok(updated);
  }

  // 更新内容
  @PutMapping("/api/{id}/content")
  @ResponseBody
  public ResponseEntity<Boolean> updateContent(@PathVariable Integer id, @RequestParam String content) {
    boolean updated = serviceApplicationService.updateContent(id, content);
    return ResponseEntity.ok(updated);
  }

  // 查找服務委託by Id
  @GetMapping("/api/{id}")
  @ResponseBody
  public ResponseEntity<ServiceApplicationsDTO> findServiceApplicationById(@PathVariable Integer id) {
    ServiceApplicationsDTO application = serviceApplicationService.findServiceApplicationById(id);
    return ResponseEntity.ok(application);
  }

  // 分頁查詢
  @GetMapping("/api/search")
  @ResponseBody
  public ResponseEntity<Page<ServiceApplicationsDTO>> searchServiceApplications(
      @RequestParam(required = false) Integer serviceId,
      @RequestParam(required = false) Integer status,
      @RequestParam(required = false) Integer caseownerId,
      @RequestParam(required = false) Integer freelancerId,
      Pageable pageable) {
    Page<ServiceApplicationsDTO> results;
    if (serviceId != null) {
      results = serviceApplicationService.findByServiceId(serviceId, pageable);
    } else if (status != null) {
      results = serviceApplicationService.findByStatus(status, pageable);
    } else if (caseownerId != null) {
      results = serviceApplicationService.findByCaseownerId(caseownerId, pageable);
    } else if (freelancerId != null) {
      results = serviceApplicationService.findByFreelancerId(freelancerId, pageable);
    } else {

      // 如果沒有指定任何參數，傳回所有結果
      results = serviceApplicationService.findByServiceId(null, pageable);
    }
    return ResponseEntity.ok(results);
  }
}
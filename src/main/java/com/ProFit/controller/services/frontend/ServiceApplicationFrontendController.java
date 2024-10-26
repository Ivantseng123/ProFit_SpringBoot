package com.ProFit.controller.services.frontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ProFit.model.bean.servicesBean.ServiceApplicationBean;
import com.ProFit.model.dto.coursesDTO.CoursesDTO;
import com.ProFit.model.dto.servicesDTO.ServiceApplicationsDTO;
import com.ProFit.model.dto.servicesDTO.ServicesDTO;
import com.ProFit.model.dto.usersDTO.UsersDTO;
import com.ProFit.service.serviceService.ServiceApplicationService;
import com.ProFit.service.serviceService.ServiceService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/c/serviceApplication")
public class ServiceApplicationFrontendController {

  @Autowired
  private ServiceApplicationService serviceApplicationService;

  @Autowired
  private ServiceService serviceService;

  // 獲取當前user 資訊
  private UsersDTO getCurrentUser(HttpSession session) {
    return (UsersDTO) session.getAttribute("CurrentUser");
  }

  // 跳轉到 新增/修改 委託的 頁面
  @GetMapping("/edit")
  public String showServiceApplicationsPage(@RequestParam Integer serviceId, HttpSession session, Model model) {
    UsersDTO currentUser = getCurrentUser(session);

    // 若沒有登入
    if (currentUser == null) {
      return "redirect:/user/profile";
    }

    // 驗證目前有登入 以及 有serviceId => 進入新增委託畫面
    if (serviceId != null) {

      ServicesDTO serviceDTO = serviceService.getServiceById(serviceId);

      model.addAttribute("currentUser", currentUser);
      model.addAttribute("ServicesDTO", serviceDTO);

      return "servicesVIEW/frontend/editServiceApplication";
    } else if (serviceId == null) {  // 若 有登入 但沒有serviceId => 進入編輯委託畫面
      model.addAttribute("currentUser", currentUser);
      return "servicesVIEW/frontend/editServiceApplication";
    }

    return "redirect:/user/profile";
  }

  // 查找服務委託by 委託Id
  @GetMapping("/api/{id}")
  @ResponseBody
  public ResponseEntity<ServiceApplicationsDTO> findServiceApplicationById(@PathVariable Integer id) {
    ServiceApplicationsDTO application = serviceApplicationService.findServiceApplicationById(id);
    if (application != null) {
      return ResponseEntity.ok(application);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  // 根据目前session的userid = caseowneruserid查询服務委託
  @GetMapping("/api/caseowner")
  @ResponseBody
  public ResponseEntity<Page<ServiceApplicationsDTO>> findByCaseownerId(HttpSession session, Pageable pageable) {
    UsersDTO currentUser = getCurrentUser(session);
    if (currentUser == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    Page<ServiceApplicationsDTO> applications = serviceApplicationService.findByCaseownerId(currentUser.getUserId(),
        pageable);
    return ResponseEntity.ok(applications);
  }

  // 根据目前session的userid = freelanceruserid查询服務委託
  @GetMapping("/api/freelancer")
  @ResponseBody
  public ResponseEntity<Page<ServiceApplicationsDTO>> findByFreelancerId(HttpSession session, Pageable pageable) {
    UsersDTO currentUser = getCurrentUser(session);
    if (currentUser == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    Page<ServiceApplicationsDTO> applications = serviceApplicationService.findByFreelancerId(currentUser.getUserId(),
        pageable);
    return ResponseEntity.ok(applications);
  }

  // 更改服務委託狀態
  @PutMapping("/api/{id}/status")
  @ResponseBody
  public ResponseEntity<Boolean> updateStatus(@PathVariable Integer id, @RequestParam Integer status,
      HttpSession session) {
    UsersDTO currentUser = getCurrentUser(session);
    if (currentUser == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    // 這裡可以加入更詳細的權限檢查邏輯
    boolean updated = serviceApplicationService.updateStatus(id, status);
    return ResponseEntity.ok(updated);
  }

  // 更新服務委託
  @PutMapping("/api/{id}")
  @ResponseBody
  public ResponseEntity<ServiceApplicationsDTO> updateServiceApplication(@PathVariable Integer id,
      @RequestBody ServiceApplicationBean serviceApplication,
      HttpSession session) {
    UsersDTO currentUser = getCurrentUser(session);
    if (currentUser == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    // 這裡可以加入更詳細的權限檢查邏輯
    serviceApplication.setServiceApplicationId(id);
    ServiceApplicationsDTO updatedApplication = serviceApplicationService.updateServiceApplication(serviceApplication);
    return ResponseEntity.ok(updatedApplication);
  }

  // 創建新的服務委託
  @PostMapping
  @ResponseBody
  public ResponseEntity<ServiceApplicationsDTO> createServiceApplication(
      @RequestBody ServiceApplicationBean serviceApplication,
      HttpSession session) {
    UsersDTO currentUser = getCurrentUser(session);
    if (currentUser == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    serviceApplication.setCaseownerId(currentUser.getUserId());
    ServiceApplicationsDTO createdApplication = serviceApplicationService.createServiceApplication(serviceApplication);
    return new ResponseEntity<>(createdApplication, HttpStatus.CREATED);
  }

  // 刪除服務委託，返回布林值表示删除是否成功
  @DeleteMapping("/{id}")
  public ResponseEntity<Boolean> deleteServiceApplication(@PathVariable Integer id, HttpSession session) {
    UsersDTO currentUser = getCurrentUser(session);
    if (currentUser == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }

    ServiceApplicationsDTO application = serviceApplicationService.findServiceApplicationById(id);
    if (application == null) {
      return ResponseEntity.ok(false);
    }

    // 驗證權限和狀態 (要是案主，且草稿狀態才能刪)
    if (!currentUser.getUserId().equals(application.getCaseownerId()) ||
        application.getStatus() != 0) {
      return ResponseEntity.ok(false);
    }

    try {
      serviceApplicationService.deleteServiceApplication(id);
      return ResponseEntity.ok(true);
    } catch (Exception e) {
      return ResponseEntity.ok(false);
    }
  }
}

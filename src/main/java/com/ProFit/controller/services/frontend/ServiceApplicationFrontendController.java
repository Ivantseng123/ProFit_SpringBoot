package com.ProFit.controller.services.frontend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import org.springframework.web.multipart.MultipartFile;

import com.ProFit.model.bean.servicesBean.ServiceApplicationBean;
import com.ProFit.model.bean.servicesBean.ServiceOrderBean;
import com.ProFit.model.dto.chatsDTO.ChatUserDTO;
import com.ProFit.model.dto.coursesDTO.CoursesDTO;
import com.ProFit.model.dto.majorsDTO.PageResponse;
import com.ProFit.model.dto.servicesDTO.ServiceApplicationsDTO;
import com.ProFit.model.dto.servicesDTO.ServiceOrdersDTO;
import com.ProFit.model.dto.servicesDTO.ServicesDTO;
import com.ProFit.model.dto.usersDTO.UsersDTO;
import com.ProFit.service.chatService.ChatService;
import com.ProFit.service.serviceService.ServiceApplicationService;
import com.ProFit.service.serviceService.ServiceOrdersService;
import com.ProFit.service.serviceService.ServiceService;
import com.ProFit.service.utilsService.FirebaseStorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/c/serviceApplication")
public class ServiceApplicationFrontendController {

  @Autowired
  private ServiceApplicationService serviceApplicationService;

  @Autowired
  private ServiceService serviceService;

  @Autowired
  private ChatService chatService;

  @Autowired
  private FirebaseStorageService firebaseStorageService;

  @Autowired
  private ServiceOrdersService serviceOrdersService;

  // 獲取當前user 資訊
  private UsersDTO getCurrentUser(HttpSession session) {
    return (UsersDTO) session.getAttribute("CurrentUser");
  }

  // 跳轉到 新增 委託的 頁面
  @GetMapping("/add")
  public String showAddServiceApplicationsPage(@RequestParam Integer serviceId, HttpSession session, Model model) {
    UsersDTO currentUser = getCurrentUser(session);

    try {
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
      } else if (serviceId == null) { // 若 有登入 但沒有serviceId => 進入編輯委託畫面
        model.addAttribute("currentUser", currentUser);
        return "servicesVIEW/frontend/editServiceApplication";
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return "redirect:/user/profile";
  }

  // 跳轉到 修改 委託的 頁面
  @GetMapping("/edit")
  public String showEditServiceApplicationsPage(@RequestParam Integer serviceApplicationId, HttpSession session,
      Model model) {
    UsersDTO currentUser = getCurrentUser(session);
    ServiceApplicationsDTO serviceApplicationDTO = serviceApplicationService
        .findServiceApplicationById(serviceApplicationId);

    System.out.println(serviceApplicationDTO);

    try {
      // 若沒有登入
      if (currentUser == null) {
        return "redirect:/user/profile";
      }

      // 驗證目前有登入 以及 有serviceApplication => 進入修改委託畫面
      if (serviceApplicationDTO != null) {

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("serviceApplicationDTO", serviceApplicationDTO);

        return "servicesVIEW/frontend/editServiceApplication";
      } else if (serviceApplicationDTO == null) { // 若 有登入 但沒有serviceId => 進入編輯委託畫面
        model.addAttribute("currentUser", currentUser);
        return "servicesVIEW/frontend/editServiceApplication";
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return "redirect:/user/profile";
  }

  @GetMapping("/order/{serviceApplicationId}")
  public String showCreateOrderPage(@PathVariable Integer serviceApplicationId, HttpSession session,
      Model model) {
    UsersDTO currentUser = getCurrentUser(session);
    ServiceApplicationsDTO serviceApplicationDTO = serviceApplicationService
        .findServiceApplicationById(serviceApplicationId);

    System.out.println(serviceApplicationDTO);

    try {
      // 若沒有登入
      if (currentUser == null) {
        return "redirect:/user/profile";
      }

      if (serviceApplicationDTO != null) {
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("serviceApplicationDTO", serviceApplicationDTO);

        return "servicesVIEW/frontend/createOrder";
      } else {
        return "redirect:/user/profile";
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return "redirect:/user/profile";

  }

  /////// -------------以下是RESTFULAPI--------/////////////
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

  // 根据目前session的userid = caseowneruserid 查询服務委託
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

  // 根据目前session的userid = freelanceruserid 查询服務委託
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

    System.out.println(status);
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
      @ModelAttribute ServiceApplicationBean serviceApplication,
      @RequestParam("appendixFile") MultipartFile file,
      HttpSession session) {
    // 獲取當前用戶
    UsersDTO currentUser = getCurrentUser(session);
    if (currentUser == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    // 將當前用戶設置為委託的擁有者
    serviceApplication.setCaseownerId(currentUser.getUserId());

    // 檢查是否有上傳文件
    if (!file.isEmpty()) {
      try {

        // 文件存儲邏輯（存儲在firebase）
        String URL = firebaseStorageService.uploadFile(file);
        serviceApplication.setAppendixUrl(URL);

      } catch (Exception e) {
        // 處理文件上傳錯誤
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
    }

    // 這裡可以加入更詳細的權限檢查邏輯
    serviceApplication.setServiceApplicationId(id);

    System.out.println(serviceApplication);
    ServiceApplicationsDTO updatedApplication = serviceApplicationService.updateServiceApplication(serviceApplication);
    System.out.println(updatedApplication);
    return ResponseEntity.ok(updatedApplication);
  }

  // 創建新的服務委託
  @PostMapping("/api")
  @ResponseBody
  public ResponseEntity<ServiceApplicationsDTO> createServiceApplication(
      @ModelAttribute ServiceApplicationBean serviceApplication,
      @RequestParam("appendixFile") MultipartFile file,
      HttpSession session) {
    // 獲取當前用戶
    UsersDTO currentUser = getCurrentUser(session);
    if (currentUser == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    // 將當前用戶設置為委託的擁有者
    serviceApplication.setCaseownerId(currentUser.getUserId());

    // 檢查是否有上傳文件
    if (!file.isEmpty()) {
      try {

        // 文件存儲邏輯（存儲在firebase）
        String URL = firebaseStorageService.uploadFile(file);
        serviceApplication.setAppendixUrl(URL);

      } catch (Exception e) {
        // 處理文件上傳錯誤
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
    }

    // 創建服務委託
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

    // 驗證權限和狀態 (要是案主，且草稿或關閉狀態才能刪)
    if (!currentUser.getUserId().equals(application.getCaseownerId()) ||
        application.getStatus() != 0 && application.getStatus() != 4 && application.getStatus() != 3) {
      return ResponseEntity.ok(false);
    }

    try {
      serviceApplicationService.deleteServiceApplication(id);
      return ResponseEntity.ok(true);
    } catch (Exception e) {
      return ResponseEntity.ok(false);
    }
  }

  // 查詢 與 currentUser 有共同聊天室的 user(freelancer)
  @GetMapping("/api/userChatList/{currentUserId}")
  @ResponseBody
  public List<ChatUserDTO> getCurrentUserChatUserList(@PathVariable Integer currentUserId) {

    // Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC,
    // "lastMessageAt"));

    List<ChatUserDTO> currentUserChatUserList = chatService.getCurrentUserChatUserList(currentUserId);
    return currentUserChatUserList;
  }

  // 查詢 這個freelancer 有的服務
  @GetMapping("/api/ServiceList/{freelancerId}")
  @ResponseBody
  public PageResponse<ServicesDTO> getServiceListByFreelancerId(@PathVariable Integer freelancerId) {

    // Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC,
    // "lastMessageAt"));

    PageResponse<ServicesDTO> servicesDTO = serviceService.searchServicePage(null, null, 1, freelancerId, null, null, 0,
        20,
        "serviceUpdateDate", false);

    return servicesDTO;
  }

  /// ------成立委託訂單-----///////
  @PostMapping("/api/order")
  @ResponseBody
  public ResponseEntity<Boolean> postMethodName(
      @RequestBody ServiceOrderBean serviceOrder, HttpSession session) {

    System.out.println("Service Order Amount: " + serviceOrder.getServiceOrderAmount());
    System.out.println(serviceOrder.getServiceApplicationId());

    // 檢查必要欄位
    if (serviceOrder.getServiceOrderAmount() == null) {
      return ResponseEntity.badRequest().body(false);
    }

    // 獲取當前用戶
    UsersDTO currentUser = getCurrentUser(session);
    if (currentUser == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }

    try {
      ServiceOrdersDTO insertServiceOrder = serviceOrdersService.insertServiceOrder(serviceOrder);
      if (insertServiceOrder != null) {
        serviceApplicationService.updateStatus(serviceOrder.getServiceApplicationId(), 5);
      }
      return ResponseEntity.ok(true);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.ok(false);
    }

  }

}

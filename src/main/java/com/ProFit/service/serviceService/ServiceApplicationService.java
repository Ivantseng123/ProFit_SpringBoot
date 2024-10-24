package com.ProFit.service.serviceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.ProFit.model.bean.chatsBean.ChatBean;
import com.ProFit.model.bean.servicesBean.ServiceApplicationBean;
import com.ProFit.model.bean.servicesBean.ServiceBean;
import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.dao.chatsCRUD.ChatRepository;
import com.ProFit.model.dao.servicesCRUD.ServiceApplicationRepository;
import com.ProFit.model.dao.servicesCRUD.ServiceRepository;
import com.ProFit.model.dao.usersCRUD.UsersRepository;
import com.ProFit.model.dto.servicesDTO.ServiceApplicationsDTO;

@Service
public class ServiceApplicationService {

  @Autowired
  private ServiceApplicationRepository serviceApplicationRepository;

  @Autowired
  private UsersRepository usersRepository;

  @Autowired
  private ServiceRepository serviceRepository;

  @Autowired
  private ChatRepository chatRepository;

  // 創建新的服務委託
  @Transactional
  public ServiceApplicationsDTO createServiceApplication(ServiceApplicationBean serviceApplication) {
    Users caseOwner = usersRepository.findById(serviceApplication.getCaseownerId()).get();
    Users freelancer = usersRepository.findById(serviceApplication.getFreelancerId()).get();
    serviceApplication.setCaseowner(caseOwner);
    serviceApplication.setFreelancer(freelancer);

    serviceApplication.setService(serviceRepository.findById(serviceApplication.getServiceId()).get());

    ChatBean chat = chatRepository.findByUserId1AndUserId2AndServiceId(serviceApplication.getFreelancerId(),
        serviceApplication.getCaseownerId(), serviceApplication.getServiceId());

    System.out.println(chat);

    if (chat == null) {
      // 創建新的聊天紀錄
      chat = new ChatBean();
      chat.setServiceId(serviceApplication.getServiceId());
      chat.setUserId1(serviceApplication.getFreelancerId());
      chat.setUserId2(serviceApplication.getCaseownerId());
      chat.setCreateAt(LocalDateTime.now());
      chat.setLastMessageAt(LocalDateTime.now());
      chat.setStatus(0); // 0 是使用中
      chat = chatRepository.save(chat);
    } else {
      // 最新訊息時間改為 最新委託時間
      chat.setLastMessageAt(LocalDateTime.now());
    }
    serviceApplication.setChatId(chat.getChatId());
    serviceApplication.setChat(chat);

    serviceApplication.setCreatedAt(LocalDateTime.now());
    serviceApplication.setUpdatedAt(LocalDateTime.now());

    ServiceApplicationBean savedBean = serviceApplicationRepository.save(serviceApplication);
    return ServiceApplicationsDTO.fromEntity(savedBean);
  }

  // 更新服務委託
  @Transactional
  public ServiceApplicationsDTO updateServiceApplication(ServiceApplicationBean newServiceApplication) {

    // 1. 驗證並取得現有服務委託
    Optional<ServiceApplicationBean> optional = serviceApplicationRepository
        .findById(newServiceApplication.getServiceApplicationId());
    if (!optional.isPresent()) {
      throw new RuntimeException("Service application not found");
    }
    ServiceApplicationBean oldServiceApplication = optional.get();

    try {
      // 2. 取得並驗證關聯用戶
      Users caseOwner = usersRepository.findById(newServiceApplication.getCaseownerId())
          .orElseThrow(() -> new RuntimeException("Case owner not found"));
      Users freelancer = usersRepository.findById(newServiceApplication.getFreelancerId())
          .orElseThrow(() -> new RuntimeException("Freelancer not found"));

      // 3. 取得並驗證服務
      ServiceBean service = serviceRepository.findById(newServiceApplication.getServiceId())
          .orElseThrow(() -> new RuntimeException("Service not found"));

      // 4. 處理聊天記錄
      ChatBean chat;
      if (oldServiceApplication.getChatId() != null) {
        // 使用現有聊天
        chat = chatRepository.findById(oldServiceApplication.getChatId())
            .orElseThrow(() -> new RuntimeException("Existing chat not found"));
      } else {
        // 建立新的聊天記錄
        chat = new ChatBean();
        chat.setServiceId(newServiceApplication.getServiceId());
        chat.setUserId1(newServiceApplication.getFreelancerId());
        chat.setUserId2(newServiceApplication.getCaseownerId());
        chat.setCreateAt(LocalDateTime.now());
        chat.setLastMessageAt(LocalDateTime.now());
        chat.setStatus(0); // 0 是使用中
        chat = chatRepository.save(chat);
      }

      // 5. 更新服務委託資訊
      oldServiceApplication.setCaseowner(caseOwner);
      oldServiceApplication.setFreelancer(freelancer);
      oldServiceApplication.setService(service);
      oldServiceApplication.setChat(chat);

      // 更新其他字段
      oldServiceApplication.setCaseownerId(newServiceApplication.getCaseownerId());
      oldServiceApplication.setFreelancerId(newServiceApplication.getFreelancerId());
      oldServiceApplication.setServiceId(newServiceApplication.getServiceId());
      oldServiceApplication.setChatId(newServiceApplication.getChatId());

      oldServiceApplication.setServiceApplicationTitle(newServiceApplication.getServiceApplicationTitle());
      oldServiceApplication.setServiceApplicationSubitem(newServiceApplication.getServiceApplicationSubitem());
      oldServiceApplication.setServiceApplicationPrice(newServiceApplication.getServiceApplicationPrice());
      oldServiceApplication.setServiceApplicationAmount(newServiceApplication.getServiceApplicationAmount());
      oldServiceApplication.setServiceApplicationUnit(newServiceApplication.getServiceApplicationUnit());
      oldServiceApplication.setServiceApplicationContent(newServiceApplication.getServiceApplicationContent());
      oldServiceApplication.setAppendixUrl(newServiceApplication.getAppendixUrl());
      oldServiceApplication.setStatus(newServiceApplication.getStatus());
      oldServiceApplication.setServiceApplicationMission(newServiceApplication.getServiceApplicationMission());
      oldServiceApplication.setServiceApplicationDoneDate(newServiceApplication.getServiceApplicationDoneDate());
      // oldServiceApplication.setCreatedAt(保持不變);
      oldServiceApplication.setUpdatedAt(LocalDateTime.now());

      // 6. 保存並回傳结果
      ServiceApplicationBean updatedBean = serviceApplicationRepository.save(oldServiceApplication);
      return ServiceApplicationsDTO.fromEntity(updatedBean);

    } catch (Exception e) {
      // 這裡可以根據需要處理不同類型的異常
      throw new RuntimeException("Failed to update service application: " + e.getMessage(), e);
    }

  }

  // 刪除服務委託
  @Transactional
  public void deleteServiceApplication(Integer id) {
    serviceApplicationRepository.deleteById(id);
  }

  // 更改狀態
  @Transactional
  public boolean updateStatus(Integer id, Integer status) {
    Optional<ServiceApplicationBean> optional = serviceApplicationRepository.findById(id);
    if (optional.isPresent()) {
      int updatedCount = serviceApplicationRepository.updateStatus(id, status);
      return updatedCount > 0;
    }
    return false;
  }

  // 更新内容
  @Transactional
  public boolean updateContent(Integer id, String content) {
    Optional<ServiceApplicationBean> optional = serviceApplicationRepository.findById(id);
    if (optional.isPresent()) {
      int updatedCount = serviceApplicationRepository.updateContent(id, content);
      return updatedCount > 0;
    }
    return false;
  }

  // 查找 服務委託 byId
  public ServiceApplicationsDTO findServiceApplicationById(Integer id) {
    Optional<ServiceApplicationBean> bean = serviceApplicationRepository.findById(id);
    return bean.map(ServiceApplicationsDTO::fromEntity).orElse(null);
  }

  // 分頁查詢方法
  public Page<ServiceApplicationsDTO> findByServiceId(Integer serviceId, Pageable pageable) {
    Page<ServiceApplicationBean> beanPage = serviceApplicationRepository.findByServiceId(serviceId, pageable);
    return beanPage.map(ServiceApplicationsDTO::fromEntity);
  }

  public Page<ServiceApplicationsDTO> findByStatus(Integer status, Pageable pageable) {
    Page<ServiceApplicationBean> beanPage = serviceApplicationRepository.findByStatus(status, pageable);
    return beanPage.map(ServiceApplicationsDTO::fromEntity);
  }

  public Page<ServiceApplicationsDTO> findByCaseownerId(Integer caseownerId, Pageable pageable) {
    Page<ServiceApplicationBean> beanPage = serviceApplicationRepository.findByCaseownerId(caseownerId, pageable);
    return beanPage.map(ServiceApplicationsDTO::fromEntity);
  }

  public Page<ServiceApplicationsDTO> findByFreelancerId(Integer freelancerId, Pageable pageable) {
    Page<ServiceApplicationBean> beanPage = serviceApplicationRepository.findByFreelancerId(freelancerId, pageable);
    return beanPage.map(ServiceApplicationsDTO::fromEntity);
  }

  // 判斷是否具操作權限
  // 管理員都可操作
  // --狀態:
  // 0草稿、1洽談中(只有發起人能編輯其他欄位,另一人只能更改為完成)、成立(自動成立訂單service_order，尚未付款)、3婉拒、4關閉(由發起人關閉)、5已完成(訂單完成付款，且接案人完成任務)

  // 問題? 現在session有啥? 只有userId? 還是包括身分權限?
  // private boolean hasPermission(Integer currentUserId, Integer currentStatus) {
  // if (usersRepository.findById(currentUserId).get().getUserIdentity() == 4) {
  // return true;
  // }
  // return true;
  // }

}

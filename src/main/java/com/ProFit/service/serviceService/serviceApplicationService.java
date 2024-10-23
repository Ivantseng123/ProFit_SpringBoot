package com.ProFit.service.serviceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.ProFit.model.bean.servicesBean.ServiceApplicationBean;
import com.ProFit.model.dao.servicesCRUD.ServiceApplicationRepository;
import com.ProFit.model.dao.usersCRUD.UsersRepository;
import com.ProFit.model.dto.servicesDTO.ServiceApplicationsDTO;

@Service
public class serviceApplicationService {

  @Autowired
  private ServiceApplicationRepository serviceApplicationRepository;

  @Autowired
  private UsersRepository usersRepository;

  // 創建新的服務委託
  @Transactional
  public ServiceApplicationsDTO createServiceApplication(ServiceApplicationBean serviceApplication) {
    ServiceApplicationBean savedBean = serviceApplicationRepository.save(serviceApplication);
    return ServiceApplicationsDTO.fromEntity(savedBean);
  }

  // 更新服務委託
  @Transactional
  public ServiceApplicationsDTO updateServiceApplication(ServiceApplicationBean serviceApplication) {
    Optional<ServiceApplicationBean> optional = serviceApplicationRepository
        .findById(serviceApplication.getServiceApplicationId());
    if (optional.isPresent()) {
      ServiceApplicationBean updatedBean = serviceApplicationRepository.save(serviceApplication);
      return ServiceApplicationsDTO.fromEntity(updatedBean);
    }
    return null;
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

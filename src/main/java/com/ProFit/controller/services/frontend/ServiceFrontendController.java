package com.ProFit.controller.services.frontend;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ProFit.model.bean.majorsBean.MajorCategoryBean;
import com.ProFit.model.bean.servicesBean.ServiceBean;
import com.ProFit.model.dto.coursesDTO.CourseCategoryDTO;
import com.ProFit.model.dto.majorsDTO.MajorDTO;
import com.ProFit.model.dto.majorsDTO.PageResponse;
import com.ProFit.model.dto.servicesDTO.ServiceCategoryDTO;
import com.ProFit.model.dto.servicesDTO.ServicesDTO;
import com.ProFit.model.dto.usersDTO.UsersDTO;
import com.ProFit.service.majorService.MajorCategoryService;
import com.ProFit.service.majorService.MajorService;
import com.ProFit.service.serviceService.ServiceService;
import com.ProFit.service.utilsService.FirebaseStorageService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/c/service")
public class ServiceFrontendController {

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private MajorCategoryService majorCategoryService;

    @Autowired
    private MajorService majorService;

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @GetMapping("")
    public String serviceFrontendPage() {
        return "servicesVIEW/frontend/serviceOverView";
    }

    @GetMapping("/add")
    public String createServiceFrontendPage() {
        return "servicesVIEW/frontend/createService";
    }

    @GetMapping("/{serviceId}")
    public String singleServiceDetailPage(@PathVariable Integer serviceId, Model model) {
        ServicesDTO serviceDTO = serviceService.getServiceById(serviceId);

        model.addAttribute("serviceDTO", serviceDTO);

        return "servicesVIEW/frontend/singleServiceDetail";
    }

    // RestfulAPI
    @PostMapping("/api/searchAll")
    @ResponseBody
    public ResponseEntity<PageResponse<ServicesDTO>> searchAllServices(
            @RequestParam(required = false) String serviceTitle,
            @RequestParam(required = false) String userName,
            @RequestParam(defaultValue = "1") Integer status,
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) List<Integer> majorIdList,
            @RequestParam(required = false) Integer majorCategoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "serviceUpdateDate") String sortBy,
            @RequestParam(defaultValue = "false") boolean ascending) {

        System.out.println(page);

        System.out.println("serviceTitle " + serviceTitle);

        System.out.println(majorIdList);

        PageResponse<ServicesDTO> searchServicePage = serviceService.searchServicePage(serviceTitle, userName, status,
                userId, majorIdList, majorCategoryId, page, size, sortBy, ascending);

        return ResponseEntity.ok(searchServicePage);
    }

    // 查單筆 Service
    @GetMapping("/api/{serviceId}")
    @ResponseBody
    public ServicesDTO singleServiceDetail(@PathVariable Integer serviceId, Model model) {
        ServicesDTO serviceDTO = serviceService.getServiceById(serviceId);

        return serviceDTO;
    }

    // 回傳已有的服務清單
    @GetMapping("/api/userId")
    @ResponseBody
    public PageResponse<ServicesDTO> ServicesByUserId(HttpSession session) {

        UsersDTO currentUser = (UsersDTO) session.getAttribute("CurrentUser");

        if (currentUser != null) {
            PageResponse<ServicesDTO> servicesByUserId = serviceService.getServicesByUserId(
                    currentUser.getUserId(), 0, 10,
                    "serviceUpdateDate", false);

            return servicesByUserId;
        }
        return null;

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

    @GetMapping("/api/searchServiceByMajorCategory")
    @ResponseBody
    public List<ServiceCategoryDTO> searchServiceByMajorCategory() {

        List<MajorCategoryBean> allMajorCategories = majorCategoryService.findAllMajorCategories();

        // 創建返回的 DTO List
        List<ServiceCategoryDTO> result = new ArrayList<>();

        for (MajorCategoryBean majorCategory : allMajorCategories) {
            // 查詢該主類別下的服務總數(status==1)
            PageResponse<ServicesDTO> servicesByMajorCategoryId = serviceService
                    .getServicesByMajorCategoryId(majorCategory.getMajorCategoryId(), 1, 0, 1, "serviceUpdateDate",
                            false);
            long totalElements = servicesByMajorCategoryId.getTotalElements();

            // 創建 DTO 並將其添加到結果列表
            ServiceCategoryDTO serviceCategoryDTO = new ServiceCategoryDTO(
                    majorCategory.getMajorCategoryId(),
                    majorCategory.getCategoryName(),
                    totalElements);

            result.add(serviceCategoryDTO);
        }

        return result;
    }

    @GetMapping("/api/searchMajorByMajorCategory/{majorCategoryId}")
    @ResponseBody
    public List<Map<String, Object>> searchMajorByMajorCategory(@PathVariable Integer majorCategoryId,
            @RequestParam(value = "ServiceStatus", defaultValue = "1") Integer serviceStatus) {
        List<Map<String, Object>> result = new ArrayList<>();
        List<MajorDTO> majorsByCategoryId = majorService.findMajorsByCategoryId(majorCategoryId);

        for (MajorDTO majorDTO : majorsByCategoryId) {
            Integer countServiceNumByMajorId = serviceService.countServiceNumByMajorId(majorDTO.getMajorId(),
                    serviceStatus);

            // 自定義 Map 格式，將 MajorDTO 轉換為所需格式
            Map<String, Object> majorInfo = new HashMap<>();
            majorInfo.put("majorId", majorDTO.getMajorId());
            majorInfo.put("majorName", majorDTO.getMajorName());
            majorInfo.put("serviceCount", countServiceNumByMajorId);

            result.add(majorInfo);
        }

        return result;
    }

}

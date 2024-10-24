package com.ProFit.controller.services.frontend;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ProFit.model.bean.majorsBean.MajorCategoryBean;
import com.ProFit.model.dto.coursesDTO.CourseCategoryDTO;
import com.ProFit.model.dto.majorsDTO.PageResponse;
import com.ProFit.model.dto.servicesDTO.ServiceCategoryDTO;
import com.ProFit.model.dto.servicesDTO.ServicesDTO;
import com.ProFit.service.majorService.MajorCategoryService;
import com.ProFit.service.serviceService.ServiceService;

@Controller
@RequestMapping("/c/service")
public class ServiceFrontendController {

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private MajorCategoryService majorCategoryService;

    @GetMapping("")
    public String serviceFrontendPage() {
        return "servicesVIEW/frontend/serviceOverView";
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

    @GetMapping("/api/searchServiceByMajorCategory")
    @ResponseBody
    public List<ServiceCategoryDTO> searchServiceByMajorCategory() {

        List<MajorCategoryBean> allMajorCategories = majorCategoryService.findAllMajorCategories();

        // 創建返回的 DTO List
        List<ServiceCategoryDTO> result = new ArrayList<>();

        for (MajorCategoryBean majorCategory : allMajorCategories) {
            // 查詢該主類別下的服務總數
            PageResponse<ServicesDTO> servicesByMajorCategoryId = serviceService
                    .getServicesByMajorCategoryId(majorCategory.getMajorCategoryId(), 0, 1, "serviceUpdateDate",
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
}

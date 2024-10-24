package com.ProFit.controller.services.frontend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ProFit.model.dto.majorsDTO.PageResponse;
import com.ProFit.model.dto.servicesDTO.ServicesDTO;
import com.ProFit.service.serviceService.ServiceService;

@Controller
@RequestMapping("/c/service")
public class ServiceFrontendController {

    @Autowired
    private ServiceService serviceService;

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

}

package com.ProFit.controller.jobs.frontEnd;

import com.ProFit.model.bean.jobsBean.Jobs;
import com.ProFit.model.bean.jobsBean.JobsApplication;
import com.ProFit.model.dto.jobsDTO.PageDTO;
import com.ProFit.model.dto.usersDTO.UsersDTO;
import com.ProFit.service.jobService.JobsApplicationService;
import com.ProFit.service.jobService.JobsService;
import com.ProFit.service.majorService.IMajorCategoryService;
import com.ProFit.service.userService.IUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(JobsProfileController.URL)
public class JobsProfileController {

    public static final String URL = "/front/profile";

    private final JobsService jobsService;
    private final IMajorCategoryService categoryService;
    private final IUserService userService;

    private final JobsApplicationService jobsApplicationService;

    public JobsProfileController(JobsService jobsService, IMajorCategoryService categoryService, IUserService userService, JobsApplicationService jobsApplicationService) {
        this.jobsService = jobsService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.jobsApplicationService = jobsApplicationService;
    }

    @Value("${doc.resume.position}")
    private String RESUME_DIR;
    @Value("${doc.contract.position}")
    private String CONTRACT_DIR;

    @GetMapping("/applications/{page}")
    public String applications(HttpSession session, @PathVariable int page, Model model) {
        UsersDTO user = (UsersDTO) session.getAttribute("CurrentUser");

        Pageable pageable = PageRequest.of(page, 6);
        Page<Jobs> jobsPage = jobsService.findAll(pageable);

        // 获取分页内容
        List<Jobs> jobsList = jobsPage.getContent();

        PageDTO pageDTO = new PageDTO();
        pageDTO.setTotalPage(jobsPage.getTotalPages());
        pageDTO.setCurrentPage(jobsPage.getNumber());
        List<JobsApplication> applicationList = jobsApplicationService.findByUserId(user.getUserId(), pageable);
        for(JobsApplication application : applicationList){
            System.out.println("app=====" + application.getJobsApplicationId());
        }
        model.addAttribute("pageDTO", pageDTO);
        model.addAttribute("user", user);
        model.addAttribute("applications", applicationList);
        return "jobsVIEW/frontEnd/profile/applicationList";
    }


}

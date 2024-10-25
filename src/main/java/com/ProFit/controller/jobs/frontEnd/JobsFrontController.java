package com.ProFit.controller.jobs.frontEnd;

import com.ProFit.model.bean.jobsBean.Jobs;
import com.ProFit.model.dto.jobsDTO.PageDTO;
import com.ProFit.service.jobService.JobsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping(JobsFrontController.URL)
public class JobsFrontController {
    public static final String URL = "/front/jobs";

    private final JobsService jobsService;

    public JobsFrontController(JobsService jobsService) {
        this.jobsService = jobsService;
    }

    //查詢全部
    @GetMapping("/list/{page}")
    public String list(Model model, @PathVariable int page){
        // 使用 jobsDAO 进行分页查询
        Pageable pageable = PageRequest.of(page, 8);
        Page<Jobs> jobsPage = jobsService.findAll(pageable);

        // 获取分页内容
        List<Jobs> jobsList = jobsPage.getContent();

        PageDTO pageDTO = new PageDTO();
        pageDTO.setTotalPage(jobsPage.getTotalPages());
        pageDTO.setCurrentPage(jobsPage.getNumber());
        model.addAttribute("pageDTO", pageDTO);
        model.addAttribute("jobs", jobsList);
        return "jobsVIEW/frontEnd/jobList";
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable int id, Model model){
        Jobs jobs = jobsService.findById(id).orElse(null);
        if (jobs == null){
            return list(model, 0);
        }
        model.addAttribute("job", jobs);
        return "jobsVIEW/frontEnd/jobDetails";
    }

    @GetMapping({"/new", "/edit/{id}"})
    public String newJobs(Model model, @PathVariable Optional<Integer> id){
        Jobs jobs = null;
        if (id.isPresent()){
            jobs = jobsService.findById(id.get()).orElse(null);
        } else {
            jobs = new Jobs();
        }
        model.addAttribute("job", jobs);
        return "jobsVIEW/frontEnd/jobPost";
    }
}

package com.ProFit.controller.jobs;

import com.ProFit.model.bean.jobsBean.JobsApplication;
import com.ProFit.model.bean.jobsBean.JobsApplicationProject;
import com.ProFit.service.jobService.IJobsApplicationProjectService;
import com.ProFit.service.jobService.IJobsApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(JobsApplicationProjectController.URL)
public class JobsApplicationProjectController {
    public static final String URL = "/jobsApplicationProject";

    private final IJobsApplicationProjectService jobsApplicationProjectService;
    private final IJobsApplicationService jobsApplicationService;

    public JobsApplicationProjectController(IJobsApplicationProjectService jobsApplicationProjectService,IJobsApplicationService jobsApplicationService) {
        this.jobsApplicationProjectService = jobsApplicationProjectService;
        this.jobsApplicationService = jobsApplicationService;
    }//用final的建構子注入，可以確保資料安全性，如用@Autoweird可能會被修改



    //查詢全部
    @GetMapping("/list")
    public String listJobs(Model model){
        List<JobsApplicationProject> jobsApplicationProjectListList = jobsApplicationProjectService.findAll();
        model.addAttribute("jobsApplicationProjectListList", jobsApplicationProjectListList);
        return "jobsVIEW/jobsApplicationProjectList";
    }



    // 單筆查詢
    @GetMapping("/find/{id}")
    public ResponseEntity<JobsApplicationProject> getJobApplicationProject(@PathVariable Integer id) {
        JobsApplicationProject jobsApplicationProject = jobsApplicationProjectService.findById(id).orElse(null);
        if (jobsApplicationProject != null) {
            return ResponseEntity.ok(jobsApplicationProject);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // 刪除
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteJobApplicationProject(@PathVariable Integer id) {
        jobsApplicationProjectService.delete(id);
        return ResponseEntity.ok().build();
    }


//    // 新增
//    @PostMapping("/add")
//    public ResponseEntity<JobsApplicationProject> addJobApplicationProject(
//            @RequestParam("jobsApplicationId") Integer jobsApplicationId,
//            @RequestParam("jobsApplicationStatus") Byte jobsApplicationStatus,
//            @RequestParam("jobsProject") String jobsProject,
//            @RequestParam("jobsAmount") Integer jobsAmount) {
//
//        JobsApplication jobsApplication = jobsApplicationService.findById(jobsApplicationId).orElse(null);
//        JobsApplicationProject jobsApplicationProject = new JobsApplicationProject();
//
//        jobsApplicationProject.setJobsApplication(jobsApplication);
//        jobsApplicationProject.setJobsApplicationStatus(jobsApplicationStatus);
//        jobsApplicationProject.setJobsProject(jobsProject);
//        jobsApplicationProject.setJobsAmount(jobsAmount);
//
//        JobsApplicationProject savedJobsApplicationProject = jobsApplicationProjectService.save(jobsApplicationProject);
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedJobsApplicationProject);
//    }




//    //導向查看頁面
//    @GetMapping("/view/{id}")
//    public String view(@PathVariable("id") Integer id, Model model){
//        if (id != null) {
//            model.addAttribute("job", jobsService.findById(id).orElse(null));;
//        }
//        return "jobsVIEW/jobsApplicationProjectForm";
//    }
}


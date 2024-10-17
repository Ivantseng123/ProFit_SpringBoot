package com.ProFit.controller.jobs;

import com.ProFit.model.bean.jobsBean.Jobs;
import com.ProFit.model.bean.jobsBean.JobsApplication;
import com.ProFit.model.bean.jobsBean.JobsApplicationProject;
import com.ProFit.service.jobService.IJobsApplicationProjectService;
import com.ProFit.service.jobService.IJobsApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    public String listJobsApplicationProject(Model model){
        List<JobsApplicationProject> jobsApplicationProjectList = jobsApplicationProjectService.findAll();
        model.addAttribute("jobsApplicationProjectListList", jobsApplicationProjectList);
        return "jobsVIEW/jobsApplicationProjectList";
    }



    // 刪除
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteJobApplicationProject(@PathVariable Integer id) {
        jobsApplicationProjectService.delete(id);
        return ResponseEntity.ok().build();
    }

    // 導向新增頁面
    @GetMapping("/add")
    public String showAddForm(Model model) {

        // 創建一個新的Jobs對象並添加到model中
        model.addAttribute("jobApplicationProject", new JobsApplicationProject());
        // 返回jobsEdit視圖
        return "jobsVIEW/jobsApplicationProjectEdit";
    }

//    // 呈現新增後
//    @PostMapping("/add")
//    public String addJob(@ModelAttribute JobsApplicationProject newJobApplicationProject,
//            @RequestParam("jobsApplicationId") Integer jobsApplicationId) {
//
//        JobsApplication jobsApplication = jobsApplicationService.findById(jobsApplicationId).orElse(null);
//        JobsApplicationProject jobsApplicationProject = new JobsApplicationProject();
//
//
//        JobsApplicationProject savedJobsApplicationProject = jobsApplicationProjectService.save(jobsApplicationProject);
//        return "redirect:/jobsApplicationProject /list";
//    }

    //導向查看頁面
    @GetMapping("/view/{id}")
    public String view(@PathVariable("id") Integer id, Model model){
        if (id != null) {
            model.addAttribute("jobApplicationProject ", jobsApplicationProjectService.findById(id).orElse(null));;
        }
        return "jobsVIEW/jobsApplicationProjectForm";
    }


    //導向更新頁面
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model){
        if (id != null) {
            model.addAttribute("jobApplicationProject ", jobsApplicationProjectService.findById(id).orElse(null));;
        }
        return "jobsVIEW/jobsApplicationProjectEdit";
    }


    //呈現更新後
    @PutMapping("/update/{id}")
    public String updateJobApplicationProject (@ModelAttribute JobsApplicationProject  updatedJobApplicationProject,
                            Model model) {


        jobsApplicationProjectService.update(updatedJobApplicationProject);
        return "redirect:/jobsApplicationProject/list" ;//只要跟Date相關的就用redirect:轉回到頁面
    }



}


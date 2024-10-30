package com.ProFit.controller.jobs;

import com.ProFit.model.bean.jobsBean.Jobs;
import com.ProFit.model.bean.jobsBean.JobsApplication;
import com.ProFit.service.jobService.IJobsApplicationService;
import com.ProFit.service.jobService.JobsService;
import com.ProFit.service.userService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping(JobsApplicationController.URL)
public class JobsApplicationController {
    public static final String URL = "/jobsApplication";

    private final IJobsApplicationService jobsApplicationService;
    private final IUserService userService;

    public JobsApplicationController(IJobsApplicationService jobsApplicationService, IUserService userService) {
        this.jobsApplicationService = jobsApplicationService;
        this.userService = userService;

    }

    // 依赖注入
    @Autowired
    private JobsService jobsService;

    //查詢全部
    @GetMapping("/list")
    public String listJobsApplication(Model model){
        List<JobsApplication> jobsApplicationList = jobsApplicationService.findAll();

        model.addAttribute("jobsApplicationList", jobsApplicationList);
        return "jobsVIEW/jobsApplicationList";
    }

    //單筆查詢
    @GetMapping("/find/{id}")
    public ResponseEntity<JobsApplication> getJobApplication(@PathVariable Integer id) {
        JobsApplication jobsApplication = jobsApplicationService.findById(id).orElse(null);
        if (jobsApplication != null) {
            return ResponseEntity.ok(jobsApplication);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // 刪除
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteJobApplication(@PathVariable Integer id) {
        jobsApplicationService.delete(id);
        return ResponseEntity.ok().build();
    }


    // 導向新增頁面
//    @GetMapping("/add")
//    public String showAddForm(Model model) {
//
//        // 創建一個新的JobsApplication對象並添加到model中
//        model.addAttribute("jobApplication", new JobsApplication());
////        model.addAttribute("categories", categoryService.findAllMajorCategories());
//        // 返回jobsApplicationEdit視圖
//        return "jobsVIEW/jobsApplicationEdit";
//    }


    // 新增
//    @PostMapping("/add")
//    public ResponseEntity<JobsApplication> addJobApplication(
//            @RequestParam("jobsApplicationJobsId") Integer jobsApplicationJobsId,
//            @RequestParam("jobsApplicationMemberId") Integer jobsApplicationMemberId,
//            @RequestParam("jobsApplicationDate") String jobsApplicationDate,
//            @RequestParam("jobsApplicationStatus") Byte jobsApplicationStatus,
//            @RequestParam("jobsApplicationContract")String jobsApplicationContract) {
//
//        // 获取关联实体
//        Jobs job = jobsService.findById(jobsApplicationJobsId).orElseThrow();
//        Users applicant = userService.getUserInfoByID(jobsApplicationMemberId);
//
//        JobsApplication jobsApplication = new JobsApplication();
//
//        //以下遇到時間的設定就用此寫法
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            java.util.Date dateFinish = formatter.parse(jobsApplicationDate);
//            jobsApplication.setJobsApplicationDate(dateFinish);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        jobsApplication.setJobs(job);  // 设置关联的Jobs实体
//        jobsApplication.setApplicant(applicant);
//        jobsApplication.setJobsApplicationStatus(jobsApplicationStatus);
//        jobsApplication.setJobsApplicationResume(jobsApplication.getJobsApplicationResume());
//
//        JobsApplication savedJobApplication = jobsApplicationService.save(jobsApplication);
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedJobApplication);
//        }




        //導向查看頁面
        @GetMapping("/view/{id}")
        public String view(@PathVariable("id") Integer id, Model model){
            if (id != null) {
                model.addAttribute("jobApplication", jobsApplicationService.findById(id).orElse(null));;
            }
            return "jobsVIEW/jobsApplicationForm";
        }

        //導向更新頁面
        @GetMapping("/edit/{id}")
        public String edit(@PathVariable("id") Integer id, Model model){
            if (id != null) {
                model.addAttribute("jobApplication", jobsApplicationService.findById(id).orElse(null));;
            }
            return "jobsVIEW/jobsApplicationEdit";
        }

        //呈現更新後
        @PutMapping("/update/{id}")
        public String updateJob(@ModelAttribute JobsApplication updatedJob,
                                @RequestParam("status") Byte status,
                                @RequestParam("Date") String date,
                                Model model) {



            //以下遇到時間的設定就用此寫法
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date dateFinish = formatter.parse(date);
                updatedJob.setJobsApplicationDate(dateFinish);
            } catch (Exception e) {
                e.printStackTrace();
            }
            jobsApplicationService.update(updatedJob);
            return "redirect:/jobsApplication/list" ;//redirect:轉回到頁面
        }

}
package com.ProFit.controller.jobs;

import com.ProFit.model.bean.jobsBean.Jobs;
import com.ProFit.model.bean.jobsBean.JobsApplication;
import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.service.jobService.IJobsApplicationService;
import com.ProFit.service.jobService.JobsService;
import com.ProFit.service.userService.IUserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping(JobsApplicationController.URL)
public class JobsApplicationController {
    public static final String URL = "/jobsApplication";

    private final IJobsApplicationService jobsApplicationService;
    private final IUserService userService;
    private final JobsService jobsService;

    public JobsApplicationController(IJobsApplicationService jobsApplicationService, IUserService userService, JobsService jobsService) {
        this.jobsApplicationService = jobsApplicationService;
        this.userService = userService;
        this.jobsService = jobsService;
    }

    //查詢全部
    @GetMapping("/list")
    public String listJobsApplication(Model model) {
        List<JobsApplication> jobsApplicationList = jobsApplicationService.findAll();
        model.addAttribute("jobsApplicationList", jobsApplicationList);
        return "jobsVIEW/jobsApplicationList";
    }


    // 刪除
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        jobsApplicationService.delete(id);
        return ResponseEntity.ok().build();
    }

    // 導向新增頁面
    @GetMapping("/add")
    public String showAddForm(Model model) {

        // 創建一個新的Jobs對象並添加到model中
        model.addAttribute("jobApplication", new JobsApplication());
        // 返回jobsEdit視圖
        return "jobsVIEW/jobsApplicationEdit";
    }

    // 呈現新增後
    @PostMapping("/add")
    public String addJobApplication(@ModelAttribute JobsApplication newjobApplication,
                                    @RequestParam("jobsId") Integer jobsId,
                                    @RequestParam("userId") Integer userId) {
        Jobs poster = jobsService.findById(jobsId).orElse(null);
        if (poster != null) {
            newjobApplication.setPoster(poster); // 將當前用戶設置為職缺的發布者
        }
        // 獲取當前用戶的 ID
        Users applicant = userService.getUserInfoByID(userId);
        if (applicant != null) {
            newjobApplication.setApplicant(applicant); // 將當前用戶設置為職缺的發布者
        }

        jobsApplicationService.save(newjobApplication);
        return "redirect:/jobsApplication/list";
    }


    //導向查看頁面
    @GetMapping("/view/{id}")
    public String view(@PathVariable("id") Integer id, Model model) {
        if (id != null) {
            model.addAttribute("jobApplication", jobsApplicationService.findById(id).orElse(null));
            ;
        }
        return "jobsVIEW/jobsApplicationForm";
    }


    //導向更新頁面
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        if (id != null) {
            model.addAttribute("jobApplication", jobsApplicationService.findById(id).orElse(null));
            ;
        }
        return "jobsVIEW/jobsApplicationEdit";
    }

    //呈現更新後
    @PutMapping("/update/{id}")
    public String updateJobApplication(@ModelAttribute JobsApplication updatedJobApplication,
                                       Model model) {
        jobsApplicationService.update(updatedJobApplication);
        return "redirect:/jobsApplication/list";//只要跟Date相關的就用redirect:轉回到頁面
    }

//    // 查看某位應徵者的所有申請(東榆使用的方法，這裡是寫在他的controller裡)
//    @GetMapping("/applicant/{applicantId}")
//    public String getApplicantApplications(@PathVariable("applicantId") Integer applicantId, Model model) {
//        List<JobsApplication> jobsApplication = jobsApplicationService.findByApplicantId(applicantId);
//        model.addAttribute("applications", jobsApplication);
//        return "jobsVIEW/applicantApplicationList";
//    }

}
package com.ProFit.controller.jobs;

import com.ProFit.model.bean.jobsBean.JobsApplication;
import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.service.jobService.IJobsApplicationService;
import com.ProFit.service.userService.IUserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Blob;
import java.sql.Date;
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


    // 新增
    @PostMapping("/add")
    public ResponseEntity<JobsApplication> addJobApplication(
            @RequestParam("jobsApplicationPostingId") Integer jobsApplicationPostingId,
            @RequestParam("jobsApplicationMemberId") Integer jobsApplicationMemberId,
            @RequestParam("jobsApplicationDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date jobsApplicationDate,
            @RequestParam("jobsApplicationStatus") Byte jobsApplicationStatus,
            @RequestParam("jobsApplicationContract")Blob jobsApplicationContract) {

        //拿到id
        Users poster = userService.getUserInfoByID(jobsApplicationPostingId);
        Users applicant = userService.getUserInfoByID(jobsApplicationMemberId);

        JobsApplication jobsApplication = new JobsApplication();

        jobsApplication.setPoster(poster);
        jobsApplication.setApplicant(applicant);
        jobsApplication.setJobsApplicationDate(jobsApplicationDate);
        jobsApplication.setJobsApplicationStatus(jobsApplicationStatus);
        jobsApplication.setJobsApplicationContract(jobsApplicationContract);

        JobsApplication savedJobApplication = jobsApplicationService.save(jobsApplication);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedJobApplication);
        }


        //導向查看頁面
        @GetMapping("/view/{id}")
        public String view(@PathVariable("id") Integer id, Model model){
            if (id != null) {
                model.addAttribute("jobApplication", jobsApplicationService.findById(id).orElse(null));;
            }
            return "jobsVIEW/jobsApplicationForm";
        }
//
//
//        //導向更新頁面
//        @GetMapping("/edit/{id}")
//        public String edit(@PathVariable("id") Integer id, Model model){
//            if (id != null) {
//                model.addAttribute("job", jobsApplicationService.findById(id).orElse(null));;
//            }
//            return "jobsVIEW/jobsApplicationEdit";
//        }
//
//
//        //呈現更新後
//        @PutMapping("/update/{id}")
//        public String updateJob(@PathVariable("id") String id, @ModelAttribute Jobs updatedJob,Model model,
//                @RequestParam("deadline") String deadline) {
//
//            //以下遇到時間的設定就用此寫法
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//            try {
//                java.util.Date dateFinish = formatter.parse(deadline);
//                updatedJobApplication.setJobsApplicationDeadline(dateFinish);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            jobsApplicationService.update(updatedJobApplication);
//            return "redirect:/jobsApplication/list" ;//只要跟Date相關的就用redirect:轉回到頁面
//        }


}
//package com.ProFit.controller.jobs;
//
//import com.ProFit.model.bean.jobsBean.JobsApplication;
//import com.ProFit.model.bean.usersBean.Users;
//import com.ProFit.service.jobService.IJobsApplicationService;
//import com.ProFit.service.userService.IUserService;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import java.sql.Blob;
//import java.sql.Date;
//import java.util.List;
//
//@Controller
//@RequestMapping(JobsApplicationController.URL)
//public class JobsApplicationController {
//    public static final String URL = "/jobsApplication";
//
//    private final IJobsApplicationService jobsApplicationService;
//    private final IUserService userService;
//
//    public JobsApplicationController(IJobsApplicationService jobsApplicationService, IUserService userService) {
//        this.jobsApplicationService = jobsApplicationService;
//        this.userService = userService;
//    }
//
//    //查詢全部
//    @GetMapping("/all")
//    public ResponseEntity<List<JobsApplication>> listJobsApplications() {
//        List<JobsApplication> jobsApplicationList = jobsApplicationService.findAll();
//        return ResponseEntity.ok(jobsApplicationList);
//    }
//
//    //單筆查詢
//    @GetMapping("/find/{id}")
//    public ResponseEntity<JobsApplication> getJobApplication(@PathVariable Integer id) {
//        JobsApplication jobsApplication = jobsApplicationService.findById(id);
//        if (jobsApplication != null) {
//            return ResponseEntity.ok(jobsApplication);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    // 新增
//    @PostMapping("/add")
//    public ResponseEntity<JobsApplication> addJobApplication(
//            @RequestParam("jobsApplicationPostingId") Integer jobsApplicationPostingId,
//            @RequestParam("jobsApplicationMemberId") Integer jobsApplicationMemberId,
//            @RequestParam("jobsApplicationDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date jobsApplicationDate,
//            @RequestParam("jobsApplicationStatus") Byte jobsApplicationStatus,
//            @RequestParam("jobsApplicationContract")Blob jobsApplicationContract) {
//
//        //拿到id
//        Users poster = userService.getUserInfoByID(jobsApplicationPostingId);
//        Users applicant = userService.getUserInfoByID(jobsApplicationMemberId);
//
//        JobsApplication jobsApplication = new JobsApplication();
//
//        jobsApplication.setPoster(poster);
//        jobsApplication.setApplicant(applicant);
//        jobsApplication.setJobsApplicationDate(jobsApplicationDate);
//        jobsApplication.setJobsApplicationStatus(jobsApplicationStatus);
//        jobsApplication.setJobsApplicationContract(jobsApplicationContract);
//
//        JobsApplication savedJobApplication = jobsApplicationService.save(jobsApplication);
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedJobApplication);
//    }
//
//    // 刪除
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<Void> deleteJobApplication(@PathVariable Integer id) {
//        jobsApplicationService.delete(id);
//        return ResponseEntity.ok().build();
//    }
//
//    // 更新
//    @PutMapping("/updated/{id}")
//    public ResponseEntity<JobsApplication> updateJobApplication(
//            @PathVariable Integer id,
//            @RequestParam("jobsApplicationPostingId") Integer jobsApplicationPostingId,
//            @RequestParam("jobsApplicationMemberId") Integer jobsApplicationMemberId,
//            @RequestParam("jobsApplicationDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date jobsApplicationDate,
//            @RequestParam("jobsApplicationStatus") Byte jobsApplicationStatus,
//            @RequestParam("jobsApplicationContract") Blob jobsApplicationContract) {
//
//        JobsApplication existingJobApplication = jobsApplicationService.findById(id);
//        if (existingJobApplication == null) {
//            return ResponseEntity.notFound().build();
//        }
//        //拿到id
//        Users poster = userService.getUserInfoByID(jobsApplicationPostingId);
//        Users applicant = userService.getUserInfoByID(jobsApplicationMemberId);
//
//        existingJobApplication.setPoster(poster);
//        existingJobApplication.setApplicant(applicant);
//        existingJobApplication.setJobsApplicationDate(jobsApplicationDate);
//        existingJobApplication.setJobsApplicationStatus(jobsApplicationStatus);
//        existingJobApplication.setJobsApplicationContract(jobsApplicationContract);
//
//        JobsApplication updatedJobApplication = jobsApplicationService.update(existingJobApplication);
//        return ResponseEntity.ok(updatedJobApplication);
//    }
//}
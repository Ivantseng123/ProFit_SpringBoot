package com.ProFit.controller.jobs;

import com.ProFit.model.bean.jobsBean.Jobs;
import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.service.jobService.IJobsService;
import com.ProFit.service.userService.IUserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@Controller
@RequestMapping(JobsController.URL)
public class JobsController {
    public static final String URL = "/jobs";

    private final IJobsService jobsService;
    private final IUserService userService;
    public JobsController(IJobsService jobsService, IUserService userService) {
        this.jobsService = jobsService;
        this.userService = userService;
    }

    //查詢全部
    @GetMapping("/list")
    public String listJobs(Model model){
        List<Jobs> jobsList = jobsService.findAll();
        model.addAttribute("jobsList", jobsList);
        return "jobsVIEW/jobsList";
    }

    // 單筆查詢
    @GetMapping("/find/{id}")
    public ResponseEntity<Jobs> getJob(@PathVariable Integer id) {
        Jobs job = jobsService.findById(id).orElse(null);
        if (job != null) {
            return ResponseEntity.ok(job);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//     刪除
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        jobsService.delete(id);
        return ResponseEntity.ok().build();//ok的意思代表後端沒問題，ok().build()代表沒回傳值給前端
    }

//    @GetMapping("/findOne")
//    public String showEditForm(@RequestParam("id") Integer id, Model model) {
//        Jobs jobs = jobsService.findById(id);
//        List<Jobs> jobsList = new ArrayList<>();
//        jobsList.add(jobs);
//        model.addAttribute("jobsList", jobsList);
//        return "jobsVIEW/jobsList";
//    }



    // 新增
    @PostMapping("/add")
    public ResponseEntity<Jobs> addJob(
            @RequestParam("jobsUserId") Integer jobsUserId,
            @RequestParam("jobsTitle") String jobsTitle,
            @RequestParam("jobsPostingDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date jobsPostingDate,
            @RequestParam("jobsApplicationDeadline") @DateTimeFormat(pattern = "yyyy-MM-dd") Date jobsApplicationDeadline,
            @RequestParam("jobsDescription") String jobsDescription,
            @RequestParam("jobsStatus") Byte jobsStatus,
            @RequestParam("jobsRequiredSkills") String jobsRequiredSkills,
            @RequestParam("jobsLocation") String jobsLocation,
            @RequestParam("jobsMaxSalary") Integer jobsMaxSalary,
            @RequestParam("jobsMinSalary") Integer jobsMinSalary,
            @RequestParam("jobsWorktime") String jobsWorktime,
            @RequestParam("jobsNumberOfOpenings") Integer jobsNumberOfOpenings) {

        Users user = userService.getUserInfoByID(jobsUserId);
        Jobs job = new Jobs();

        job.setUsers(user);
        job.setJobsTitle(jobsTitle);
        job.setJobsPostingDate(jobsPostingDate);
        job.setJobsApplicationDeadline(jobsApplicationDeadline);
        job.setJobsDescription(jobsDescription);
        job.setJobsStatus(jobsStatus);
        job.setJobsRequiredSkills(jobsRequiredSkills);
        job.setJobsLocation(jobsLocation);
        job.setJobsMaxSalary(jobsMaxSalary);
        job.setJobsMinSalary(jobsMinSalary);
        job.setJobsWorktime(jobsWorktime);
        job.setJobsNumberOfOpenings(jobsNumberOfOpenings);

        Jobs savedJob = jobsService.save(job);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedJob);
    }

//    @PostMapping(value = "/add")
//    public String addJob(@RequestParam("jobsUserId") Integer jobsUserId,
//                         @RequestParam("jobsTitle") String jobsTitle,
//                         @RequestParam("jobsPostingDate") String jobsPostingDate,
//                         @RequestParam("jobsApplicationDeadline") String jobsApplicationDeadline,
//                         @RequestParam("jobsDescription") String jobsDescription,
//                         @RequestParam("jobsStatus") Byte jobsStatus,
//                         @RequestParam("jobsRequiredSkills") String jobsRequiredSkills,
//                         @RequestParam("jobsLocation") String jobsLocation,
//                         @RequestParam("jobsMaxSalary") Integer jobsMaxSalary,
//                         @RequestParam("jobsMinSalary") Integer jobsMinSalary,
//                         @RequestParam("jobsWorktime") String jobsWorktime,
//                         @RequestParam("jobsNumberOfOpenings") Integer jobsNumberOfOpenings) {
//        Users user = userService.getUserInfoByID(jobsUserId);
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        Date postingDate = new Date();
//        Date deadline = new Date();
//        try {
//            postingDate = formatter.parse(jobsPostingDate);
//            deadline = formatter.parse(jobsApplicationDeadline);
//        } catch (ParseException ignore) {}
//        Jobs jobs = new Jobs();
//        jobs.setUsers(user);
//        jobs.setJobsTitle(jobsTitle);
//        jobs.setJobsPostingDate(postingDate);
//        jobs.setJobsApplicationDeadline(deadline);
//        jobs.setJobsDescription(jobsDescription);
//        jobs.setJobsStatus(jobsStatus);
//        jobs.setJobsRequiredSkills(jobsRequiredSkills);
//        jobs.setJobsLocation(jobsLocation);
//        jobs.setJobsMaxSalary(jobsMaxSalary);
//        jobs.setJobsMinSalary(jobsMinSalary);
//        jobs.setJobsWorktime(jobsWorktime);
//        jobs.setJobsNumberOfOpenings(jobsNumberOfOpenings);
//        jobsService.save(jobs);
//        return "jobsVIEW/jobsSuccess";
//    }





    // 刪除
//    @DeleteMapping("/delete")
//    public ResponseEntity<?> delete(@RequestParam("id") Integer id){
//        jobsService.delete(id);
//        return ResponseEntity.ok().build();//ok的意思代表後端沒問題，ok().build()代表沒回傳值給前端
//    }

//第二種寫法
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteJob(@PathVariable Integer id) {
//        jobsService.delete(id);
//        return ResponseEntity.ok().build();
//    }






    //導向更新頁面
//    @GetMapping("/edit")
//    public String edit(@RequestParam(value = "id", required = false) Integer id, Model model){
//        if (id != null) {
//            model.addAttribute("job", jobsService.findById(id));
//        } else {
//            model.addAttribute("job", null);
//        }
//        return "jobsVIEW/jobsForm";
//    }





    // 更新
//    @PutMapping("/updated/{id}")
//    public ResponseEntity<Jobs> updateJob(
//            @PathVariable Integer id,
//            @RequestParam("jobsUserId") Integer jobsUserId,
//            @RequestParam("jobsTitle") String jobsTitle,
//            @RequestParam("jobsPostingDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date jobsPostingDate,
//            @RequestParam("jobsApplicationDeadline") @DateTimeFormat(pattern = "yyyy-MM-dd") Date jobsApplicationDeadline,
//            @RequestParam("jobsDescription") String jobsDescription,
//            @RequestParam("jobsStatus") Byte jobsStatus,
//            @RequestParam("jobsRequiredSkills") String jobsRequiredSkills,
//            @RequestParam("jobsLocation") String jobsLocation,
//            @RequestParam("jobsMaxSalary") Integer jobsMaxSalary,
//            @RequestParam("jobsMinSalary") Integer jobsMinSalary,
//            @RequestParam("jobsWorktime") String jobsWorktime,
//            @RequestParam("jobsNumberOfOpenings") Integer jobsNumberOfOpenings) {
//
//        Jobs existingJob = jobsService.findById(id);
//        if (existingJob == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        Users user = userService.getUserInfoByID(jobsUserId);
//        existingJob.setUsers(user);
//        existingJob.setJobsTitle(jobsTitle);
//        existingJob.setJobsPostingDate(jobsPostingDate);
//        existingJob.setJobsApplicationDeadline(jobsApplicationDeadline);
//        existingJob.setJobsDescription(jobsDescription);
//        existingJob.setJobsStatus(jobsStatus);
//        existingJob.setJobsRequiredSkills(jobsRequiredSkills);
//        existingJob.setJobsLocation(jobsLocation);
//        existingJob.setJobsMaxSalary(jobsMaxSalary);
//        existingJob.setJobsMinSalary(jobsMinSalary);
//        existingJob.setJobsWorktime(jobsWorktime);
//        existingJob.setJobsNumberOfOpenings(jobsNumberOfOpenings);
//
//        Jobs updatedJob = jobsService.update(existingJob);
//        return ResponseEntity.ok(updatedJob);
//    }
//    @PostMapping("/edit")
//    public String edit(
//                       @RequestParam("jobsId") Integer id,
//                       @RequestParam("jobsUserId") Integer jobsUserId,
//                       @RequestParam("jobsTitle") String jobsTitle,
//                       @RequestParam("jobsPostingDate") String jobsPostingDate,
//                       @RequestParam("jobsApplicationDeadline") String jobsApplicationDeadline,
//                       @RequestParam("jobsDescription") String jobsDescription,
//                       @RequestParam("jobsStatus") Byte jobsStatus,
//                       @RequestParam("jobsRequiredSkills") String jobsRequiredSkills,
//                       @RequestParam("jobsLocation") String jobsLocation,
//                       @RequestParam("jobsMaxSalary") Integer jobsMaxSalary,
//                       @RequestParam("jobsMinSalary") Integer jobsMinSalary,
//                       @RequestParam("jobsWorktime") String jobsWorktime,
//                       @RequestParam("jobsNumberOfOpenings") Integer jobsNumberOfOpenings){
//        Users user = userService.getUserInfoByID(jobsUserId);
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        Date postingDate = new Date();
//        Date deadline = new Date();
//        try {
//            postingDate = formatter.parse(jobsPostingDate);
//            deadline = formatter.parse(jobsApplicationDeadline);
//        } catch (ParseException ignore) {
//        }
//        Jobs jobs = jobsService.findById(id);
//        jobs.setUsers(user);
//        jobs.setJobsTitle(jobsTitle);
//        jobs.setJobsPostingDate(postingDate);
//        jobs.setJobsApplicationDeadline(deadline);
//        jobs.setJobsDescription(jobsDescription);
//        jobs.setJobsStatus(jobsStatus);
//        jobs.setJobsRequiredSkills(jobsRequiredSkills);
//        jobs.setJobsLocation(jobsLocation);
//        jobs.setJobsMaxSalary(jobsMaxSalary);
//        jobs.setJobsMinSalary(jobsMinSalary);
//        jobs.setJobsWorktime(jobsWorktime);
//        jobs.setJobsNumberOfOpenings(jobsNumberOfOpenings);
//        jobsService.update(jobs);
//        return "redirect:all";
//    }
}


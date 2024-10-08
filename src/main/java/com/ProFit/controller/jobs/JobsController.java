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

import java.text.SimpleDateFormat;
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

//    // 單筆查詢
//    @GetMapping("/find/{id}")
//    public ResponseEntity<Jobs> getJob(@PathVariable Integer id) {
//        Jobs job = jobsService.findById(id).orElse(null);
//        if (job != null) {
//            return ResponseEntity.ok(job);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

//    @GetMapping("/findOne")
//    public String showEditForm(@RequestParam("id") Integer id, Model model) {
//        Jobs jobs = jobsService.findById(id);
//        List<Jobs> jobsList = new ArrayList<>();
//        jobsList.add(jobs);
//        model.addAttribute("jobsList", jobsList);
//        return "jobsVIEW/jobsList";
//    }


    //     刪除
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        jobsService.delete(id);
        return ResponseEntity.ok().build();//ok的意思代表後端沒問題，ok().build()代表沒回傳值給前端
    }
//第二種寫法
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteJob(@PathVariable Integer id) {
//        jobsService.delete(id);
//        return ResponseEntity.ok().build();
//    }



    // 導向新增頁面
    @GetMapping("/add")
    public String showAddForm(Model model) {

        // 創建一個新的Jobs對象並添加到model中
        model.addAttribute("job", new Jobs());
        // 返回jobsEdit視圖
        return "jobsVIEW/jobsEdit";
    }


//    // 呈現新增後
//    @PostMapping("/add")
//    public String addJob(@ModelAttribute Jobs newJob,
//                         @RequestParam("userId") Integer userId,
//                         @RequestParam("deadline")
//                         @DateTimeFormat(pattern = "yyyy-MM-dd") Date deadline){
//        // 獲取當前用戶的 ID
//        Users id = userService.getUserInfoByID(userId); // 獲取當前用戶
//        if (id != null) {
//            newJob.setUsers(id); // 將當前用戶設置為職缺的發布者
//        }
//        // 設置職缺的發布日期為當前日期
//        newJob.setJobsPostingDate(new Date());
//        // 設置職缺的截止日期
//        newJob.setJobsApplicationDeadline(deadline);
//
//        jobsService.save(newJob);
//        return "redirect:/jobs/list";
//    }
    // 呈現新增後
    @PostMapping("/add")
    public String addJob(
                         @ModelAttribute Jobs newJob,
                         @RequestParam("userId") Integer userId,
                         @RequestParam("deadline")String deadline) {
        // 獲取當前用戶的 ID
        Users usersId = userService.getUserInfoByID(userId); // 獲取當前用戶
        if (usersId != null) {
            newJob.setUsers(usersId); // 將當前用戶設置為職缺的發布者
        }

        //以下遇到時間的設定就用此寫法
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dateFinish = formatter.parse(deadline);
            newJob.setJobsApplicationDeadline(dateFinish);
        } catch (Exception e) {
            e.printStackTrace();
        }
        jobsService.save(newJob);
        return "redirect:/jobs/list";
    }


    //導向查看頁面
    @GetMapping("/view/{id}")
    public String view(@PathVariable("id") Integer id, Model model){
        if (id != null) {
            model.addAttribute("job", jobsService.findById(id).orElse(null));;
        }
        return "jobsVIEW/jobsForm";
    }


    //導向更新頁面
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model){
        if (id != null) {
            model.addAttribute("job", jobsService.findById(id).orElse(null));;
        }
        return "jobsVIEW/jobsEdit";
    }


    //呈現更新後
    @PutMapping("/update/{id}")
    public String updateJob(@PathVariable("id") String id,
                            @ModelAttribute Jobs updatedJob,
                            Model model,
                            @RequestParam("deadline") String deadline) {

        //以下遇到時間的設定就用此寫法
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dateFinish = formatter.parse(deadline);
            updatedJob.setJobsApplicationDeadline(dateFinish);
        } catch (Exception e) {
            e.printStackTrace();
        }

        jobsService.update(updatedJob);
        return "redirect:/jobs/list" ;//只要跟Date相關的就用redirect:轉回到頁面
    }
//    // 更新現有的updateJob方法
//    @PutMapping("/update/{id}")
//    public String updateJob(@PathVariable("id") Integer id,
//                            @ModelAttribute Jobs updatedJob,
//                            @RequestParam("deadline") @DateTimeFormat(pattern = "yyyy-MM-dd") Date deadline) {
//        // 從數據庫獲取現有的職缺信息
//        Jobs existingJob = jobsService.findById(id).orElse(null);
//        if (existingJob != null) {
//            // 更新職缺信息
//            existingJob.setJobsTitle(updatedJob.getJobsTitle());
//
//        existingJob.setJobsPostingDate(updatedJob.getJobsPostingDate());
//        existingJob.setJobsApplicationDeadline(deadline);
//        existingJob.setJobsDescription(updatedJob.getJobsDescription());
//        existingJob.setJobsStatus(updatedJob.getJobsStatus());
//        existingJob.setJobsRequiredSkills(updatedJob.getJobsRequiredSkills());
//        existingJob.setJobsLocation(updatedJob.getJobsLocation());
//        existingJob.setJobsMaxSalary(updatedJob.getJobsMaxSalary());
//        existingJob.setJobsMinSalary(updatedJob.getJobsMinSalary());
//        existingJob.setJobsWorktime(updatedJob.getJobsWorktime());
//        existingJob.setJobsNumberOfOpenings(updatedJob.getJobsNumberOfOpenings());
//
//            jobsService.save(existingJob);
//        }
//        // 重定向到職缺列表頁面
//        return "redirect:/jobs/list";
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

}


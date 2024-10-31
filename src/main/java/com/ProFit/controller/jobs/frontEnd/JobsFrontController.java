package com.ProFit.controller.jobs.frontEnd;

import com.ProFit.model.bean.jobsBean.Jobs;
import com.ProFit.model.bean.jobsBean.JobsApplication;
import com.ProFit.model.bean.usersBean.Users;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class JobsFrontController {

    private final JobsService jobsService;
    private final IMajorCategoryService categoryService;
    private final IUserService userService;

    private final JobsApplicationService jobsApplicationService;

    @Value("${doc.resume.position}")
    private String RESUME_DIR;
    @Value("${doc.contract.position}")
    private String CONTRACT_DIR;

    public JobsFrontController(JobsService jobsService, IMajorCategoryService categoryService, IUserService userService,
                               JobsApplicationService jobsApplicationService) {
        this.jobsService = jobsService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.jobsApplicationService = jobsApplicationService;
    }

    //查詢全部
    @GetMapping("/public/front/jobs/list/{page}")
    public String list(Model model, @PathVariable int page, HttpSession session){
        // 使用 jobsDAO 进行分页查询
        Pageable pageable = PageRequest.of(page, 8);
        Page<Jobs> jobsPage = jobsService.findAll(pageable);

        // 获取分页内容
        List<Jobs> jobsList = jobsPage.getContent();
        UsersDTO user = (UsersDTO) session.getAttribute("CurrentUser");

        PageDTO pageDTO = new PageDTO();
        pageDTO.setTotalPage(jobsPage.getTotalPages());
        pageDTO.setCurrentPage(jobsPage.getNumber());
        model.addAttribute("pageDTO", pageDTO);
        model.addAttribute("jobs", jobsList);
        model.addAttribute("user", user);
        return "jobsVIEW/frontEnd/jobList";
    }

    //查看單筆
    @GetMapping("/public/front/jobs/view/{id}")
    public String view(@PathVariable int id, Model model){
        Jobs jobs = jobsService.findById(id).orElse(null);
        if (jobs == null){
            return "redirect:/public/front/jobs/list/0";
        }
        model.addAttribute("job", jobs);
        return "jobsVIEW/frontEnd/jobDetails";
    }

    //導向 更新/新增 頁面
    @GetMapping({"/front/jobs/new", "/front/jobs/edit/{id}"})
    public String edit(Model model, @PathVariable Optional<Integer> id){
        Jobs jobs = null;
        if (id.isPresent()){
            jobs = jobsService.findById(id.get()).orElse(null);
        } else {
            jobs = new Jobs();
        }
        model.addAttribute("categories", categoryService.findAllMajorCategories());
        model.addAttribute("job", jobs);
        return "jobsVIEW/frontEnd/jobPost";
    }

    //新增
    @PostMapping({"/front/jobs/new", "/front/jobs/edit/{id}"})
    public String addJobs(HttpSession session, Model model, @ModelAttribute Jobs jobs, @RequestParam String deadline){
        UsersDTO user = (UsersDTO) session.getAttribute("CurrentUser");
        if (user != null){
            jobs.setUsers(userService.getUserInfoByID(user.getUserId()));
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dateFinish = formatter.parse(deadline);
            jobs.setJobsApplicationDeadline(dateFinish);
        } catch (Exception e) {
            e.printStackTrace();
        }
        jobsService.save(jobs);
        return "redirect:/public/front/jobs/list/0";
    }

    @GetMapping("/front/jobs/view/{id}/new")
    public String edit(Model model, @PathVariable Integer id) {
        model.addAttribute("job", jobsService.findById(id).orElse(null));
        model.addAttribute("jobsApplication", new JobsApplication());
        return "jobsVIEW/frontEnd/jobApplicationPost";
    }

//上傳合約
//    @PostMapping("/view/{id}/new")
//    public String edit(Model model, @ModelAttribute JobsApplication jobsApplication,
//                       @RequestParam("doc") MultipartFile file,
//                       @PathVariable Integer id, HttpSession session) {
//        try {
//            // 檢查是否為 PDF
////            if (!file.getContentType().equals("application/pdf")) {
////                return ResponseEntity.badRequest().body("只能上傳 PDF 文件");
////            }
//
//            //此為抓登入者id
////            UsersDTO dto = (UsersDTO) session.getAttribute("CurrentUser");
////            if (dto != null){
////                jobsApplication.setApplicant(userService.getUserInfoByID(dto.getUserId()));
////            }
//
//            //此為測試文件功能 之後要改上面登入者id
//            jobsApplication.setApplicant(userService.getUserInfoByID(101));
//            jobsApplication.setJobs(jobsService.findById(id).orElse(null));
//
//            //============上傳合約============
//            // 獲取專案根目錄
//            String projectPath1 = new File("").getAbsolutePath();
//
//            // 組合完整路徑
//            Path uploadPath1 = Paths.get(projectPath1, CONTRACT_DIR);
//
//            // 創建目錄（如果不存在）
//            Files.createDirectories(uploadPath1);
//
//            // 生成唯一文件名
//            String fileName1 = UUID.randomUUID() + ".doc";
//            Path filePath1 = uploadPath1.resolve(fileName1);
//
//            // 保存文件
//            Files.copy(file.getInputStream(), filePath1, StandardCopyOption.REPLACE_EXISTING);
//
//
//            /*要改的
//                1.參考jobApplicationPost.html <form id="editForm" th:action="@{/front/jobs/view/{id}/new(id=${job.jobsId})}" method="POST" enctype="multipart/form-data">
//                改action路徑
//
//                2. 檔案上傳的html
//                <div class="col-lg-12">
//                    <div class="choose-img">
//                        <p>合約上傳</p>
//                        <input type="file" id="pdf" name="pdf" accept="application/pdf">
//                        <p>Maximum file size: 2 MB</p>
//                    </div>
//                </div>
//
//                3.第二張Controller參數要加 @RequestParam("pdf") MultipartFile file
//
//                4.上傳合約部分照抄
//
//                5.要把fileName set回去要存的jobsApplication
//                ex: jobsApplication.setJobsApplicationContract(fileName);
//            */
//
//
//
//
//            jobsApplication.setJobsApplicationContract(fileName);
//        } catch (IOException ignore) {
//        }
//
//        jobsApplicationService.save(jobsApplication);
//        return "redirect:/front/jobs/list/0";
//    }





//上傳履歷
    @PostMapping("/front/jobs/view/{id}/new")
    public String edit(Model model, @ModelAttribute JobsApplication jobsApplication,
                       @RequestParam("pdf") MultipartFile file,
                       @PathVariable Integer id, HttpSession session) {
        try {
            // 檢查是否為 PDF
//            if (!file.getContentType().equals("application/pdf")) {
//                return ResponseEntity.badRequest().body("只能上傳 PDF 文件");
//            }

            //此為抓登入者id
            UsersDTO usersDTO = (UsersDTO) session.getAttribute("CurrentUser");
            Users user = userService.getUserInfoByID(usersDTO.getUserId());
            jobsApplication.setApplicant(user);

            jobsApplication.setJobs(jobsService.findById(id).orElse(null));


            // 獲取專案根目錄
            String projectPath = new File("").getAbsolutePath();

            // 組合完整路徑
            Path uploadPath = Paths.get(projectPath, RESUME_DIR);

            // 創建目錄（如果不存在）
            Files.createDirectories(uploadPath);

            // 生成唯一文件名
            String fileName = UUID.randomUUID() + ".pdf";
            Path filePath = uploadPath.resolve(fileName);

            // 保存文件
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            //============上傳履歷============

            jobsApplication.setJobsApplicationResume(fileName);
        } catch (IOException ignore) {
        }

        jobsApplicationService.save(jobsApplication);
        return "redirect:/public/front/jobs/list/0";
    }
}

package com.ProFit.controller.jobs.frontEnd;

import com.ProFit.model.bean.jobsBean.JobsApplication;
import com.ProFit.model.bean.jobsBean.JobsApplicationProject;
import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.dto.jobsDTO.PageDTO;
import com.ProFit.model.dto.usersDTO.UsersDTO;
import com.ProFit.service.jobService.JobsApplicationService;
import com.ProFit.service.jobService.JobsService;
import com.ProFit.service.majorService.IMajorCategoryService;
import com.ProFit.service.userService.IUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(JobsProfileController.URL)
public class JobsProfileController {

    public static final String URL = "/front/profile";

    private final JobsService jobsService;
    private final IMajorCategoryService categoryService;
    private final IUserService userService;

    private final JobsApplicationService jobsApplicationService;

    public JobsProfileController(JobsService jobsService, IMajorCategoryService categoryService, IUserService userService, JobsApplicationService jobsApplicationService) {
        this.jobsService = jobsService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.jobsApplicationService = jobsApplicationService;
    }

    @Value("${doc.resume.position}")
    private String RESUME_DIR;
    @Value("${doc.contract.position}")
    private String CONTRACT_DIR;

    @GetMapping("/applications/{page}")
    public String applications(HttpSession session, @PathVariable int page, Model model) {
        UsersDTO user = (UsersDTO) session.getAttribute("CurrentUser");

        Pageable pageable = PageRequest.of(page, 6);
        Page<JobsApplication> applicationPage = jobsApplicationService.findCompanyByUserId(user.getUserId(), pageable);

        // 获取分页内容
        PageDTO pageDTO = new PageDTO();
        pageDTO.setTotalPage(applicationPage.getTotalPages());
        pageDTO.setCurrentPage(applicationPage.getNumber());
        List<JobsApplication> applicationList = applicationPage.getContent();

        model.addAttribute("pageDTO", pageDTO);
        model.addAttribute("user", user);
        model.addAttribute("applications", applicationList);
        return "jobsVIEW/frontEnd/profile/applicationList";
    }

    @GetMapping("/applicationsReturn/{page}")
    public String applicationsReturn(HttpSession session, @PathVariable int page, Model model) {
        UsersDTO user = (UsersDTO) session.getAttribute("CurrentUser");

        Pageable pageable = PageRequest.of(page, 6);
        Page<JobsApplication> applicationPage = jobsApplicationService.findApplicantByUserId(user.getUserId(), pageable);

        // 获取分页内容
        PageDTO pageDTO = new PageDTO();
        pageDTO.setTotalPage(applicationPage.getTotalPages());
        pageDTO.setCurrentPage(applicationPage.getNumber());
        List<JobsApplication> applicationList = applicationPage.getContent();

        model.addAttribute("pageDTO", pageDTO);
        model.addAttribute("user", user);
        model.addAttribute("applications", applicationList);
        return "jobsVIEW/frontEnd/profile/applicationReturnList";
    }

    //拒絕應徵
    @PutMapping("/applicationFire/{id}")
    public ResponseEntity<?> fire(HttpSession session, @PathVariable Integer id, Model model) {
        UsersDTO user = (UsersDTO) session.getAttribute("CurrentUser");
        JobsApplication jobsApplication = jobsApplicationService.findById(id).orElse(null);
        if (jobsApplication != null && user.getUserIdentity() == 2){
            jobsApplication.setJobsApplicationStatus((byte)2);
            JobsApplicationProject project = new JobsApplicationProject();
            project.setJobsApplication(jobsApplication);
            project.setJobsApplicationStatus((byte)2);
            jobsApplication.getProjects().add(project);

            jobsApplicationService.update(jobsApplication);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(401).build();
    }

    //邀約合作
    @PostMapping("/applicationHire/{id}")
    public String hire(HttpSession session, @PathVariable Integer id,
                       @RequestParam("doc") MultipartFile file, @RequestParam("amount") Integer amount, Model model) {
        JobsApplication jobsApplication = jobsApplicationService.findById(id).orElse(null);
        if (jobsApplication != null) {
            try {
                //此為抓登入者id
                UsersDTO usersDTO = (UsersDTO) session.getAttribute("CurrentUser");
                Users user = userService.getUserInfoByID(usersDTO.getUserId());

                JobsApplicationProject project = new JobsApplicationProject();
                project.setJobsApplication(jobsApplication);

                // 獲取專案根目錄
                String projectPath = new File("").getAbsolutePath();

                // 組合完整路徑
                Path uploadPath = Paths.get(projectPath, CONTRACT_DIR);

                // 創建目錄（如果不存在）
                Files.createDirectories(uploadPath);

                // 生成唯一文件名
                String fileName = UUID.randomUUID() + ".doc";
                Path filePath = uploadPath.resolve(fileName);

                // 保存文件
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                //============上傳履歷============

                project.setJobsContract(fileName);

                //處理狀態
                jobsApplication.setJobsApplicationStatus((byte)1);
                project.setJobsApplicationStatus((byte)1);
                project.setJobsAmount(amount);

                List<JobsApplicationProject> projects = new ArrayList<>();
                projects.add(project);
                jobsApplication.setProjects(projects);

            } catch (IOException ignore) {
            }

            jobsApplicationService.save(jobsApplication);
        }

        return "redirect:/front/profile/applicationsReturn/0";
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Integer id) {
        try {
            JobsApplication jobsApplication = jobsApplicationService.findById(id)
                    .orElseThrow(() -> new RuntimeException("找不到記錄"));

            String fileName = jobsApplication.getProjects().get(0).getJobsContract();
            String projectPath = new File("").getAbsolutePath();
            Path filePath = Paths.get(projectPath, CONTRACT_DIR, fileName);

            Resource resource = new FileSystemResource(filePath.toFile());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/preview/{id}")
    public ResponseEntity<byte[]> previewPDF(@PathVariable Integer id) {
        try {
            // 從數據庫獲取文件名
            JobsApplication jobsApplication = jobsApplicationService.findById(id)
                    .orElseThrow(() -> new RuntimeException("找不到應聘記錄"));

            String fileName = jobsApplication.getJobsApplicationResume();

            // 構建文件路徑
            String projectPath = new File("").getAbsolutePath();
            Path filePath = Paths.get(projectPath, RESUME_DIR, fileName);

            // 讀取文件
            byte[] content = Files.readAllBytes(filePath);

            // 設置響應頭
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.add("Content-Disposition", "inline; filename=" + fileName);
            headers.add("X-Frame-Options", "SAMEORIGIN");
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(content);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/getJobs/{id}", produces = "application/json")
    public ResponseEntity<?> getJobs(HttpSession session, @PathVariable Integer id, Model model) {
        JobsApplication jobsApplication = jobsApplicationService.findById(id).orElse(null);
        if (jobsApplication != null){
           return ResponseEntity.ok(jobsApplication.getJobs().getJobsTitle());
        }
        return ResponseEntity.badRequest().build();
    }


}

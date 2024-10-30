package com.ProFit.controller.courses.frontend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.ProFit.model.bean.coursesBean.CourseBean;
import com.ProFit.model.bean.coursesBean.CourseModuleBean;
import com.ProFit.service.courseService.IcourseService;
import com.ProFit.service.utilsService.FirebaseStorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.internal.FirebaseService;

@Controller
public class CourseCreateFrontend {

    @Autowired
    public IcourseService courseService;

    @Autowired
    public FirebaseStorageService firebaseStorageService;

    /**
     * 新增課程的方法
     * 此方法會自動將表單數據綁定到 CourseBean 對象
     *
     * @param courseBean         課程資料
     * @param courseCoverPicture 課程封面圖片
     * @return 插入後的 CourseBean 對象
     */
    @PostMapping("/course/create")
    public ResponseEntity<Map<String, String>> insertCourse(
            @ModelAttribute CourseBean courseBean,
            @RequestParam(required = false) List<String> courseModuleNames,
            @RequestPart(required = false) MultipartFile courseCoverPicture) {

        try {
            if (courseCoverPicture != null && !courseCoverPicture.isEmpty()) {
                // 上傳圖片到 Firebase Storage 並獲取 URL
                String photoURL = firebaseStorageService.uploadFile(courseCoverPicture);
                courseBean.setCourseCoverPictureURL(photoURL);
            } else {
                courseBean.setCourseCoverPictureURL(null);
            }

            // 插入課程
            CourseBean insertedCourse = courseService.insertCourse(courseBean);

            List<CourseModuleBean> courseModules = new ArrayList<CourseModuleBean>();
            if (courseModuleNames != null) {
                CourseModuleBean courseModule = null;
                for (String courseModuleName : courseModuleNames) {
                    courseModule = new CourseModuleBean();
                    courseModule.setCourseModuleName(courseModuleName);
                    // 必須設置 courseModule 的 course 屬性
                    courseModule.setCourse(insertedCourse);
                    courseModules.add(courseModule);
                }
                insertedCourse.setCourseModules(courseModules);
                courseService.updateCourseById(insertedCourse);
            } else {
                System.out.println("沒有module");
            }
            System.out.println(insertedCourse.getCourseId());
            // 返回 JSON
            Map<String, String> response = new HashMap<>();
            response.put("message", "OK");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ProFit.model.bean.coursesBean.CourseBean;
import com.ProFit.model.bean.coursesBean.CourseLessonBean;
import com.ProFit.model.bean.coursesBean.CourseModuleBean;
import com.ProFit.model.dto.coursesDTO.CourseLessonDTO;
import com.ProFit.model.dto.coursesDTO.CourseModuleDTO;
import com.ProFit.model.dto.coursesDTO.CourseModuleDTOFrontend;
import com.ProFit.model.dto.coursesDTO.CoursesDTO;
import com.ProFit.service.courseService.IcourseLessonService;
import com.ProFit.service.courseService.IcourseModuleService;
import com.ProFit.service.courseService.IcourseService;
import com.ProFit.service.utilsService.FirebaseStorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.internal.FirebaseService;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CourseCreateFrontend {

    @Autowired
    public IcourseService courseService;

    @Autowired
    public IcourseModuleService courseModuleService;

    @Autowired
    public IcourseLessonService courseLessonService;

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
    public ResponseEntity<Map<String, Object>> insertCourse(
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
            }

            Map<String, Object> response = new HashMap<>();
            response.put("insertedCourseId", insertedCourse.getCourseId());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/course/returnCreatedCourse")
    @ResponseBody
    public Map<String, Object> returnCreatedCourse(@RequestParam String courseId) {

        CoursesDTO insertedCourseDTO = courseService.searchOneCourseById(courseId);

        List<CourseModuleDTOFrontend> insertedCourseModuleList = courseModuleService
                .searchCourseModulesFrontend(courseId);

        Map<String, Object> insertedCourseMap = new HashMap<String, Object>();

        insertedCourseMap.put("insertedCourseDTO", insertedCourseDTO);
        insertedCourseMap.put("insertedCourseModuleList", insertedCourseModuleList);

        return insertedCourseMap;
    }

    @PostMapping("/courseLesson/insert")
    public ResponseEntity<Map<String, String>> createCourseLesson(
            @ModelAttribute CourseLessonBean courseLesson,
            @RequestPart(required = false) MultipartFile lessonMedia) {

        try {
            if (lessonMedia != null && !lessonMedia.isEmpty()) {
                String uploadFileURL = firebaseStorageService.uploadFile(lessonMedia);
                courseLesson.setLessonMediaUrl(uploadFileURL);
            } else {
                courseLesson.setLessonMediaUrl(null);
            }

            if (courseLesson != null) {
                CourseLessonBean insertedCourseLesson = courseLessonService.insertCourseLesson(courseLesson);
            }

            Map<String, String> response = new HashMap<>();
            response.put("message", "ok");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/courseLesson/searchByModuleId")
    @ResponseBody
    public List<CourseLessonDTO> searchByModuleId(@RequestParam Integer courseModuleId) {

        List<CourseLessonDTO> moduleLessonList = courseLessonService.searchCourseLessons(courseModuleId);

        return moduleLessonList;
    }

}

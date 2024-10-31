package com.ProFit.controller.courses.frontend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ProFit.model.bean.coursesBean.CourseGradeContentBean;
import com.ProFit.model.bean.coursesBean.CourseOrderBean;
import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.dto.coursesDTO.CourseCategoryDTO;
import com.ProFit.model.dto.coursesDTO.CourseGradeContentDTO;
import com.ProFit.model.dto.coursesDTO.CourseLessonDTO;
import com.ProFit.model.dto.coursesDTO.CourseModuleDTO;
import com.ProFit.model.dto.coursesDTO.CourseModuleDTOFrontend;
import com.ProFit.model.dto.coursesDTO.CourseOrderDTO;
import com.ProFit.model.dto.coursesDTO.CoursesDTO;
import com.ProFit.model.dto.usersDTO.UsersDTO;
import com.ProFit.service.courseService.IcourseGradeContentService;
import com.ProFit.service.courseService.IcourseLessonService;
import com.ProFit.service.courseService.IcourseModuleService;
import com.ProFit.service.courseService.IcourseOrderService;
import com.ProFit.service.courseService.IcourseService;
import com.ProFit.service.majorService.IMajorCategoryService;
import com.ProFit.service.utilsService.FirebaseStorageService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CourseFrontend {

    @Autowired
    private IcourseService courseService;

    @Autowired
    private IMajorCategoryService majorCategoryService;

    @Autowired
    private IcourseModuleService courseModuleService;

    @Autowired
    private IcourseGradeContentService courseGradeContentService;

    @Autowired
    private IcourseOrderService courseOrderService;

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @Autowired
    private IcourseLessonService courseLessonService;

    @GetMapping("/course")
    public String courseFrontendPage() {
        return "coursesVIEW/frontend/courseOverView";
    }

    @GetMapping("/course/purchase")
    public String purchaseCoursePage(@RequestParam String courseId, HttpSession session, Model model) {

        UsersDTO currentUser = (UsersDTO) session.getAttribute("CurrentUser");

        if (currentUser != null && courseId != null) {

            CoursesDTO courseDTO = courseService.searchOneCourseById(courseId);

            model.addAttribute("currentUser", currentUser);
            model.addAttribute("courseDTO", courseDTO);

            return "coursesVIEW/frontend/coursePurchaseView";
        }

        return "redirect:/user/profile";
    }

    @GetMapping("/course/watch")
    public String watchCoursePage(HttpSession session) {

        UsersDTO currentUser = (UsersDTO) session.getAttribute("CurrentUser");

        if (currentUser != null) {

            return "coursesVIEW/frontend/courseWatchView";
        }

        return "redirect:/user/profile";

    }

    @GetMapping("/course/create")
    public String createCoursePage(HttpSession session, Model model) {

        UsersDTO currentUser = (UsersDTO) session.getAttribute("CurrentUser");

        // if (currentUser != null) {

        List<CourseCategoryDTO> allCourseCategoryList = majorCategoryService.findAllMajorCategories().stream()
                .map(CourseCategoryDTO::new).collect(Collectors.toList());

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("allCourseCategoryList", allCourseCategoryList);

        return "coursesVIEW/frontend/createCourseViewFrontend";
        // }
        // return "coursesVIEW/frontend/createCourseViewFrontend";
    }

    @GetMapping("/course/watch/{courseId}")
    public ResponseEntity<Map<String, Object>> watchCoursePage(HttpSession session, @PathVariable String courseId) {

        UsersDTO currentUser = (UsersDTO) session.getAttribute("CurrentUser");

        List<CourseOrderDTO> isCourseAvalible = courseOrderService.searchAllCourseOrders(courseId,
                currentUser.getUserId(), "Completed");

        if (isCourseAvalible.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        CoursesDTO currentCourse = courseService.searchOneCourseById(courseId);

        List<CourseModuleDTOFrontend> courseModuleDTOList = courseModuleService.searchCourseModulesFrontend(courseId);

        PageRequest pageRequest = PageRequest.of(0, 3);

        Page<CourseGradeContentDTO> courseGradeDTOList = courseGradeContentService
                .searchCourseGradeContents(courseId, null, pageRequest);

        Map<String, Object> courseMap = new HashMap<String, Object>();
        courseMap.put("currentUser", currentUser);
        courseMap.put("currentCourse", currentCourse);
        courseMap.put("courseModuleDTOList", courseModuleDTOList);
        courseMap.put("courseGradeDTOList", courseGradeDTOList);

        return new ResponseEntity<>(courseMap, HttpStatus.OK);
    }

    // 新增課程訂單的方法
    @PostMapping("/course/purchase/add")
    @ResponseBody
    public Integer postMethodName(@ModelAttribute CourseOrderBean courseOrder) {
        try {
            if (courseOrder != null) {

                courseOrder.setCourseOrderPaymentMethod("綠界");
                courseOrder.setCourseOrderStatus("Pending");

                Integer statusCode = courseOrderService.insertCourseOrder(courseOrder);
                // 0:課程不存在
                // 1:課程進行中
                // 2:課程還未開課
                return statusCode;
            }
            return null;
        } catch (Exception e) {
            return 0;
        }
    }

    @GetMapping("/course/searchOne")
    @ResponseBody
    public CoursesDTO searchOneCourse(@RequestParam String courseId) {

        CoursesDTO searchOneCourseById = courseService.searchOneCourseById(courseId);

        return searchOneCourseById;
    }

    @PostMapping("/course/searchAll")
    @ResponseBody
    public Page<CoursesDTO> searchAllCourses(
            @RequestParam(required = false) String courseName,
            @RequestParam(required = false) String courseCreateUserName,
            @RequestParam(defaultValue = "Active") String courseStatus,
            @RequestParam(required = false) Integer courseMajor,
            @RequestParam(defaultValue = "ASC") String sort,
            @RequestParam(defaultValue = "courseEndDate") String sortBy,
            @RequestParam(defaultValue = "1") Integer pageNumber,
            @RequestParam(defaultValue = "12") Integer pageSize) {

        Page<CoursesDTO> searchCoursesPage = courseService.searchCoursesPage(courseName, courseCreateUserName,
                courseStatus, null,
                courseMajor, sort, sortBy, pageNumber, pageSize);

        return searchCoursesPage;
    }

    @GetMapping("/course/searchCourseByMajorCategory")
    @ResponseBody
    public List<CourseCategoryDTO> searchCourseByMajorCategory() {

        List<CourseCategoryDTO> allCourseCategoryList = majorCategoryService.findAllMajorCategories().stream()
                .map(CourseCategoryDTO::new).collect(Collectors.toList());

        return allCourseCategoryList;
    }

    // 回傳單筆課程相關資訊 確認登入狀態，判斷是否已經購買
    @GetMapping("/course/{courseId}")
    public String findSingleCourse(@PathVariable String courseId, Model model, HttpSession session) {

        UsersDTO currentUser = (UsersDTO) session.getAttribute("CurrentUser");

        boolean isPurchased = false;

        if (currentUser != null) {
            List<CourseOrderDTO> isCoursePurchased = courseOrderService.searchAllCourseOrders(courseId,
                    currentUser.getUserId(), "Complete");
            if (!isCoursePurchased.isEmpty()) {
                isPurchased = true;
            }
        }

        model.addAttribute("isPurchased", isPurchased);

        CoursesDTO courseDTO = courseService.searchOneCourseById(courseId);

        List<CourseCategoryDTO> allCourseCategoryList = majorCategoryService.findAllMajorCategories().stream()
                .map(CourseCategoryDTO::new).collect(Collectors.toList());

        List<CourseModuleDTOFrontend> CourseModuleDTOList = courseModuleService.searchCourseModulesFrontend(courseId);

        PageRequest pageRequest = PageRequest.of(0, 5);

        Page<CourseGradeContentDTO> courseGradeDTOList = courseGradeContentService
                .searchCourseGradeContents(courseId, null, pageRequest);

        model.addAttribute("courseDTO", courseDTO);
        model.addAttribute("allCourseCategoryList", allCourseCategoryList);
        model.addAttribute("CourseModuleDTOList", CourseModuleDTOList);
        model.addAttribute("courseGradeDTOList", courseGradeDTOList);

        return "coursesVIEW/frontend/singleCourseDetailPage";
    }

    // 回傳已購買課程的Api
    @GetMapping("/c/course/purchasedList")
    @ResponseBody
    public Map<String, Object> getPurchasedListByUserId(HttpSession session) {

        UsersDTO currentUser = (UsersDTO) session.getAttribute("CurrentUser");

        if (currentUser != null) {

            List<CourseOrderDTO> purchasedCourses = courseOrderService.searchAllCourseOrders(null,
                    currentUser.getUserId(), "Completed");

            List<CoursesDTO> appliedCourses = courseService.searchCourses(null,
                    null, null, currentUser.getUserId(), null);

            Map<String, Object> coursesMap = new HashMap<String, Object>();

            coursesMap.put("purchasedCourses", purchasedCourses);
            coursesMap.put("appliedCourses", appliedCourses);

            return coursesMap;
        }

        return null;
    }

    // 回傳30分鐘有效的單元影片url
    @GetMapping("/course/lessons/{lessonId}")
    @ResponseBody
    public Map<String, String> getLessonVideoURL(@PathVariable Integer lessonId, HttpSession session) {

        UsersDTO currentUser = (UsersDTO) session.getAttribute("CurrentUser");

        if (currentUser != null) {
            CourseLessonDTO currentLesson = courseLessonService.searchOneCourseLessonById(lessonId);

            String fileNameFromUrl = firebaseStorageService.extractFileNameFromUrl(currentLesson.getLessonMediaUrl());

            if (fileNameFromUrl != null) {
                String timeSensitiveUrl = firebaseStorageService.generateTimeSensitiveUrl(fileNameFromUrl);
                Map<String, String> response = new HashMap<>();
                response.put("url", timeSensitiveUrl);
                return response;
            }
            return null;
        }

        return null;
    }

    @PostMapping("/course/addcourseGrade")
    @ResponseBody
    public boolean insertCourseGrade(
            @ModelAttribute CourseGradeContentBean courseGradeContent, HttpSession session) {

        UsersDTO currentUser = (UsersDTO) session.getAttribute("CurrentUser");

        if (courseGradeContent != null) {

            courseGradeContent.setStudentId(currentUser.getUserId());

            CourseGradeContentBean insertCourseGradeContent = courseGradeContentService
                    .insertCourseGradeContent(courseGradeContent);

            if (insertCourseGradeContent != null) {
                return true;

            }
        }
        return false;

    }

}

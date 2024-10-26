package com.ProFit.controller.courses.frontend;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import com.ProFit.model.bean.coursesBean.CourseOrderBean;
import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.dto.coursesDTO.CourseCategoryDTO;
import com.ProFit.model.dto.coursesDTO.CourseGradeContentDTO;
import com.ProFit.model.dto.coursesDTO.CourseModuleDTO;
import com.ProFit.model.dto.coursesDTO.CourseModuleDTOFrontend;
import com.ProFit.model.dto.coursesDTO.CourseOrderDTO;
import com.ProFit.model.dto.coursesDTO.CoursesDTO;
import com.ProFit.model.dto.usersDTO.UsersDTO;
import com.ProFit.service.courseService.IcourseGradeContentService;
import com.ProFit.service.courseService.IcourseModuleService;
import com.ProFit.service.courseService.IcourseOrderService;
import com.ProFit.service.courseService.IcourseService;
import com.ProFit.service.majorService.IMajorCategoryService;
import com.ProFit.service.utilsService.FirebaseStorageService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/course")
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

    @GetMapping("")
    public String courseFrontendPage() {
        return "coursesVIEW/frontend/courseOverView";
    }

    @GetMapping("/purchase")
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

    // @GetMapping("/watch")
    // public ResponseEntity<List<CourseOrderDTO>> watchCoursePage(HttpSession
    // session, @RequestParam String courseId) {

    // UsersDTO currentUser = (UsersDTO) session.getAttribute("CurrentUser");

    // List<CourseOrderDTO> isCourseAvalible =
    // courseOrderService.searchAllCourseOrders(courseId,
    // currentUser.getUserId(), "Completed");

    // if (isCourseAvalible.isEmpty()) {
    // return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    // }

    // CoursesDTO OneCourse = courseService.searchOneCourseById(courseId);

    // List<CourseModuleDTOFrontend> CourseModuleDTOList =
    // courseModuleService.searchCourseModulesFrontend(courseId);

    // PageRequest pageRequest = PageRequest.of(0, 3);

    // Page<CourseGradeContentDTO> courseGradeDTOList = courseGradeContentService
    // .searchCourseGradeContents(courseId, null, pageRequest);

    // return new ResponseEntity<>(OneCourse, HttpStatus.OK);
    // }

    // 新增課程訂單的方法
    @PostMapping("/purchase/add")
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

    @GetMapping("/searchOne")
    @ResponseBody
    public CoursesDTO searchOneCourse(@RequestParam String courseId) {

        CoursesDTO searchOneCourseById = courseService.searchOneCourseById(courseId);

        return searchOneCourseById;
    }

    @PostMapping("/searchAll")
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
        System.out.println(pageNumber);

        Page<CoursesDTO> searchCoursesPage = courseService.searchCoursesPage(courseName, courseCreateUserName,
                courseStatus, null,
                courseMajor, sort, sortBy, pageNumber, pageSize);

        return searchCoursesPage;
    }

    @GetMapping("/searchCourseByMajorCategory")
    @ResponseBody
    public List<CourseCategoryDTO> searchCourseByMajorCategory() {

        List<CourseCategoryDTO> allCourseCategoryList = majorCategoryService.findAllMajorCategories().stream()
                .map(CourseCategoryDTO::new).collect(Collectors.toList());

        return allCourseCategoryList;
    }

    @GetMapping("/{courseId}")
    public String findSingleCourse(@PathVariable String courseId, Model model) {

        CoursesDTO courseDTO = courseService.searchOneCourseById(courseId);

        List<CourseCategoryDTO> allCourseCategoryList = majorCategoryService.findAllMajorCategories().stream()
                .map(CourseCategoryDTO::new).collect(Collectors.toList());

        List<CourseModuleDTOFrontend> CourseModuleDTOList = courseModuleService.searchCourseModulesFrontend(courseId);

        PageRequest pageRequest = PageRequest.of(0, 3);

        Page<CourseGradeContentDTO> courseGradeDTOList = courseGradeContentService
                .searchCourseGradeContents(courseId, null, pageRequest);

        model.addAttribute("courseDTO", courseDTO);
        model.addAttribute("allCourseCategoryList", allCourseCategoryList);
        model.addAttribute("CourseModuleDTOList", CourseModuleDTOList);
        model.addAttribute("courseGradeDTOList", courseGradeDTOList);

        return "coursesVIEW/frontend/singleCourseDetailPage";
    }

    // 回傳已購買課程的Api
    @GetMapping("/purchasedList")
    @ResponseBody
    public List<CourseOrderDTO> getPurchasedListByUserId(HttpSession session) {

        UsersDTO currentUser = (UsersDTO) session.getAttribute("CurrentUser");

        if (currentUser != null) {

            List<CourseOrderDTO> searchCourseOrdersByUserId = courseOrderService.searchAllCourseOrders(null,
                    currentUser.getUserId(), "Completed");

            return searchCourseOrdersByUserId;
        }

        return null;
    }

}

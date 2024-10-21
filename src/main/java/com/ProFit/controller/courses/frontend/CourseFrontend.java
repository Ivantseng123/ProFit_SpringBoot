package com.ProFit.controller.courses.frontend;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ProFit.model.dto.coursesDTO.CourseCategoryDTO;
import com.ProFit.model.dto.coursesDTO.CourseGradeContentDTO;
import com.ProFit.model.dto.coursesDTO.CourseModuleDTO;
import com.ProFit.model.dto.coursesDTO.CourseModuleDTOFrontend;
import com.ProFit.model.dto.coursesDTO.CoursesDTO;
import com.ProFit.service.courseService.IcourseGradeContentService;
import com.ProFit.service.courseService.IcourseModuleService;
import com.ProFit.service.courseService.IcourseService;
import com.ProFit.service.majorService.IMajorCategoryService;

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

    @GetMapping("")
    public String courseFrontendPage() {
        return "coursesVIEW/frontend/courseOverView";
    }

    @PostMapping("/searchAll")
    @ResponseBody
    public Page<CoursesDTO> searchAllCourses(
            @RequestParam(required = false) String courseName,
            @RequestParam(required = false) String courseCreateUserName,
            @RequestParam(defaultValue = "Active") String courseStatus,
            @RequestParam(required = false) Integer courseMajor,
            @RequestParam(defaultValue = "ASC") String sort,
            @RequestParam(defaultValue = "1") Integer pageNumber,
            @RequestParam(defaultValue = "12") Integer pageSize) {

        System.out.println(pageNumber);

        Page<CoursesDTO> searchCoursesPage = courseService.searchCoursesPage(courseName, courseCreateUserName,
                courseStatus, null,
                courseMajor, sort, pageNumber, pageSize);

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
    public String getMethodName(@PathVariable String courseId, Model model) {

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

}

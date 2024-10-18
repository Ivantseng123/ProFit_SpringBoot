package com.ProFit.controller.courses.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.ProFit.model.bean.coursesBean.CourseGradeContentBean;
import com.ProFit.model.dto.coursesDTO.CourseGradeContentDTO;
import com.ProFit.model.dto.coursesDTO.CoursesDTO;
import com.ProFit.service.courseService.CourseGradeContentService;
import com.ProFit.service.courseService.CoursesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CourseGradeContentController {

    @Autowired
    private CourseGradeContentService courseGradeContentService;

    @Autowired
    private CoursesService coursesService;

    @GetMapping("/courseGrades")
    public String courseGradesPage(@RequestParam String courseId, Model model) {

        CoursesDTO coursesDTO = coursesService.searchOneCourseById(courseId);

        model.addAttribute("coursesDTO", coursesDTO);

        return "coursesVIEW/backend/courseGradesView";
    }

    @GetMapping("/courseGrades/search")
    @ResponseBody
    public Page<CourseGradeContentDTO> findCourseGradesByCourseId(
            @RequestParam String courseId,
            @RequestParam(defaultValue = "ASC") String sort,
            @RequestParam(defaultValue = "1") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize) {

        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);

        Page<CourseGradeContentDTO> searchCourseGradeContents = courseGradeContentService
                .searchCourseGradeContents(courseId, sort, pageRequest);

        return searchCourseGradeContents;
    }

    @GetMapping("/courseGrades/delete/{courseGradeId}")
    @ResponseBody
    public boolean deleteCourseGrade(@PathVariable Integer courseGradeId) {

        try {
            courseGradeContentService.searchOneGradeContent(courseGradeId);

            courseGradeContentService.deleteCourseGradeContentById(courseGradeId);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @PostMapping("/courseGrades/insert")
    @ResponseBody
    public boolean insertCourseGrade(
            @ModelAttribute CourseGradeContentBean courseGradeContent) {

        if (courseGradeContent != null) {
            CourseGradeContentBean insertCourseGradeContent = courseGradeContentService
                    .insertCourseGradeContent(courseGradeContent);

            if (insertCourseGradeContent != null) {
                return true;

            }
        }
        return false;

    }

}

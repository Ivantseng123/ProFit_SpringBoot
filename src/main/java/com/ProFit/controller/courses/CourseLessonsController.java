package com.ProFit.controller.courses;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ProFit.model.dto.coursesDTO.CourseLessonDTO;
import com.ProFit.model.dto.coursesDTO.CourseModuleDTO;
import com.ProFit.service.courseService.IcourseLessonService;
import com.ProFit.service.courseService.IcourseModuleService;

@Controller
public class CourseLessonsController {

	@Autowired
	private IcourseLessonService courseLessonService;
	
	@Autowired
	private IcourseModuleService courseModuleService;
	
	@GetMapping("/courseLessons")
	public String courseLessonsPage(@RequestParam Integer courseModuleId,Model model) {
		
		List<CourseLessonDTO> courseLessonDTOList = courseLessonService.searchCourseLessons(courseModuleId);
		CourseModuleDTO courseModuleDTO = courseModuleService.searchOneCourseModuleById(courseModuleId);
		
		model.addAttribute("courseModuleDTO",courseModuleDTO);
		model.addAttribute("courseLessonDTOList",courseLessonDTOList);
		
		return "coursesVIEW/courseLessonView";
	}
	
	@GetMapping("courseLessons/add")
	public String createLessonPage(@RequestParam Integer courseModuleId,Model model) {
		
		model.addAttribute("courseModuleId",courseModuleId);
		
		return "coursesVIEW/createCourseLessonView";
	}
	
	
	
}

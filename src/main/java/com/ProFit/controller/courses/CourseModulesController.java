package com.ProFit.controller.courses;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ProFit.model.bean.coursesBean.CourseBean;
import com.ProFit.model.bean.coursesBean.CourseModuleBean;
import com.ProFit.model.dto.coursesDTO.CourseModuleDTO;
import com.ProFit.model.dto.coursesDTO.CoursesDTO;
import com.ProFit.service.courseService.IcourseModuleService;
import com.ProFit.service.courseService.IcourseService;


@Controller
public class CourseModulesController {

	@Autowired
	private IcourseModuleService courseModuleService;
	
	@Autowired
	private IcourseService courseService;
	
	@GetMapping("/courseModules")
	public String courseModulePage(@RequestParam String courseId,Model model) {
		List<CourseModuleDTO> courseModuleDTOList = courseModuleService.searchCourseModules(courseId);
		
		CoursesDTO courseDTO = courseService.searchOneCourseById(courseId);
		
		model.addAttribute("courseModuleDTOList",courseModuleDTOList);
		model.addAttribute("courseDTO",courseDTO);
		
		return "coursesVIEW/courseModuleView";
	}
	
	@GetMapping("/courseModules/searchJSON")
	@ResponseBody
	public List<CourseModuleDTO> searchOneCourseModuleJSON(@RequestParam String courseId){
		return courseModuleService.searchCourseModules(courseId);
	}
	
	@GetMapping("/courseModules/delete")
	@ResponseBody
	public CourseModuleDTO deleteCourseModulesById(@RequestParam Integer courseModuleId) {
		
		if(courseModuleId!=null) {
			CourseModuleDTO courseModule = courseModuleService.searchOneCourseModuleById(courseModuleId);
			courseModuleService.deleteCourseModuleById(courseModuleId);
			return courseModule;
		}
		return null;
	}
	
	
	@PostMapping("/courseModules/addModule")
	@ResponseBody
	public boolean insertCourseModule(
			@RequestParam String courseId,
			@RequestParam String courseModuleName) {
		
		if(courseModuleName!=null) {
			CourseBean insertedCourse = new CourseBean();
			insertedCourse.setCourseId(courseId);
			
			CourseModuleBean courseModule = null;
			courseModule = new CourseModuleBean();
			courseModule.setCourseModuleName(courseModuleName);
			// 必須設置 courseModule 的 course 屬性
			courseModule.setCourse(insertedCourse);
			courseModuleService.insertCourseModule(courseModule);
			return true;
		}
		return false;
	}
	
	@PostMapping("/courseModules/updateModule")
	@ResponseBody
	public boolean updateCourseModule(
			@RequestParam Integer courseModuleId,
			@RequestParam String courseModuleName
			) {
		
		CourseModuleBean newCourseModule = new CourseModuleBean();
		newCourseModule.setCourseModuleId(courseModuleId);
		newCourseModule.setCourseModuleName(courseModuleName);
		
		boolean isUpdated = courseModuleService.updateCourseModuleById(newCourseModule);
		
		return isUpdated;
	}
	
}

package com.ProFit.controller.courses.backend;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.ProFit.model.bean.coursesBean.CourseLessonBean;
import com.ProFit.model.dto.coursesDTO.CourseLessonDTO;
import com.ProFit.model.dto.coursesDTO.CourseModuleDTO;
import com.ProFit.service.courseService.IcourseLessonService;
import com.ProFit.service.courseService.IcourseModuleService;
import com.ProFit.service.utilsService.FirebaseStorageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CourseLessonsController {

	@Autowired
	private IcourseLessonService courseLessonService;

	@Autowired
	private IcourseModuleService courseModuleService;

	@Autowired
	private FirebaseStorageService firebaseStorageService;

	@GetMapping("/courseLessons")
	public String courseLessonsPage(@RequestParam Integer courseModuleId, Model model) {

		List<CourseLessonDTO> courseLessonDTOList = courseLessonService.searchCourseLessons(courseModuleId);
		CourseModuleDTO courseModuleDTO = courseModuleService.searchOneCourseModuleById(courseModuleId);

		model.addAttribute("courseModuleDTO", courseModuleDTO);
		model.addAttribute("courseLessonDTOList", courseLessonDTOList);

		return "coursesVIEW/backend/courseLessonView";
	}

	@GetMapping("/courseLessons/add")
	public String createLessonPage(@RequestParam Integer courseModuleId, Model model) {

		CourseModuleDTO courseModuleDTO = courseModuleService.searchOneCourseModuleById(courseModuleId);

		model.addAttribute("courseModuleDTO", courseModuleDTO);
		model.addAttribute("courseModuleId", courseModuleId);

		return "coursesVIEW/backend/createCourseLessonView";
	}

	@GetMapping("/courseLessons/update")
	public String updateLessonPage(@RequestParam Integer courseLessonId) {

		return "coursesVIEW/backend/updateCourseLessonView";
	}

	@GetMapping("/courseLessons/search/{courseLessonId}")
	@ResponseBody
	public CourseLessonDTO searchOneLessonPage(@PathVariable Integer courseLessonId) {

		CourseLessonDTO courseLessonDTO = courseLessonService.searchOneCourseLessonById(courseLessonId);

		return courseLessonDTO;
	}

	@PostMapping("/courseLessons/insert")
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

	@GetMapping("/courseLessons/delete/{courseLessonId}")
	@ResponseBody
	public String deleteCourseLesson(
			@PathVariable Integer courseLessonId) {

		if (courseLessonId != null) {
			CourseLessonDTO searchOneCourseLessonById = courseLessonService.searchOneCourseLessonById(courseLessonId);

			courseLessonService.deleteCourseLessonById(courseLessonId);

			return searchOneCourseLessonById.getCourseModuleId().toString();
		} else {
			return null;
		}
	}

	@PostMapping("/courseLessons/update")
	@ResponseBody
	public boolean updateCourseLesson(
			@ModelAttribute CourseLessonBean courseLesson,
			@RequestPart(required = false) MultipartFile lessonMedia) {

		if (courseLesson != null) {

			try {
				if (lessonMedia != null) {
					String lessonMediaURL = firebaseStorageService.uploadFile(lessonMedia);
					courseLesson.setLessonMediaUrl(lessonMediaURL);
				}

				boolean isUpdated = courseLessonService.updateCourseLessonById(courseLesson);
				return isUpdated;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

}

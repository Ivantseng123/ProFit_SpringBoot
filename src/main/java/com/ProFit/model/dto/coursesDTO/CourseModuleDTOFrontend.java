package com.ProFit.model.dto.coursesDTO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ProFit.model.bean.coursesBean.CourseLessonBean;
import com.ProFit.model.bean.coursesBean.CourseModuleBean;

public class CourseModuleDTOFrontend {

	private Integer courseModuleId;
	private String courseModuleName;
	private String courseId;
	private String courseName;
	private Map<Integer, CourseLessonDTO> moduleLessonsMap;

	public CourseModuleDTOFrontend() {
	}

	public CourseModuleDTOFrontend(CourseModuleBean courseModule) {
		this.courseModuleId = courseModule.getCourseModuleId();
		this.courseModuleName = courseModule.getCourseModuleName();
		this.courseId = courseModule.getCourse().getCourseId();
		this.courseName = courseModule.getCourse().getCourseName();

		List<CourseLessonBean> moduleLessons = courseModule.getModuleLessons();

		Map<Integer, CourseLessonDTO> lessonMap = moduleLessons.stream()
				.map(CourseLessonDTO::new)
				.collect(Collectors.toMap(
						CourseLessonDTO::getCourseLessonId, // 這是 key 的來源
						dto -> dto // 這是 value 的來源
				));

		this.moduleLessonsMap = lessonMap;

	}

	public Integer getCourseModuleId() {
		return courseModuleId;
	}

	public void setCourseModuleId(Integer courseModuleId) {
		this.courseModuleId = courseModuleId;
	}

	public String getCourseModuleName() {
		return courseModuleName;
	}

	public void setCourseModuleName(String courseModuleName) {
		this.courseModuleName = courseModuleName;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public Map<Integer, CourseLessonDTO> getModuleLessonsMap() {
		return moduleLessonsMap;
	}

	public void setModuleLessonsMap(Map<Integer, CourseLessonDTO> moduleLessonsMap) {
		this.moduleLessonsMap = moduleLessonsMap;
	}
}

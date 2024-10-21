package com.ProFit.model.dto.coursesDTO;

import java.util.HashMap;
import java.util.Map;

import com.ProFit.model.bean.coursesBean.CourseModuleBean;

public class CourseModuleDTOFrontend {

	private Integer courseModuleId;
	private String courseModuleName;
	private String courseId;
	private String courseName;

	private Map<Integer, Map<String, Integer>> moduleLessonsMap;

	public CourseModuleDTOFrontend() {
	}

	public CourseModuleDTOFrontend(CourseModuleBean courseModule) {
		this.courseModuleId = courseModule.getCourseModuleId();
		this.courseModuleName = courseModule.getCourseModuleName();
		this.courseId = courseModule.getCourse().getCourseId();
		this.courseName = courseModule.getCourse().getCourseName();

		this.moduleLessonsMap = new HashMap<>();

		// 將模組的每個 lesson 的數據添加到 map 中
		for (int i = 0; i < courseModule.getModuleLessons().size(); i++) {
			Integer moduleLessonsId = courseModule.getModuleLessons().get(i).getCourseLessonId();
			String moduleLessonsName = courseModule.getModuleLessons().get(i).getCourseLessonName();
			Integer moduleLessonsMediaDuration = courseModule.getModuleLessons().get(i).getMediaDuration();

			// 將 lesson name 和 media duration 存入內部的 Map 中
			Map<String, Integer> lessonDetails = new HashMap<>();
			lessonDetails.put(moduleLessonsName, moduleLessonsMediaDuration);

			// 使用 moduleLessonsId 作為 key，內部的 map 作為 value
			this.moduleLessonsMap.put(moduleLessonsId, lessonDetails);
		}
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

	public Map<Integer, Map<String, Integer>> getModuleLessonsMap() {
		return moduleLessonsMap;
	}

	public void setModuleLessonsMap(Map<Integer, Map<String, Integer>> moduleLessonsMap) {
		this.moduleLessonsMap = moduleLessonsMap;
	}
}

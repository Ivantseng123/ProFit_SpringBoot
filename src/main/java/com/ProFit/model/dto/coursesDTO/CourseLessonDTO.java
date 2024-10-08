package com.ProFit.model.dto.coursesDTO;

import com.ProFit.model.bean.coursesBean.CourseLessonBean;

public class CourseLessonDTO {
	
	private Integer courseLessonId;
	private Integer courseModuleId;
	private String courseModuleName;
	private String courseId;
	private String courseName;
	private String courseLessonName;
	private Integer courseLessonSort;
	private String lessonMediaUrl;
	private String lessonMediaType;
	private Integer mediaDuration;
	
	public CourseLessonDTO() {
		
	}
	
	public CourseLessonDTO(CourseLessonBean courseLesson) {
		this.courseLessonId = courseLesson.getCourseLessonId();
		this.courseModuleId = courseLesson.getCourseModuleId();
		this.courseModuleName = courseLesson.getCourseModule().getCourseModuleName();
		this.courseId = courseLesson.getCourseId();
		this.courseName = courseLesson.getCourseModule().getCourse().getCourseName();
		this.courseLessonName = courseLesson.getCourseLessonName();
		this.courseLessonSort = courseLesson.getCourseLessonSort();
		this.lessonMediaUrl = courseLesson.getLessonMediaUrl();
		this.lessonMediaType = courseLesson.getLessonMediaType();
		this.mediaDuration = courseLesson.getMediaDuration();
	}

	public Integer getCourseLessonId() {
		return courseLessonId;
	}

	public void setCourseLessonId(Integer courseLessonId) {
		this.courseLessonId = courseLessonId;
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

	public String getCourseLessonName() {
		return courseLessonName;
	}

	public void setCourseLessonName(String courseLessonName) {
		this.courseLessonName = courseLessonName;
	}

	public Integer getCourseLessonSort() {
		return courseLessonSort;
	}

	public void setCourseLessonSort(Integer courseLessonSort) {
		this.courseLessonSort = courseLessonSort;
	}

	public String getLessonMediaUrl() {
		return lessonMediaUrl;
	}

	public void setLessonMediaUrl(String lessonMediaUrl) {
		this.lessonMediaUrl = lessonMediaUrl;
	}

	public String getLessonMediaType() {
		return lessonMediaType;
	}

	public void setLessonMediaType(String lessonMediaType) {
		this.lessonMediaType = lessonMediaType;
	}

	public Integer getMediaDuration() {
		return mediaDuration;
	}

	public void setMediaDuration(Integer mediaDuration) {
		this.mediaDuration = mediaDuration;
	}
	
	
}

package com.ProFit.model.dto.coursesDTO;

import java.util.ArrayList;
import java.util.List;

import com.ProFit.model.bean.coursesBean.CourseModuleBean;

public class CourseModuleDTO {
	
	private Integer courseModuleId;
	
	private String courseModuleName;

	private String courseId;
	
	private String courseName;
	
	private List<Integer> moduleLessonsId;
	
	private List<String> moduleLessonsName;
	
	private List<Integer> moduleLessonsMediaDuration;
	
	public CourseModuleDTO() {
	}
	
	public CourseModuleDTO(CourseModuleBean courseModule) {
		this.courseModuleId = courseModule.getCourseModuleId();
		this.courseModuleName= courseModule.getCourseModuleName();
		this.courseId= courseModule.getCourse().getCourseId();
		this.courseName= courseModule.getCourse().getCourseName();
		
		ArrayList<Integer> moduleLessonIdList = new ArrayList<Integer>();
		ArrayList<String> moduleLessonNameList = new ArrayList<String>();
		ArrayList<Integer> moduleLessonsMediaDurationList = new ArrayList<Integer>();
		
		for(int i =0 ; i<courseModule.getModuleLessons().size();i++) {
			Integer moduleLessonsId = courseModule.getModuleLessons().get(i).getCourseLessonId();
			String moduleLessonsName = courseModule.getModuleLessons().get(i).getCourseLessonName();
			Integer moduleLessonsMediaDuration = courseModule.getModuleLessons().get(i).getMediaDuration();
			
			moduleLessonIdList.add(moduleLessonsId);
			moduleLessonNameList.add(moduleLessonsName);
			moduleLessonsMediaDurationList.add(moduleLessonsMediaDuration);
		}
		
		this.moduleLessonsId=moduleLessonIdList;
		this.moduleLessonsName=moduleLessonNameList;
		this.moduleLessonsMediaDuration=moduleLessonsMediaDurationList;
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

	public List<Integer> getModuleLessonsId() {
		return moduleLessonsId;
	}

	public void setModuleLessonsId(List<Integer> moduleLessonsId) {
		this.moduleLessonsId = moduleLessonsId;
	}

	public List<String> getModuleLessonsName() {
		return moduleLessonsName;
	}

	public void setModuleLessonsName(List<String> moduleLessonsName) {
		this.moduleLessonsName = moduleLessonsName;
	}

	public List<Integer> getModuleLessonsMediaDuration() {
		return moduleLessonsMediaDuration;
	}

	public void setModuleLessonsMediaDuration(List<Integer> moduleLessonsMediaDuration) {
		this.moduleLessonsMediaDuration = moduleLessonsMediaDuration;
	}
	
}

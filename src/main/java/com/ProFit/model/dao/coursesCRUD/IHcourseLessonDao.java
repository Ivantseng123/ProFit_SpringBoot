package com.ProFit.model.dao.coursesCRUD;

import java.util.List;

import com.ProFit.model.bean.coursesBean.CourseLessonBean;

public interface IHcourseLessonDao {
	
	// 新增課程單元
	public CourseLessonBean insertCourseLesson(CourseLessonBean courseLesson);

	// 刪除課程單元 by id
	public boolean deleteCourseLessonByID(Integer courseLessonId);

	// 更新課程單元 by id
	public boolean updateCourseLessonById(CourseLessonBean newCourseLesson);

	// 查詢單筆課程單元By courseLessonId
	public CourseLessonBean searchOneCourseLessonById(Integer courseLessonId);

	// 查詢全部 多形
	public List<CourseLessonBean> searchCourseLessons();

	// 查詢全部 By 章節id
	public List<CourseLessonBean> searchCourseLessonsByModuleId(Integer courseModuleid);
	
}

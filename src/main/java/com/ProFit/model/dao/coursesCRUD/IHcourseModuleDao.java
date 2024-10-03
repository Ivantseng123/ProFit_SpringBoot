package com.ProFit.model.dao.coursesCRUD;

import java.util.List;

import com.ProFit.model.bean.coursesBean.CourseModuleBean;



public interface IHcourseModuleDao {
	
	// 新增課程章節
	public CourseModuleBean insertCourseModule(CourseModuleBean courseModule);

	// 刪除課程章節 by id
	public boolean deleteCourseModuleById(Integer courseModuleId);

	// 更新課程章節 by id
	public boolean updateCourseModuleById(CourseModuleBean newCourseModule);

	// 查詢單筆課程章節By courseModuleId
	public CourseModuleBean searchOneCourseModuleById(Integer courseModuleId);

	// 查詢全部 多形
	public List<CourseModuleBean> searchCourseModules();

	// 查詢全部 By 課程id
	public List<CourseModuleBean> searchCourseModules(String course_id);

}
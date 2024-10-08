package com.ProFit.service.courseService;

import java.util.List;
import org.springframework.data.domain.Page;
import com.ProFit.model.bean.coursesBean.CourseLessonBean;
import com.ProFit.model.dto.coursesDTO.CourseLessonDTO;

public interface IcourseLessonService {
	
	//新增章節單元
	public CourseLessonBean insertCourseLesson(CourseLessonBean courseLesson);
	
	//刪除章節單元by courseLessonId
	public void deleteCourseLessonById(Integer courseLessonId);
	
	//修改章節單元
	public boolean updateCourseLessonById(CourseLessonBean newCourseLesson);
	
	//查詢單筆章節單元By courseLessonId
	public CourseLessonDTO searchOneCourseLessonById(Integer courseLessonId);
	
	//查詢全部章節單元
	public List<CourseLessonDTO> searchCourseLessons();
	
	//查詢全部章節單元By courseModuleId
	public List<CourseLessonDTO> searchCourseLessons(Integer courseModuleId);
	
	//查詢結果以分頁顯示
	public Page<CourseLessonDTO> findMsgByPage(Integer pageNumber);
	
}

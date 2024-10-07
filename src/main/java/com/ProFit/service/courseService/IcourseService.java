package com.ProFit.service.courseService;

import java.util.List;
import org.springframework.data.domain.Page;
import com.ProFit.model.bean.coursesBean.CourseBean;
import com.ProFit.model.dto.coursesDTO.CoursesDTO;

public interface IcourseService {
	
	//新增課程
	public CourseBean insertCourse(CourseBean course);
	
	//刪除課程by courseId
	public boolean deleteCourseById(String courseId);
	
	//修改課程
	public boolean updateCourseById(CourseBean newCourse);
	
	//查詢單筆課程By courseId
	public CoursesDTO searchOneCourseById(String courseId);
	
	//查詢全部課程 
	public List<CourseBean> searchCourses();
	
	//查詢全部課程By多條件查詢
	public List<CoursesDTO> searchCourses(String courseName, String userName, String status, Integer userId,String category);
	
	//查詢結果以分頁顯示
	public Page<CoursesDTO> findMsgByPage(Integer pageNumber);
	
}

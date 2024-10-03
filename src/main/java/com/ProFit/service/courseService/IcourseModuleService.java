package com.ProFit.service.courseService;

import java.util.List;
import org.springframework.data.domain.Page;

import com.ProFit.model.bean.coursesBean.CourseModuleBean;
import com.ProFit.model.dto.coursesDTO.CourseModuleDTO;


public interface IcourseModuleService {

	//新增課程章節
	public CourseModuleBean insertCourseModule(CourseModuleBean courseModule);
	
	//刪除課程章節by courseId
	public void deleteCourseModuleById(Integer courseModuleId);
	
	//修改課程章節
	public boolean updateCourseModuleById(CourseModuleBean newCourseModule);
	
	//查詢單筆課程章節By courseModuleId
	public CourseModuleDTO searchOneCourseModuleById(Integer courseModuleId);
	
	//查詢全部課程章節
	public List<CourseModuleDTO> searchCourseModules();
	
	//查詢全部課程章節By courseModuleId
	public List<CourseModuleDTO> searchCourseModules(String courseId);
	
	//查詢結果以分頁顯示
	public Page<CourseModuleDTO> findMsgByPage(Integer pageNumber);
	
	
}

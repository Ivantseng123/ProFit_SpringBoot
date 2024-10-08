package com.ProFit.service.courseService;

import java.util.List;

import org.springframework.data.domain.Page;
import com.ProFit.model.bean.coursesBean.CourseOrderBean;
import com.ProFit.model.dto.coursesDTO.CourseOrderDTO;

public interface IcourseOrderService {

	//新增課程訂單
	public Integer insertCourseOrder(CourseOrderBean courseOrder);
	
	//刪除課程訂單by Id
	public void deleteCourseOrderById(String courseOrderId);
	
	//修改課程訂單
	public boolean updateCourseOrderById(CourseOrderBean newCourseOrder);
	
	//查詢單筆課程訂單By courseId
	public CourseOrderDTO searchOneCourseOrderById(String courseOrderId);
	
	//查詢全部訂單
	public List<CourseOrderDTO> searchAllCourseOrders();
	
	//查詢全部訂單By多條件查詢
	public List<CourseOrderDTO> searchAllCourseOrders(String courseId,Integer studentId,String status);
	
	//查詢結果以分頁顯示
	public Page<CourseOrderDTO> findMsgByPage(Integer pageNumber);
	
}

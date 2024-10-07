package com.ProFit.controller.courses;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ProFit.model.bean.coursesBean.CourseOrderBean;
import com.ProFit.model.dao.coursesCRUD.IHcourseOrderDao;
import com.ProFit.model.dto.coursesDTO.CourseOrderDTO;
import com.ProFit.model.dto.coursesDTO.CoursesDTO;
import com.ProFit.service.courseService.IcourseOrderService;
import com.ProFit.service.courseService.IcourseService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class CourseOrderController {
	
	@Autowired
	private IcourseOrderService courseOrderService;
	
	@Autowired
	private IcourseService courseService;
	
	@GetMapping("/courseOrders")
	public String courseOrder() {
		return "coursesVIEW/courseOrderView";
	}

	@GetMapping("/courseOrders/addOrder")
	public String addCourseOrderPage() {
		return "coursesVIEW/createCourseOrderView";
	}
	
	@GetMapping("/courseOrders/updateOrder")
	public String upDateCourseOrderPage() {
		return "coursesVIEW/updateCourseOrderView";
	}
	
	// 搜尋全部的方法
	@GetMapping("/courseOrders/search")
	@ResponseBody
	public List<CourseOrderDTO> searchAllCourseOrders(
		@RequestParam(required = false) String courseId,
		@RequestParam(required = false) Integer courseStudentId,
		@RequestParam(required = false) String orderStatus
	){
		List<CourseOrderDTO> courseOrderDTOList = courseOrderService.searchAllCourseOrders(courseId, courseStudentId, orderStatus);
		
		return courseOrderDTOList;
		
	}	
	
	// 搜尋單筆訂單的方法
	@GetMapping("/courseOrders/searchOne/{courseOrderId}")
	@ResponseBody
	public CourseOrderDTO searchOneCourseOrder(@PathVariable String courseOrderId) {
		
		if(courseOrderId!=null) {
			CourseOrderDTO courseOrderDTO = courseOrderService.searchOneCourseOrderById(courseOrderId);
			return courseOrderDTO;
		}
		
		return null;
	}
	
	// 刪除課程訂單
	@GetMapping("/courseOrders/delete/{courseOrderId}")
	@ResponseBody
	public boolean deleteCourseOrderById(@PathVariable String courseOrderId) {
		
		if(courseOrderId != null) {
			courseOrderService.deleteCourseOrderById(courseOrderId);
			return true;
		}
		
		return false;
		
	}
	
	// 新增課程訂單的方法
	@PostMapping("/courseOrders/add")
	@ResponseBody
	public boolean postMethodName(@ModelAttribute CourseOrderBean courseOrder) {
		
		if(courseOrder != null) {
			CoursesDTO orderdCourse = courseService.searchOneCourseById(courseOrder.getCourseId());
			courseOrder.setCourseOrderPrice(orderdCourse.getCoursePrice());
			courseOrderService.insertCourseOrder(courseOrder);
			return true;
		}
		
		return false;
	}
	
	
	// 更新課程訂單的方法
	@PostMapping("/courseOrders/update/{oldCourseOrderId}")
	@ResponseBody
	public boolean updateCourseOrderById( @PathVariable("oldCourseOrderId") String courseOrderId,
	        @RequestParam String courseId,
	        @RequestParam Integer studentId,
	        @RequestParam String courseOrderRemark,
	        @RequestParam String courseOrderStatus) {
		
		CourseOrderBean updatedCourseOrder = new CourseOrderBean();
		updatedCourseOrder.setCourseOrderId(courseOrderId);
		updatedCourseOrder.setCourseId(courseId);
		updatedCourseOrder.setStudentId(studentId);
		updatedCourseOrder.setCourseOrderRemark(courseOrderRemark);
		updatedCourseOrder.setCourseOrderStatus(courseOrderStatus);
		
		boolean isUpdated = courseOrderService.updateCourseOrderById(updatedCourseOrder);
		
		return isUpdated;
	}
	
}

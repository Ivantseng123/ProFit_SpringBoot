package com.ProFit.controller.courses;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class CourseOrderController {
	
	
	@GetMapping("/courseOrders")
	public String courseOrder() {
		return "coursesVIEW/courseOrderView";
	}
	
	
	
}

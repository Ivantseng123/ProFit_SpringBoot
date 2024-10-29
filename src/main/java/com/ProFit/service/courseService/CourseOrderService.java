package com.ProFit.service.courseService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ProFit.model.bean.coursesBean.CourseBean;
import com.ProFit.model.bean.coursesBean.CourseOrderBean;
import com.ProFit.model.dao.coursesCRUD.CourseOrderRepository;
import com.ProFit.model.dao.coursesCRUD.CoursesRepository;
import com.ProFit.model.dao.coursesCRUD.IHcourseOrderDao;
import com.ProFit.model.dto.coursesDTO.CourseOrderDTO;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CourseOrderService implements IcourseOrderService {

	@Autowired
	private IHcourseOrderDao hcourseOrderDao;

	@Autowired
	private CourseOrderRepository courseOrderRepo;

	@Autowired
	private CoursesRepository coursesRepo;

	// 添加注入
	@Autowired
	private CourseOrderRepository courseOrderRepository;

	@Override
	public Integer insertCourseOrder(CourseOrderBean courseOrder) {

		Optional<CourseBean> optional = coursesRepo.findById(courseOrder.getCourseId());

		// 確認課程存在 不是返回0
		if (optional.isPresent()) {
			CourseBean courseBean = optional.get();
			String courseStatus = courseBean.getCourseStatus();

			// 確認課程是進行中 不是返回2 是的話返回1
			if (courseStatus.equals("Active")) {
				hcourseOrderDao.insertCourseOrder(courseOrder);
				return 1;
			}
			return 2;
		}
		return 0;
	}

	@Override
	public void deleteCourseOrderById(String courseOrderId) {
		if (courseOrderId != null) {
			courseOrderRepo.deleteById(courseOrderId);
		}
	}

	@Override
	public boolean updateCourseOrderById(CourseOrderBean newCourseOrder) {
		if (newCourseOrder != null) {
			return hcourseOrderDao.updateCourseOrderById(newCourseOrder);
		}

		return false;
	}

	@Override
	public CourseOrderDTO searchOneCourseOrderById(String courseOrderId) {

		if (courseOrderId != null) {

			Optional<CourseOrderBean> optional = courseOrderRepo.findById(courseOrderId);

			if (optional.isPresent()) {
				return new CourseOrderDTO(optional.get());
			}
		}

		return null;
	}

	@Override
	public List<CourseOrderDTO> searchAllCourseOrders() {

		List<CourseOrderBean> allCourseOrders = courseOrderRepo.findAll();

		List<CourseOrderDTO> courseOrderDTO = allCourseOrders.stream().map(CourseOrderDTO::new)
				.collect(Collectors.toList());

		return courseOrderDTO;
	}

	@Override
	public List<CourseOrderDTO> searchAllCourseOrders(String courseId, Integer studentId, String status) {

		List<CourseOrderBean> searchCourseOrders = courseOrderRepo.searchCourseOrders(courseId, studentId, status);

		List<CourseOrderDTO> courseOrderDTO = searchCourseOrders.stream().map(CourseOrderDTO::new)
				.collect(Collectors.toList());

		return courseOrderDTO;
	}

	@Override
	public Page<CourseOrderDTO> findMsgByPage(Integer pageNumber) {
		Pageable pgb = PageRequest.of(pageNumber - 1, 10, Sort.Direction.DESC, "added");

		Page<CourseOrderBean> courseOrderPage = courseOrderRepo.findAll(pgb);

		Page<CourseOrderDTO> dtoPage = courseOrderPage.map(CourseOrderDTO::new);

		return dtoPage;
	}

	// 訂單更新狀態
	public void updateOrderStatusById(String courseOrderId, String status) {
		Optional<CourseOrderBean> optional = courseOrderRepo.findById(courseOrderId);
		if (optional.isPresent()) {
			CourseOrderBean courseOrder = optional.get();
			courseOrder.setCourseOrderStatus(status);
			courseOrderRepo.save(courseOrder);
		}
	}

	// 改變參數的資料型態
	public Double getOrderAmountById(String orderId) {
		Integer courseAmount = courseOrderRepository.findOrderAmountById(orderId);
		return courseAmount != null ? courseAmount.doubleValue() : 0.0;
	}

	// 獲取創建課程的ID
	public Integer getCreatorUserIdByOrderId(String orderId) {
		Optional<CourseOrderBean> optionalOrder = courseOrderRepo.findById(orderId);

		if (optionalOrder.isPresent()) {
			CourseOrderBean courseOrder = optionalOrder.get();
			return courseOrder.getCourse().getCourseCreater().getUserId();
		}
		return null;
	}

	public List<Object[]> getCourseOrderAnalysis() {
		List<Object[]> courseOrderAnalysis = courseOrderRepo.getCourseOrderAnalysis();

		return courseOrderAnalysis;
	}

	public List<Object[]> getTop10Courses() {
		List<Object[]> top10Courses = courseOrderRepo.getTop10Courses();
		return top10Courses;
	}

	public Double getOrderAmountByMerchantTradeNo(String merchantTradeNo) {
		// TODO Auto-generated method stub
		return null;
	}

	//訂單管理 顯示登入用戶的訂單詳情
	public List<CourseOrderDTO> getOrdersByUserId(Integer userId) {
	    List<CourseOrderBean> courseOrders = courseOrderRepository.findOrdersByUserId(userId);
	    return courseOrders.stream()
	                       .map(CourseOrderDTO::new)
	                       .collect(Collectors.toList());
	}

}

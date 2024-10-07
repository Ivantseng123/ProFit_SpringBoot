package com.ProFit.model.dao.coursesCRUD;

import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ProFit.model.bean.coursesBean.CourseOrderBean;

import jakarta.persistence.EntityManager;


@Repository
@Transactional
public class HcourseOrderDao implements IHcourseOrderDao {

	@Autowired
    private EntityManager entityManager;

	// 新增課程訂單
	@Override
	public CourseOrderBean insertCourseOrder(CourseOrderBean courseOrder) {
		Session session = entityManager.unwrap(Session.class);
		//查詢當前最大值的數值部分
		String hql = "SELECT MAX(CAST(SUBSTRING(co.courseOrderId,3) AS int)) FROM CourseOrderBean co";
		Integer maxCourseOrderIdNumber = (Integer)session.createQuery(hql,Integer.class).uniqueResult();
		
		//生成新的course_id
		int newCourseOrderIdNumber = (maxCourseOrderIdNumber== null)?100:maxCourseOrderIdNumber+1;
		String newCourseOrderId = String.format("CR%03d", newCourseOrderIdNumber);
		System.out.println("New CourseOrder ID: "+newCourseOrderId);
		
		//設置生成的courseOrderId 到 courseOrderBean中
		courseOrder.setCourseOrderId(newCourseOrderId);
		
		courseOrder.setCourseOrderCreateDate(LocalDateTime.now());
		
		//保存課程
		session.persist(courseOrder);
		return courseOrder;
	}
	
	// 刪除課程訂單
	@Override
	public boolean deleteCourseOrderByID(String courseOrderId) {
		Session session = entityManager.unwrap(Session.class);
		CourseOrderBean resultBean = session.get(CourseOrderBean.class, courseOrderId);
		if(resultBean!=null) {
			session.remove(resultBean);
			return true;
		}
		return false;
	}

	// 更新課程訂單 by id
	@Override
	public boolean updateCourseOrderById(CourseOrderBean newCourseOrder) {
		Session session = entityManager.unwrap(Session.class);
		CourseOrderBean oldCourseOrder = session.get(CourseOrderBean.class, newCourseOrder.getCourseOrderId());
		
		if(oldCourseOrder==null) {
			System.out.println("CourseOrder with ID"+newCourseOrder.getCourseOrderId()+"does not exist");
			return false;
		}
		
		// 對比新舊對象的屬性值
		oldCourseOrder.setCourseId(newCourseOrder.getCourseId() == null|| newCourseOrder.getCourseId().isEmpty()
				?oldCourseOrder.getCourseId()
				:newCourseOrder.getCourseId());
		
		oldCourseOrder.setStudentId(newCourseOrder.getStudentId()==0
				?oldCourseOrder.getStudentId()
				:newCourseOrder.getStudentId());
		
		oldCourseOrder.setCourseOrderPrice(newCourseOrder.getCourseOrderPrice()==null
				?oldCourseOrder.getCourseOrderPrice()
				:newCourseOrder.getCourseOrderPrice());
		
		oldCourseOrder.setCourseOrderRemark(newCourseOrder.getCourseOrderRemark()==null||newCourseOrder.getCourseOrderRemark().isEmpty()
				?oldCourseOrder.getCourseOrderRemark()
				:newCourseOrder.getCourseOrderRemark());
		
		oldCourseOrder.setCourseOrderStatus(newCourseOrder.getCourseOrderStatus()==null||newCourseOrder.getCourseOrderStatus().isEmpty()
				?oldCourseOrder.getCourseOrderStatus()
				:newCourseOrder.getCourseOrderStatus());
			
		session.merge(oldCourseOrder);
		return true;
	}

	// 搜尋單筆課程訂單 by id
	@Override
	public CourseOrderBean searchOneCourseOrderById(String courseOrderId) {
		Session session = entityManager.unwrap(Session.class);
		return session.get(CourseOrderBean.class, courseOrderId);
	}

	// 搜尋全部課程訂單
	@Override
	public List<CourseOrderBean> searchCourseOrders() {
		Session session = entityManager.unwrap(Session.class);
		Query<CourseOrderBean> query = session.createQuery("from CourseOrderBean",CourseOrderBean.class);
		return query.list();
	}
	
	// 搜尋課程訂單by 多條件
	@Override
	public List<CourseOrderBean> searchCourseOrders(String courseId,Integer studentId,String status) {
		Session session = entityManager.unwrap(Session.class);
		StringBuilder hql = new StringBuilder("from CourseOrderBean CO WHERE 1=1");
		
		if(courseId !=null && !courseId.trim().isEmpty()) {
			hql.append(" AND courseId LIKE :courseId");
		}
		if(studentId != null) {
			hql.append(" AND studentId = :studentId");
		}
		if(status != null && !status.trim().isEmpty()) {
			hql.append(" AND status = :status");
		}
		
		Query<CourseOrderBean> query = session.createQuery(hql.toString(),CourseOrderBean.class);
		if(courseId !=null && !courseId.trim().isEmpty()) {
			query.setParameter("courseId", "%"+courseId+"%");
		}
		if(studentId != null) {
			query.setParameter("studentId", studentId);
		}
		if(status != null && !status.trim().isEmpty()) {
			query.setParameter("status","%"+status+"%");
		}
		
		return query.getResultList();
	}

}

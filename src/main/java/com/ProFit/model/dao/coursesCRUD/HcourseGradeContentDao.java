package com.ProFit.model.dao.coursesCRUD;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ProFit.model.bean.coursesBean.CourseGradeContentBean;

import jakarta.persistence.EntityManager;

@Repository
@Transactional
public class HcourseGradeContentDao implements IHcourseGradeContentDao {

	@Autowired
	private EntityManager entityManager;

	// 新增課程評價
	@Override
	public CourseGradeContentBean insertCourse(CourseGradeContentBean courseGradeContent) {
		Session session = entityManager.unwrap(Session.class);
		if (courseGradeContent != null) {
			session.persist(courseGradeContent);
		}
		return courseGradeContent;
	}

	// 刪除課程評價 by id
	@Override
	public boolean deleteCourseByID(Integer courseGradeId) {
		Session session = entityManager.unwrap(Session.class);
		if (courseGradeId != null) {
			session.remove(courseGradeId);
			return true;
		}
		return false;
	}

	// 更新課程評價 by id
	@Override
	public boolean updateCourseById(CourseGradeContentBean newCourseGrade) {
		Session session = entityManager.unwrap(Session.class);
		// 查詢原始資料，確認存在
		CourseGradeContentBean oldGrade = session.get(CourseGradeContentBean.class, newCourseGrade);

		if (oldGrade == null) {
			System.out.println("CourseGradeId " + newCourseGrade.getCourseGradeId() + "does not exist");
			return false;
		}

		oldGrade.setCourseGradeScore(newCourseGrade.getCourseGradeScore() == null
				|| newCourseGrade.getCourseGradeScore().toString().isEmpty()
						? oldGrade.getCourseGradeScore()
						: newCourseGrade.getCourseGradeScore());

		oldGrade.setCourseGradeComment(
				newCourseGrade.getCourseGradeComment() == null || newCourseGrade.getCourseGradeComment().isEmpty()
						? oldGrade.getCourseGradeComment()
						: newCourseGrade.getCourseGradeComment());

		session.merge(oldGrade);

		return true;
	}

	// 查詢單筆評價By courseGradeId
	@Override
	public CourseGradeContentBean searchOneCourseGradeContentById(Integer courseGradeId) {
		Session session = entityManager.unwrap(Session.class);
		return session.get(CourseGradeContentBean.class, courseGradeId);
	}

	// 查詢全部 多形
	@Override
	public List<CourseGradeContentBean> searchCourseGradeContents() {
		Session session = entityManager.unwrap(Session.class);
		Query<CourseGradeContentBean> query = session.createQuery("from CourseGradeContentBean",
				CourseGradeContentBean.class);
		return query.list();
	}

	// 查詢全部 By 課程評價分數
	@Override
	public List<CourseGradeContentBean> searchCourseGradeContents(String courseId, String order) {
		Session session = entityManager.unwrap(Session.class);
		StringBuilder hql = new StringBuilder("FROM CourseGradeContentBean cg WHERE 1=1");

		if (courseId != null && !courseId.trim().isEmpty()) {
			hql.append(" AND cg.courseId = :courseId");
		}

		// 動態添加排序條件，防止order傳入無效值
		if ("ASC".equalsIgnoreCase(order)) {
			hql.append(" ORDER BY cg.courseGradeScore ASC");
		} else if ("DESC".equalsIgnoreCase(order)) {
			hql.append(" ORDER BY cg.courseGradeScore DESC");
		}

		Query<CourseGradeContentBean> query = session.createQuery(hql.toString(), CourseGradeContentBean.class);

		if (courseId != null && !courseId.trim().isEmpty()) {
			query.setParameter("courseId", courseId);
		}

		return query.getResultList();
	}

}

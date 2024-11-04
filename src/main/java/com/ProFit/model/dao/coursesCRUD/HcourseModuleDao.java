package com.ProFit.model.dao.coursesCRUD;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ProFit.model.bean.coursesBean.CourseModuleBean;

import jakarta.persistence.EntityManager;

@Repository
@Transactional
public class HcourseModuleDao implements IHcourseModuleDao {

	@Autowired
	private EntityManager entityManager;

	// 新增課程章節
	@Override
	public CourseModuleBean insertCourseModule(CourseModuleBean courseModule) {
		Session session = entityManager.unwrap(Session.class);
		if (courseModule != null) {
			session.persist(courseModule);
			return courseModule;
		}
		return null;
	}

	// 刪除課程章節By id
	@Override
	public boolean deleteCourseModuleById(Integer courseModuleId) {
		Session session = entityManager.unwrap(Session.class);
		CourseModuleBean courseModuleBean = session.get(CourseModuleBean.class, courseModuleId);
		if (courseModuleBean != null) {
			session.remove(courseModuleBean);
			return true;
		}
		return false;
	}

	// 更新課程章節
	@Override
	public boolean updateCourseModuleById(CourseModuleBean newCourseModule) {
		Session session = entityManager.unwrap(Session.class);
		CourseModuleBean oldCourseModule = session.get(CourseModuleBean.class, newCourseModule.getCourseModuleId());

		if (oldCourseModule == null) {
			return false;
		}

		// 對比新舊物件的屬性
		oldCourseModule.setCourseModuleName(
				newCourseModule.getCourseModuleName() == null || newCourseModule.getCourseModuleName().isEmpty()
						? oldCourseModule.getCourseModuleName()
						: newCourseModule.getCourseModuleName());

		session.merge(oldCourseModule);
		return true;
	}

	// 搜尋課程章節by id
	@Override
	public CourseModuleBean searchOneCourseModuleById(Integer courseModuleId) {
		Session session = entityManager.unwrap(Session.class);
		return session.get(CourseModuleBean.class, courseModuleId);
	}

	// 搜尋全部課程章節
	@Override
	public List<CourseModuleBean> searchCourseModules() {
		Session session = entityManager.unwrap(Session.class);
		Query<CourseModuleBean> query = session.createQuery("from CourseModuleBean", CourseModuleBean.class);
		return query.list();
	}

	// 搜尋課程章節by courseId
	@Override
	public List<CourseModuleBean> searchCourseModules(String courseId) {
		Session session = entityManager.unwrap(Session.class);
		Query<CourseModuleBean> query = session.createQuery(
				"from CourseModuleBean cm where cm.course.courseId = :courseId",
				CourseModuleBean.class);
		query.setParameter("courseId", courseId); // 使用 setParameter 設定參數的值
		return query.getResultList(); // 執行查詢並返回結果
	}

}

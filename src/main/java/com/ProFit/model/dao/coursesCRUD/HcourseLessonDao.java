package com.ProFit.model.dao.coursesCRUD;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ProFit.model.bean.coursesBean.CourseLessonBean;

import jakarta.persistence.EntityManager;


@Repository
@Transactional
public class HcourseLessonDao implements IHcourseLessonDao {
	
	@Autowired
    private EntityManager entityManager;
	
	//新增章節單元
	@Override
	public CourseLessonBean insertCourseLesson(CourseLessonBean courseLesson) {
		Session session = entityManager.unwrap(Session.class);
		if(courseLesson!=null) {
			session.persist(courseLesson);
			return courseLesson;
		}
		return null;
	}

	//刪除章節單元By id
	@Override
	public boolean deleteCourseLessonByID(Integer courseLessonId) {
		Session session = entityManager.unwrap(Session.class);
		CourseLessonBean courseLessonBean = session.get(CourseLessonBean.class, courseLessonId);
		if(courseLessonBean!=null) {
			session.remove(courseLessonBean);
			return true;
		}
		return false;
	}
	
	//更新課程單元
	@Override
	public boolean updateCourseLessonById(CourseLessonBean newCourseLesson) {
		Session session = entityManager.unwrap(Session.class);
		CourseLessonBean oldCourseLesson = session.get(CourseLessonBean.class, newCourseLesson.getCourseLessonId());
		
		if(oldCourseLesson==null) {
			System.out.println("Lesson does not exist");
			return false;
		}
		
		//對比新舊物件的屬性
		oldCourseLesson.setCourseLessonName(
				newCourseLesson.getCourseLessonName() == null || newCourseLesson.getCourseLessonName().isEmpty()
					?oldCourseLesson.getCourseLessonName()
					:newCourseLesson.getCourseLessonName());
		
		oldCourseLesson.setCourseLessonSort(
				newCourseLesson.getCourseLessonSort() == null
					?oldCourseLesson.getCourseLessonSort()
					:newCourseLesson.getCourseLessonSort());
		
		oldCourseLesson.setLessonMediaType(
				newCourseLesson.getLessonMediaType() == null || newCourseLesson.getLessonMediaType().isEmpty()
					?oldCourseLesson.getLessonMediaType()
					:newCourseLesson.getLessonMediaType());
		
		oldCourseLesson.setLessonMediaUrl(
				newCourseLesson.getLessonMediaUrl() == null || newCourseLesson.getLessonMediaUrl().isEmpty()
					?oldCourseLesson.getLessonMediaUrl()
					:newCourseLesson.getLessonMediaUrl());
		
		oldCourseLesson.setMediaDuration(
				newCourseLesson.getMediaDuration() ==null
					?oldCourseLesson.getMediaDuration()
					:newCourseLesson.getMediaDuration());
		
		session.merge(oldCourseLesson);
		return true;
	}
	
	//搜尋單個單元by id
	@Override
	public CourseLessonBean searchOneCourseLessonById(Integer courseLessonId) {
		Session session = entityManager.unwrap(Session.class);
		return session.get(CourseLessonBean.class, courseLessonId);
	}

	//搜尋全部單元
	@Override
	public List<CourseLessonBean> searchCourseLessons() {
		Session session = entityManager.unwrap(Session.class);
		Query<CourseLessonBean> query = session.createQuery("from CourseLessonBean",CourseLessonBean.class);
		return query.list();
	}

	//搜尋全部單元by courseModule id
	@Override
	public List<CourseLessonBean> searchCourseLessonsByModuleId(Integer courseModuleId) {
		Session session = entityManager.unwrap(Session.class);
		Query<CourseLessonBean> query = session.createQuery("from CourseLessonBean where courseModule.courseModuleId = :courseModuleId",CourseLessonBean.class);
		query.setParameter("courseModuleId", courseModuleId);
		return query.getResultList();
	}

}

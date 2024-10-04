//package com.ProFit.model.dao.jobsCRUD;
//
//
//import com.ProFit.model.bean.jobsBean.JobsApplication;
//import jakarta.persistence.EntityManager;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.hibernate.query.Query;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Repository
//@Transactional
//public class HJobsApplicationDAO implements IHJobsApplicationDAO {
//
//    // session設置
////	@Autowired
////	private SessionFactory factory;
//    @Autowired
//    private EntityManager entityManager;
//
//
//
//    // 新增
//    @Override
//    public JobsApplication save(JobsApplication jobsApplication) {
//        Session session = entityManager.unwrap(Session.class);
//		session.persist(jobsApplication);
//		return jobsApplication;
//
//        }
//
//
//
//
//    // 刪除
//    @Override
//    public boolean delete(Integer jobsApplicationId) {
//        Session session = entityManager.unwrap(Session.class);
//    	JobsApplication resultBean = session.get(JobsApplication.class, jobsApplicationId);
//		if (resultBean != null) {
//			session.remove(resultBean);
//			session.flush(); //刷新
//			return true;
//		}
//		return false;
//    }
//
//
//
//
//    // 修改
//    @Override
//    public boolean update(JobsApplication jobsApplication) {
//        Session session = entityManager.unwrap(Session.class);
//    	JobsApplication originalJobs2 = session.get(JobsApplication.class, jobsApplication.getJobsApplicationId());
//
//		if (originalJobs2 == null) {
//			return false;
//		}
//		BeanUtils.copyProperties(jobsApplication, originalJobs2, "jobsApplicationId");
//		session.merge(originalJobs2);
//		session.flush();
//		return true;
//    }
//
//    // id查詢
//    @Override
//    public JobsApplication findById(Integer jobsApplicationId) {
//        Session session = entityManager.unwrap(Session.class);
//    	return session.get(JobsApplication.class, jobsApplicationId);
//    }
//
//    // 查詢全部
//    @Override
//    public List<JobsApplication> findAll() {
//        Session session = entityManager.unwrap(Session.class);
//        Query<JobsApplication> query = session.createQuery("from JobsApplication", JobsApplication.class);
//        return query.list();
//    }
//}
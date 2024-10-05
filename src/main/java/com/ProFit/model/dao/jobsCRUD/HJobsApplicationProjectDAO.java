//package com.ProFit.model.dao.jobsCRUD;
//
//
//import com.ProFit.model.bean.jobsBean.JobsApplicationProject;
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
//public class HJobsApplicationProjectDAO implements IHJobsApplicationProjectDAO {
//
//	//sessio設置
////		@Autowired
////		private SessionFactory factory;
//
//	@Autowired
//	private EntityManager entityManager;
//
//    // 新增
//    @Override
//    public JobsApplicationProject save(JobsApplicationProject jobsApplicationProject) {
//		Session session = entityManager.unwrap(Session.class);
//		session.persist(jobsApplicationProject);
////		session.flush(); //刷新
//		return jobsApplicationProject;
//        }
//
//
//
//
//
//    // 刪除
//    @Override
//    public boolean delete(Integer jobsApplicationProjectId) {
//		Session session = entityManager.unwrap(Session.class);
//    	JobsApplicationProject resultBean = session.get(JobsApplicationProject.class, jobsApplicationProjectId);
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
//    public boolean update(JobsApplicationProject jobsApplicationProject) {
//		Session session = entityManager.unwrap(Session.class);
//    	JobsApplicationProject originalJobs3 = session.get(JobsApplicationProject.class, jobsApplicationProject.getJobsApplicationProjectId());
//
//		if (originalJobs3 == null) {
//			return false;
//		}
//		BeanUtils.copyProperties(jobsApplicationProject, originalJobs3, "jobsApplicationProjectId");//copyProperties將jobs複製到originalJobs
//		session.merge(originalJobs3);
//		session.flush();
//		return true;
//        }
//
//
//    // id查詢
//    @Override
//    public JobsApplicationProject findById(Integer jobsApplicationProjectId) {
//		Session session = entityManager.unwrap(Session.class);
//
//		return session.get(JobsApplicationProject.class, jobsApplicationProjectId);
//    }
//
//    // 查詢全部
//    @Override
//    public List<JobsApplicationProject> findAll() {
//		Session session = entityManager.unwrap(Session.class);
//        Query<JobsApplicationProject> query = session.createQuery("from JobsApplicationProject", JobsApplicationProject.class);
//        return query.list();
//    }
//}
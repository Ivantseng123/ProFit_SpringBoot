package com.ProFit.model.dao.jobsCRUD;


import com.ProFit.model.bean.jobsBean.JobsApplication;
import com.ProFit.model.bean.usersBean.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IHJobsApplicationDAO extends CrudRepository<JobsApplication,Integer > {
    //    public interface IHJobsApplicationDAO extends JpaRepository<JobsApplication, Integer> {
    List<JobsApplication> findByApplicant(Users applicant);
}

package com.ProFit.model.dao.jobsCRUD;


import com.ProFit.model.bean.jobsBean.JobsApplication;
import com.ProFit.model.bean.usersBean.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IHJobsApplicationDAO extends JpaRepository<JobsApplication,Integer > {

    //公司
    Page<JobsApplication> findByJobs_Users_UserId(Integer userId, Pageable pageable);

    //個人應徵
    Page<JobsApplication> findJobsApplicationsByApplicant(Users users, Pageable pageable);
}

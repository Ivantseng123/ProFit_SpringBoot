package com.ProFit.model.dao.jobsCRUD;


import com.ProFit.model.bean.jobsBean.JobsApplication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IHJobsApplicationDAO extends CrudRepository<JobsApplication,Integer > {
}

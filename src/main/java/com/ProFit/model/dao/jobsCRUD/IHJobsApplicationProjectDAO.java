package com.ProFit.model.dao.jobsCRUD;


import com.ProFit.model.bean.jobsBean.JobsApplicationProject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IHJobsApplicationProjectDAO extends CrudRepository<JobsApplicationProject,Integer > {
    //    public interface IHJobsApplicationProjectDAO extends JpaRepository<JobsApplicationProject, Integer> {

}

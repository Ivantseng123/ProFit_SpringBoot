package com.ProFit.model.dao.jobsCRUD;


import java.util.List;
import java.util.Optional;

import com.ProFit.model.bean.jobsBean.Jobs;
import com.ProFit.model.bean.jobsBean.JobsApplication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHJobsDAO extends CrudRepository<Jobs, Integer> {
//    public interface IHJobsDAO extends JpaRepository<Jobs, Integer> {


}

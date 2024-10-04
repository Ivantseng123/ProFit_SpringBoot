package com.ProFit.model.dao.jobsCRUD;


import java.util.List;

import com.ProFit.model.bean.jobsBean.Jobs;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHJobsDAO extends CrudRepository<Jobs, Integer> {
}

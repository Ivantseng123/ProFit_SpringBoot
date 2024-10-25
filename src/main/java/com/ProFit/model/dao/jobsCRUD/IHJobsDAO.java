package com.ProFit.model.dao.jobsCRUD;



import com.ProFit.model.bean.jobsBean.Jobs;
import com.ProFit.model.bean.usersBean.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IHJobsDAO extends JpaRepository<Jobs, Integer> {
    List<Jobs> findJobsByUsers(Users user);
}

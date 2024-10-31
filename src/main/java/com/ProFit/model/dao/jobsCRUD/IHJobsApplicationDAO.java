package com.ProFit.model.dao.jobsCRUD;


import com.ProFit.model.bean.jobsBean.JobsApplication;
import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.dto.jobsDTO.AnalysisDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IHJobsApplicationDAO extends JpaRepository<JobsApplication,Integer > {

    //公司
    Page<JobsApplication> findByJobs_Users_UserIdOrderByJobsApplicationIdDesc(Integer userId, Pageable pageable);

    //個人應徵
    Page<JobsApplication> findJobsApplicationsByApplicantOrderByJobsApplicationIdDesc(Users users, Pageable pageable);

    // 用已完成的訂單分析課程購買情況
    @Query("SELECT new com.ProFit.model.dto.jobsDTO.AnalysisDTO(c.categoryName, COUNT(j)) " +
            "FROM JobsApplication j " +
            "JOIN j.jobs.category c " +
            "GROUP BY c.categoryName " +
            "ORDER BY COUNT(j) DESC")
    List<AnalysisDTO> getJobsCategoryAnalysis();
}

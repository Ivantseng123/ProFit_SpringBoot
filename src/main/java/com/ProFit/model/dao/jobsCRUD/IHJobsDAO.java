package com.ProFit.model.dao.jobsCRUD;

import com.ProFit.model.bean.jobsBean.Jobs;
import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.dto.jobsDTO.AnalysisDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IHJobsDAO extends JpaRepository<Jobs, Integer> {
    List<Jobs> findJobsByUsers(Users user);

    // 用已完成的訂單分析課程購買情況
    @Query("SELECT new com.ProFit.model.dto.jobsDTO.AnalysisDTO(c.categoryName, COUNT(j)) " +
            "FROM Jobs j " +
            "JOIN j.category c " +
            "GROUP BY c.categoryName " +
            "ORDER BY COUNT(j) DESC")
    List<AnalysisDTO> getJobsCategoryAnalysis();
}

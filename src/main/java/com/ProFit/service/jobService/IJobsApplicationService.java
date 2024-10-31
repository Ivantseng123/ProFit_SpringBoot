package com.ProFit.service.jobService;


import com.ProFit.model.bean.jobsBean.JobsApplication;
import com.ProFit.model.dto.jobsDTO.AnalysisDTO;

import java.util.List;
import java.util.Optional;

public interface IJobsApplicationService {
	JobsApplication save(JobsApplication jobsApplication);

	Optional<JobsApplication> findById(Integer jobsApplicationId);

    List<JobsApplication> findAll();

    JobsApplication update(JobsApplication jobsApplication);

    void delete(Integer jobsApplicationId);

    List<AnalysisDTO> getApplicationCategoryAnalysis();
}

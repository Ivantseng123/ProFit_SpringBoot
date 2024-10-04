package com.ProFit.service.jobService;


import com.ProFit.model.bean.jobsBean.JobsApplicationProject;

import java.util.List;
import java.util.Optional;

public interface IJobsApplicationProjectService {
	JobsApplicationProject save(JobsApplicationProject jobsApplicationProject);

	Optional<JobsApplicationProject> findById(Integer jobsApplicationProjectId);

    List<JobsApplicationProject> findAll();

    JobsApplicationProject update(JobsApplicationProject jobsApplicationProject);

    void delete(Integer jobsApplicationProjectId);
}

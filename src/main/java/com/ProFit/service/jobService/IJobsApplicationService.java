package com.ProFit.service.jobService;


import com.ProFit.model.bean.jobsBean.Jobs;
import com.ProFit.model.bean.jobsBean.JobsApplication;
import com.ProFit.model.bean.usersBean.Users;

import java.util.List;
import java.util.Optional;

public interface IJobsApplicationService {
	JobsApplication save(JobsApplication jobsApplication);

	Optional<JobsApplication> findById(Integer jobsApplicationId);

    List<JobsApplication> findAll();

    //void影響boolean
    JobsApplication update(JobsApplication jobsApplication);

    void delete(Integer jobsApplicationId);

    List<JobsApplication> findByApplicantId(Integer applicantId);

}

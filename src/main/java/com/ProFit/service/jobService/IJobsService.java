package com.ProFit.service.jobService;


import com.ProFit.model.bean.jobsBean.Jobs;

import java.util.List;
import java.util.Optional;

public interface IJobsService {
    Jobs save(Jobs jobs);

    Optional<Jobs> findById(Integer jobsId);

    List<Jobs> findAll();

    Jobs update(Jobs jobs);

    void delete(Integer jobsId);
}
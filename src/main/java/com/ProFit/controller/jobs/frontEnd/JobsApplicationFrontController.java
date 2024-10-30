package com.ProFit.controller.jobs.frontEnd;

import com.ProFit.model.bean.jobsBean.Jobs;
import com.ProFit.model.bean.jobsBean.JobsApplication;
import com.ProFit.service.jobService.JobsApplicationService;
import com.ProFit.service.jobService.JobsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(JobsApplicationFrontController.URL)
public class JobsApplicationFrontController {

    public static final String URL = "/front/jobsApplication";

    private final JobsService jobsService;
    private final JobsApplicationService applicationService;

    public JobsApplicationFrontController(JobsService jobsService, JobsApplicationService applicationService) {
        this.jobsService = jobsService;
        this.applicationService = applicationService;
    }
}

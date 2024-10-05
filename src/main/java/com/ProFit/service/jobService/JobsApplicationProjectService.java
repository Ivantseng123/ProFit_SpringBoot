package com.ProFit.service.jobService;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ProFit.model.bean.jobsBean.JobsApplicationProject;
import com.ProFit.model.dao.jobsCRUD.IHJobsApplicationProjectDAO;

@Service
@Transactional
public class JobsApplicationProjectService implements IJobsApplicationProjectService{
	
	private final IHJobsApplicationProjectDAO jobsApplicationProjectDAO;
	
	public JobsApplicationProjectService(IHJobsApplicationProjectDAO jobsApplicationProjectDAO) {
        this.jobsApplicationProjectDAO = jobsApplicationProjectDAO;
    }

	@Override
    public JobsApplicationProject save(JobsApplicationProject jobsApplicationProject) {
        return jobsApplicationProjectDAO.save(jobsApplicationProject);
    }

	@Override
    public Optional<JobsApplicationProject> findById(Integer jobsApplicationProjectId) {
        return jobsApplicationProjectDAO.findById(jobsApplicationProjectId);
    }

	@Override
    public List<JobsApplicationProject> findAll() {
        return (List<JobsApplicationProject>) jobsApplicationProjectDAO.findAll();
    }

	@Override
    public JobsApplicationProject update(JobsApplicationProject jobsApplicationProject) {
        return jobsApplicationProject;
    }

	@Override
    public void delete(Integer jobsApplicationProjectId) {
        JobsApplicationProject jobsApplicationProject = findById(jobsApplicationProjectId).orElse(null);//orElse如果找不到就會是null
        if(jobsApplicationProject != null) {
            jobsApplicationProjectDAO.delete(jobsApplicationProject);

        }
    }


}

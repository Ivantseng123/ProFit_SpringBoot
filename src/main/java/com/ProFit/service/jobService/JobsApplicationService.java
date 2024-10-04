package com.ProFit.service.jobService;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ProFit.model.bean.jobsBean.JobsApplication;
import com.ProFit.model.dao.jobsCRUD.IHJobsApplicationDAO;

@Service
@Transactional
public class JobsApplicationService implements IJobsApplicationService{
	
	
//	@Autowired
//	private IHJobsApplicationDAO jobsApplicationDAO;
	
	private final IHJobsApplicationDAO jobsApplicationDAO;
    public JobsApplicationService(IHJobsApplicationDAO jobsApplicationDAO) {
        this.jobsApplicationDAO = jobsApplicationDAO;
    }
	
	@Override
    public JobsApplication save(JobsApplication jobsApplication) {
        return jobsApplicationDAO.save(jobsApplication);
    }

	@Override
    public Optional<JobsApplication> findById(Integer jobsApplicationId) {
        return jobsApplicationDAO.findById(jobsApplicationId);
    }

	@Override
    public List<JobsApplication> findAll() {
        return (List<JobsApplication>) jobsApplicationDAO.findAll();
    }

	//void影響boolean
	@Override
    public JobsApplication update(JobsApplication jobsApplication) {
        return jobsApplication;
    }

	//void影響boolean
	@Override
    public void delete(Integer jobsApplicationId) {
        JobsApplication jobsApplication = findById(jobsApplicationId).orElse(null);//orElse如果找不到就會是null
        if (jobsApplication != null) {
            jobsApplicationDAO.delete(jobsApplication);
        }
    }


}

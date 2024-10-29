package com.ProFit.service.jobService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.dao.usersCRUD.UsersRepository;
import com.ProFit.service.userService.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
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
	private final UsersRepository usersRepository;

    public JobsApplicationService(IHJobsApplicationDAO jobsApplicationDAO, UsersRepository usersRepository) {
        this.jobsApplicationDAO = jobsApplicationDAO;
        this.usersRepository = usersRepository;
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

            JobsApplication existJobsApplication = jobsApplicationDAO.findById(jobsApplication.getJobsApplicationId()).orElse(new JobsApplication());
            BeanUtils.copyProperties(jobsApplication, existJobsApplication,"jobsApplicationDate");

        return jobsApplicationDAO.save(existJobsApplication);
    }

	//void影響boolean
	@Override
    public void delete(Integer jobsApplicationId) {
        JobsApplication jobsApplication = findById(jobsApplicationId).orElse(null);//orElse如果找不到就會是null
        if (jobsApplication != null) {
            jobsApplicationDAO.delete(jobsApplication);
        }
    }

    public List<JobsApplication> findByUserId(Integer userId, Pageable pageable) {
        Users users = usersRepository.findById(userId).orElse(null);
        List<JobsApplication> applications = new ArrayList<>();
        if (users != null && (users.getUserIdentity() == 2 || users.getUserIdentity() == 1)) {
            applications = jobsApplicationDAO.findJobsApplicationsByApplicant(users, pageable);
        }
        return applications;
    }


}

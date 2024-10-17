package com.ProFit.service.jobService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ProFit.model.bean.jobsBean.Jobs;
import com.ProFit.model.bean.jobsBean.JobsApplication;
import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.dao.jobsCRUD.IHJobsApplicationDAO;

import com.ProFit.model.dao.usersCRUD.UsersRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class JobsApplicationService implements IJobsApplicationService {


//	@Autowired
//	private IHJobsApplicationDAO jobsApplicationDAO;

    private final UsersRepository usersDAO;

    private final IHJobsApplicationDAO jobsApplicationDAO;

    public JobsApplicationService(UsersRepository usersDAO, IHJobsApplicationDAO jobsApplicationDAO) {
        this.usersDAO = usersDAO;
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

//        JobsApplication existJobsApplication = jobsApplicationDAO.findById(jobsApplication.getJobsApplicationId()).orElse(new JobsApplication());
//        BeanUtils.copyProperties(jobsApplication, existJobsApplication, "jobsApplicationDate");

        return jobsApplicationDAO.save(jobsApplication);
    }

    @Override
    public void delete(Integer jobsApplicationId) {
        JobsApplication jobsApplication = findById(jobsApplicationId).orElse(null);//orElse如果找不到就會是null
        if (jobsApplication != null) {
            jobsApplicationDAO.delete(jobsApplication);
        }
    }


//        @Override
//        // 新增方法：檢查應徵申請狀態
//        public boolean checkApplicationStatus(Integer jobsApplicationId) {
//            Optional<JobsApplication> application = findById(jobsApplicationId);
//            if (application.isPresent()) {
//                // 假設狀態 1 是已接受的狀態
//                return application.get().getJobsApplicationStatus() == 1;
//            }
//            return false;
//        }

    // 根據應徵者ID查找申請
    @Override
    public List<JobsApplication> findByApplicantId(Integer applicantId) {
        Users applicant = usersDAO.findById(applicantId).orElse(null);
        if(applicant != null){
            return jobsApplicationDAO.findByApplicant(applicant);
        }else {
            return new ArrayList<>();
        }

    }


}



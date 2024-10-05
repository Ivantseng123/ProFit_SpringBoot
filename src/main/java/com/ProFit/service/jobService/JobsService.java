package com.ProFit.service.jobService;

import java.util.List;
import java.util.Optional;

import com.ProFit.model.bean.jobsBean.Jobs;
import com.ProFit.model.dao.jobsCRUD.IHJobsDAO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class JobsService implements IJobsService{
    
//	@Autowired
//	private IHJobsDAO jobsDAO;

	private final IHJobsDAO jobsDAO;
    public JobsService(IHJobsDAO jobsDAO) {
        this.jobsDAO = jobsDAO;
    }
    //以上4行爲裝逼寫法，省略了 @Autowired 註解，因為當一個類只有一個構造函數時，
    //Spring 自動會進行依賴注入，叫作「構造函數注入」。此寫法違反ioc，依賴於controller

    @Override
    public Jobs save(Jobs jobs) {
        return jobsDAO.save(jobs);
    }

    @Override
    public Optional<Jobs> findById(Integer jobsId) {
        return jobsDAO.findById(jobsId);
    }

    @Override
    public List<Jobs> findAll() {
        return (List<Jobs>) jobsDAO.findAll();
    }

    @Override
    public Jobs update(Jobs jobs) {
        return jobs;
    }

    @Override
    public void delete(Integer jobsId) {
        Jobs jobs = findById(jobsId).orElse(null);
        if(jobs != null) {
            jobsDAO.delete(jobs);
        }
    }
}

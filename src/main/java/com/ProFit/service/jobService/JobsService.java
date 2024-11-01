package com.ProFit.service.jobService;

import java.util.List;
import java.util.Optional;

import com.ProFit.model.bean.jobsBean.Jobs;
import com.ProFit.model.bean.usersBean.Employer_profile;
import com.ProFit.model.dao.jobsCRUD.IHJobsDAO;

import com.ProFit.model.dto.jobsDTO.AnalysisDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        Jobs existJobs = jobsDAO.findById(jobs.getJobsId()).orElse(new Jobs());
            BeanUtils.copyProperties(jobs, existJobs,"jobsPostingDate");

        return jobsDAO.save(existJobs);
    }

    @Override
    public void delete(Integer jobsId) {
        Jobs jobs = findById(jobsId).orElse(null);
        if(jobs != null) {
            jobsDAO.delete(jobs);
        }
    }

    @Override
    public Page<Jobs> findAll(Pageable pageable) {
        return jobsDAO.findAll(pageable);
    }


    //俊翰使用
    public List<Jobs> findAllJobsByEmployee(Employer_profile employer){
        return jobsDAO.findJobsByUsers(employer.getUser());
    }

    @Override
    public List<AnalysisDTO> getJobsCategoryAnalysis(){
        return jobsDAO.getJobsCategoryAnalysis();
    }
}

package com.ProFit.service.userService;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.ProFit.model.bean.usersBean.Employer_application;
import com.ProFit.model.dto.usersDTO.EmpApplDTO;


public interface IEmpApplService {

//	List<Employer_application> getAllEmpAppl();

	Employer_application saveEmpApplInfo(Employer_application emp);

	void deleteEmpInfo(int employer_application_id);

	Employer_application updateEmpApplInfo(Employer_application emp);

	boolean updateEmpApplcheck_pass(int employer_application_id, int user_id);

	boolean updateEmpApplcheck_reject(int employer_application_id);

	Employer_application getEmpApplInfoByID(int employer_application_id);

	Page<EmpApplDTO> findEmpApplByPage(Integer pageNumber);

	Page<EmpApplDTO> findEmpApplByPageAndSearch(Integer pageNumber, String search);
	
	Employer_application findLatestByUserId(Integer userId);

}
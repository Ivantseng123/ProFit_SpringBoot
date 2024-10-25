package com.ProFit.service.userService;

import java.util.List;
import org.springframework.data.domain.Page;
import com.ProFit.model.bean.usersBean.Employer_profile;
import com.ProFit.model.dto.usersDTO.CompanyStatistics;
import com.ProFit.model.dto.usersDTO.EmpPfDTO;

public interface IEmpPfService {

	// 新增企業資訊
	Employer_profile saveEmployerInfo(Employer_profile emp);

	// 刪除企業資訊BY ID
	void deleteEmpInfo(int employer_profile_id);

	Employer_profile updateEmpInfo(Employer_profile emp);

	List<Employer_profile> getAllEmpInfo();

	Employer_profile getEmpPfInfoByID(int employer_profile_id);

	Employer_profile getEmpPfInfoByUserId(int user_id);

	Page<EmpPfDTO> findEmpPfByPage(Integer pageNumber);

	Page<EmpPfDTO> findEmpPfByPageAndSearch(Integer pageNumber, String search);

	List<CompanyStatistics> getCompanyStatistics();

}
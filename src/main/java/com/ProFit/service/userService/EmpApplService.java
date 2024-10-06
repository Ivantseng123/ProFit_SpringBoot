package com.ProFit.service.userService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ProFit.model.bean.usersBean.Employer_application;
import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.dao.usersCRUD.EmpApplRepository;
import com.ProFit.model.dao.usersCRUD.UsersRepository;
import com.ProFit.model.dto.usersDTO.EmpApplDTO;
import com.ProFit.model.dto.usersDTO.UsersDTO;

@Service
@Transactional
public class EmpApplService implements IEmpApplService {

	@Autowired
	private EmpApplRepository empApplRepository;

	@Autowired
	private UsersRepository usersRepository;

//	@Override
//	public List<Employer_application> getAllEmpAppl() {
//			
//		return empApplRepository.findAll();
//	}

	@Override
	public Employer_application saveEmpApplInfo(Employer_application emp) {

		Optional<Users> optional = usersRepository.findById(emp.getUserId());

		if (optional.isPresent()) {
			return empApplRepository.save(emp);
		}

		return null;
	}

	@Override
	public void deleteEmpInfo(int employer_application_id) {

		empApplRepository.deleteById(employer_application_id);
	}

	@Override
	public Employer_application updateEmpApplInfo(Employer_application emp) {

		Optional<Employer_application> optional = empApplRepository.findById(emp.getEmployerApplicationId());

		if (optional.isPresent()) {

			return empApplRepository.save(emp);
		}

		return null;
	}

	@Override
	public boolean updateEmpApplcheck_pass(int employer_application_id, int user_id) {

		Optional<Employer_application> optional = empApplRepository.findById(employer_application_id);
		
		Optional<Users> optional1 = usersRepository.findById(user_id);

		if (optional.isPresent()) {
			Employer_application empAppl = optional.get();
			empAppl.setApplicationCheck(1);
			empApplRepository.save(empAppl);
			
			Users user = optional1.get();
			user.setUserIdentity(2);
			usersRepository.save(user);

			return true;
		}
		return false;
	}

	@Override
	public boolean updateEmpApplcheck_reject(int employer_application_id) {
		Optional<Employer_application> optional = empApplRepository.findById(employer_application_id);

		if (optional.isPresent()) {
			Employer_application empAppl = optional.get();
			empAppl.setApplicationCheck(2);
			empApplRepository.save(empAppl);

			return true;
		}
		return false;
	}

	@Override
	public Employer_application getEmpApplInfoByID(int employer_application_id) {

		Optional<Employer_application> optional = empApplRepository.findById(employer_application_id);

		if (optional.isPresent()) {
			return optional.get();
		}
		return null;

	}

	@Override
	public Page<EmpApplDTO> findEmpApplByPage(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.Direction.DESC, "employerApplicationId");
		Page<Employer_application> empApplsPage = empApplRepository.findAll(pageable);

		return empApplsPage.map(empAppl -> new EmpApplDTO(empAppl));
	}

	@Override
	public Page<EmpApplDTO> findEmpApplByPageAndSearch(Integer pageNumber, String search) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.Direction.DESC, "employerApplicationId");

		if (search == null || search.isEmpty()) {

			Page<Employer_application> empApplsPage = empApplRepository.findAll(pageable);
			return empApplsPage.map(empAppl -> new EmpApplDTO(empAppl));
		}

		Page<Employer_application> empApplsPage = empApplRepository.findByUserEmailOrCompanyNameContaining(search,
				search, pageable);
		return empApplsPage.map(empAppl -> new EmpApplDTO(empAppl));
	}

}

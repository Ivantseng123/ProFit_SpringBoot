package com.ProFit.service.userService;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ProFit.model.bean.usersBean.Employer_profile;
import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.dao.usersCRUD.EmpPfRepository;
import com.ProFit.model.dto.usersDTO.CompanyStatistics;
import com.ProFit.model.dto.usersDTO.EmpPfDTO;
import com.ProFit.model.dto.usersDTO.UserStatistics;
import com.ProFit.model.dto.usersDTO.UsersDTO;

import jakarta.persistence.criteria.Join;

@Service
@Transactional
public class EmpPfService implements IEmpPfService {

	@Autowired
	private EmpPfRepository empPfRepository;

	// 新增企業資訊
	@Override
	public Employer_profile saveEmployerInfo(Employer_profile emp) {
		return empPfRepository.save(emp);
	}

	// 刪除企業資訊BY ID
	@Override
	public void deleteEmpInfo(int employer_profile_id) {
		empPfRepository.deleteById(employer_profile_id);
	}

	@Override
	public Employer_profile updateEmpInfo(Employer_profile emp) {
		Optional<Employer_profile> optional = empPfRepository.findById(emp.getEmployerProfileId());

		if (optional.isPresent()) {

			return empPfRepository.save(emp);
		}

		return null;
	}

	@Override
	public List<Employer_profile> getAllEmpInfo() {
		return empPfRepository.findAll();
	}

	@Override
	public Employer_profile getEmpPfInfoByID(int employer_profile_id) {
		Optional<Employer_profile> optional = empPfRepository.findById(employer_profile_id);

		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public Page<EmpPfDTO> findEmpPfByPage(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.Direction.DESC, "employerProfileId");
		Page<Employer_profile> empPfsPage = empPfRepository.findAll(pageable);

		return empPfsPage.map(empPf -> new EmpPfDTO(empPf));
	}

	@Override
	public Page<EmpPfDTO> findEmpPfByPage_frontend(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 9, Sort.Direction.DESC, "employerProfileId");
		Page<Employer_profile> empPfsPage = empPfRepository.findAll(pageable);

		return empPfsPage.map(empPf -> new EmpPfDTO(empPf));
	}

	@Override
	public Page<EmpPfDTO> findEmpPfByPageAndSearch(Integer pageNumber, String search) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.Direction.DESC, "employerProfileId");

		if (search == null || search.isEmpty()) {

			Page<Employer_profile> empPfsPage = empPfRepository.findAll(pageable);
			return empPfsPage.map(empPf -> new EmpPfDTO(empPf));
		}

		Page<Employer_profile> empApplsPage = empPfRepository.findByUserEmailOrCompanyNameContaining(search, search,
				pageable);
		return empApplsPage.map(empPf -> new EmpPfDTO(empPf));
	}

	@Override
	public Page<EmpPfDTO> findEmpPfByPageAndSearch_frontend(Integer pageNumber, String search, String address, String category) {
	    Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.Direction.DESC, "employerProfileId");

	    // 初始化Specification
	    Specification<Employer_profile> spec = Specification.where(null);

	    // 加入 search 查詢條件（不依賴其他條件）
	    if (search != null && !search.isEmpty()) {
	        spec = spec.and((root, query, criteriaBuilder) -> {
	            Join<Object, Object> userJoin = root.join("user");
	            return criteriaBuilder.or(
	                criteriaBuilder.like(userJoin.get("userEmail"), "%" + search + "%"),
	                criteriaBuilder.like(root.get("companyName"), "%" + search + "%")
	            );
	        });
	    }

	    // 加入 address 查詢條件
	    if (address != null && !address.isEmpty()) {
	        spec = spec.and((root, query, criteriaBuilder) ->
	            criteriaBuilder.like(root.get("companyAddress"), address + "%")
	        );
	    }

	    // 加入 category 查詢條件
	    if (category != null && !category.isEmpty()) {
	        spec = spec.and((root, query, criteriaBuilder) ->
	            criteriaBuilder.equal(root.get("companyCategory"), category)
	        );
	    }

	    // 使用指定的查詢條件查詢數據庫並映射到DTO
	    Page<Employer_profile> empPfsPage = empPfRepository.findAll(spec, pageable);
	    return empPfsPage.map(empPf -> new EmpPfDTO(empPf));
	}




	@Override
	public Employer_profile getEmpPfInfoByUserId(int user_id) {
		return empPfRepository.findByUserId(user_id);
	}

	@Override
	public List<CompanyStatistics> getCompanyStatistics() {
		return empPfRepository.countByCompanyCategory();
	}

}

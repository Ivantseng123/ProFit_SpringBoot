package com.ProFit.controller.users;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ProFit.model.bean.usersBean.Employer_profile;
import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.dto.usersDTO.EmpApplDTO;
import com.ProFit.model.dto.usersDTO.EmpPfDTO;
import com.ProFit.service.userService.IEmpPfService;
import com.ProFit.service.userService.IUserService;

@Controller
public class empPfController {

	@Autowired
	private IEmpPfService empPfService;

	@Autowired
	private IUserService userService;

	@GetMapping("empPf/allempPf")
	public String GetAllEmpPf(Model model) {

		model.addAttribute("emps", empPfService.getAllEmpInfo());

		return "usersVIEW/AllemployerProfile";
	}

	@PostMapping("empPf/addEmpPf")
	@ResponseBody
	public String InsertEmpPf(@ModelAttribute EmpPfDTO empPfDTO) {

		Integer userId = Integer.valueOf(empPfDTO.getUserId());

		String companyAddress = empPfDTO.getCompanyAddress() + empPfDTO.getCompanyAddress1();

		Employer_profile emp = new Employer_profile();
		Users user = new Users();
		emp.setUserId(userId);
		emp.setCompanyName(empPfDTO.getCompanyName());
		emp.setCompanyAddress(companyAddress);
		emp.setCompanyCategory(empPfDTO.getCompanyCategory());
		emp.setCompanyPhoneNumber(empPfDTO.getCompanyPhoneNumber());
		emp.setCompanyTaxID(empPfDTO.getCompanyTaxID());

		user = userService.getUserInfoByID(userId);

		Employer_profile existempPf = empPfService.getEmpPfInfoByUserId(userId);

		if (user != null && existempPf == null) {
			empPfService.saveEmployerInfo(emp);
			return "新增成功";
		}

		return "新增失敗";
	}

	@DeleteMapping("empPf/deleteEmpPf")
	@ResponseBody
	public String DeleteEmpPf(@RequestBody Map<String, String> emp) {

		int employer_profile_id = Integer.parseInt(emp.get("employer_profile_id"));

		empPfService.deleteEmpInfo(employer_profile_id);

		return "Ok";
	}

	@PutMapping("empPf/updateEmpPf")
	@ResponseBody
	public Employer_profile UpdateEmpPf(@ModelAttribute EmpPfDTO empPfDTO) {

		Integer userId = Integer.valueOf(empPfDTO.getUserId());

		String companyAddress = empPfDTO.getCompanyAddress() + empPfDTO.getCompanyAddress1();

		Employer_profile emp = new Employer_profile();

		emp.setEmployerProfileId(empPfDTO.getEmployerProfileId());
		emp.setUserId(userId);
		emp.setCompanyName(empPfDTO.getCompanyName());
		emp.setCompanyAddress(companyAddress);
		emp.setCompanyCategory(empPfDTO.getCompanyCategory());
		emp.setCompanyPhoneNumber(empPfDTO.getCompanyPhoneNumber());
		emp.setCompanyTaxID(empPfDTO.getCompanyTaxID());
		emp.setCompanyNumberOfemployee(empPfDTO.getCompanyNumberOfemployee());
		emp.setCompanyCaptital(empPfDTO.getCompanyCaptital());
		emp.setCompanyDescription(empPfDTO.getCompanyDescription());
		emp.setCompanyPhotoURL(empPfDTO.getCompanyPhotoURL());

		empPfService.updateEmpInfo(emp);

		return empPfService.updateEmpInfo(emp);
	}

	@GetMapping("/getEmpPfPage")
	public String GetEmpPf(@RequestParam String employerProfileId, @RequestParam String action) {

		if (action.equals("edit")) {
			return "usersVIEW/UpdateEmpPf";
		} else {
			return "usersVIEW/GetEmpPf";
		}

	}

	@GetMapping(path = "/getempPf/{employerProfileId}")
	@ResponseBody
	public EmpPfDTO GetEmpPf(@PathVariable String employerProfileId) {

		Integer employerProfileId1 = Integer.valueOf(employerProfileId);

		EmpPfDTO empPf = new EmpPfDTO(empPfService.getEmpPfInfoByID(employerProfileId1));

		return empPf;

	}

	@ResponseBody
	@GetMapping("/api/empPf/page")
	public Page<EmpPfDTO> findByPageApi(@RequestParam Integer pageNumber,
			@RequestParam(required = false) String search) {
		Page<EmpPfDTO> page;
		if (search != null && !search.isEmpty()) {
			page = empPfService.findEmpPfByPageAndSearch(pageNumber, search);
		} else {
			page = empPfService.findEmpPfByPage(pageNumber);
		}
		return page;
	}

}

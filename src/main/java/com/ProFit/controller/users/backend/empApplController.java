package com.ProFit.controller.users.backend;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ProFit.model.bean.usersBean.Employer_application;
import com.ProFit.model.bean.usersBean.Employer_profile;
import com.ProFit.model.dto.usersDTO.EmpApplDTO;
import com.ProFit.model.dto.usersDTO.UsersDTO;
import com.ProFit.service.userService.IEmpApplService;
import com.ProFit.service.userService.IEmpPfService;

import jakarta.servlet.http.HttpSession;

@Controller
public class empApplController {

	@Autowired
	private IEmpApplService empApplService;

	@Autowired
	private IEmpPfService empPfService;

	@GetMapping("empAppls/allEmpAppl")
	public String GetAllEmpAppl() {

		return "usersVIEW/AllemployerAppl";
	}

	@PostMapping("empAppl/addEmpAppl")
	@ResponseBody
	public String InsertAllEmpAppl(@ModelAttribute EmpApplDTO empApplDTO) {
		
		System.out.println(empApplDTO);
		
		int userId = Integer.valueOf(empApplDTO.getUserId());
		String address = empApplDTO.getCompanyAddress() + empApplDTO.getCompanyAddress1();
		
		Employer_application emp = new Employer_application();

		emp.setUserId(userId);
		emp.setCompanyName(empApplDTO.getCompanyName());
		emp.setCompanyPhoneNumber(empApplDTO.getCompanyPhoneNumber());
		emp.setCompanyAddress(address);
		emp.setCompanyTaxID(empApplDTO.getCompanyTaxID());
		emp.setCompanyCategory(empApplDTO.getCompanyCategory());
		emp.setUserNationalId(empApplDTO.getUserNationalId());
		emp.setCompanyTaxIdDocURL(empApplDTO.getCompanyTaxIdDocURL());
		emp.setIdCardPictureURL1(empApplDTO.getIdCardPictureURL1());
		emp.setIdCardPictureURL2(empApplDTO.getIdCardPictureURL2());
		emp.setApplicationCheck(0);

		if(empApplService.saveEmpApplInfo(emp) != null) {
			
			return "新增OK";
		}else {
			return "會員不存在";
		}
		
		
	}

	@DeleteMapping("empAppl/deleteEmpAppl")
	@ResponseBody
	public String DeleteEmpAppl(@RequestBody Map<String, String> emp) {

		int employer_application_id = Integer.parseInt(emp.get("employer_application_id"));

		empApplService.deleteEmpInfo(employer_application_id);

		return "Ok";
	}

	@PutMapping("empAppl/updateEmpAppl")
	@ResponseBody
	public Employer_application UpdateEmpAppl(@ModelAttribute EmpApplDTO empApplDTO) {
		
		
		System.out.println("-------------------------------------------------");
		System.out.println(empApplDTO);
		
		int employerApplicationId = Integer.valueOf(empApplDTO.getEmployerApplicationId());
		int userId = Integer.valueOf(empApplDTO.getUserId());
		String address = empApplDTO.getCompanyAddress() + empApplDTO.getCompanyAddress1();

		Employer_application emp = new Employer_application();
		emp.setEmployerApplicationId(employerApplicationId);
		emp.setUserId(userId);
		emp.setCompanyName(empApplDTO.getCompanyName());
		emp.setCompanyPhoneNumber(empApplDTO.getCompanyPhoneNumber());
		emp.setCompanyAddress(address);
		emp.setCompanyTaxID(empApplDTO.getCompanyTaxID());
		emp.setCompanyCategory(empApplDTO.getCompanyCategory());
		emp.setUserNationalId(empApplDTO.getUserNationalId());
		emp.setCompanyTaxIdDocURL(empApplDTO.getCompanyTaxIdDocURL());
		emp.setIdCardPictureURL1(empApplDTO.getIdCardPictureURL1());
		emp.setIdCardPictureURL2(empApplDTO.getIdCardPictureURL2());
		emp.setApplicationCheck(0);

		return empApplService.updateEmpApplInfo(emp);

	}

	@GetMapping("/getEmpApplPage")
	public String GetEmpAppl(@RequestParam String employerApplicationId, @RequestParam String action) {

		if (action.equals("edit")) {
			return "usersVIEW/UpdateEmpAppl";
		} else {
			return "usersVIEW/GetEmpAppl";
		}

	}

	@GetMapping(path = "/getempAppl/{employerApplicationId}")
	@ResponseBody
	public EmpApplDTO GetUser(@PathVariable String employerApplicationId) {

		int employerApplicationId1 = Integer.valueOf(employerApplicationId);

		EmpApplDTO empAppl = new EmpApplDTO(empApplService.getEmpApplInfoByID(employerApplicationId1));
		
		return empAppl;

	}

	@PostMapping("empAppl/checkEmpAppl")
	@ResponseBody
	public String CheckEmpAppl(@RequestBody HashMap<String, String> emp, HttpSession session) {
		
		UsersDTO updateUser = (UsersDTO) session.getAttribute("CurrentUser");

		int employer_application_id = Integer.parseInt(emp.get("employer_application_id"));
		int user_id = Integer.valueOf(emp.get("user_id"));
		int check = Integer.valueOf(emp.get("check"));

		Employer_application empappl = empApplService.getEmpApplInfoByID(employer_application_id);
		Employer_profile existingProfile = empPfService.getEmpPfInfoByUserId(user_id);

		String company_name = empappl.getCompanyName();

		String company_phoneNumber = empappl.getCompanyPhoneNumber();

		String taxID = empappl.getCompanyTaxID();

		String company_address = empappl.getCompanyAddress();

		String company_category = empappl.getCompanyCategory();
		
		Employer_profile employer_profile = new Employer_profile();
		employer_profile.setUserId(user_id);
		employer_profile.setCompanyName(company_name);
		employer_profile.setCompanyAddress(company_address);
		employer_profile.setCompanyCategory(company_category);
		employer_profile.setCompanyPhoneNumber(company_phoneNumber);
		employer_profile.setCompanyTaxID(taxID);
		
		if (check == 1) {

			empApplService.updateEmpApplcheck_pass(employer_application_id,user_id);

			if (existingProfile != null) {
				existingProfile.setCompanyName(company_name);
				existingProfile.setCompanyAddress(company_address);
				existingProfile.setCompanyCategory(company_category);
				existingProfile.setCompanyPhoneNumber(company_phoneNumber);
				existingProfile.setCompanyTaxID(taxID);
				
				empPfService.updateEmpInfo(existingProfile);
						
				
			}else {
				empPfService.saveEmployerInfo(employer_profile);
			}

		} else {

			empApplService.updateEmpApplcheck_reject(employer_application_id);
		}

		return "OK";
	}

	@ResponseBody
	@GetMapping("/api/empAppl/page")
	public Page<EmpApplDTO> findByPageApi(@RequestParam Integer pageNumber,
			@RequestParam(required = false) String search) {
		Page<EmpApplDTO> page;
		if (search != null && !search.isEmpty()) {
			page = empApplService.findEmpApplByPageAndSearch(pageNumber, search);
		} else {
			page = empApplService.findEmpApplByPage(pageNumber);
		}
		return page;
	}
}

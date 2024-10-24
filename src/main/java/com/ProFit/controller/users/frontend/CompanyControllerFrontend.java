package com.ProFit.controller.users.frontend;

import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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
import com.ProFit.model.dto.usersDTO.UsersDTO;
import com.ProFit.service.userService.IEmpPfService;
import com.ProFit.service.userService.IUserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CompanyControllerFrontend {

	@Autowired
	private IEmpPfService empPfService;

	@Autowired
	private IUserService userService;
	
	@Autowired
	private ModelMapper modelMapper;

	@GetMapping("empPf/allCompany")
	public String GetAllEmpPf(Model model) {

		model.addAttribute("emps", empPfService.getAllEmpInfo());

		return "usersVIEW/AllemployerProfile";
	}

	
	@GetMapping("emp/CompProfile")
	public String GetUserPage() {

		return "usersVIEW/frontendVIEW/companyProfile";
	}
	
	@PutMapping("empPf/updateCompPf")
	@ResponseBody
	public ResponseEntity<?> UpdateComPf(@ModelAttribute EmpPfDTO empPfDTO, HttpSession session) {

	
		
		if (session.getAttribute("CurrentUser") != null) {
			
			UsersDTO userDTO = (UsersDTO) session.getAttribute("CurrentUser");
			
			Employer_profile emp = empPfService.getEmpPfInfoByUserId(userDTO.getUserId());

			emp.setEmployerProfileId(emp.getEmployerProfileId());
			emp.setCompanyCategory(empPfDTO.getCompanyCategory());
			emp.setCompanyPhoneNumber(empPfDTO.getCompanyPhoneNumber());
			emp.setCompanyNumberOfemployee(empPfDTO.getCompanyNumberOfemployee());
			emp.setCompanyCaptital(empPfDTO.getCompanyCaptital());
			emp.setCompanyDescription(empPfDTO.getCompanyDescription());
			emp.setCompanyPhotoURL(empPfDTO.getCompanyPhotoURL());
			
			Employer_profile emppf = modelMapper.map(emp, Employer_profile.class);
			
			empPfService.updateEmpInfo(emppf);

			return ResponseEntity.ok("Updated Successful");
		}else {
			
			return ResponseEntity.badRequest().body("Updated Failed");
		}

		
	}

	@GetMapping("emp/getCompPfinfo")
	public ResponseEntity<?> GetComp(HttpSession session) {

		if (session.getAttribute("CurrentUser") != null) {

			UsersDTO userDTO = (UsersDTO) session.getAttribute("CurrentUser");
			
			Employer_profile emppf = empPfService.getEmpPfInfoByUserId(userDTO.getUserId());
			
			
			
			if(emppf != null) {
				EmpPfDTO empPfDTO = modelMapper.map(emppf, EmpPfDTO.class);
				
				return ResponseEntity.ok(empPfDTO);			
			}else {
				return ResponseEntity.status(404).body("Get Company Fail!");
			}
				
		} else {
			return ResponseEntity.status(404).body("No user Fail!");
		}

	}

	@GetMapping(path = "/getCompPf/{employerProfileId}")
	@ResponseBody
	public EmpPfDTO GetEmpPf(@PathVariable String employerProfileId) {

		Integer employerProfileId1 = Integer.valueOf(employerProfileId);

		EmpPfDTO empPf = new EmpPfDTO(empPfService.getEmpPfInfoByID(employerProfileId1));

		return empPf;

	}

//	@ResponseBody
//	@GetMapping("/api/empPf/page")
//	public Page<EmpPfDTO> findByPageApi(@RequestParam Integer pageNumber,
//			@RequestParam(required = false) String search) {
//		Page<EmpPfDTO> page;
//		if (search != null && !search.isEmpty()) {
//			page = empPfService.findEmpPfByPageAndSearch(pageNumber, search);
//		} else {
//			page = empPfService.findEmpPfByPage(pageNumber);
//		}
//		return page;
//	}

}

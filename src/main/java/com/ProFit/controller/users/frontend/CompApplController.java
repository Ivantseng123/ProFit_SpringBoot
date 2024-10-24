package com.ProFit.controller.users.frontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ProFit.model.bean.usersBean.Employer_application;
import com.ProFit.model.dto.usersDTO.EmpApplDTO;
import com.ProFit.model.dto.usersDTO.UsersDTO;
import com.ProFit.service.userService.IEmpApplService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CompApplController {

	@Autowired
	private IEmpApplService empApplService;

	@PostMapping("empAppl/addEmpAppl_frontend")
	@ResponseBody
	public ResponseEntity<?> InsertEmpAppl(@ModelAttribute EmpApplDTO empApplDTO, HttpSession session) {

		if (session.getAttribute("CurrentUser") != null) {

			UsersDTO userDTO = (UsersDTO) session.getAttribute("CurrentUser");

			String address = empApplDTO.getCompanyAddress() + empApplDTO.getCompanyAddress1();

			Employer_application emp = new Employer_application();

			emp.setUserId(userDTO.getUserId());
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

			empApplService.saveEmpApplInfo(emp);
						
			return ResponseEntity.ok("Create Successful");

		} else {

			return ResponseEntity.badRequest().body("Create Failed");
		}

	}

//	@GetMapping("/getEmpApplPage")
//	public String GetEmpAppl(@RequestParam String employerApplicationId, @RequestParam String action) {
//
//		if (action.equals("edit")) {
//			return "usersVIEW/UpdateEmpAppl";
//		} else {
//			return "usersVIEW/GetEmpAppl";
//		}
//
//	}

	@GetMapping(path = "empAppl/getEmpAppl")
	@ResponseBody
	public ResponseEntity<?> GetEmpAppl(HttpSession session) {

		if (session.getAttribute("CurrentUser") != null) {

			UsersDTO updateUser = (UsersDTO) session.getAttribute("CurrentUser");

			Employer_application empAppl = empApplService.findLatestByUserId(updateUser.getUserId());

			if (empAppl != null) {

				return ResponseEntity.ok(empAppl);
			}
			return ResponseEntity.badRequest().body("Get applicatiob Failed");
		}

		return ResponseEntity.badRequest().body("Get applicatiob Failed");

	}

//	@ResponseBody
//	@GetMapping("/api/empAppl/page")
//	public Page<EmpApplDTO> findByPageApi(@RequestParam Integer pageNumber,
//			@RequestParam(required = false) String search) {
//		Page<EmpApplDTO> page;
//		if (search != null && !search.isEmpty()) {
//			page = empApplService.findEmpApplByPageAndSearch(pageNumber, search);
//		} else {
//			page = empApplService.findEmpApplByPage(pageNumber);
//		}
//		return page;
//	}
}

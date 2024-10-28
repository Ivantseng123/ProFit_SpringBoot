package com.ProFit.controller.users.frontend;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.dto.usersDTO.UsersDTO;
import com.ProFit.service.userService.IUserService;
import com.google.cloud.storage.Acl.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class usersControllerFrontend {

	@Autowired
	private IUserService userService;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping("user/profile")
	public String GetUserPage(Model model, HttpSession session) {
		UsersDTO usersDTO = (UsersDTO) session.getAttribute("CurrentUser");
		model.addAttribute("user", usersDTO);
		return "usersVIEW/frontendVIEW/memberProfile";
	}

	// 取得單筆會員
	@GetMapping(path = "user/profileinfo")
	@ResponseBody
	public ResponseEntity<?> GetUser(HttpSession session) {

		if (session.getAttribute("CurrentUser") != null) {

			UsersDTO userDTO = (UsersDTO) session.getAttribute("CurrentUser");

			return ResponseEntity.ok(userDTO);
		} else {
			return ResponseEntity.status(404).body("Get User Fail!");
		}

	}

	// 編輯個人資料
	@PutMapping(path = "users/updateProfile")
	@ResponseBody
	@Transactional
	public ResponseEntity<?> UpdateUser(@ModelAttribute UsersDTO usersDTO, HttpSession session) throws NoSuchAlgorithmException {
		
		UsersDTO currentUser = (UsersDTO) session.getAttribute("CurrentUser");
		
		System.out.println(usersDTO.toString());
		
		
		Integer userId = currentUser.getUserId();
		Integer freelancerProfileStatus = Integer.valueOf(usersDTO.getFreelancerProfileStatus());
		
		UsersDTO updateUser = modelMapper.map( userService.updateUserInfo(userId, usersDTO.getUserPictureURL(), usersDTO.getUserName(),
				usersDTO.getUserPhoneNumber(), usersDTO.getUserCity(), usersDTO.getFreelancerLocationPrefer(),
				usersDTO.getFreelancerExprience(), usersDTO.getFreelancerIdentity(), freelancerProfileStatus,
				usersDTO.getFreelancerDisc()), UsersDTO.class);
		
		updateUser.setLoginTime(currentUser.getLoginTime()); // 保持登入時間不變
		
		session.setAttribute("CurrentUser", updateUser);  // 將更新過後的會員更新到 session
		
		if (session.getAttribute("CurrentUser") != null) {
			
			return ResponseEntity.ok("Updated Successful");
		}else {
			
			return ResponseEntity.badRequest().body("Updated Failed");
		}
		
	}

	// 修改會員密碼
	@PutMapping(path = "users/updateuserpwdFrontend")
	@ResponseBody
	@Transactional
	public ResponseEntity<?> UpdateUserPwd(@RequestParam String userPassword, HttpSession session)
			throws NoSuchAlgorithmException {

		if (session.getAttribute("CurrentUser") != null) {

			UsersDTO userDTO = (UsersDTO) session.getAttribute("CurrentUser");

			userService.updateUserPwd(userPassword, userDTO.getUserId());

			return ResponseEntity.ok("Update Successful");
		}

		return ResponseEntity.badRequest().body("Update Failed");

	}
//
//	@ResponseBody
//	@GetMapping("/api/user/page")
//	public Page<UsersDTO> findByPageApi(@RequestParam Integer pageNumber,
//			@RequestParam(required = false) String search, @RequestParam(required = false) String userIdentity) {
//		Page<UsersDTO> page;
//		
//		System.out.println("會員身分-----------------------------------:  " + userIdentity);
//		
//		if (!userIdentity.isEmpty() ) {
//			
//			Integer userIdentity1 = Integer.valueOf(userIdentity);
//			page = userService.findUserByPageAndSearch(pageNumber, search, userIdentity1);
//			
//		}else {
//			
//			page = userService.findUserByPageAndSearch(pageNumber, search, null);
//		}
//		
//		
//		
//		return page;
//	}
}

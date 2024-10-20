package com.ProFit.controller.users.frontend;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

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
	public String GetUserPage() {
	
			return "usersVIEW/frontendVIEW/memberProfile";	
	}

	// 取得單筆會員
	@GetMapping(path = "user/profileinfo")
	@ResponseBody
	public ResponseEntity<?> GetUser(HttpSession session) {

		if (session.getAttribute("CurrentUser") != null) {
			
			 UsersDTO userDTO = modelMapper.map(session.getAttribute("CurrentUser"), UsersDTO.class);
			
			 return ResponseEntity.ok(userDTO);
		} else {
			return ResponseEntity.status(404).body("Get User Fail!");
		}

	}

//	// 編輯會員
//	@PutMapping(path = "users/updateuser")
//	@ResponseBody
//	@Transactional
//	public Users UpdateUser(@ModelAttribute UsersDTO usersDTO) throws NoSuchAlgorithmException {
//
//		Integer userId = Integer.valueOf(usersDTO.getUserId());
//		Integer userIdentity = Integer.valueOf(usersDTO.getUserIdentity());
//		Integer userBalance = Integer.valueOf(usersDTO.getUserBalance());
//		Integer freelancerProfileStatus = Integer.valueOf(usersDTO.getFreelancerProfileStatus());
//
//		return userService.updateUserInfo(userId, usersDTO.getUserPictureURL(), usersDTO.getUserName(),
//				usersDTO.getUserEmail(), usersDTO.getUserPasswordHash(), usersDTO.getUserPhoneNumber(),
//				usersDTO.getUserCity(), userIdentity, userBalance, usersDTO.getFreelancerLocationPrefer(),
//				usersDTO.getFreelancerExprience(), usersDTO.getFreelancerIdentity(), freelancerProfileStatus,
//				usersDTO.getFreelancerDisc(),usersDTO.getEnabled());
//	}
//
//	// 編輯會員
//	@PutMapping(path = "users/updateuserpwd")
//	@ResponseBody
//	@Transactional
//	public Users UpdateUserPwd(@RequestBody Map<String, String> user) throws NoSuchAlgorithmException {
//
//		Integer user_Id = Integer.valueOf(user.get("user_id"));
//
//		System.out.println(user);
//
//		Users existuser = userService.getUserInfoByID(user_Id);
//		if (existuser != null) {
//
//			return userService.updateUserPwd(user.get("password"), user_Id);
//		}
//
//		return null;
//	}
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

package com.ProFit.controller.users;


import java.security.NoSuchAlgorithmException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.dto.usersDTO.UsersDTO;
import com.ProFit.service.userService.IUserService;

@Controller
public class usersController {

	@Autowired
	private IUserService userService;

	// 全部會員頁面
	@GetMapping(path = "user/alluserPage")
	public String AlluserPage() {

		return "usersVIEW/Allusers";
	}

//	// 全部會員
//	@GetMapping(path = "user/alluser")
//	@ResponseBody
//	public List<UsersDTO> GetAlluser() {
//
//		List<UsersDTO> userlist = userService.getAllUserInfo().stream().map(UsersDTO::new) // 使用 DTO 的構造函數
//				.collect(Collectors.toList());
//
//		return userlist;
//	}

	// 新增會員
	@PostMapping(path = "user/adduser")
	@ResponseBody
	public String InsertUser(@RequestParam String user_name, @RequestParam String user_email,
			@RequestParam String user_password, @RequestParam String user_phonenumber, @RequestParam String user_city)
			throws NoSuchAlgorithmException {

		Users existuser = userService.getUserByEmail(user_email);
		if (existuser == null) {

			userService.saveUserInfo(user_name, user_email, user_password, user_phonenumber, user_city);
			return "新增成功";
		}

		return "信箱已註冊";
	}

	// 刪除會員
	@DeleteMapping(path = "user/deleteuser")
	@ResponseBody
	public String DeleteUser(@RequestBody Map<String, String> user) {

		int user_id = Integer.parseInt(user.get("user_id"));

		userService.deleteUserInfo(user_id);

		return "Ok";
	}

	@GetMapping("/getUserPage")
	public String GetUserPage(@RequestParam String userId, @RequestParam String action) {
		if (action.equals("view")) {
			return "usersVIEW/GetUser";
		} else {
			return "usersVIEW/UpdateUser";
		}
	}

	// 取得單筆會員
	@GetMapping(path = "/getuser/{userId}")
	@ResponseBody
	public UsersDTO GetUser(@PathVariable String userId) {

		int user_id = Integer.valueOf(userId);

		UsersDTO user = new UsersDTO(userService.getUserInfoByID(user_id));

//		System.out.println(user);
//		System.out.println(user.getUserEmail());
		return user;

	}

	// 編輯會員
	@PutMapping(path = "users/updateuser")
	@ResponseBody
	@Transactional
	public Users UpdateUser(@ModelAttribute UsersDTO usersDTO)
			throws NoSuchAlgorithmException {
				
		Integer userId = Integer.valueOf(usersDTO.getUserId());
		Integer userIdentity = Integer.valueOf(usersDTO.getUserIdentity());
		Integer userBalance = Integer.valueOf(usersDTO.getUserBalance()); 
		Integer freelancerProfileStatus = Integer.valueOf(usersDTO.getFreelancerProfileStatus());

		return userService.updateUserInfo(userId, usersDTO.getUserPictureURL(), usersDTO.getUserName(), usersDTO.getUserEmail(), usersDTO.getUserPasswordHash(),
				usersDTO.getUserPhoneNumber(), usersDTO.getUserCity(), userIdentity, userBalance, usersDTO.getFreelancerLocationPrefer(),
				usersDTO.getFreelancerExprience(), usersDTO.getFreelancerIdentity(), freelancerProfileStatus, usersDTO.getFreelancerDisc());
	}

	// 編輯會員
	@PutMapping(path = "users/updateuserpwd")
	@ResponseBody
	@Transactional
	public Users UpdateUserPwd(@RequestBody Map<String, String> user) throws NoSuchAlgorithmException {

		Integer user_Id = Integer.valueOf(user.get("user_id"));

		Users existuser = userService.getUserInfoByID(user_Id);
		if (existuser != null) {

			return userService.updateUserPwd(user.get("password"), user_Id);
		}

		return null;
	}

	@ResponseBody
	@GetMapping("/api/user/page")
	public Page<UsersDTO> findByPageApi(@RequestParam Integer pageNumber,
			@RequestParam(required = false) String search) {
		Page<UsersDTO> page;
		if (search != null && !search.isEmpty()) {
			page = userService.findUserByPageAndSearch(pageNumber, search);
		} else {
			page = userService.findUserByPage(pageNumber);
		}
		return page;
	}
}

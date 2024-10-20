package com.ProFit.controller.users.backend;

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

@Controller
public class usersController {

	@Autowired
	private IUserService userService;

	@Autowired
	private ModelMapper modelMapper;

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

	// 註冊會員頁面
	@GetMapping(path = "user/registerPage")
	public String RegisterUser() {

		return "usersVIEW/RegisterForm";
	}

	//註冊會員 
	//@ModelAttribute，Spring從表單數據中提取字段並自動映射到 DTO 中的對應字段。
	@PostMapping(path = "user/register")
	@ResponseBody
	public ResponseEntity<?> RegisterUser(@ModelAttribute UsersDTO usersDTO) throws NoSuchAlgorithmException {

		Users existuser = userService.getUserByEmail(usersDTO.getUserEmail());
		if (existuser == null) {

			Users user = modelMapper.map(usersDTO, Users.class);

			return userService.registerUser(user);
		}

		return ResponseEntity.badRequest().body("信箱已註冊");
	}
	
	@RequestMapping(value="user/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
	public String confirmUserAccount(@RequestParam("token") String confirmationToken) {
		
		if (userService.confirmEmail(confirmationToken)) {
			return "redirect:/user/verifiedSuccess";
		}
		
		return null;
	}
	
	@GetMapping(path = "user/verifiedSuccess")
	public String verifiedSuccessPage() {

		return "usersVIEW/frontendVIEW/mail-success";
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
	public Users UpdateUser(@ModelAttribute UsersDTO usersDTO) throws NoSuchAlgorithmException {

		Integer userId = Integer.valueOf(usersDTO.getUserId());
		Integer userIdentity = Integer.valueOf(usersDTO.getUserIdentity());
		Integer userBalance = Integer.valueOf(usersDTO.getUserBalance());
		Integer freelancerProfileStatus = Integer.valueOf(usersDTO.getFreelancerProfileStatus());

		return userService.updateUserInfo(userId, usersDTO.getUserPictureURL(), usersDTO.getUserName(),
				usersDTO.getUserEmail(), usersDTO.getUserPasswordHash(), usersDTO.getUserPhoneNumber(),
				usersDTO.getUserCity(), userIdentity, userBalance, usersDTO.getFreelancerLocationPrefer(),
				usersDTO.getFreelancerExprience(), usersDTO.getFreelancerIdentity(), freelancerProfileStatus,
				usersDTO.getFreelancerDisc(),usersDTO.getEnabled());
	}

	// 編輯會員
	@PutMapping(path = "users/updateuserpwd")
	@ResponseBody
	@Transactional
	public Users UpdateUserPwd(@RequestBody Map<String, String> user) throws NoSuchAlgorithmException {

		Integer user_Id = Integer.valueOf(user.get("user_id"));

		System.out.println(user);

		Users existuser = userService.getUserInfoByID(user_Id);
		if (existuser != null) {

			return userService.updateUserPwd(user.get("password"), user_Id);
		}

		return null;
	}

	@ResponseBody
	@GetMapping("/api/user/page")
	public Page<UsersDTO> findByPageApi(@RequestParam Integer pageNumber,
			@RequestParam(required = false) String search, @RequestParam(required = false) String userIdentity) {
		Page<UsersDTO> page;
		
		System.out.println("會員身分-----------------------------------:  " + userIdentity);
		
		if (!userIdentity.isEmpty() ) {
			
			Integer userIdentity1 = Integer.valueOf(userIdentity);
			page = userService.findUserByPageAndSearch(pageNumber, search, userIdentity1);
			
		}else {
			
			page = userService.findUserByPageAndSearch(pageNumber, search, null);
		}
		
		
		
		return page;
	}
}

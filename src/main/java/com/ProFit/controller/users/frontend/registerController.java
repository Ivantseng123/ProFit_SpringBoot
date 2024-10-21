package com.ProFit.controller.users.frontend;

import java.security.NoSuchAlgorithmException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.dto.usersDTO.UsersDTO;
import com.ProFit.service.userService.IUserService;

@Controller
public class registerController {

	@Autowired
	private IUserService userService;

	@Autowired
	private ModelMapper modelMapper;

	// 註冊會員頁面
	@GetMapping(path = "user/registerPage")
	public String RegisterUser() {

		return "usersVIEW/RegisterForm";
	}

	// 註冊會員
	// @ModelAttribute，Spring從表單數據中提取字段並自動映射到 DTO 中的對應字段。
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

	@RequestMapping(value = "user/confirm-account", method = { RequestMethod.GET, RequestMethod.POST })
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
}

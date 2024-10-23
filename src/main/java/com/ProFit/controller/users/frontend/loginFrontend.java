package com.ProFit.controller.users.frontend;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.dto.usersDTO.UsersDTO;
import com.ProFit.service.userService.IUserService;

import ch.qos.logback.classic.Logger;
import jakarta.servlet.http.HttpSession;

@Controller
public class loginFrontend {

	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("/login_frontend")
	@ResponseBody
	public ResponseEntity<?> Login_frontend(@RequestBody Map<String, String> user, HttpSession session)
			throws NoSuchAlgorithmException {

		System.out.println(user);

		if (userService.validateForfrontend(user.get("userEmail"), user.get("userPassword"))) {

			Users user1 = userService.getUserByEmail(user.get("userEmail"));
					
			UsersDTO userDTO = modelMapper.map(user1, UsersDTO.class);
			
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");		
			String loginTime = now.format(dateFormatter);
			
			userDTO.setLoginTime(loginTime);
			
			System.out.println("登入時間-------------------" + userDTO.getLoginTime());
			
			System.out.println("登入成功");

			session.setAttribute("CurrentUser", userDTO);

			return ResponseEntity.ok("Login Successful");

		} else {
			System.out.println("登入失敗");
			return ResponseEntity.status(404).body("Login Failed");
		}

	}

	@GetMapping("login/checklogin")
	public ResponseEntity<?> getSessionAttributeFrontend(HttpSession session) {

		if (session.getAttribute("CurrentUser") != null) {
			return ResponseEntity.ok("Login status: TRUE");
		} else {
			return ResponseEntity.status(404).body("Login status: False");
		}
	}

	@GetMapping("/logout_frontend")
	public ResponseEntity<?> LogoutFrontend(HttpSession session) {

		if (session.getAttribute("CurrentUser") != null) {
			session.invalidate();
			System.out.println("登出成功");
			return ResponseEntity.ok("登出成功");
		}
		return ResponseEntity.status(404).body("登出失敗");
	}

}

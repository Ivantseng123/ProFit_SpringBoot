package com.ProFit.controller.users;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.dao.usersCRUD.UsersRepository;
import com.ProFit.service.userService.IUserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class loginController {

	@Autowired
	private IUserService userService;
	

	@GetMapping("/loginPage")
	public String LoginPage() {
		return "usersVIEW/Login";
	}

	@PostMapping("/login")
	@ResponseBody
	public String Login(@RequestBody Map<String, String> user, HttpSession session) throws NoSuchAlgorithmException {
		
		System.out.println(user);
		
		if (userService.validate(user.get("userEmail"), user.get("userPassword"))) {
			
			Users user1 = userService.getUserByEmail(user.get("userEmail"));
			
			String user_pictureURL = user1.getUserPictureURL();	
			Integer user_identity = user1.getUserIdentity();
			String user_name = user1.getUserName();
			
			System.out.println("登入成功");
			
			
			session.setAttribute("user_email", user.get("userEmail"));
			session.setAttribute("user_name", user_name);
			session.setAttribute("user_pictureURL", user_pictureURL);
			session.setAttribute("user_identity", user_identity);
			
			
			return "Login Successful";

		} else {
			System.out.println("登入失敗");
			return "Login Failed";
		}

	}
	
	@PostMapping("/login_frontend")
	@ResponseBody
	public String Login_frontend(@RequestBody Map<String, String> user, HttpSession session) throws NoSuchAlgorithmException {
		
		System.out.println(user);
		
		if (userService.validate(user.get("userEmail"), user.get("userPassword"))) {
			
			Users user1 = userService.getUserByEmail(user.get("userEmail"));
			
			String user_pictureURL = user1.getUserPictureURL();	
			Integer user_identity = user1.getUserIdentity();
			String user_name = user1.getUserName();
			
			System.out.println("登入成功");
			
			
			session.setAttribute("user_email", user.get("userEmail"));
			session.setAttribute("user_name", user_name);
			session.setAttribute("user_pictureURL", user_pictureURL);
			session.setAttribute("user_identity", user_identity);
			
			
			return "Login Successful";

		} else {
			System.out.println("登入失敗");
			return "Login Failed";
		}

	}

	@GetMapping("login/getUserSession")
	public ResponseEntity<Map<String, String>> getSessionAttribute(HttpSession session) {

		String user_email = (String) session.getAttribute("user_email");
		String user_name = (String) session.getAttribute("user_name");
		String user_pictureURL = (String) session.getAttribute("user_pictureURL");
		String user_identity = String.valueOf(session.getAttribute("user_identity"));
		if (user_email != null) {
			Map<String, String> response = new HashMap<>();
			response.put("userEmail", user_email);
			response.put("userName", user_name);
			response.put("userPictureURL", user_pictureURL);
			response.put("userIdentity", user_identity);
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.status(404).body(null);
		}
	}

	@GetMapping("/logout")
	public String Logout(HttpSession session) {

		if (session.getAttribute("user_email") != null) {
			session.invalidate();
		}
		return "redirect:/loginPage";

	}

}

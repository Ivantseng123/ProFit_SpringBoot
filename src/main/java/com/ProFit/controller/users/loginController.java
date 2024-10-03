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

		if (userService.validate(user.get("userEmail"), user.get("userPassword"))) {

			String user_pictureURL = userService.getUserPictureByEmail(user.get("userEmail"));
			System.out.println("登入成功");
			session.setAttribute("user_email", user.get("userEmail"));

			session.setAttribute("user_pictureURL", user_pictureURL);

			return "Login Successful";

		} else {
			System.out.println("登入失敗");
			return "Login Failed";
		}

	}

	@GetMapping("login/getUserSession")
	public ResponseEntity<Map<String, String>> getSessionAttribute(HttpSession session) {

		String user_email = (String) session.getAttribute("user_email");
		String user_pictureURL = (String) session.getAttribute("user_pictureURL");
		if (user_email != null) {
			Map<String, String> response = new HashMap<>();
			response.put("userEmail", user_email);
			response.put("userPictureURL", user_pictureURL);
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

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
	public ResponseEntity<?> Login_frontend(@RequestBody Map<String, String> user, HttpSession session)
			throws NoSuchAlgorithmException {

		System.out.println(user);

		if (userService.validateForfrontend(user.get("userEmail"), user.get("userPassword"))) {

			Users user1 = userService.getUserByEmail(user.get("userEmail"));

			System.out.println("登入成功");

			session.setAttribute("CurrentUser", user1);

			return ResponseEntity.ok("Login Successful");

		} else {
			System.out.println("登入失敗");
			return ResponseEntity.status(404).body("Login Failed");
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
	
	@GetMapping("login/getUserSession_frontend")
	public ResponseEntity<Users> getSessionAttributeFrontend(HttpSession session) {

		
	
		if (session.getAttribute("CurrentUser") != null) {
			Users user = (Users) session.getAttribute("CurrentUser");
			return ResponseEntity.ok(user);
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
	
	@GetMapping("/logout_frontend")
	public ResponseEntity<?> LogoutFrontend(HttpSession session) {

		if (session.getAttribute("CurrentUser") != null) {
			session.invalidate();
			System.out.println("登出成功");
			return ResponseEntity.ok("登出成功");
		}
		return  ResponseEntity.status(404).body("登出失敗");
	}

}

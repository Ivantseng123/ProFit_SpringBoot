package com.ProFit.controller.users.frontend;

import java.security.NoSuchAlgorithmException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ProFit.model.bean.usersBean.Pwd_reset_tokens;
import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.service.userService.IPwdresetService;
import com.ProFit.service.userService.IUserService;

@Controller
public class PwdresetControllerFrontend {

	@Autowired
	private IPwdresetService pwdresetService;
		
	@Autowired
	private PasswordEncoder pwdEncoder;

	@Autowired
	private IUserService userService;

	@PostMapping("tokens/addToken_frontend")
	@ResponseBody
	public ResponseEntity<?> insertToken(@RequestBody Map<String, String> emailData) throws NoSuchAlgorithmException {

		String email = emailData.get("email");

		Users user = userService.getUserByEmail(email);

		if (user != null) {
			Integer userId = Integer.valueOf(user.getUserId());

			Pwd_reset_tokens tokens = new Pwd_reset_tokens();
			
			tokens.setUserId(userId);
			
			String token = pwdresetService.generateToken();
			
			tokens.setUserTokenHash(pwdEncoder.encode(token));		

			pwdresetService.saveTokensInfo(tokens);
			
			userService.sendResetToken(email, token);
			
			return ResponseEntity.ok("Send Successful");
			
		}

		return ResponseEntity.badRequest().body("Send Failed");
	}
	
	@PostMapping(value = "user/confirm-resetToken")
	@Transactional
	public ResponseEntity<?> confirmResetToken(@RequestBody Map<String, String> pwdData) throws NoSuchAlgorithmException {
		
		Integer userId = Integer.valueOf(pwdData.get("userId"));
		String confirmationToken = pwdData.get("token");
		
		System.out.println(pwdData);
		
		if (userService.confirm_resetToken(userId,confirmationToken)) {
			
			String password = pwdData.get("password");
			
			userService.updateUserPwd(password, userId);
			
			return ResponseEntity.ok("Reset Successful");
		}

		return ResponseEntity.ok("Reset Failed");
	}
	
	@GetMapping("user/resetPwd")
	public String getMethodName() {
		
		return "usersVIEW/frontendVIEW/resetPwd";
	}
	

}

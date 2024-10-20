package com.ProFit.controller.users.backend;

import java.security.NoSuchAlgorithmException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ProFit.model.bean.usersBean.Pwd_reset_tokens;
import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.dto.usersDTO.TokensDTO;
import com.ProFit.model.dto.usersDTO.UsersDTO;
import com.ProFit.service.userService.IPwdresetService;
import com.ProFit.service.userService.IUserService;

@Controller
public class pwdresetController {

	@Autowired
	private IPwdresetService pwdresetService;

	@Autowired
	private IUserService userService;

	@GetMapping("tokens/alltokenPage")
	public String getAlltoken() {

		return "usersVIEW/AllresetTokens";

	}

	@PostMapping("tokens/addToken")
	@ResponseBody
	public String insertToken(@RequestParam String user_id) throws NoSuchAlgorithmException {

		Integer userId = Integer.valueOf(user_id);
		Pwd_reset_tokens tokens = new Pwd_reset_tokens();
		tokens.setUserId(userId);
		tokens.setUserTokenHash(pwdresetService.generateToken());

		Users existuser = userService.getUserInfoByID(userId);
		if (existuser != null) {

			pwdresetService.saveTokensInfo(tokens);

			return "新增成功";
		}

		return "新增失敗";
	}

	@PostMapping("tokens/deletetoken")
	@ResponseBody
	public String deleteToken(@RequestBody Map<String, String> token) throws NoSuchAlgorithmException {

		Integer tokenId = Integer.valueOf(token.get("token_id"));

		pwdresetService.deleteTokensInfo(tokenId);

		return "Ok";
	}
	
	@ResponseBody
	@GetMapping("/api/token/page")
	public Page<TokensDTO> findByPageApi(@RequestParam Integer pageNumber,
			@RequestParam(required = false) String search) {
		Page<TokensDTO> page;
		if (search != null && !search.isEmpty()) {
			page = pwdresetService.findTokenByPageAndSearch(pageNumber, search);
		} else {
			page = pwdresetService.findTokenByPage(pageNumber);
		}
		return page;
	}
	
}

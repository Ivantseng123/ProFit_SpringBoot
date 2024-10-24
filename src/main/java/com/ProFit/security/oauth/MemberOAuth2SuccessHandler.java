package com.ProFit.security.oauth;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.dao.usersCRUD.UsersRepository;
import com.ProFit.model.dto.usersDTO.UsersDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class MemberOAuth2SuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private UsersRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		// 獲取 OAuth2User
		OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

		HttpSession session = request.getSession();

		Map<String, Object> attributes = oAuth2User.getAttributes();

		String email = (String) attributes.get("email");

		Optional<Users> optional = userRepository.findByUserEmail(email);

		if (optional.isPresent()) {
			Users user = optional.get();

			UsersDTO userDTO = modelMapper.map(user, UsersDTO.class);

			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String loginTime = now.format(dateFormatter);

			userDTO.setLoginTime(loginTime);

			System.out.println("登入時間-------------------" + userDTO.getLoginTime());

			System.out.println("登入成功");

			session.setAttribute("CurrentUser", userDTO);

			System.out.println("登入時間-------------------" + userDTO.getLoginTime());

		}

		response.sendRedirect(request.getContextPath() + "/homepage");

	}

}

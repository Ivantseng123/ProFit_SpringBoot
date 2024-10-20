package com.ProFit.security.oauth;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.dao.usersCRUD.UsersRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class MemberOAuth2SuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private UsersRepository userRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		// 獲取 OAuth2User
		OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

		HttpSession session = request.getSession();

		Map<String, Object> attributes = oAuth2User.getAttributes();

		String googleId = (String) attributes.get("sub");
		String email = (String) attributes.get("email");

		Optional<Users> optional = userRepository.findByUserEmail(email);

		if (optional.isPresent()) {
			session.setAttribute("googleId", googleId);
			session.setAttribute("CurrentUser", optional.get());
		}

		response.sendRedirect(request.getContextPath() + "/homepage");

	}

}

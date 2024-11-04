package com.ProFit.controller.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ProFit.model.dto.usersDTO.UsersDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class LoginInterceptor_frontend implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HttpSession session = request.getSession();

		UsersDTO currentUser = (UsersDTO) session.getAttribute("CurrentUser");

		if (currentUser == null) {
			response.sendRedirect(request.getContextPath() + "/homepage?login=false");
			return false;
		}

		request.setAttribute("CurrentUser", currentUser);

		return true; // 返回 true 繼續請求
	}
}

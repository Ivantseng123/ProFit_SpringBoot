//package com.ProFit.security.oauth;
//
//import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
//import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
//import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
//import org.springframework.stereotype.Component;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;
//
//@Component
//public class MemberAuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {
//
//	private final AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository;
//
//	public MemberAuthorizationRequestResolver(
//			AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository) {
//		this.authorizationRequestRepository = authorizationRequestRepository;
//	}
//
//	@Override
//	public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
//
//		OAuth2AuthorizationRequest authorizationRequest = authorizationRequestRepository
//				.loadAuthorizationRequest(request);
//
//		// 如果用户已經授權過，添加 prompt=none
//		if (isUserAlreadyAuthorized(request)) {
//			String redirectUri = authorizationRequest.getRedirectUri();
//			return OAuth2AuthorizationRequest.from(authorizationRequest).redirectUri(redirectUri) // 添加 redirectUri
//					.additionalParameters(params -> params.put("prompt", "none")) // 添加 prompt 参数
//					.build();
//		}
//
//		return authorizationRequest;
//	}
//
//	@Override
//	public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	private boolean isUserAlreadyAuthorized(HttpServletRequest request) {
//		HttpSession session = request.getSession(false);
//		return session != null && session.getAttribute("googleId") != null;
//	}
//
//}

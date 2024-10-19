package com.ProFit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.SecurityFilterChain;
import com.ProFit.security.oauth.MemberOAuth2SuccessHandler;
import com.ProFit.security.oauth.MemeberOAuth2UserService;
import com.ProFit.security.oauth.MemeberUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private MemeberUserDetailsService customUserDetailsService;

	@Autowired
	private MemeberOAuth2UserService memberOAuth2UserService;

	@Autowired
	private MemberOAuth2SuccessHandler memberOAuth2SuccessHandler;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http
		) throws Exception {

//		return http.authorizeHttpRequests(registry -> {
//			registry.requestMatchers("/").permitAll();
//			registry.anyRequest().authenticated();
//		}).oauth2Login(oauth2login -> {
//			oauth2login.loginPage("/login")
//					.successHandler((request, response, authentication) -> response.sendRedirect("/home"));
//		}).build();

		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(authorize -> authorize.requestMatchers("/**").permitAll())
				.oauth2Login(oauth2 -> oauth2.loginPage("/oauth2/authorization/google") // 使用 Google OAuth2 登入
						.userInfoEndpoint(userInfo -> userInfo.userService(memberOAuth2UserService) // 使用自定義的OAuth2UserService
						).successHandler(memberOAuth2SuccessHandler)); // 使用自定義的成功處理

//		http.csrf(csrf -> csrf.disable())
//				.authorizeHttpRequests(authorize -> authorize.requestMatchers("/loginPage").permitAll() // 目前允許所有請求
//						.requestMatchers("/").hasAuthority("ROLE_ADMIN").anyRequest().authenticated())
//				.formLogin(login -> login.loginPage("/loginPage").usernameParameter("userEmail")
//						.defaultSuccessUrl("/", true).permitAll())
//				.logout(logout -> logout.logoutUrl("/logout").permitAll());

		return http.build();
	}

	// 忽略靜態資源的請求
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {

		return (web) -> web.ignoring().requestMatchers("/css/**", "/js/**", "/images/**", "/fonts/**");
	}

	@Bean
	UserDetailsService userDetailsService() {
		return new MemeberUserDetailsService();
	}

	@Bean
	AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder) {

		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder); // 用來比對密碼

		return new ProviderManager(authProvider);
	}
}

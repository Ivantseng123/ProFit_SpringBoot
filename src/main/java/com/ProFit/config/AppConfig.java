package com.ProFit.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.ProFit.security.oauth.MemeberUserDetailsService;

@Configuration
@EnableWebSecurity
public class AppConfig {

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Autowired
	private MemeberUserDetailsService customUserDetailsService;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

//		return http.authorizeHttpRequests(registry -> {
//			registry.requestMatchers("/").permitAll();
//			registry.anyRequest().authenticated();
//		}).oauth2Login(oauth2login -> {
//			oauth2login.loginPage("/login")
//					.successHandler((request, response, authentication) -> response.sendRedirect("/home"));
//		}).build();

		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(authorize -> authorize.requestMatchers("/**").permitAll()); // 目前允許所有請求

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

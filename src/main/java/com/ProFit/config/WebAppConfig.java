package com.ProFit.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import com.ProFit.controller.interceptor.LoginInterceptor;
import com.ProFit.controller.interceptor.LoginInterceptor_frontend;

//相當於mvc-servlet.xml的Java程式組態
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 保持默認的 /static/ 路徑映射
		registry.addResourceHandler("/**")
				.addResourceLocations("classpath:/static/");

		// 自定義 /ProFit/photos 路徑，映射到外部的 photos 資料夾
		registry.addResourceHandler("/ProFit/photos/**")
				.addResourceLocations("file:/Users/ivantseng/Desktop/photos/");
	}

	@Autowired
	private LoginInterceptor loginInterceptor;

	@Autowired
	private LoginInterceptor_frontend loginInterceptor_frontend;

	List<String> pathPatterns_backend = List.of(
			"/",
			"/user/alluserPage",
			"/user/adduser",
			"/user/deleteuser",
			"/getUserPage",
			"/getuser/",
			"/users/updateuser",
			"/users/updateuserpwd",
			"/api/user/page",
			"/user/charts",
			"/user/statistics",
			"/user/registration_statistics",
			"/tokens/alltokenPage",
			"/tokens/addToken",
			"/tokens/deletetoken",
			"/api/token/page",
			"/download/logs",
			"/empPf/allempPf",
			"/empPf/addEmpPf",
			"/empPf/deleteEmpPf",
			"/empPf/updateEmpPf",
			"/getEmpPfPage",
			"/getempPf/",
			"/api/empPf/page",
			"/empAppls/allEmpAppl",
			"/empAppl/addEmpAppl",
			"/empAppl/deleteEmpAppl",
			"/empAppl/updateEmpAppl",
			"/getEmpApplPage",
			"/getempAppl/",
			"/empAppl/checkEmpAppl",
			"/api/empAppl/page",
			"/b/courses/");

	List<String> pathPatterns_frontend = List.of(
			"/user/profile",
			"/user/profileinfo",
			"/users/updateProfile",
			"/users/updateuserpwdFrontend",
			"/user/customerService",
			"/user/sendEmail",
			"/empAppl/addEmpAppl_frontend",
			"/empAppl/getEmpAppl",
			"/empPf/updateCompPf",
			"/emp/getCompPfinfo",
			"/c/course/");

	// @Override
	// public void addInterceptors(InterceptorRegistry registry) {
	// registry.addInterceptor(loginInterceptor).addPathPatterns(pathPatterns_backend);
	// //.excludePathPatterns("/loginPage","/login","/js/users/Login.js"); // 排除登入頁面
	//
	// registry.addInterceptor(loginInterceptor_frontend).addPathPatterns(pathPatterns_frontend);
	// //.excludePathPatterns("/homepage","/login_frontend","/js/users/frontend_login.js");
	// // 排除登入頁面
	// }

}
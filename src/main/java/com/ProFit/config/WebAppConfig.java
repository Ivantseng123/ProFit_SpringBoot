package com.ProFit.config;

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

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginInterceptor).addPathPatterns("/**") // 過濾所有請求
				.excludePathPatterns("/loginPage","/login"); // 排除登入頁面
	}
}
package com.ProFit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource({"classpath:db-secrets.properties","classpath:OAuth2-Credential.properties"})
public class ProFitApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProFitApplication.class, args);
	}

}

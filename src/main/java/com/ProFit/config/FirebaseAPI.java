package com.ProFit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;



@Configuration
@PropertySource("classpath:firebaseAPIkey.properties")
public class FirebaseAPI {
	
	@Value("${firbaseAPIkey}")
	private String apiKey;
	
	@Value("${authDomain}")
	private String authDomain;
	
	@Value("${projectId}")
	private String projectId;
	
	@Value("${storageBucket}")
	private String storageBucket;
	
	@Value("${messagingSenderId}")
	private String messagingSenderId;
	
	@Value("${appId}")
	private String appId;
	
	@Value("${measurementId}")
	private String measurementId;
	
	
	public String getStorageBucket() {
		return storageBucket;
	}

	public void setStorageBucket(String storageBucket) {
		this.storageBucket = storageBucket;
	}

	public String getAuthDomain() {
		return authDomain;
	}

	public void setAuthDomain(String authDomain) {
		this.authDomain = authDomain;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getMessagingSenderId() {
		return messagingSenderId;
	}

	public void setMessagingSenderId(String messagingSenderId) {
		this.messagingSenderId = messagingSenderId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getMeasurementId() {
		return measurementId;
	}

	public void setMeasurementId(String measurementId) {
		this.measurementId = measurementId;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}


	
}

package com.ProFit.model.dto.servicesDTO;

import java.time.LocalDateTime;

import com.ProFit.model.bean.servicesBean.ServiceBean;
import com.ProFit.model.dto.majorsDTO.UserMajorDTO;

public class ServicesDTO {

	private Integer serviceId;
	private UserMajorDTO userMajor;
	private String serviceTitle;
	private String serviceContent;
	private Integer servicePrice;
	private String serviceUnitName;
	private Double serviceDuration;
	private LocalDateTime serviceCreateDate;
	private LocalDateTime serviceUpdateDate;
	private String servicePictureURL1;
	private String servicePictureURL2;
	private String servicePictureURL3;
	private Integer serviceStatus;

	public static ServicesDTO fromEntity(ServiceBean serviceBean) {
		if (serviceBean == null) return null;
		
		ServicesDTO dto = new ServicesDTO();
		dto.setServiceId(serviceBean.getServiceId());
		dto.setUserMajor(UserMajorDTO.fromEntity(serviceBean.getUserMajor()));
		dto.setServiceTitle(serviceBean.getServiceTitle());
		dto.setServiceContent(serviceBean.getServiceContent());
		dto.setServicePrice(serviceBean.getServicePrice());
		dto.setServiceUnitName(serviceBean.getServiceUnitName());
		dto.setServiceDuration(serviceBean.getServiceDuration());
		dto.setServiceCreateDate(serviceBean.getServiceCreateDate());
		dto.setServiceUpdateDate(serviceBean.getServiceUpdateDate());
		dto.setServicePictureURL1(serviceBean.getServicePictureURL1());
		dto.setServicePictureURL2(serviceBean.getServicePictureURL2());
		dto.setServicePictureURL3(serviceBean.getServicePictureURL3());
		dto.setServiceStatus(serviceBean.getServiceStatus());
		return dto;
	}
	

	public ServicesDTO() {
	}

	public ServicesDTO(Integer serviceId, UserMajorDTO userMajor, String serviceTitle, String serviceContent,
			Integer servicePrice, String serviceUnitName, Double serviceDuration, LocalDateTime serviceCreateDate,
			LocalDateTime serviceUpdateDate, String servicePictureURL1, String servicePictureURL2,
			String servicePictureURL3, Integer serviceStatus) {
		super();
		this.serviceId = serviceId;
		this.userMajor = userMajor;
		this.serviceTitle = serviceTitle;
		this.serviceContent = serviceContent;
		this.servicePrice = servicePrice;
		this.serviceUnitName = serviceUnitName;
		this.serviceDuration = serviceDuration;
		this.serviceCreateDate = serviceCreateDate;
		this.serviceUpdateDate = serviceUpdateDate;
		this.servicePictureURL1 = servicePictureURL1;
		this.servicePictureURL2 = servicePictureURL2;
		this.servicePictureURL3 = servicePictureURL3;
		this.serviceStatus = serviceStatus;
	}

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public UserMajorDTO getUserMajor() {
		return userMajor;
	}

	public void setUserMajor(UserMajorDTO userMajor) {
		this.userMajor = userMajor;
	}

	public String getServiceTitle() {
		return serviceTitle;
	}

	public void setServiceTitle(String serviceTitle) {
		this.serviceTitle = serviceTitle;
	}

	public String getServiceContent() {
		return serviceContent;
	}

	public void setServiceContent(String serviceContent) {
		this.serviceContent = serviceContent;
	}

	public Integer getServicePrice() {
		return servicePrice;
	}

	public void setServicePrice(Integer servicePrice) {
		this.servicePrice = servicePrice;
	}

	public String getServiceUnitName() {
		return serviceUnitName;
	}

	public void setServiceUnitName(String serviceUnitName) {
		this.serviceUnitName = serviceUnitName;
	}

	public Double getServiceDuration() {
		return serviceDuration;
	}

	public void setServiceDuration(Double serviceDuration) {
		this.serviceDuration = serviceDuration;
	}

	public LocalDateTime getServiceCreateDate() {
		return serviceCreateDate;
	}

	public void setServiceCreateDate(LocalDateTime serviceCreateDate) {
		this.serviceCreateDate = serviceCreateDate;
	}

	public LocalDateTime getServiceUpdateDate() {
		return serviceUpdateDate;
	}

	public void setServiceUpdateDate(LocalDateTime serviceUpdateDate) {
		this.serviceUpdateDate = serviceUpdateDate;
	}

	public String getServicePictureURL1() {
		return servicePictureURL1;
	}

	public void setServicePictureURL1(String servicePictureURL1) {
		this.servicePictureURL1 = servicePictureURL1;
	}

	public String getServicePictureURL2() {
		return servicePictureURL2;
	}

	public void setServicePictureURL2(String servicePictureURL2) {
		this.servicePictureURL2 = servicePictureURL2;
	}

	public String getServicePictureURL3() {
		return servicePictureURL3;
	}

	public void setServicePictureURL3(String servicePictureURL3) {
		this.servicePictureURL3 = servicePictureURL3;
	}

	public Integer getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(Integer serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	@Override
	public String toString() {
		return "servicesDTO [serviceId=" + serviceId + ", userMajor=" + userMajor + ", serviceTitle=" + serviceTitle
				+ ", serviceContent=" + serviceContent + ", servicePrice=" + servicePrice + ", serviceUnitName="
				+ serviceUnitName + ", serviceDuration=" + serviceDuration + ", serviceCreateDate=" + serviceCreateDate
				+ ", serviceUpdateDate=" + serviceUpdateDate + ", servicePictureURL1=" + servicePictureURL1
				+ ", servicePictureURL2=" + servicePictureURL2 + ", servicePictureURL3=" + servicePictureURL3
				+ ", serviceStatus=" + serviceStatus + "]";
	}

	
}

package com.ProFit.model.dto.usersDTO;

import com.ProFit.model.bean.usersBean.Employer_profile;
import com.ProFit.model.bean.usersBean.Users;

public class EmpPfDTO {
	
	private Integer employerProfileId;
	
	private Users user;
	
	private Integer userId;
	
	private String userEmail;
	
	private String companyName;
	
	private String companyAddress;
	
	private String companyAddress1;
	
	private String companyCategory;
	
	private String companyPhoneNumber;
		
	private String companyTaxID;
	
	private String companyNumberOfemployee;
	
	private String companyCaptital;
	
	private String companyDescription;
	
	private String companyPhotoURL;


	public EmpPfDTO(Employer_profile emp) {
		super();
		this.employerProfileId = emp.getEmployerProfileId();
		this.userId = emp.getUserId();
		this.userEmail = emp.getUser().getUserEmail();
		this.companyName = emp.getCompanyName();
		this.companyAddress = emp.getCompanyAddress();
		this.companyCategory = emp.getCompanyCategory();
		this.companyPhoneNumber = emp.getCompanyPhoneNumber();
		this.companyTaxID = emp.getCompanyTaxID();
		this.companyNumberOfemployee = emp.getCompanyNumberOfemployee();
		this.companyCaptital = emp.getCompanyCaptital();
		this.companyDescription = emp.getCompanyDescription();
		this.companyPhotoURL = emp.getCompanyPhotoURL();
	}

	

	public EmpPfDTO(Integer user_id, String company_name, String company_address,
			String company_category, String company_phoneNumber, String company_taxID) {
		super();
		this.userId = user_id;
		this.companyName = company_name;
		this.companyAddress = company_address;
		this.companyCategory = company_category;
		this.companyPhoneNumber = company_phoneNumber;
		this.companyTaxID = company_taxID;
	}

	public EmpPfDTO() {}



	public Integer getEmployerProfileId() {
		return employerProfileId;
	}



	public void setEmployerProfileId(Integer employerProfileId) {
		this.employerProfileId = employerProfileId;
	}



	public Users getUser() {
		return user;
	}



	public void setUser(Users user) {
		this.user = user;
	}



	public Integer getUserId() {
		return userId;
	}



	public void setUserId(Integer userId) {
		this.userId = userId;
	}



	public String getCompanyName() {
		return companyName;
	}



	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}



	public String getCompanyAddress() {
		return companyAddress;
	}



	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}



	public String getCompanyCategory() {
		return companyCategory;
	}



	public void setCompanyCategory(String companyCategory) {
		this.companyCategory = companyCategory;
	}



	public String getCompanyPhoneNumber() {
		return companyPhoneNumber;
	}



	public void setCompanyPhoneNumber(String companyPhoneNumber) {
		this.companyPhoneNumber = companyPhoneNumber;
	}



	public String getCompanyTaxID() {
		return companyTaxID;
	}



	public void setCompanyTaxID(String companyTaxID) {
		this.companyTaxID = companyTaxID;
	}



	public String getCompanyNumberOfemployee() {
		return companyNumberOfemployee;
	}



	public void setCompanyNumberOfemployee(String companyNumberOfemployee) {
		this.companyNumberOfemployee = companyNumberOfemployee;
	}



	public String getCompanyCaptital() {
		return companyCaptital;
	}



	public void setCompanyCaptital(String companyCaptital) {
		this.companyCaptital = companyCaptital;
	}



	public String getCompanyDescription() {
		return companyDescription;
	}



	public void setCompanyDescription(String companyDescription) {
		this.companyDescription = companyDescription;
	}



	public String getCompanyPhotoURL() {
		return companyPhotoURL;
	}


	public void setCompanyPhotoURL(String companyPhotoURL) {
		this.companyPhotoURL = companyPhotoURL;
	}
	
	public String getCompanyAddress1() {
		return companyAddress1;
	}


	public void setCompanyAddress1(String companyAddress1) {
		this.companyAddress1 = companyAddress1;
	}



	public String getUserEmail() {
		return userEmail;
	}



	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

}

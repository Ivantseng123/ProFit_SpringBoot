package com.ProFit.model.dto.usersDTO;

import com.ProFit.model.bean.usersBean.Employer_application;
import com.ProFit.model.bean.usersBean.Users;

public class EmpApplDTO {

	private Integer employerApplicationId;

	private Users user;

	private Integer userId;

	private String userEmail;

	private String companyName;

	private String companyAddress;

	private String companyAddress1;

	private String companyCategory;

	private String companyPhoneNumber;

	private String companyTaxID;

	private String companyTaxIdDocURL;

	private String userNationalId;

	private String idCardPictureURL1;

	private String idCardPictureURL2;

	private Integer applicationCheck;

	public EmpApplDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmpApplDTO(Employer_application emp) {
		super();
		this.employerApplicationId = emp.getEmployerApplicationId();
		this.userId = emp.getUserId();
		this.userEmail = emp.getUser().getUserEmail();
		this.companyName = emp.getCompanyName();
		this.companyAddress = emp.getCompanyAddress();
		this.companyCategory = emp.getCompanyCategory();
		this.companyPhoneNumber = emp.getCompanyPhoneNumber();
		this.companyTaxID = emp.getCompanyTaxID();
		this.companyTaxIdDocURL = emp.getCompanyTaxIdDocURL();
		this.userNationalId = emp.getUserNationalId();
		this.idCardPictureURL1 = emp.getIdCardPictureURL1();
		this.idCardPictureURL2 = emp.getIdCardPictureURL2();
		this.applicationCheck = emp.getApplicationCheck();
	}

	public EmpApplDTO(Integer user_id, String company_name, String company_address, String company_category,
			String company_phoneNumber, String company_taxID, String company_taxID_docURL, String user_national_id,
			String idCard_pictureURL_1, String idCard_pictureURL_2, Integer application_check) {
		super();
		this.userId = user_id;
		this.companyName = company_name;
		this.companyAddress = company_address;
		this.companyCategory = company_category;
		this.companyPhoneNumber = company_phoneNumber;
		this.companyTaxID = company_taxID;
		this.companyTaxIdDocURL = company_taxID_docURL;
		this.userNationalId = user_national_id;
		this.idCardPictureURL1 = idCard_pictureURL_1;
		this.idCardPictureURL2 = idCard_pictureURL_2;
		this.applicationCheck = application_check;
	}

	public EmpApplDTO(Integer employer_application_id, Integer user_id, String company_name, String company_address,
			String company_category, String company_phoneNumber, String company_taxID, String company_taxID_docURL,
			String user_national_id, String idCard_pictureURL_1, String idCard_pictureURL_2) {
		super();
		this.employerApplicationId = employer_application_id;
		this.userId = user_id;
		this.companyName = company_name;
		this.companyAddress = company_address;
		this.companyCategory = company_category;
		this.companyPhoneNumber = company_phoneNumber;
		this.companyTaxID = company_taxID;
		this.companyTaxIdDocURL = company_taxID_docURL;
		this.userNationalId = user_national_id;
		this.idCardPictureURL1 = idCard_pictureURL_1;
		this.idCardPictureURL2 = idCard_pictureURL_2;
	}

	public Integer getEmployerApplicationId() {
		return employerApplicationId;
	}

	public void setEmployerApplicationId(Integer employerApplicationId) {
		this.employerApplicationId = employerApplicationId;
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

	public String getCompanyTaxIdDocURL() {
		return companyTaxIdDocURL;
	}

	public void setCompanyTaxIdDocURL(String companyTaxIdDocURL) {
		this.companyTaxIdDocURL = companyTaxIdDocURL;
	}

	public String getUserNationalId() {
		return userNationalId;
	}

	public void setUserNationalId(String userNationalId) {
		this.userNationalId = userNationalId;
	}

	public String getIdCardPictureURL1() {
		return idCardPictureURL1;
	}

	public void setIdCardPictureURL1(String idCardPictureURL1) {
		this.idCardPictureURL1 = idCardPictureURL1;
	}

	public String getIdCardPictureURL2() {
		return idCardPictureURL2;
	}

	public void setIdCardPictureURL2(String idCardPictureURL2) {
		this.idCardPictureURL2 = idCardPictureURL2;
	}

	public Integer getApplicationCheck() {
		return applicationCheck;
	}

	public void setApplicationCheck(Integer applicationCheck) {
		this.applicationCheck = applicationCheck;
	}
	
	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	public String getCompanyAddress1() {
		return companyAddress1;
	}

	public void setCompanyAddress1(String companyAddress1) {
		this.companyAddress1 = companyAddress1;
	}

	@Override
	public String toString() {
		return "Employer_application [employer_application_id=" + employerApplicationId + ", user_id=" + userId
				+ ", user_email=" + ", company_name=" + companyName + ", company_address=" + companyAddress
				+ ", company_category=" + companyCategory + ", company_phoneNumber=" + companyPhoneNumber
				+ ", company_taxID=" + companyTaxID + ", company_taxID_docURL=" + companyTaxIdDocURL
				+ ", user_national_id=" + userNationalId + ", idCard_pictureURL_1=" + idCardPictureURL1
				+ ", idCard_pictureURL_2=" + idCardPictureURL2 + ", application_check=" + applicationCheck + "]";
	}
}

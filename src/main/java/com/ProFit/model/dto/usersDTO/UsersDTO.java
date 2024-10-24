package com.ProFit.model.dto.usersDTO;

import java.util.LinkedList;
import java.util.List;

import com.ProFit.model.bean.usersBean.Employer_application;
import com.ProFit.model.bean.usersBean.Employer_profile;
import com.ProFit.model.bean.usersBean.Users;

public class UsersDTO {

	private Integer userId;

	
	private String userName;

	
	private String userEmail;

	
	private String userPasswordHash;

	
	private String userPhoneNumber;

	
	private String userCity;

	
	private Integer userIdentity;

	
	private String userRegisterTime;

	
	private String userPictureURL;

	
	private Integer userBalance;

	
	private String freelancerLocationPrefer;

	
	private String freelancerExprience;

	
	private String freelancerIdentity;

	
	private Integer freelancerProfileStatus;

	
	private String freelancerDisc;
	
	private Integer enabled;
	
	private String verificationCode;
	
	private String loginTime;
	
//	private List<Pwd_reset_tokens> pwd_reset_tokens = new LinkedList<Pwd_reset_tokens>();
//
//	
//	private List<Employer_application> employerApplications = new LinkedList<Employer_application>();
//	
//	
	private EmpPfDTO employer_profile;

	
//	private Set<MajorBean> majors = new LinkedHashSet<MajorBean>(0);

	public UsersDTO() {
	}

	public UsersDTO(Users userBean) {
		super();
		this.userId = userBean.getUserId();
		this.userName = userBean.getUserName();
		this.userEmail = userBean.getUserEmail();
		this.userPasswordHash = userBean.getUserPasswordHash();
		this.userPhoneNumber = userBean.getUserPhoneNumber();
		this.userCity = userBean.getUserCity();
		this.userIdentity = userBean.getUserIdentity();
		this.userRegisterTime = userBean.getUserRegisterTime();
		this.userPictureURL = userBean.getUserPictureURL();
		this.userBalance = userBean.getUserBalance();
		this.freelancerLocationPrefer = userBean.getFreelancerLocationPrefer();
		this.freelancerExprience = userBean.getFreelancerExprience();
		this.freelancerIdentity = userBean.getFreelancerIdentity();
		this.freelancerProfileStatus = userBean.getFreelancerProfileStatus();
		this.freelancerDisc = userBean.getFreelancerDisc();
		this.enabled = userBean.getEnabled();
	}

	public UsersDTO(Integer user_id, String user_name, String user_email, String user_passwordHash,
			String user_phoneNumber, String user_city, Integer user_identity, String user_register_time,
			Integer user_balance, String freelancer_location_prefer, String freelancer_exprience,
			String freelancer_identity, Integer freelancer_profile_status, String freelancer_disc, Integer enabled) {
		super();
		this.userId = user_id;
		this.userName = user_name;
		this.userEmail = user_email;
		this.userPasswordHash = user_passwordHash;
		this.userPhoneNumber = user_phoneNumber;
		this.userCity = user_city;
		this.userIdentity = user_identity;
		this.userRegisterTime = user_register_time;
		this.userBalance = user_balance;
		this.freelancerLocationPrefer = freelancer_location_prefer;
		this.freelancerExprience = freelancer_exprience;
		this.freelancerIdentity = freelancer_identity;
		this.freelancerProfileStatus = freelancer_profile_status;
		this.freelancerDisc = freelancer_disc;
		this.enabled = enabled;
	}

	public UsersDTO(String user_name, String user_email, String user_passwordHash, String user_phoneNumber,
			String user_city, Integer user_identity, String user_register_time, Integer freelancer_profile_status, Integer enabled) {
		super();
		this.userName = user_name;
		this.userEmail = user_email;
		this.userPasswordHash = user_passwordHash;
		this.userPhoneNumber = user_phoneNumber;
		this.userCity = user_city;
		this.userIdentity = user_identity;
		this.userRegisterTime = user_register_time;
		this.freelancerProfileStatus = freelancer_profile_status;
		this.enabled = enabled;
	}

	public UsersDTO(String user_name, String user_email, String user_passwordHash, String user_phoneNumber,
			String user_city, Integer user_identity, Integer user_balance, Integer freelancer_profile_status, Integer enabled) {
		super();
		this.userName = user_name;
		this.userEmail = user_email;
		this.userPasswordHash = user_passwordHash;
		this.userPhoneNumber = user_phoneNumber;
		this.userCity = user_city;
		this.userIdentity = user_identity;
		this.userBalance = user_balance;
		this.freelancerProfileStatus = freelancer_profile_status;
		this.enabled = enabled;
	}
	

	public UsersDTO(Integer user_id,String user_pictureURL,String user_name, String user_email, 
			Integer user_identity, String user_register_time, Integer enabled) {
		super();
		this.userId = user_id;
		this.userPictureURL = user_pictureURL;
		this.userName = user_name;
		this.userEmail = user_email;		
		this.userIdentity = user_identity;
		this.userRegisterTime = user_register_time;
		this.enabled = enabled;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPasswordHash() {
		return userPasswordHash;
	}

	public void setUserPasswordHash(String userPasswordHash) {
		this.userPasswordHash = userPasswordHash;
	}

	public String getUserPhoneNumber() {
		return userPhoneNumber;
	}

	public void setUserPhoneNumber(String userPhoneNumber) {
		this.userPhoneNumber = userPhoneNumber;
	}

	public String getUserCity() {
		return userCity;
	}

	public void setUserCity(String userCity) {
		this.userCity = userCity;
	}

	public Integer getUserIdentity() {
		return userIdentity;
	}

	public void setUserIdentity(Integer userIdentity) {
		this.userIdentity = userIdentity;
	}

	public String getUserRegisterTime() {
		return userRegisterTime;
	}

	public void setUserRegisterTime(String userRegisterTime) {
		this.userRegisterTime = userRegisterTime;
	}

	public String getUserPictureURL() {
		return userPictureURL;
	}

	public void setUserPictureURL(String userPictureURL) {
		this.userPictureURL = userPictureURL;
	}

	public Integer getUserBalance() {
		return userBalance;
	}

	public void setUserBalance(Integer userBalance) {
		this.userBalance = userBalance;
	}

	public String getFreelancerLocationPrefer() {
		return freelancerLocationPrefer;
	}

	public void setFreelancerLocationPrefer(String freelancerLocationPrefer) {
		this.freelancerLocationPrefer = freelancerLocationPrefer;
	}

	public String getFreelancerExprience() {
		return freelancerExprience;
	}

	public void setFreelancerExprience(String freelancerExprience) {
		this.freelancerExprience = freelancerExprience;
	}

	public String getFreelancerIdentity() {
		return freelancerIdentity;
	}

	public void setFreelancerIdentity(String freelancerIdentity) {
		this.freelancerIdentity = freelancerIdentity;
	}

	public Integer getFreelancerProfileStatus() {
		return freelancerProfileStatus;
	}

	public void setFreelancerProfileStatus(Integer freelancerProfileStatus) {
		this.freelancerProfileStatus = freelancerProfileStatus;
	}

	public String getFreelancerDisc() {
		return freelancerDisc;
	}

	public void setFreelancerDisc(String freelancerDisc) {
		this.freelancerDisc = freelancerDisc;
	}
	
	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	
	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	
	

//	public List<Employer_application> getEmployerApplications() {
//		return employerApplications;
//	}
//	
//	public void setEmployerApplications(List<Employer_application> employerApplications) {
//		this.employerApplications = employerApplications;
//	}
//	
//	
//	public Set<MajorBean> getMajors() {
//		return majors;
//	}
//
//	public void setMajors(Set<MajorBean> majors) {
//		this.majors = majors;
//	}


	public EmpPfDTO getEmployer_profile() {
		return employer_profile;
	}

	public void setEmployer_profile(EmpPfDTO emppfDTO) {
		this.employer_profile = emppfDTO;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	@Override
	public String toString() {
		return "Users [user_id=" + userId + ", user_name=" + userName + ", user_email=" + userEmail
				+ ", user_passwordHash=" + userPasswordHash + ", user_phoneNumber=" + userPhoneNumber + ", user_city="
				+ userCity + ", user_identity=" + userIdentity + ", user_register_time=" + userRegisterTime
				+ ", user_pictureURL=" + userPictureURL + ", user_balance=" + userBalance
				+ ", freelancer_location_prefer=" + freelancerLocationPrefer + ", freelancer_exprience="
				+ freelancerExprience + ", freelancer_identity=" + freelancerIdentity + ", freelancer_profile_status="
				+ freelancerProfileStatus + ", freelancer_disc=" + freelancerDisc + "]";
	}


}

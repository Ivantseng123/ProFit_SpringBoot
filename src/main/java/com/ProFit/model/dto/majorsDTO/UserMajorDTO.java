package com.ProFit.model.dto.majorsDTO;

import com.ProFit.model.bean.majorsBean.UserMajorBean;
import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.dto.usersDTO.UsersDTO;

// 處理lazy和序列化的問題
public class UserMajorDTO {

	private Integer userId;
	private Integer majorId;
	// UsersDTO 代表用戶信息，這樣可以控制要傳輸的user信息量。
	private UsersDTO user;
	private MajorDTO major;

	// 構造函數
	public UserMajorDTO() {
	}

	// GETTER SETTER
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getMajorId() {
		return majorId;
	}

	public void setMajorId(Integer majorId) {
		this.majorId = majorId;
	}

	public UsersDTO getUser() {
		return user;
	}

	public void setUser(UsersDTO user) {
		this.user = user;
	}

	public MajorDTO getMajor() {
		return major;
	}

	public void setMajor(MajorDTO major) {
		this.major = major;
	}

	// 從 UserMajorBean 提取data轉換成 UserMajorDTO 的靜態方法
	public static UserMajorDTO fromEntity(UserMajorBean userMajorBean) {
		UserMajorDTO dto = new UserMajorDTO();
		if (userMajorBean.getId() != null) {
			dto.setUserId(userMajorBean.getId().getUserId());
			dto.setMajorId(userMajorBean.getId().getMajorId());
		}

		Users user = userMajorBean.getUser();
		if (user != null) {
			// 假設有一個UserDTO.fromEntity方法 (但沒有，手動設置)
			// dto.setUser(UsersDTO.fromEntity(userMajorBean.getUser()));
			UsersDTO usersDto = new UsersDTO();
			usersDto.setUserId(user.getUserId());
			usersDto.setUserName(user.getUserName());
			usersDto.setUserEmail(user.getUserEmail());
			usersDto.setUserPasswordHash(user.getUserPasswordHash());
			usersDto.setUserPhoneNumber(user.getUserPhoneNumber());
			usersDto.setUserCity(user.getUserCity());
			usersDto.setUserIdentity(user.getUserIdentity());
			usersDto.setUserRegisterTime(user.getUserRegisterTime());
			usersDto.setUserPictureURL(user.getUserPictureURL());
			usersDto.setUserBalance(user.getUserBalance());
			usersDto.setFreelancerLocationPrefer(user.getFreelancerLocationPrefer());
			usersDto.setFreelancerExprience(user.getFreelancerExprience());
			usersDto.setFreelancerIdentity(user.getFreelancerIdentity());
			usersDto.setFreelancerProfileStatus(user.getFreelancerProfileStatus());
			usersDto.setFreelancerDisc(user.getFreelancerDisc());

			dto.setUser(usersDto);
		}

		if (userMajorBean.getMajor() != null) {
			dto.setMajor(MajorDTO.fromEntity(userMajorBean.getMajor()));
		}
		return dto;
	}
	

}

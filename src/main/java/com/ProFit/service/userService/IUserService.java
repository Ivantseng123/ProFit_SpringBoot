package com.ProFit.service.userService;

import java.security.NoSuchAlgorithmException;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.dto.usersDTO.UsersDTO;
public interface IUserService {

	// 新增user
	Users saveUserInfo(String user_name, String user_email, String user_password, String user_phonenumber,
			String user_city) throws NoSuchAlgorithmException;

	// 刪除user By ID
	void deleteUserInfo(int user_id);

	// 修改user密碼
	Users updateUserPwd(String pwd, int user_id) throws NoSuchAlgorithmException;

	Users updateUserInfo(Integer user_id, String user_pictureURL, String user_name, String user_email,
			String user_passwordHash, String user_phoneNumber, String user_city, Integer user_identity,
			Integer user_balance, String freelancer_location_prefer, String freelancer_exprience,
			String freelancer_identity, Integer freelancer_profile_status, String freelancer_disc, Integer enabled
			);

	//給予user企業資格
	boolean updateUserIdentity(int user_id);

	List<Users> getAllUserInfo();

	boolean validate(String userEmail, String userPasswordHash);

	String getUserPictureByEmail(String userEmail);

	Users getUserInfoByID(Integer user_id);

	Users getUserByEmail(String user_email);

	Page<UsersDTO> findUserByPageAndSearch(Integer pageNumber, String search, Integer userIdentity);

	//Page<UsersDTO> findUserByPage(Integer pageNumber);

	ResponseEntity<?> registerUser(Users users);

	boolean confirmEmail(String confirmationToken);

	boolean validateForfrontend(String userEmail, String userPassword);

	//用來更新用戶餘額
	Users updateUserBalance(Users user);

}
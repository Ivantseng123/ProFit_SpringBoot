package com.ProFit.service.userService;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.dao.usersCRUD.UsersRepository;
import com.ProFit.model.dto.usersDTO.UsersDTO;

@Service
//@Transactional
public class UserService implements IUserService {

	@Autowired
	private PasswordEncoder pwdEncoder;

	@Autowired
	private UsersRepository usersRepository;

	// 新增user
	@Override
	public Users saveUserInfo(String user_name, String user_email, String user_password, String user_phonenumber,
			String user_city) throws NoSuchAlgorithmException {

		String user_passwordHash = pwdEncoder.encode(user_password);
		Users user = new Users();
		user.setUserName(user_name);
		user.setUserEmail(user_email);
		user.setUserPasswordHash(user_passwordHash);
		user.setUserPhoneNumber(user_phonenumber);
		user.setUserCity(user_city);
		user.setUserIdentity(1);
		user.setUserBalance(0);
		user.setFreelancerProfileStatus(0);

		return usersRepository.save(user);
	}

	// 刪除user By ID
	@Override
	public void deleteUserInfo(int user_id) {
		usersRepository.deleteById(user_id);
	}

	// 修改user密碼
	@Override
	public Users updateUserPwd(String pwd, int user_id) throws NoSuchAlgorithmException {
		Optional<Users> optional = usersRepository.findById(user_id);

		if (optional.isPresent()) {
			Users user = optional.get();
			String user_passwordHash = pwdEncoder.encode(pwd);
			user.setUserPasswordHash(user_passwordHash);
			return user;
		}
		return null;
	}

	// 修改user資料
	@Override
	public Users updateUserInfo(Integer user_id, String user_pictureURL, String user_name, String user_email,
			String user_passwordHash, String user_phoneNumber, String user_city, Integer user_identity,
			Integer user_balance, String freelancer_location_prefer, String freelancer_exprience,
			String freelancer_identity, Integer freelancer_profile_status, String freelancer_disc) {
		Optional<Users> optional = usersRepository.findById(user_id);

		if (optional.isPresent()) {
			Users user = optional.get();
//			user.setUserId(user_id);
			user.setUserName(user_name);
			user.setUserEmail(user_email);
			user.setUserPasswordHash(user_passwordHash);
			user.setUserPhoneNumber(user_phoneNumber);
			user.setUserCity(user_city);
			user.setUserPictureURL(user_pictureURL);
			user.setFreelancerLocationPrefer(freelancer_location_prefer);
			user.setFreelancerExprience(freelancer_exprience);
			user.setFreelancerDisc(freelancer_disc);
			user.setFreelancerIdentity(freelancer_identity);
			user.setUserIdentity(user_identity);
			user.setUserBalance(user_balance);
			user.setFreelancerProfileStatus(freelancer_profile_status);
			return user;
		}
		return null;
	}

	// 給予user企業資格
	@Override
	public boolean updateUserIdentity(int user_id) {
		Optional<Users> optional = usersRepository.findById(user_id);

		if (optional.isPresent()) {
			Users user = optional.get();
			user.setUserIdentity(2);
			return true;
		}
		return false;
	}

	@Override
	public List<Users> getAllUserInfo() {
		return usersRepository.findAll();
	}

	@Override
	public boolean validate(String userEmail, String userPassword) {

		Optional<Users> optional = usersRepository.findByUserEmail(userEmail);

		if (optional.isPresent() && optional.get().getUserIdentity() == 3) {
			String dbPassword = optional.get().getUserPasswordHash();

			return pwdEncoder.matches(userPassword, dbPassword);
		}
		return false;

	}

	@Override
	public String getUserPictureByEmail(String userEmail) {
		Optional<Users> optional = usersRepository.findByUserEmail(userEmail);

		if (optional.isPresent()) {
			return optional.get().getUserPictureURL();
		}
		return null;
	}

	@Override
	public Users getUserInfoByID(Integer user_id) {
		Optional<Users> optional = usersRepository.findById(user_id);

		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public Users getUserByEmail(String user_email) {
		Optional<Users> optional = usersRepository.findByUserEmail(user_email);

		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

//	public Page<Users> findUserByPage(Integer pageNumber){
//		Pageable pgb = PageRequest.of(pageNumber-1, 10, Sort.Direction.DESC ,"userId");
//		Page<Users> page = usersRepository.findAll(pgb);
//		return page;
//	}
//	
//	public Page<Users> findUserByPageAndSearch(Integer pageNumber, String search) {
//		Pageable pgb = PageRequest.of(pageNumber - 1, 10, Sort.Direction.DESC, "userId");
//		// 这里假设你想搜索 `userName` 或 `userEmail`，可以根据需求修改
//		Page<Users> page = usersRepository.findByUserNameContainingOrUserEmailContaining(search, search, pgb);
//		return page;
//	}

	@Override
	public Page<UsersDTO> findUserByPage(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.Direction.DESC, "userId");
		Page<Users> usersPage = usersRepository.findAll(pageable);

		return usersPage.map(user -> new UsersDTO(user.getUserId(), user.getUserName(), user.getUserEmail(),
				user.getUserIdentity(), user.getUserRegisterTime()));
	}

	@Override
	public Page<UsersDTO> findUserByPageAndSearch(Integer pageNumber, String search) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.Direction.DESC, "userId");

		if (search == null || search.isEmpty()) {

			Page<Users> usersPage = usersRepository.findAll(pageable);
			return usersPage.map(user -> new UsersDTO(user.getUserId(),user.getUserName(), user.getUserEmail(),
					user.getUserIdentity(), user.getUserRegisterTime()));
		}

		Page<Users> usersPage = usersRepository.findByUserNameContainingOrUserEmailContaining(search, search, pageable);
		return usersPage.map(user -> new UsersDTO(user.getUserId(),user.getUserName(), user.getUserEmail(),
				user.getUserIdentity(), user.getUserRegisterTime()));
	}

}

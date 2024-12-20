package com.ProFit.service.userService;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ProFit.model.bean.usersBean.Pwd_reset_tokens;
import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.dao.usersCRUD.PwdResetTokenRepository;
import com.ProFit.model.dao.usersCRUD.UsersRepository;
import com.ProFit.model.dto.usersDTO.UserStatistics;
import com.ProFit.model.dto.usersDTO.UserStatistics_registerTime;
import com.ProFit.model.dto.usersDTO.UsersDTO;
import net.bytebuddy.utility.RandomString;

@Service
// @Transactional
public class UserService implements IUserService {

	@Autowired
	private PasswordEncoder pwdEncoder;

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private PwdResetTokenRepository pwdResetTokenRepository;

	@Autowired
	private EmailService emailService;

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
		user.setEnabled(1);

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
			System.out.println("修改的密碼----------------" + pwd);
			return usersRepository.save(user);
		}
		return null;
	}

	// 修改user資料
	@Override
	public Users updateUserInfo(Integer user_id, String user_pictureURL, String user_name, String user_email,
			String user_passwordHash, String user_phoneNumber, String user_city, Integer user_identity,
			Integer user_balance, String freelancer_location_prefer, String freelancer_exprience,
			String freelancer_identity, Integer freelancer_profile_status, String freelancer_disc, Integer enabled) {
		Optional<Users> optional = usersRepository.findById(user_id);

		if (optional.isPresent()) {
			Users user = optional.get();
			// user.setUserId(user_id);
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
			user.setEnabled(enabled);
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

		System.out.println("登入的密碼: " + userPassword);
		if (optional.isPresent() && optional.get().getUserIdentity() == 3 || optional.get().getUserIdentity() == 4) {
			String dbPassword = optional.get().getUserPasswordHash();

			System.out.println("User------------" + optional.get());

			System.out.println("比對結果: " + pwdEncoder.matches(userPassword, dbPassword));
			return pwdEncoder.matches(userPassword, dbPassword);
		}
		return false;

	}

	@Override
	public boolean validateForfrontend(String userEmail, String userPassword) {

		Optional<Users> optional = usersRepository.findByUserEmail(userEmail);

		if (optional.isPresent() && (optional.get().getUserIdentity() == 1 || optional.get().getUserIdentity() == 2)
				&& optional.get().getEnabled() == 1) {
			String dbPassword = optional.get().getUserPasswordHash();

			System.out.println("User------------" + optional.get());

			System.out.println("比對結果: " + pwdEncoder.matches(userPassword, dbPassword));
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

	// public Page<Users> findUserByPage(Integer pageNumber){
	// Pageable pgb = PageRequest.of(pageNumber-1, 10, Sort.Direction.DESC
	// ,"userId");
	// Page<Users> page = usersRepository.findAll(pgb);
	// return page;
	// }
	//
	// public Page<Users> findUserByPageAndSearch(Integer pageNumber, String search)
	// {
	// Pageable pgb = PageRequest.of(pageNumber - 1, 10, Sort.Direction.DESC,
	// "userId");
	// // 这里假设你想搜索 `userName` 或 `userEmail`，可以根据需求修改
	// Page<Users> page =
	// usersRepository.findByUserNameContainingOrUserEmailContaining(search, search,
	// pgb);
	// return page;
	// }

	// @Override
	// public Page<UsersDTO> findUserByPage(Integer pageNumber) {
	// Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.Direction.DESC,
	// "userId");
	// Page<Users> usersPage = usersRepository.findAll(pageable);
	//
	// return usersPage.map(user -> new UsersDTO(user.getUserId(),
	// user.getUserPictureURL(), user.getUserName(),
	// user.getUserEmail(), user.getUserIdentity(), user.getUserRegisterTime(),
	// user.getEnabled()));
	// }

	@Override
	public Page<UsersDTO> findUserByPageAndSearch(Integer pageNumber, String search, Integer userIdentity) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.Direction.DESC, "userId");

		if ((search == null || search.isEmpty()) && userIdentity == null) {

			Page<Users> usersPage = usersRepository.findAll(pageable);
			return usersPage.map(user -> new UsersDTO(user.getUserId(), user.getUserPictureURL(), user.getUserName(),
					user.getUserEmail(), user.getUserIdentity(), user.getUserRegisterTime(), user.getEnabled()));

		}

		if (userIdentity == null) {
			Page<Users> usersPage = usersRepository.findByUserNameContainingOrUserEmailContaining(search, search,
					pageable);
			return usersPage.map(user -> new UsersDTO(user.getUserId(), user.getUserPictureURL(), user.getUserName(),
					user.getUserEmail(), user.getUserIdentity(), user.getUserRegisterTime(), user.getEnabled()));
		}

		if (search == null || search.isEmpty()) {
			Page<Users> usersPage = usersRepository.findByUserIdentity(userIdentity, pageable);
			return usersPage.map(user -> new UsersDTO(user.getUserId(), user.getUserPictureURL(), user.getUserName(),
					user.getUserEmail(), user.getUserIdentity(), user.getUserRegisterTime(), user.getEnabled()));
		}

		Page<Users> usersPage = usersRepository.findBySearchAndUserIdentity(search, userIdentity, pageable);
		return usersPage.map(user -> new UsersDTO(user.getUserId(), user.getUserPictureURL(), user.getUserName(),
				user.getUserEmail(), user.getUserIdentity(), user.getUserRegisterTime(), user.getEnabled()));
	}

	@Override
	public ResponseEntity<?> registerUser(Users users) {

		String passwordHash = pwdEncoder.encode(users.getUserPasswordHash());

		users.setUserPasswordHash(passwordHash);
		users.setEnabled(0);
		users.setUserBalance(0);
		users.setUserIdentity(1);
		users.setFreelancerProfileStatus(0);

		String randomCode = RandomString.make(64);
		users.setVerificationCode(randomCode);
		usersRepository.save(users);

		String confirmationLink = "http://localhost:8080/ProFit/user/confirm-account?token="
				+ users.getVerificationCode();

		String content = "<html><body><h3>" + users.getUserName() + "，您好!</h3>"
				+ "<p>歡迎您加入ProFit，為了啟用您的帳戶, 請點擊下面的按鈕:</p>" + "<a href=\"" + confirmationLink
				+ "\" style=\"display:inline-block; padding:10px 20px; margin:10px 0; font-size:16px; "
				+ "color:white; background-color:#007BFF; text-decoration:none; border-radius:5px;\">" + "驗證</a>"
				+ "</body></html>";

		emailService.sendSimpleHtml(List.of(users.getUserEmail()), "ProFit 註冊驗證", content);

		// SimpleMailMessage mailMessage = new SimpleMailMessage();
		// mailMessage.setTo(users.getUserEmail());
		// mailMessage.setSubject("註冊完成!");
		// mailMessage.setText(
		// "為了啟用您的帳戶, 請點擊此連結 : " +
		// "http://localhost:8080/ProFit/user/confirm-account?token=" +
		// users.getVerificationCode());
		// emailService.sendEmail(mailMessage);
		//
		// System.out.println("Confirmation Token: " + users.getVerificationCode());
		//
		return ResponseEntity.ok("Verify email by the link sent on your email address");

	}

	@Override
	public ResponseEntity<?> sendResetToken(String email, String token) {

		Optional<Users> user = usersRepository.findByUserEmail(email);

		if (user.isPresent()) {

			Users existUser = user.get();

			String confirmationLink = "http://localhost:8080/ProFit/user/resetPwd?userId=" + existUser.getUserId()
					+ "&token=" + token;

			String content = "<html><body><h3>" + existUser.getUserName() + "，您好!</h3>" + "<p>為了重設您的密碼, 請點擊下面的按鈕:</p>"
					+ "<a href=\"" + confirmationLink
					+ "\" style=\"display:inline-block; padding:10px 20px; margin:10px 0; font-size:16px; "
					+ "color:white; background-color:#007BFF; text-decoration:none; border-radius:5px;\">" + "重設密碼</a>"
					+ "</body></html>";

			emailService.sendSimpleHtml(List.of(email), "ProFit 重設密碼", content);

		}

		return ResponseEntity.ok("Verify token by the link sent on your email address");

	}

	@Override
	public boolean confirmEmail(String confirmationToken) {
		Optional<Users> optional = usersRepository.findByVerificationCode(confirmationToken);

		if (optional.isPresent()) {

			Users user = optional.get();
			user.setEnabled(1);
			usersRepository.save(user);
			return true;
		}
		return false;
	}

	@Override
	public boolean confirm_resetToken(Integer userId, String confirmationToken) {

		Optional<Pwd_reset_tokens> optional = pwdResetTokenRepository.findLatestByUserId(userId);

		if (optional.isPresent()) {

			String dbtoken = optional.get().getUserTokenHash();

			System.out.println("Token--------------------" + dbtoken);

			return pwdEncoder.matches(confirmationToken, dbtoken);
		}
		return false;
	}

	@Override
	public Users updateUserInfo(Integer userId, String userPictureURL, String userName, String userPhoneNumber,
			String userCity, String freelancerLocationPrefer, String freelancerExprience, String freelancerIdentity,
			Integer freelancerProfileStatus, String freelancerDisc) {

		Optional<Users> optional = usersRepository.findById(userId);

		if (optional.isPresent()) {
			Users user = optional.get();
			// user.setUserId(user_id);
			user.setUserName(userName);
			user.setUserPhoneNumber(userPhoneNumber);
			user.setUserCity(userCity);
			user.setUserPictureURL(userPictureURL);
			user.setFreelancerLocationPrefer(freelancerLocationPrefer);
			user.setFreelancerExprience(freelancerExprience);
			user.setFreelancerDisc(freelancerDisc);
			user.setFreelancerIdentity(freelancerIdentity);
			user.setFreelancerProfileStatus(freelancerProfileStatus);

			return user;
		}
		return null;
	}

	@Override
	public List<UserStatistics> getUserStatistics() {
		return usersRepository.countByUserIdentity();
	}
	
	@Override
	public List<UserStatistics_registerTime> getUserStatisticsByDate() {
        return usersRepository.getUserStatisticsByDate();
    }

	// 用來更新用戶餘額的方法
	@Override
	public Users updateUserBalance(Users user) {
		return usersRepository.save(user); // 使用 usersRepository 進行保存操作
	}
	
	public Users getUserById(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	// 前台獲取用戶餘額
	public Integer getUserBalanceById(Integer userId) {
		Users user = usersRepository.findById(userId).orElse(null);
		if (user != null) {
			return user.getUserBalance();
		} else {
			return null;
		}
	}

	// 更新創建課程者的餘額
	public void updateUserBalance(Integer userId, Double amount) {
		Users user = usersRepository.findById(userId).orElse(null);
		if (user != null) {
			user.setUserBalance(user.getUserBalance() + amount.intValue());
			usersRepository.save(user);
		}
	}
}

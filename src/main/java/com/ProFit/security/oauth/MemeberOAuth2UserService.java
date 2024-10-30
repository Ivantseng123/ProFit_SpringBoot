package com.ProFit.security.oauth;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.dao.usersCRUD.UsersRepository;

@Service
public class MemeberOAuth2UserService extends DefaultOAuth2UserService {
	
	@Autowired
	private UsersRepository userRepository;

	public MemeberOAuth2UserService(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

	@Override
	@Transactional
	public OAuth2User loadUser(OAuth2UserRequest userRequest) {
		
		OAuth2User oAuth2User = super.loadUser(userRequest);

		// 從 Google 獲取使用者資料
		Map<String, Object> attributes = oAuth2User.getAttributes();
		String email = (String) attributes.get("email");
		String name = (String) attributes.get("name");
		
		//subject identifier 的縮寫，它是每個使用者的唯一 ID，這個 ID 是 Google 對於每個使用者分配的一個固定的標識符
		String googleId = (String) attributes.get("sub");
		String pictureUrl = (String) attributes.get("picture");

		// 檢查資料庫中是否已存在該使用者
		Optional<Users> user = userRepository.findByUserEmail(email);
		if (user.isEmpty()) {
			
			// 如果使用者不存在，則插入新資料
			Users newUser = new Users();
			newUser.setUserEmail(email);
			newUser.setUserName(name);
			newUser.setUserPictureURL(pictureUrl);
			newUser.setFreelancerProfileStatus(0);
			newUser.setUserIdentity(1);
			newUser.setEnabled(1);
			
			userRepository.save(newUser);
		} else {
			Users existUser = user.get();

			existUser.setEnabled(1);
			userRepository.save(existUser);
		}

		return oAuth2User;
	}
}

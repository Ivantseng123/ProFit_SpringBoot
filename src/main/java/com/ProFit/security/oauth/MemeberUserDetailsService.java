package com.ProFit.security.oauth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.dao.usersCRUD.UsersRepository;

@Service
public class MemeberUserDetailsService implements UserDetailsService {

	@Autowired
	private UsersRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

		Optional<Users> optional = userRepository.findByUserEmail(userEmail);

		// 如果找不到用戶，則拋出異常
		if (optional.isEmpty()) {
			throw new UsernameNotFoundException("User not found: " + userEmail);
		}
		
		Users user = optional.get();

		// 根據 userIdentity 分配權限
		Collection<GrantedAuthority> authorities = new ArrayList<>();


		if (user.getUserIdentity() == 1 || user.getUserIdentity() == 2) {
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		} else if (user.getUserIdentity() == 3 || user.getUserIdentity() == 4) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}

		return new org.springframework.security.core.userdetails.User(user.getUserEmail(), user.getUserPasswordHash(),
				authorities // 根據身分返回對應的權限
		);
	}

}

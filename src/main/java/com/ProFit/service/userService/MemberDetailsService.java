package com.ProFit.service.userService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.dao.usersCRUD.UsersRepository;

@Service
public class MemberDetailsService implements UserDetailsService {

	@Autowired
	private UsersRepository usersRepository;

	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

		Optional<Users> optional = usersRepository.findByUserEmail(userEmail);

		Users user = optional.get();

		if (user == null) {
			throw new UsernameNotFoundException("User not found: " + userEmail);
		}

		
		String role;
		
		if (user.getUserIdentity() == 1 || user.getUserIdentity() == 2) {
			role = "ROLE_USER";
		} else if (user.getUserIdentity() == 3 || user.getUserIdentity() == 4) {
			role = "ROLE_ADMIN";
		} else {
			throw new UsernameNotFoundException("Invalid identity");
		}

		
		return User.builder().username(user.getUserEmail()).password(user.getUserPasswordHash())
				.roles(role)
				.build();
	}

}

package com.ProFit.service.userService;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ProFit.model.bean.usersBean.Pwd_reset_tokens;
import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.dao.usersCRUD.PwdResetTokenRepository;
import com.ProFit.model.dto.usersDTO.TokensDTO;
import com.ProFit.model.dto.usersDTO.UsersDTO;

import net.bytebuddy.utility.RandomString;

@Service
@Transactional
public class PwdresetService implements IPwdresetService {

	@Autowired
	private PwdResetTokenRepository pwdRepository;

	

	// 新增token
	@Override
	public Pwd_reset_tokens saveTokensInfo(Pwd_reset_tokens token) {

		return pwdRepository.save(token);
	}

	// 刪除token By ID
	@Override
	public void deleteTokensInfo(int token_id) {
		pwdRepository.deleteById(token_id);
	}

	// 查詢全部tokens
	@Override
	public List<Pwd_reset_tokens> getAllTokensInfo() {

		return pwdRepository.findAll();
	}

	@Override
	public Page<TokensDTO> findTokenByPage(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.Direction.DESC, "tokenId");
		Page<Pwd_reset_tokens> tokensPage = pwdRepository.findAll(pageable);

		return tokensPage.map(token -> new TokensDTO(token));
	}

	@Override
	public Page<TokensDTO> findTokenByPageAndSearch(Integer pageNumber, String search) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.Direction.DESC, "tokenId");

		Page<Pwd_reset_tokens> tokensPage;
	    if (search == null || search.isEmpty()) {
	        tokensPage = pwdRepository.findAll(pageable);
	    } else {
	        tokensPage = pwdRepository.findByUserEmailContaining(search, pageable);
	    }
	    
	    return tokensPage.map(token -> new TokensDTO(token));
	}

	// 產生token
	@Override
	public String generateToken() throws NoSuchAlgorithmException {
		
		String randomCode = RandomString.make(64);


		return randomCode;
	}
}

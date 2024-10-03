package com.ProFit.service.userService;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.data.domain.Page;

import com.ProFit.model.bean.usersBean.Pwd_reset_tokens;
import com.ProFit.model.dto.usersDTO.TokensDTO;




public interface IPwdresetService {

	Pwd_reset_tokens saveTokensInfo(Pwd_reset_tokens token);

	void deleteTokensInfo(int token_id);

	List<Pwd_reset_tokens> getAllTokensInfo();

	String generateToken() throws NoSuchAlgorithmException;

	Page<TokensDTO> findTokenByPage(Integer pageNumber);

	Page<TokensDTO> findTokenByPageAndSearch(Integer pageNumber, String search);

}
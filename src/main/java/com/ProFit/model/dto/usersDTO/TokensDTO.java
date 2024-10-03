package com.ProFit.model.dto.usersDTO;

import com.ProFit.model.bean.usersBean.Pwd_reset_tokens;
import com.ProFit.model.bean.usersBean.Users;

public class TokensDTO {

	private Integer tokenId;

	private Integer userId;
	
	private String userEmail;

	private String userTokenHash;

	private String expirationTime;

	public TokensDTO(Pwd_reset_tokens tokens) {
		this.tokenId = tokens.getTokenId();
		this.userId = tokens.getUserId();
		this.userEmail = tokens.getUser().getUserEmail();
		this.userTokenHash = tokens.getUserTokenHash();
		this.expirationTime = tokens.getExpirationTime();
	}

	public TokensDTO(Integer user_id, String user_tokenHash) {
		super();
		this.userId = user_id;
		this.userTokenHash = user_tokenHash;
	}

	public TokensDTO() {

	}

	public TokensDTO(Integer tokenId, Integer userId, String userEmail, String userTokenHash, String expirationTime) {

		this.tokenId = tokenId;
		this.userId = userId;
		this.userEmail = userEmail;
		this.userTokenHash = userTokenHash;
		this.expirationTime = expirationTime;

	}

	public Integer getTokenId() {
		return tokenId;
	}

	public void setTokenId(Integer tokenId) {
		this.tokenId = tokenId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserTokenHash() {
		return userTokenHash;
	}

	public void setUserTokenHash(String userTokenHash) {
		this.userTokenHash = userTokenHash;
	}

	public String getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(String expirationTime) {
		this.expirationTime = expirationTime;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}


}

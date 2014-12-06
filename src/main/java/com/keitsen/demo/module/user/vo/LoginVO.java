package com.keitsen.demo.module.user.vo;

public class LoginVO {
	
	private String loginId;
	
	private String username;
	
	private String password;
	
	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginVO [username=" + username + ", password=" + password + "]";
	}
	
}

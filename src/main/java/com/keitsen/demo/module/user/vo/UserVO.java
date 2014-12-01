package com.keitsen.demo.module.user.vo;

import com.keitsen.demo.basic.entity.Module;
import com.keitsen.demo.basic.vo.UUIDEntityVO;
import com.keitsen.demo.basic.vo.VO;

public class UserVO extends UUIDEntityVO implements VO{

	private static final long serialVersionUID = -2995582496239329451L;

	private String username;
	
	private String password;
	
	
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
	public Module getModule() {
		return null;
	}
	

}

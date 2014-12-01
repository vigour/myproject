package com.keitsen.demo.module.user.service;

import com.keitsen.demo.basic.service.IBasicService;
import com.keitsen.demo.module.user.entity.User;

public interface IUserService extends IBasicService<User, String>{

	public final static String SERVICE_NAME = "userService";
	
	public User getUser(com.keitsen.demo.module.user.vo.LoginVO loginVO);
	public User getUserLogin(String username, String password);
}

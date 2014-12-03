package com.keitsen.demo.module.user.service;

import java.util.List;

import com.keitsen.demo.basic.service.IBasicService;
import com.keitsen.demo.module.user.entity.User;
import com.keitsen.demo.module.user.vo.UserVO;

public interface IUserService extends IBasicService<User, String>{

	final static String SERVICE_NAME = "userService";
	
	User getUser(com.keitsen.demo.module.user.vo.LoginVO loginVO);
	User getUserLogin(String username, String password);
	List<UserVO> getAllUser();
}

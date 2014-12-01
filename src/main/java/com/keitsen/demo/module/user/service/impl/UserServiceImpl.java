package com.keitsen.demo.module.user.service.impl;

import java.util.LinkedHashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keitsen.demo.basic.service.impl.BasicService;
import com.keitsen.demo.module.user.dao.IUserDao;
import com.keitsen.demo.module.user.entity.User;
import com.keitsen.demo.module.user.service.IUserService;
import com.keitsen.demo.module.user.vo.LoginVO;

@Service(IUserService.SERVICE_NAME)
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends BasicService<User, String> implements IUserService{


	private IUserDao userDao;
	
	
	@Resource(name = IUserDao.DAO_NAME)
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	
	
	public IUserDao getUserDao() {
		return userDao;
	}



	public User getUser(LoginVO loginVO) {
		LinkedHashMap<Object, Object> equalFields = new LinkedHashMap<Object, Object>();
		equalFields.put("username", loginVO.getUsername());
		equalFields.put("password", loginVO.getPassword());
		return userDao.get(equalFields, null, null, null, null);
	}


	@Override
	public User getUserLogin(String username, String password) {
		LinkedHashMap<Object, Object> equalFields = new LinkedHashMap<Object, Object>();
		equalFields.put("username", username);
		equalFields.put("password", password);
		return userDao.get(equalFields, null, null, null, null);
	}




}

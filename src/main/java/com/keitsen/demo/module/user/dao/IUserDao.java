package com.keitsen.demo.module.user.dao;

import com.keitsen.demo.basic.dao.IBasicDao;
import com.keitsen.demo.module.user.entity.User;

public interface IUserDao extends IBasicDao<User, String>{
	public final static String DAO_NAME = "userDao";
}

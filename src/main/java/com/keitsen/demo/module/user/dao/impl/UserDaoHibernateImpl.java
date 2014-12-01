package com.keitsen.demo.module.user.dao.impl;

import org.springframework.stereotype.Repository;

import com.keitsen.demo.basic.dao.impl.HibernateBasicDao;
import com.keitsen.demo.module.user.dao.IUserDao;
import com.keitsen.demo.module.user.entity.User;

@Repository(IUserDao.DAO_NAME)
public class UserDaoHibernateImpl extends HibernateBasicDao<User, String> implements IUserDao{
}

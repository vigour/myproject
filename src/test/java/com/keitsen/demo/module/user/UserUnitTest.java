package com.keitsen.demo.module.user;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.keitsen.demo.module.user.dao.UserDaoImpHibernateTest;
import com.keitsen.demo.module.user.service.UserServiceTest;


@RunWith(Suite.class)
@Suite.SuiteClasses({UserDaoImpHibernateTest.class, UserServiceTest.class})
//@RunWith(Suite.class)集合测试
//@SuiteClasses( { AccountDaoTest.class })集合，包括AccountDaoTest类，多个测试类可使用逗号分隔！
//        这个测试类可用于Dao层集合测试，与Spring无关！
public class UserUnitTest {

}

package com.keitsen.demo.module.user.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.junit.Test;

import com.keitsen.demo.basic.AbstractTestCase;
import com.keitsen.demo.module.user.entity.User;

public class UserServiceTest  extends AbstractTestCase{
	
	@Resource(name = "userService")
	private IUserService userService;
	
	@Test
    public void testGetUser() throws Exception {
        User userEntity=new User();
        userEntity.setUsername("admin");
        userEntity.setPassword("admin");
        User userEntity1=userService.getUserLogin(userEntity.getUsername(), userEntity.getPassword());
        assertEquals("admin",userEntity1.getUsername());
        assertEquals("admin",userEntity1.getPassword());
    }
	
	@Test
	public void testSpring(){
		System.out.println(UUID.randomUUID().toString().replace("-", ""));
		User user = new User();
//		user.setId(2);
		user.setModificationDate(new Date());
		user.setCreationDate(new Date());
		user.setUsername("adam");
		user.setPassword("adam");
		user.setStatus(1);
		user.setVisible(true);
		userService.save(user);
	}
	
	
}

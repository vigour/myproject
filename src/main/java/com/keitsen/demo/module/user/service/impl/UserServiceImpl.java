package com.keitsen.demo.module.user.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keitsen.demo.basic.entity.PageModel;
import com.keitsen.demo.basic.service.impl.BasicService;
import com.keitsen.demo.module.user.dao.IUserDao;
import com.keitsen.demo.module.user.entity.User;
import com.keitsen.demo.module.user.service.IUserService;
import com.keitsen.demo.module.user.vo.LoginVO;
import com.keitsen.demo.module.user.vo.UserVO;

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


	@Override
	public List<UserVO> getAllUser() {
		List<UserVO> voList = new ArrayList<UserVO>();
		List<User> users = this.userDao.getAll();
		for(User user : users){
			UserVO vo = (UserVO) user.getVO();
			voList.add(vo);
		}
		return voList;
	}


	@Override
	public PageModel<UserVO> getAllUserPage(PageModel<UserVO> page) {
		List<UserVO> userVOs = new ArrayList<UserVO>();
		List<User> pageUser =  
				this.userDao.findResultList(null,null,null,null,null,null,page.getFistResult(),page.getMaxResults());
		for(User u: pageUser){
			UserVO vo = new UserVO();
			vo = (UserVO) u.getVO();
			userVOs.add(vo); 
		}
		page.setList(userVOs);
		page.setTotalRecords(this.userDao.getTotalCount(null, null, null, null, null));
		
		return page;
	}

	
	/**
	 * 新增用户
	 */
	@Override
	public void createUser(UserVO uservo) {
		User user = new User();
		user = (User) uservo.getModule();
		this.save(user);
		
	}




}

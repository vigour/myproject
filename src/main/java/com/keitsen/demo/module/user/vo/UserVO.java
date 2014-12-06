package com.keitsen.demo.module.user.vo;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.keitsen.demo.basic.entity.Module;
import com.keitsen.demo.basic.vo.UUIDEntityVO;
import com.keitsen.demo.basic.vo.VO;
import com.keitsen.demo.module.user.entity.User;

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
		User user = new User();
		user.setId(this.getId());
		user.setUsername(username);
		user.setPassword(password);
		user.setVisible(isVisible());
		user.setStatus(getStatus());
		user.setModificationDate(new Date());
		user.setRemark(getRemark());
		if(StringUtils.isBlank(user.getId())){
			User creator = new User();
			creator.setId(getCreatorId());
			user.setCreator(creator);
			user.setCreationDate(new Date());
		}else{
			User modifier = new User();
			modifier.setId(getModifierId());
			user.setModifier(modifier);
			user.setModificationDate(new Date());
		}
		return user;
	}
	

}

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
		User model = new User();
		model.setId(this.getId());
		model.setUsername(username);
		model.setPassword(password);
		
		model.setVisible(isVisible());
		model.setStatus(getStatus());
		model.setModificationDate(new Date());
		model.setRemark(getRemark());
		
		if(StringUtils.isBlank(model.getId())){
			User creator = new User();
			creator.setId(getCreatorId());
			model.setCreator(creator);
			model.setCreationDate(new Date());
		}else{
			User modifier = new User();
			modifier.setId(getModifierId());
			model.setModifier(modifier);
			model.setModificationDate(new Date());
		}
		return model;
	}
	

}

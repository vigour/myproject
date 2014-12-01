package com.keitsen.demo.module.function.dto;

import org.apache.commons.lang.StringUtils;

import com.keitsen.demo.basic.dto.DTO;
import com.keitsen.demo.basic.dto.UUIDEntityDTO;
import com.keitsen.demo.basic.entity.Module;
import com.keitsen.demo.module.function.entity.Function;
import com.keitsen.demo.module.user.entity.User;

public class FunctionDTO extends UUIDEntityDTO implements DTO {

	/**
	 * 功能名称
	 */
	private String functionName;

	/**
	 * 图标
	 */
	private String icon;

	/**
	 * 功能层级
	 */
	private int level;

	/**
	 * 叶子ID
	 */
	private String leafId;

	/**
	 * url
	 */
	private String url;
	
	/**
	 * 顺序
	 */
	private int showOrder;
	
	
	/**
	 * 父模块的ID
	 */
	private String parentFunctionId;

	
	
	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getLeafId() {
		return leafId;
	}

	public void setLeafId(String leafId) {
		this.leafId = leafId;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}

	public Module getModule() {
		Function entity = new Function();
		entity.setId(getId());
		entity.setVisible(isVisible());
		entity.setStatus(getStatus());
		
		if(!StringUtils.isBlank(getCreatorId())){
			User creator = new User();
			creator.setId(getCreatorId());
			entity.setCreator(creator);
		}
		
		entity.setCreationDate(getCreationDate());
		
		if(!StringUtils.isBlank(getModifierId())){
			User modifier = new User();
			modifier.setId(getModifierId());
			entity.setModifier(modifier);
		}
		entity.setModificationDate(getModificationDate());
		
		entity.setRemark(getRemark());
		entity.setVersion(getVersion());
		
		entity.setFunctionName(functionName);
		entity.setIcon(icon);
		entity.setLevel(level);
		entity.setLeafId(leafId);
		entity.setUrl(url);
		entity.setShowOrder(showOrder);
		if(StringUtils.isBlank(parentFunctionId)){
			Function function = new Function();
			function.setId(parentFunctionId);
			entity.setParentFunction(function);
		}
		
		return entity;
	}

}

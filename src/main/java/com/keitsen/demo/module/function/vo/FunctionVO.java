package com.keitsen.demo.module.function.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.keitsen.demo.basic.entity.Module;
import com.keitsen.demo.basic.vo.UUIDEntityVO;
import com.keitsen.demo.basic.vo.VO;
import com.keitsen.demo.module.function.entity.Function;
import com.keitsen.demo.module.user.entity.User;

public class FunctionVO extends UUIDEntityVO implements VO {

	private static final long serialVersionUID = 1L;

	/**
	 * 功能名称
	 */
	private String functionName;

	/**
	 * 父功能
	 */
	private String parentFunction;

	private String parentFunctionName;

	/**
	 * 图标
	 */
	private String icon;
	
	/**
	 * url
	 */
	private String url;

	/**
	 * 子功能
	 */
	private List<FunctionVO> childFunction = new ArrayList<FunctionVO>();

	/**
	 * 功能层级
	 */
	private int level;

	/**
	 * 叶子ID
	 */
	private String leafId;

	/**
	 * 顺序
	 */
	private int showOrder;

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getParentFunction() {
		return parentFunction;
	}

	public void setParentFunction(String parentFunction) {
		this.parentFunction = parentFunction;
	}

	public String getParentFunctionName() {
		return parentFunctionName;
	}

	public void setParentFunctionName(String parentFunctionName) {
		this.parentFunctionName = parentFunctionName;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<FunctionVO> getChildFunction() {
		return childFunction;
	}

	public void setChildFunction(List<FunctionVO> childFunction) {
		this.childFunction = childFunction;
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

	public int getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}

	@Override
	public Module getModule() {
		// TODO Auto-generated method stub
		Function model = new Function();
		model.setId(this.getId());
		model.setFunctionName(functionName);
		model.setUrl(url);
		model.setIcon(icon);
		model.setShowOrder(showOrder);

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

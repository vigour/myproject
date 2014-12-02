package com.keitsen.demo.basic.vo;

import java.util.HashMap;
import java.util.Map;


/**
 * EasyUITreeVO继承自TreeVO 用于EasyUI定义的类
 * @author Miles
 *
 */
public class EasyUITreeVO extends TreeVO{
	
	private String iconCls;
	
	private String state;
	
	private Map<String, Object> attributes = new HashMap<String, Object>();

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	
}

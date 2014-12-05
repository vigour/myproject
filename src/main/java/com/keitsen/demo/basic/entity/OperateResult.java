package com.keitsen.demo.basic.entity;

public class OperateResult {
	
	/**
	 * 返回信息的类型:info
	 */
	private String type; 
	
	/**
	 * 返回信息状态
	 */
	private String status;
	
	/**
	 * 返回信息图标
	 */
	private String icon;
	
	/**
	 * 返回信息内容
	 */
	private String message;
	
	/**
	 * 返回操作类型
	 */
	private String operate;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String toJsonString(){
		return "\"type\":\"" + type + "\",\"message\":\"" + message + "\",\"operate\":\"" + operate +"\"";
	}
	
	@Override
	public String toString() {
		return "OperateResult [type=" + type + ", message=" + message
				+ ", operate=" + operate + "]";
	}
	
	
}

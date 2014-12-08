package com.keitsen.demo.module.user.vo;


/**
 * 封装用于查询条件的VO对象
 * @author Miles
 *
 */
public class SearchUserVO {
	private String username;
	
	private int status;
	
	private String startCreateDate;
	
	private String endCreateTime;
	
	private String startModifierDate;
	
	private String endModifierDate;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStartCreateDate() {
		return startCreateDate;
	}

	public void setStartCreateDate(String startCreateDate) {
		this.startCreateDate = startCreateDate;
	}

	public String getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(String endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

	public String getStartModifierDate() {
		return startModifierDate;
	}

	public void setStartModifierDate(String startModifierDate) {
		this.startModifierDate = startModifierDate;
	}

	public String getEndModifierDate() {
		return endModifierDate;
	}

	public void setEndModifierDate(String endModifierDate) {
		this.endModifierDate = endModifierDate;
	}
}

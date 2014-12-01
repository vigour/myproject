package com.keitsen.demo.basic.vo;

import java.io.Serializable;
import java.util.Date;

import com.keitsen.demo.basic.util.DateUtil;

public class UUIDEntityVO implements Serializable{
	
	private static final long serialVersionUID = 1188246073008534631L;

	private String id;

	private boolean visible = true;// 是否显示

	private int status;// 状态

	private String creationDate;// 创建时间
	
	private String creator;		//创建人

	private String modificationDate;// 最后修改时间
	
	private String modifier;	//修改人

	private String remark;	// 备注
	
	private int version;	//版本

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}


	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = DateUtil.dateToStr(creationDate, DateUtil.YYMMDDHHMMSS_EN);
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}


	public String getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = DateUtil.dateToStr(modificationDate, DateUtil.YYMMDDHHMMSS_EN);
	}


	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
}

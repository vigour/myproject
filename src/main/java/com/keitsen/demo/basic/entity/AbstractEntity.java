package com.keitsen.demo.basic.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.ToStringBuilder;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable{

	@Transient
	protected static final long serialVersionUID = 5639006408483990605L;
	
	/**
	 * 是否显示
	 */
	protected boolean visible = true;
	
	/**
	 * 状态
	 */
	protected int status = 1;
	
	/**
	 * 创建时间
	 */
	protected Date creationDate = new Date();
	
	
	/**
	 * 最后修改时间
	 */
	protected Date modificationDate;
	
	
	/**
	 * 备注
	 */
	protected String remark;
	
	/**
     * 版本号 乐观锁
     */
    protected int version;
    
    
    
	@Column(nullable = false)
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	@Column(name="status")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creattion_date", nullable = false, updatable = false)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modification_date")
	public Date getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}
	

	@Column(name = "remark",length=200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Version
    @Column(name = "version")
	@XmlTransient
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	

	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	

}

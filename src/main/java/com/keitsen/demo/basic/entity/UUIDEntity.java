package com.keitsen.demo.basic.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import com.keitsen.demo.module.user.entity.User;


@SuppressWarnings("serial")
@MappedSuperclass
public class UUIDEntity extends AbstractEntity {

	private String id;
	
	/**
	 * 是否显示
	 */
	private boolean visible = true;
	
	/**
	 * 状态
	 */
	private int status;
	
	/**
	 * 创建时间
	 */
	private Date creationDate;
	
	/**
	 * 创建人
	 */
	private User creator;	
	
	/**
	 * 最后修改时间
	 */
	private Date modificationDate;
	
	/**
	 * 修改人
	 */
	private User modifier;	
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
     * 版本号 乐观锁
     */
    private int version;

	@Id
	@Column(name = "ID", unique = true, nullable = false, insertable = true, updatable = true, length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
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
	
	@ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "creator")
	@Fetch(FetchMode.SELECT)
    @Basic(fetch = FetchType.LAZY)
	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modification_date")
	public Date getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}
	
	@ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "modifier")
	@Fetch(FetchMode.SELECT)
    @Basic(fetch = FetchType.LAZY)
	public User getModifier() {
		return modifier;
	}

	public void setModifier(User modifier) {
		this.modifier = modifier;
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
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	
	
	
}

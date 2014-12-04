package com.keitsen.demo.module.user.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.keitsen.demo.basic.entity.Module;
import com.keitsen.demo.basic.entity.UUIDEntity;
import com.keitsen.demo.basic.vo.VO;
import com.keitsen.demo.module.role.entity.Role;
import com.keitsen.demo.module.user.vo.UserVO;

/**
 * 用户模块
 * 
 * @author Miles XP
 *
 */
@Entity
@Table(name = "T_USER")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User extends UUIDEntity implements Module {

	private static final long serialVersionUID = 2508011296395493041L;

	private String username;

	private String password;
	

	private Set<Role> roles = new HashSet<Role>(0);

	@Column(name = "USERNAME")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "PASSWORD")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@ManyToMany(targetEntity = Role.class, fetch = FetchType.LAZY, cascade = {
			CascadeType.MERGE, CascadeType.PERSIST })
	@JoinTable(name = "T_USER_ROLE", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Transient
	public VO getVO() {
		UserVO vo = new UserVO();
		vo.setId(this.getId());
		vo.setUsername(username);
		vo.setPassword(password);
		vo.setStatus(this.getStatus());
		vo.setVisible(this.isVisible());
		if(creator!=null){
			vo.setCreator(creator.getUsername());
			vo.setCreatorId(creator.getId());
		}
		vo.setCreationDate(getCreationDate());
		if(modifier!=null){
			vo.setModifier(modifier.getUsername());
			vo.setModifierId(modifier.getId());
		}
		vo.setModificationDate(getModificationDate());
		vo.setRemark(getRemark());
		return vo;
	}

}

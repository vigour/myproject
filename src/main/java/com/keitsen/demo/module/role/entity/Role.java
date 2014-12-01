package com.keitsen.demo.module.role.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.keitsen.demo.basic.entity.Module;
import com.keitsen.demo.basic.entity.UUIDEntity;
import com.keitsen.demo.basic.vo.VO;
import com.keitsen.demo.module.company.entity.Company;
import com.keitsen.demo.module.user.entity.User;


@Entity
@Table(name = "T_ROLE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role extends UUIDEntity implements Module {

	private static final long serialVersionUID = -4927133809653880092L;

	/**
	 * 角色代码
	 */
	private String RoleCode;

	/**
	 * 角色名称
	 */
	private String RoleName;
	
	/**
	 * 显示
	 */
	private int showOrder;

	/**
	 * 机构
	 */
	private Company company;

	/**
	 * 用户关联
	 */
	private Set<User> users;

	/**
	 * 模块关联
	 */
//	private Set<Function> functions = new HashSet<Function>(0);
	private Set<RoleFunctionResource> roleFunctionResources = new HashSet<RoleFunctionResource>(0);

	
	@Column(name = "ROLE_CODE")
	public String getRoleCode() {
		return RoleCode;
	}

	public void setRoleCode(String roleCode) {
		RoleCode = roleCode;
	}

	@Column(name = "ROLE_NAME")
	public String getRoleName() {
		return RoleName;
	}

	public void setRoleName(String roleName) {
		RoleName = roleName;
	}

	@Column(name = "SHOW_ORDER")
	public int getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

//	@ManyToMany(targetEntity = Function.class, fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
//	@JoinTable(name = "T_COMPANY_ROLE_FUNCTION", 
//	joinColumns = { @JoinColumn(name = "ROLE_ID") }, inverseJoinColumns = { @JoinColumn(name = "FUNCTION_ID") })
//	public Set<Function> getFunctions() {
//		return functions;
//	}
//
//	public void setFunctions(Set<Function> functions) {
//		this.functions = functions;
//	}
	
	
	//@OneToMany(mappedBy = "role",cascade=CascadeType.ALL)
	@OneToMany(targetEntity = RoleFunctionResource.class, cascade = { CascadeType.ALL }, mappedBy = "role")
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("showOrder")
	public Set<RoleFunctionResource> getRoleFunctionResources() {
		return roleFunctionResources;
	}

	public void setRoleFunctionResources(
			Set<RoleFunctionResource> roleFunctionResources) {
		this.roleFunctionResources = roleFunctionResources;
	}


	@Override
	@Transient
	public VO getVO() {
		return null;
	}
}

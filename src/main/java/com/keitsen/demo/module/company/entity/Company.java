package com.keitsen.demo.module.company.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
import com.keitsen.demo.module.domain.entity.Region;
import com.keitsen.demo.module.function.entity.Function;
import com.keitsen.demo.module.role.entity.Role;
import com.keitsen.demo.module.role.entity.RoleFunctionResource;
import com.keitsen.demo.module.user.entity.User;

/**
 * 机构
 * 
 * @author Miles XP
 *
 */
@Entity
@Table(name = "T_COMPANY")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Company extends UUIDEntity implements Module {

	private static final long serialVersionUID = -8882369681258017083L;

	/**
	 * 机构编码
	 */
	private String companyCode;

	/**
	 * 机构名称
	 */
	private String companyName;

	/**
	 * 机构地址
	 */
	private String companyAddress;

	/**
	 * 简称
	 */
	private String shortName;

	/**
	 * 联系人
	 */
	private String contact;

	/**
	 * 电话
	 */
	private String phone;

	/**
	 * 电话
	 */
	private String otherPhone;

	/**
	 * 传真
	 */
	private String fax;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 显示顺序
	 */
	private int showOrder;

	/**
	 * 负责人
	 */
	private User principal;

	/**
	 * 区域
	 */
	private Region region;

	/**
	 * 父机构
	 */
	private Company parentCompany;

	/**
	 * 子机构
	 */
	private Set<Company> childCompany = new HashSet<Company>();

	/**
	 * 机构拥有的角色
	 */
	private Set<Role> roles = new HashSet<Role>(0);

	/**
	 * 机构拥有的功能
	 */
	private Set<Function> functions = new HashSet<Function>(0);
	
	/**
	 * 机构拥有功能资源权限
	 */
	private Set<RoleFunctionResource> roleFunctionResources = new HashSet<RoleFunctionResource>(0);

	@Column(name = "COMPANY_CODE")
	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	@Column(name = "COMPANY_NAME")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Column(name = "COMPANY_ADDRESS")
	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	@Column(name = "SHORT_NAME")
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Column(name = "CONTACT")
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Column(name = "PHONE")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "PRINCIPAL")
	public User getPrincipal() {
		return principal;
	}

	public void setPrincipal(User principal) {
		this.principal = principal;
	}

	@Column(name = "OTHER_PHONE")
	public String getOtherPhone() {
		return otherPhone;
	}

	public void setOtherPhone(String otherPhone) {
		this.otherPhone = otherPhone;
	}

	@Column(name = "FAX")
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "SHOW_ORDER")
	public int getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REGION_ID")
	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_COMPANY_ID")
	public Company getParentCompany() {
		return parentCompany;
	}

	public void setParentCompany(Company parentCompany) {
		this.parentCompany = parentCompany;
	}

	@OneToMany(targetEntity = Company.class, cascade = { CascadeType.ALL }, mappedBy = "parentCompany")
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("showOrder")
	public Set<Company> getChildCompany() {
		return childCompany;
	}

	public void setChildCompany(Set<Company> childCompany) {
		this.childCompany = childCompany;
	}

	@OneToMany(targetEntity = Role.class, cascade = { CascadeType.ALL }, mappedBy = "company")
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("showOrder")
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@ManyToMany(targetEntity = Function.class, fetch = FetchType.LAZY, cascade = {
			CascadeType.MERGE, CascadeType.PERSIST })
	@JoinTable(name = "T_COMPANY_FUNCTION", joinColumns = { @JoinColumn(name = "COMPANY_ID") }, inverseJoinColumns = { @JoinColumn(name = "FUNCTION_ID") })
	@OrderBy("showOrder")
	public Set<Function> getFunctions() {
		return functions;
	}

	public void setFunctions(Set<Function> functions) {
		this.functions = functions;
	}

	@OneToMany(targetEntity = RoleFunctionResource.class, cascade = { CascadeType.ALL }, mappedBy = "company")
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
		// TODO Auto-generated method stub
		return null;
	}

}

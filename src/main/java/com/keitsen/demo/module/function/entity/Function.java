package com.keitsen.demo.module.function.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import com.keitsen.demo.module.company.entity.Company;
import com.keitsen.demo.module.function.vo.FunctionVO;
import com.keitsen.demo.module.role.entity.RoleFunctionResource;

/**
 * 功能模块
 * 
 * @author Miles XP
 *
 */
@Entity
@Table(name = "T_FUNCTION")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Function extends UUIDEntity implements Module {

	private static final long serialVersionUID = -9155141931401742135L;

	public Function() {
		super();
	}

	/**
	 * 功能名称
	 */
	private String functionName;

	/**
	 * 图标
	 */
	private String icon;
	
	/**
	 * 功能URL
	 */
	private String url;

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

	/**
	 * 父功能
	 */
	private Function parentFunction;

	/**
	 * 子功能
	 */
	private Set<Function> childFunction = new HashSet<Function>(0);

	/**
	 * 对应机构
	 */
	private Set<Company> companies = new HashSet<Company>(0);

	/**
	 * 资源权限
	 */
	private List<FunctionResource> functionResources = new ArrayList<FunctionResource>();

	/**
	 * 角色资源权限
	 */
	private Set<RoleFunctionResource> roleFunctionResource = new HashSet<RoleFunctionResource>(0);

	@Column(name = "FUNCTION_NAME")
	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	@Column(name = "ICON")
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "URL")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "LEVEL")
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Column(name = "LEAF_ID")
	public String getLeafId() {
		return leafId;
	}

	public void setLeafId(String leafId) {
		this.leafId = leafId;
	}

	@Column(name = "SHOW_ORDER")
	public int getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_FUNCTION_ID")
	public Function getParentFunction() {
		return parentFunction;
	}

	public void setParentFunction(Function parentFunction) {
		this.parentFunction = parentFunction;
	}

	@OneToMany(targetEntity = Function.class, cascade = { CascadeType.ALL }, mappedBy = "parentFunction")
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("showOrder")
	public Set<Function> getChildFunction() {
		return childFunction;
	}

	public void setChildFunction(Set<Function> childFunction) {
		this.childFunction = childFunction;
	}

	@ManyToMany(targetEntity = Company.class, fetch = FetchType.LAZY, cascade = {
			CascadeType.MERGE, CascadeType.PERSIST })
	@JoinTable(name = "T_COMPANY_FUNCTION", joinColumns = { @JoinColumn(name = "FUNCTION_ID") }, inverseJoinColumns = { @JoinColumn(name = "COMPANY_ID") })
	public Set<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(Set<Company> companies) {
		this.companies = companies;
	}

	@OneToMany(targetEntity = FunctionResource.class, cascade = { CascadeType.ALL }, mappedBy = "function")
	@Fetch(FetchMode.SUBSELECT)
	public List<FunctionResource> getFunctionResources() {
		return functionResources;
	}

	public void setFunctionResources(List<FunctionResource> functionResources) {
		this.functionResources = functionResources;
	}

	@OneToMany(targetEntity = RoleFunctionResource.class, cascade = { CascadeType.ALL }, mappedBy = "function")
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("showOrder")
	public Set<RoleFunctionResource> getRoleFunctionResource() {
		return roleFunctionResource;
	}

	public void setRoleFunctionResource(
			Set<RoleFunctionResource> roleFunctionResource) {
		this.roleFunctionResource = roleFunctionResource;
	}

	@Transient
	public VO getVO() {
		FunctionVO vo = new FunctionVO();
		vo.setId(getId());
		vo.setFunctionName(functionName);
		vo.setIcon(icon);
		vo.setUrl(url);
		vo.setLeafId(leafId);
		vo.setLevel(level);
		vo.setShowOrder(showOrder);
		if(parentFunction != null){
			vo.setParentFunction(parentFunction.getId());
			vo.setParentFunctionName(parentFunction.getFunctionName());
		}
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
		vo.setVisible(this.isVisible());
		vo.setStatus(getStatus());
		return vo;
	}

}

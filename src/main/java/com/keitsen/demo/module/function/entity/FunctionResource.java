package com.keitsen.demo.module.function.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
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
import com.keitsen.demo.module.role.entity.RoleFunctionResource;

@Entity
@Table(name = "T_FUNCTION_RESOURCE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FunctionResource extends UUIDEntity implements Module {

	private static final long serialVersionUID = 2879722921424107899L;

	/**
	 * 资源类型
	 */
	private String resourceType;

	/**
	 * 资源编码
	 */
	private String resourceCode;

	/**
	 * 资源名称
	 */
	private String resourceName;

	/**
	 * 资源值
	 */
	private String resourceValue;

	/**
	 * 值类型
	 */
	private String valueType;

	/**
	 * 枚举类型
	 */
	private String enumType;
	/**
	 * 显示顺序
	 */
	private int showOrder;

	/**
	 * 父模块
	 */
	private Function function;
	
	/**
	 * 角色权限资源
	 */
	private Set<RoleFunctionResource> roleFunctionResource = new HashSet<RoleFunctionResource>(0);
	
	

	@Column(name = "RESOURCE_TYPE")
	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	@Column(name = "RESOURCE_CODE")
	public String getResourceCode() {
		return resourceCode;
	}

	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}

	@Column(name = "RESOURCE_NAME")
	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	@Column(name = "RESOURCE_VALUE")
	public String getResourceValue() {
		return resourceValue;
	}

	public void setResourceValue(String resourceValue) {
		this.resourceValue = resourceValue;
	}

	@Column(name = "VALUE_TYPE")
	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	@Column(name = "ENUM_TYPE")
	public String getEnumType() {
		return enumType;
	}

	public void setEnumType(String enumType) {
		this.enumType = enumType;
	}

	@Column(name = "SHOW_ORDER")
	public int getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FUNCTION")
	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}
	
	
	@OneToMany(targetEntity = RoleFunctionResource.class, cascade = { CascadeType.ALL }, mappedBy = "functionResource")
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
		// TODO Auto-generated method stub
		return null;
	}

}

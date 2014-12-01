package com.keitsen.demo.module.role.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.keitsen.demo.basic.entity.Module;
import com.keitsen.demo.basic.entity.UUIDEntity;
import com.keitsen.demo.basic.vo.VO;
import com.keitsen.demo.module.company.entity.Company;
import com.keitsen.demo.module.function.entity.Function;
import com.keitsen.demo.module.function.entity.FunctionResource;

@Entity
@Table(name = "T_ROLE_FUNCTION_RESOURCE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RoleFunctionResource extends UUIDEntity implements Module{
	
	private static final long serialVersionUID = -3351396522815209469L;

	private Role role;
	
	private Function function;

	private Company company;
	
	private FunctionResource functionResource;
	
	private String resourceValue;
	
	private int showOrder;

	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "ROLE_ID", unique = true)
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}


	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "FUNCTION_ID", unique = true)
	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}

	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "COMPANY_ID", unique = true)
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "RESOURCE_ID", unique = true)
	public FunctionResource getFunctionResource() {
		return functionResource;
	}

	public void setFunctionResource(FunctionResource functionResource) {
		this.functionResource = functionResource;
	}

	@Column(name = "RESOURCE_VALUE")
	public String getResourceValue() {
		return resourceValue;
	}

	public void setResourceValue(String resourceValue) {
		this.resourceValue = resourceValue;
	}

	@Column(name = "SHOW_ORDER")
	public int getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}

	@Override
	@Transient
	public VO getVO() {
		// TODO Auto-generated method stub
		return null;
	}
	

}

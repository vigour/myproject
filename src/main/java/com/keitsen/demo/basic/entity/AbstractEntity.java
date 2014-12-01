package com.keitsen.demo.basic.entity;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class AbstractEntity implements Serializable{

	private static final long serialVersionUID = 5639006408483990605L;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	

}

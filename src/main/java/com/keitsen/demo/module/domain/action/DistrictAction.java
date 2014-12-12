package com.keitsen.demo.module.domain.action;

import java.util.List;

import com.keitsen.demo.basic.BasicConstants;
import com.keitsen.demo.basic.action.BasicAction;
import com.keitsen.demo.module.domain.vo.DistrictTreeVO;
import com.keitsen.demo.module.domain.vo.DistrictVO;

public class DistrictAction extends BasicAction<DistrictVO>{

	private static final long serialVersionUID = 7296117116184286640L;

	/**
	 * 列出全部省市地区
	 */
	public String execute() throws Exception {
		
		return BasicConstants.MODULE_LIST;
	}
	
	
	public String getDistrictTree() throws Exception{
		//TODO
		return null;
		
	}
}

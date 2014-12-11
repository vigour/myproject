package com.keitsen.demo.module.domain.vo;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.keitsen.demo.basic.entity.Module;
import com.keitsen.demo.basic.vo.UUIDEntityVO;
import com.keitsen.demo.basic.vo.VO;
import com.keitsen.demo.module.domain.entity.Region;
import com.keitsen.demo.module.user.entity.User;

public class RegionVO extends UUIDEntityVO implements VO {

	private static final long serialVersionUID = -2575406694810374572L;

	/**
	 * 区域名称
	 */
	private String regionName;

	/**
	 * 区域代码
	 */
	private String regionCode;

	/**
	 * 简称
	 */
	private String shortName;

	/**
	 * 区域描述
	 */
	private String description;

	
	
	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Module getModule() {
		Region model = new Region();
		model.setId(this.getId());
		model.setRegionName(regionName);
		model.setRegionCode(regionCode);
		model.setShortName(shortName);
		model.setDescription(description);
		
		model.setVisible(isVisible());
		model.setStatus(getStatus());
		model.setModificationDate(new Date());
		model.setRemark(getRemark());
		
		if(StringUtils.isBlank(model.getId())){
			User creator = new User();
			creator.setId(getCreatorId());
			model.setCreator(creator);
			model.setCreationDate(new Date());
		}else{
			User modifier = new User();
			modifier.setId(getModifierId());
			model.setModifier(modifier);
			model.setModificationDate(new Date());
		}
		return model;
	}

}

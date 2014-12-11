package com.keitsen.demo.module.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.keitsen.demo.basic.entity.Module;
import com.keitsen.demo.basic.entity.UUIDEntity;
import com.keitsen.demo.basic.vo.VO;
import com.keitsen.demo.module.domain.vo.RegionVO;

/**
 * 区域
 * 
 * @author Miles XP
 *
 */
@Entity
@Table(name = "T_REGION")
//默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Region extends UUIDEntity implements Module {

	private static final long serialVersionUID = -3675700809923038377L;

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

	@Column(name = "REGION_NAME")
	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	@Column(name = "REGION_CODE")
	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	@Column(name = "SHORT_NAME")
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	@Transient
	public VO getVO() {
		RegionVO vo = new RegionVO();
		vo.setId(this.getId());
		vo.setRegionName(regionName);
		vo.setRegionCode(regionCode);
		vo.setShortName(shortName);
		vo.setDescription(description);
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

package com.keitsen.demo.module.domain.vo;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.keitsen.demo.basic.entity.Module;
import com.keitsen.demo.basic.vo.UUIDEntityVO;
import com.keitsen.demo.basic.vo.VO;
import com.keitsen.demo.module.domain.entity.District;
import com.keitsen.demo.module.domain.entity.Region;
import com.keitsen.demo.module.user.entity.User;

public class DistrictVO extends UUIDEntityVO  implements VO{

	private static final long serialVersionUID = -5087648300859795500L;
	
	/**
	 * 名称
	 */
	private String districtName;

	/**
	 * 简写
	 */
	private String shortName;

	/**
	 * 编码
	 */
	private String code;

	/**
	 * 类型：11:省,12:直辖市,13:自治区,14:特别行政区,\r\n21:市, 31:市辖区,6:县
	 */
	private int type;
	
	/**
	 * 3位数字组成
	 */
	private String typeCode;
	

	/**
	 * 邮编
	 */
	private String zip;

	/**
	 * 区号
	 */
	private String zoneCode;

	/**
	 * 后缀名
	 */
	private String suffix;

	/**
	 * URL
	 */
	private String url;

	/**
	 * 图标
	 */
	private String icon;

	/**
	 * 描述
	 */
	private String description;
	
	/**
	 * 地区
	 */
	private String region;
	
	/**
	 * 地区名称
	 */
	private String regionName;

	/**
	 * 层级
	 */
	private int level;

	/**
	 * 叶子ID
	 */
	private String leafId;

	/**
	 * 显示顺序
	 */
	private int showOrder;

	/**
	 * 上级地区
	 */
	private String parentDistrict;
	
	
	/**
	 * 上级地区名称
	 */
	private String parentDistrictName;


	public String getDistrictName() {
		return districtName;
	}


	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}


	public String getShortName() {
		return shortName;
	}


	public void setShortName(String shortName) {
		this.shortName = shortName;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	public String getTypeCode() {
		return typeCode;
	}


	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}


	public String getZip() {
		return zip;
	}


	public void setZip(String zip) {
		this.zip = zip;
	}


	public String getZoneCode() {
		return zoneCode;
	}


	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}


	public String getSuffix() {
		return suffix;
	}


	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getIcon() {
		return icon;
	}


	public void setIcon(String icon) {
		this.icon = icon;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getRegion() {
		return region;
	}


	public void setRegion(String region) {
		this.region = region;
	}


	public String getRegionName() {
		return regionName;
	}


	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}


	public int getLevel() {
		return level;
	}


	public void setLevel(int level) {
		this.level = level;
	}


	public String getLeafId() {
		return leafId;
	}


	public void setLeafId(String leafId) {
		this.leafId = leafId;
	}


	public int getShowOrder() {
		return showOrder;
	}


	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}


	public String getParentDistrict() {
		return parentDistrict;
	}


	public void setParentDistrict(String parentDistrict) {
		this.parentDistrict = parentDistrict;
	}


	public String getParentDistrictName() {
		return parentDistrictName;
	}


	public void setParentDistrictName(String parentDistrictName) {
		this.parentDistrictName = parentDistrictName;
	}


	@Override
	public Module getModule() {
		District model = new District();
		model.setId(this.getId());
		model.setDistrictName(districtName);
		model.setShortName(shortName);
		model.setCode(code);
		model.setType(type);
		model.setTypeCode(typeCode);
		model.setZip(zip);
		model.setZoneCode(zoneCode);
		model.setSuffix(suffix);
		model.setUrl(url);
		model.setIcon(icon);
		model.setDescription(description);

		if(StringUtils.isNotBlank(region)){
			Region reg = new Region();
			reg.setId(region);
			model.setRegion(reg);
		}

		model.setLevel(level);
		model.setLeafId(leafId);
		model.setShowOrder(showOrder);
		
		if(StringUtils.isNotBlank(parentDistrict)){
			District parentDist = new District();
			
			parentDist.setId(parentDistrict);
		}
		
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
		
		return null;
	}


	
}

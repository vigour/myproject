package com.keitsen.demo.module.domain.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.keitsen.demo.basic.entity.Module;
import com.keitsen.demo.basic.entity.UUIDEntity;
import com.keitsen.demo.basic.vo.VO;

/**
 * 按照中国行政区域划分：省/直辖市(LV1)--市/自治县/自治州(LV2)--区/县(LV3)--街道/镇/乡(LV4)--社区/居委会/村委会(LV5)
 * 
 * 
 * @author Miles XP
 *
 */
@Entity
@Table(name = "T_DISTRICT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class District extends UUIDEntity implements Module {

	private static final long serialVersionUID = -3730364255005279631L;
	
	

	public District() {
	}
	
	

	public District(String districtName, String code, String zip,
			String zoneCode, String suffix, String description, int level) {
		super();
		this.districtName = districtName;
		this.code = code;
		this.zip = zip;
		this.zoneCode = zoneCode;
		this.suffix = suffix;
		this.description = description;
		this.level = level;
	}
	
	



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
	private Region region;

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
	private District parentDistrict;

	/**
	 * 下级地区
	 */
	private Set<District> childDistrict = new HashSet<District>(0);

	
	
	@Column(name = "DISTRICT_NAME")
	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	@Column(name = "SHORT_NAME")
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Column(name = "CODE")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "TYPE")
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Column(name = "TYPE_CODE")
	public String getTypeCode() {
		return typeCode;
	}



	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}



	@Column(name = "ZIP", length = 6)
	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	@Column(name = "ZONE_CODE")
	public String getZoneCode() {
		return zoneCode;
	}

	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

	@Column(name = "SUFFIX")
	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	@Column(name = "URL")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "ICON")
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REGION_ID")
	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}
	
	@Column(name= "LEVEL")
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
	@JoinColumn(name = "PARENT_DISTRICT_ID")
	public District getParentDistrict() {
		return parentDistrict;
	}

	public void setParentDistrict(District parentDistrict) {
		this.parentDistrict = parentDistrict;
	}

	@OneToMany(targetEntity = District.class, cascade = { CascadeType.ALL }, mappedBy = "parentDistrict")
	@Fetch(FetchMode.SUBSELECT)
	//@OrderBy("showOrder")
	public Set<District> getChildDistrict() {
		return childDistrict;
	}

	public void setChildDistrict(Set<District> childDistrict) {
		this.childDistrict = childDistrict;
	}

	@Transient
	public VO getVO() {
		// TODO Auto-generated method stub
		return null;
	}

}

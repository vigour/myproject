package com.keitsen.demo.module.domain.dao;

import java.util.List;

import com.keitsen.demo.basic.dao.IBasicDao;
import com.keitsen.demo.module.domain.entity.District;

public interface IDistrictDao extends IBasicDao<District, String> {

	final static String DAO_NAME = "districtDao";
	
	
	List<District>  getChildrenDistrict(String id);
}

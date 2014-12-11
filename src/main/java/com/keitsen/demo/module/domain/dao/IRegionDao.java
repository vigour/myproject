package com.keitsen.demo.module.domain.dao;

import com.keitsen.demo.basic.dao.IBasicDao;
import com.keitsen.demo.module.domain.entity.Region;

public interface IRegionDao extends IBasicDao<Region, String> {

	final static String DAO_NAME = "regionDao";
}

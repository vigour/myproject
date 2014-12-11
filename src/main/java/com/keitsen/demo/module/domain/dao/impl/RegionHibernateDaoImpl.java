package com.keitsen.demo.module.domain.dao.impl;

import org.springframework.stereotype.Repository;

import com.keitsen.demo.basic.dao.impl.HibernateBasicDao;
import com.keitsen.demo.module.domain.dao.IRegionDao;
import com.keitsen.demo.module.domain.entity.Region;

@Repository(IRegionDao.DAO_NAME)
public class RegionHibernateDaoImpl extends HibernateBasicDao<Region, String> implements IRegionDao{

}

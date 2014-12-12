package com.keitsen.demo.module.domain.dao.impl;

import org.springframework.stereotype.Repository;

import com.keitsen.demo.basic.dao.impl.HibernateBasicDao;
import com.keitsen.demo.module.domain.dao.IDistrictDao;
import com.keitsen.demo.module.domain.entity.District;

@Repository(IDistrictDao.DAO_NAME)
public class DistrictHibernateDaoImpl extends HibernateBasicDao<District, String>
		implements IDistrictDao {

}

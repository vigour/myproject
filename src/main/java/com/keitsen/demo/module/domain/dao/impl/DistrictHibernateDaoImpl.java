package com.keitsen.demo.module.domain.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.keitsen.demo.basic.dao.impl.HibernateBasicDao;
import com.keitsen.demo.module.domain.dao.IDistrictDao;
import com.keitsen.demo.module.domain.entity.District;

@Repository(IDistrictDao.DAO_NAME)
public class DistrictHibernateDaoImpl extends HibernateBasicDao<District, String>
		implements IDistrictDao {

	@Override
	public List<District> getChildrenDistrict(String id) {
		 return this.find(" from District o where o.parentDistrict.id=? order by o.code ", id);
	}

}

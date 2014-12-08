package com.keitsen.demo.module.function.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.keitsen.demo.basic.dao.impl.HibernateBasicDao;
import com.keitsen.demo.module.function.dao.IFunctionDao;
import com.keitsen.demo.module.function.entity.Function;

@Repository(IFunctionDao.DAO_NAME)
public class FunctionDaoHibernateImpl extends HibernateBasicDao<Function, String> implements IFunctionDao{

	public List<Function> getChildrenFunction(String id){
		return this.find(" from Function o where o.parentFunction.id=?", id);
	}

}

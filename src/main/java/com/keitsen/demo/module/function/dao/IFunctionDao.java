package com.keitsen.demo.module.function.dao;

import java.util.List;

import com.keitsen.demo.basic.dao.IBasicDao;
import com.keitsen.demo.module.function.entity.Function;

public interface IFunctionDao extends IBasicDao<Function, String>{

	
	final static String DAO_NAME = "functionDao";
	
	List<Function> getChildrenFunction(String id);
}

package com.keitsen.demo.module.function.dao;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.keitsen.demo.basic.AbstractTestCase;
import com.keitsen.demo.module.function.entity.Function;

public class FunctionDaoImplHibernateTest extends AbstractTestCase{
	
	@Resource(name = "functionDao")
	private IFunctionDao functionDao;
	
	@Test
	public void testSave(){
		
		Function function = new Function();
		function.setFunctionName("主菜单功能");
		function.setCreationDate(new Date());
		function.setModificationDate(new Date());
		
		Function parentFunction = new Function();
		
		parentFunction.setFunctionName("根菜单功能");
		parentFunction.setCreationDate(new Date());
		parentFunction.setModificationDate(new Date());
		parentFunction.setParentFunction(null);
		function.setParentFunction(parentFunction);
		parentFunction.getChildFunction().add(function);
		
		functionDao.save(function);
		
	}

}

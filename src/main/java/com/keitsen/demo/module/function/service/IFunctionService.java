package com.keitsen.demo.module.function.service;

import java.util.List;

import com.keitsen.demo.basic.entity.PageModel;
import com.keitsen.demo.basic.service.IBasicService;
import com.keitsen.demo.module.function.entity.Function;
import com.keitsen.demo.module.function.vo.FunctionTreeVO;
import com.keitsen.demo.module.function.vo.FunctionVO;

public interface IFunctionService extends IBasicService<Function, String>{

	final static String SERVICE_NAME = "functionService";
	
	List<FunctionTreeVO> getFunctionTreeByParentId(String id);

	PageModel<FunctionVO> getChildFunction(PageModel<FunctionVO> pager, String id);

	void createFunction(FunctionVO vo);
}

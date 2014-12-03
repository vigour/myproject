package com.keitsen.demo.module.function.action;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import com.keitsen.demo.basic.action.BasicAction;
import com.keitsen.demo.module.function.service.IFunctionService;
import com.keitsen.demo.module.function.vo.FunctionTreeVO;
import com.keitsen.demo.module.function.vo.FunctionVO;

//@Controller
//@Namespace("/")
public class FunctionAction extends BasicAction<FunctionVO >{

	private static final long serialVersionUID = 6220038897701201113L;

	private String id;
	
	private IFunctionService functionService;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	@Resource(name = IFunctionService.SERVICE_NAME)
	public void setFunctionService(IFunctionService functionService) {
		this.functionService = functionService;
	}



	
	public String getFunctionTree() throws Exception{
		List<FunctionTreeVO> functionTree = this.functionService.getFunctionTreeByParentId(getId());
		renderString(JSONArray .fromObject(functionTree).toString());
		return null;				
	}
}

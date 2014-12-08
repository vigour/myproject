package com.keitsen.demo.module.function.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.stereotype.Controller;

import net.sf.json.JSONArray;

import com.keitsen.demo.basic.BasicConstants;
import com.keitsen.demo.basic.action.BasicAction;
import com.keitsen.demo.module.function.service.IFunctionService;
import com.keitsen.demo.module.function.vo.FunctionTreeVO;
import com.keitsen.demo.module.function.vo.FunctionVO;

@Controller
@Namespace("/sys/function")
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


	/**
	 * 功能列表
	 */
	public String execute() throws Exception {
		getLog().info("进入功能模块列表");
		return BasicConstants.MODULE_LIST;
	}
	
	
	/**
	 * 功能列表树
	 * @return
	 * @throws Exception
	 */
	public String getFunctionTree() throws Exception{
		List<FunctionTreeVO> functionTree = this.functionService.getFunctionTreeByParentId(getId());
		renderString(JSONArray .fromObject(functionTree).toString());
		return null;				
	}
	
	
	/**
	 * 获取子功能列表
	 * @return
	 * @throws Exception
	 */
	public String getChildFunction() throws Exception{
		getLog().info("分页获取当前数据库功能模块信息");
		pager = functionService.getChildFunction(pager,id);
		
		getLog().info(pager.getTotalRecords());
		renderPageModel(pager);
		return null;
	}
	
}

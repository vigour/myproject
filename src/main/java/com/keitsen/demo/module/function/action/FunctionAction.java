package com.keitsen.demo.module.function.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.stereotype.Controller;

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
	 * 添加模块
	 * @return
	 * @throws Exception
	 */
	public String addFunction() throws Exception{
		getLog().info("保存新增的模块信息");
		vo.setCreatorId(getLoginVO().getLoginId());
		vo.setCreationDate(new Date());
		this.functionService.createFunction(vo);
		result.setStatus("ok");
		result.setType(BasicConstants.RESULT_TYPE_SUCCESS);
		result.setMessage(BasicConstants.CREATE_SUCCESS_MESSAGE);
		renderJson(result.toJsonString());
		return null;
	}
	
	/**
	 * 更新功能
	 * @return
	 * @throws Exception
	 */
	public String updateFunction() throws Exception{
		getLog().info("保存修改的模块信息");
		vo.setModifierId(getLoginVO().getLoginId());
		vo.setModificationDate(new Date());
		this.functionService.modifierFunction(vo);
		result.setStatus("ok");
		result.setType(BasicConstants.RESULT_TYPE_SUCCESS);
		result.setMessage(BasicConstants.EDIT_SUCCESS_MESSAGE);
		renderJson(result.toJsonString());
		return null;
	}
	
	
	
	public String deleteFunction() throws Exception{
		getLog().info("删除模块信息");
		String ids[] = request.getParameter("ids").split(",");
		this.functionService.deleteByIds(ids);
		result.setStatus("ok");
		result.setType(BasicConstants.RESULT_TYPE_SUCCESS);
		result.setMessage(BasicConstants.DELETE_SUCCESS_MESSAGE);
		renderJson(result.toJsonString());
		return null;
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
	 * 列出全部功能列表树
	 * @return
	 * @throws Exception
	 */
	public String getAllFunctionTree() throws Exception{
		List<FunctionTreeVO> functionTree = this.functionService.getAllFunctionTree();
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
		
		renderPageModel(pager);
		return null;
	}
	
}

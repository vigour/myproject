package com.keitsen.demo.module.domain.action;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.stereotype.Controller;

import com.keitsen.demo.basic.BasicConstants;
import com.keitsen.demo.basic.action.BasicAction;
import com.keitsen.demo.module.domain.service.IRegionService;
import com.keitsen.demo.module.domain.vo.RegionVO;

@Controller
@Namespace("/sys/domain")
public class RegionAction extends BasicAction<RegionVO> {

	private static final long serialVersionUID = 2080212132984014962L;

	private IRegionService regionService;

	
	@Resource(name = IRegionService.SERVICE_NAME)
	public void setRegionService(IRegionService regionService) {
		this.regionService = regionService;
	}
	
	/**
	 * 跳转页面
	 */
	@Override
	public String execute() throws Exception {
		getLog().info("进入区域模块列表");
		return BasicConstants.MODULE_LIST;
	}
	
	/**
	 * 分页获取区域列表
	 * @return
	 * @throws Exception
	 */
	public String getAllRegion() throws Exception {
		getLog().info("分页显示区域模块列表");
		pager = regionService.getAllRegionPage(pager);
		renderPageModel(pager);
		return null;
	}
	
	
	/**
	 * 添加区域
	 * @return
	 * @throws Exception
	 */
	public String addRegion() throws Exception{
		getLog().info("保存区域模块信息");
		vo.setCreatorId(getLoginVO().getLoginId());
		vo.setCreationDate(new Date());
		this.regionService.createRegion(vo);
		result.setStatus("ok");
		result.setType(BasicConstants.RESULT_TYPE_SUCCESS);
		result.setMessage(BasicConstants.CREATE_SUCCESS_MESSAGE);
		renderJson(result.toJsonString());
		
		return null;
	}
	
	
	
	/**
	 * 修改区域信息
	 * @return
	 * @throws Exception
	 */
	public String updateRegion() throws Exception{
		getLog().info("保存修改的区域模块信息");
		vo.setModifierId(getLoginVO().getLoginId());
		vo.setModificationDate(new Date());
		this.regionService.modifierRegion(vo);
		result.setStatus("ok");
		result.setType(BasicConstants.RESULT_TYPE_SUCCESS);
		result.setMessage(BasicConstants.EDIT_SUCCESS_MESSAGE);
		renderJson(result.toJsonString());
		return null;
	}
	
	
	/**
	 * 删除区域信息
	 * @return
	 * @throws Exception
	 */
	public String deleteRegions() throws Exception{
		getLog().info("删除用户区域信息");
		String ids[] = request.getParameter("ids").split(",");
		this.regionService.deleteByIds(ids);
		result.setStatus("ok");
		result.setType(BasicConstants.RESULT_TYPE_SUCCESS);
		result.setMessage(BasicConstants.DELETE_SUCCESS_MESSAGE);
		renderJson(result.toJsonString());
		return null;
	}
}

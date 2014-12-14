package com.keitsen.demo.module.domain.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.stereotype.Controller;

import net.sf.json.JSONArray;

import com.keitsen.demo.basic.BasicConstants;
import com.keitsen.demo.basic.action.BasicAction;
import com.keitsen.demo.module.domain.service.IDistrictService;
import com.keitsen.demo.module.domain.vo.DistrictTreeVO;
import com.keitsen.demo.module.domain.vo.DistrictVO;

@Controller
@Namespace("/sys/domain")
public class DistrictAction extends BasicAction<DistrictVO>{

	private static final long serialVersionUID = 7296117116184286640L;

	
	private String id;
	
	private IDistrictService districtService;
	
	
	public IDistrictService getDistrictService() {
		return districtService;
	}

	@Resource(name = IDistrictService.SERVICE_NAME)
	public void setDistrictService(IDistrictService districtService) {
		this.districtService = districtService;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 列出全部省市地区
	 */
	public String execute() throws Exception {
		getLog().info("全部省市地区");
		return BasicConstants.MODULE_LIST;
	}
	
	
	
	/**
	 * 获取子功能列表
	 * @return
	 * @throws Exception
	 */
	public String getChildDistrict() throws Exception{
		getLog().info("分页获取当前数据库地区信息");
		if(StringUtils.isBlank(id)){
			id = this.districtService.getRootDistrict().getId();
		}
		pager = districtService.getChildDistrict(pager,id);
		renderPageModel(pager);
		return null;
	}
	
	
	/**
	 * 树节点显示地区
	 * @return
	 * @throws Exception
	 */
	public String getDistrictTree() throws Exception{
		List<DistrictTreeVO> districtTree = this.districtService.getDistrictTreeByParentId(getId());
		renderString(JSONArray.fromObject(districtTree).toString());
		return null;
		
	}
}

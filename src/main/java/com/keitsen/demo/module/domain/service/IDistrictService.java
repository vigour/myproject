package com.keitsen.demo.module.domain.service;

import java.util.List;

import com.keitsen.demo.basic.entity.PageModel;
import com.keitsen.demo.basic.service.IBasicService;
import com.keitsen.demo.module.domain.entity.District;
import com.keitsen.demo.module.domain.vo.DistrictTreeVO;
import com.keitsen.demo.module.domain.vo.DistrictVO;

public interface IDistrictService extends IBasicService<District, String> {

	final static String SERVICE_NAME = "districtService";
	
	List<DistrictTreeVO> getDistrictTreeByParentId(String id);
	
	District getRootDistrict();

	PageModel<DistrictVO> getChildDistrict(PageModel<DistrictVO> pager,
			String id);
	
}

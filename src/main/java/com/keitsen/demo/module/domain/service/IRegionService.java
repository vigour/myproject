package com.keitsen.demo.module.domain.service;

import com.keitsen.demo.basic.entity.PageModel;
import com.keitsen.demo.basic.service.IBasicService;
import com.keitsen.demo.module.domain.entity.Region;
import com.keitsen.demo.module.domain.vo.RegionVO;

public interface IRegionService extends IBasicService<Region, String> {
	
	final static String SERVICE_NAME = "regionService";

	PageModel<RegionVO> getAllRegionPage(PageModel<RegionVO> pager);

	void createRegion(RegionVO vo);

	void modifierRegion(RegionVO vo);

}

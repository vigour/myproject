package com.keitsen.demo.module.domain.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keitsen.demo.basic.BasicConstants;
import com.keitsen.demo.basic.entity.PageModel;
import com.keitsen.demo.basic.service.impl.BasicService;
import com.keitsen.demo.module.domain.dao.IDistrictDao;
import com.keitsen.demo.module.domain.entity.District;
import com.keitsen.demo.module.domain.service.IDistrictService;
import com.keitsen.demo.module.domain.vo.DistrictTreeVO;
import com.keitsen.demo.module.domain.vo.DistrictVO;

@Service(IDistrictService.SERVICE_NAME)
@Transactional(rollbackFor = Exception.class)
public class DistrictServiceImpl extends BasicService<District, String> implements
		IDistrictService {

	
	private IDistrictDao districtDao;
	
	public IDistrictDao getDistrictDao() {
		return districtDao;
	}

	@Resource(name = IDistrictDao.DAO_NAME)
	public void setDistrictDao(IDistrictDao districtDao) {
		this.districtDao = districtDao;
	}



	
	/**
	 * 封装用于前台显示的树
	 * @param functions
	 * @return
	 */
	private  List<DistrictTreeVO> encapsulationTreeVO(List<District> districts){
		// 初始化树
		List<DistrictTreeVO> districtTreesVO = new ArrayList<DistrictTreeVO>();
		for (District district : districts) {
			DistrictTreeVO districtTreeVO = new DistrictTreeVO();
			districtTreeVO.setId(district.getId());
			districtTreeVO.setText(district.getDistrictName());
			districtTreeVO.setUrl(district.getUrl());
			if(district.getChildDistrict().size() >0){
				districtTreeVO.setState(BasicConstants.TREE_STATE_CLOSE);
			}else {
				districtTreeVO.setState(BasicConstants.TREE_STATE_OPEN);
			}
			
			districtTreeVO.setChecked(0);
			districtTreeVO.setParent_id(district.getParentDistrict().getId());

			districtTreesVO.add(districtTreeVO);
		}
		return districtTreesVO;
	}
	

	@Override
	public List<DistrictTreeVO> getDistrictTreeByParentId(String id) {
		if(StringUtils.isBlank(id)){
			District root = this.findUnique(" FROM District d where d.level = '0'");
			id = root.getId();
		}
		List<District> districts = this.districtDao.getChildrenDistrict(id);
		
		return encapsulationTreeVO(districts);
	}

	@Override
	public District getRootDistrict() {
		return this.districtDao.findUnique(" FROM District d where d.level = '0'");
	}

	@Override
	public PageModel<DistrictVO> getChildDistrict(PageModel<DistrictVO> pager,
			String id) {
		List<DistrictVO> districtVOs = new ArrayList<DistrictVO>();
		LinkedHashMap<Object, Object> equalFields = new LinkedHashMap<Object, Object>();
		if(StringUtils.isBlank(id)){
			District root = this.getRootDistrict();
			id = root.getId();
		}
		equalFields.put("parentDistrict.id", id);
		
		
		LinkedHashMap<String, String> orderFields = new LinkedHashMap<String, String>();
		orderFields.put("code","asc");
		List<District> districts = this.districtDao.findResultList(equalFields, null, null, null, orderFields, null, pager.getFistResult(), pager.getMaxResults());
		for(District f : districts){
			DistrictVO vo = new DistrictVO();	
			vo = (DistrictVO) f.getVO();
			districtVOs.add(vo);
		}
		pager.setList(districtVOs);
		pager.setTotalRecords(this.districtDao.getTotalCount(equalFields, null, null, null, null));
		
		return pager;
	}

}

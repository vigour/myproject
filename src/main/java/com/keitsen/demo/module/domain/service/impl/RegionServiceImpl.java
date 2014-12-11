package com.keitsen.demo.module.domain.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keitsen.demo.basic.entity.PageModel;
import com.keitsen.demo.basic.service.impl.BasicService;
import com.keitsen.demo.module.domain.dao.IRegionDao;
import com.keitsen.demo.module.domain.entity.Region;
import com.keitsen.demo.module.domain.service.IRegionService;
import com.keitsen.demo.module.domain.vo.RegionVO;
import com.keitsen.demo.module.user.entity.User;


@Service(IRegionService.SERVICE_NAME)
@Transactional(rollbackFor = Exception.class)
public class RegionServiceImpl extends BasicService<Region, String> implements
		IRegionService {

	private IRegionDao regionDao;
	
	@Resource(name = IRegionDao.DAO_NAME)
	public void setRegionDao(IRegionDao regionDao) {
		this.regionDao = regionDao;
	}


	
	public IRegionDao getRegionDao() {
		return regionDao;
	}




	private List<RegionVO> encapsulationListVO(List<Region> entities){
		List<RegionVO> VOs = new ArrayList<RegionVO>();
		for(Region u: entities){
			RegionVO vo = new RegionVO();
			vo = (RegionVO) u.getVO();
			VOs.add(vo); 
		}
		return VOs;
	}

	@Override
	public PageModel<RegionVO> getAllRegionPage(PageModel<RegionVO> pager) {
		List<Region> regions =  
				this.regionDao.findResultList(null,null,null,null,null,null,pager.getFistResult(),pager.getMaxResults());
		//将List<module> 转为List<VO>
		pager.setList(encapsulationListVO(regions));
		pager.setTotalRecords(this.regionDao.getTotalCount(null, null, null, null, null));
		
		return pager;
	}



	@Override
	public void createRegion(RegionVO vo) {
		Region region = new Region();
		region = (Region) vo.getModule();
		this.save(region);
	}



	@Override
	public void modifierRegion(RegionVO vo) {
		Region region = this.get(vo.getId());
		if(region != null){
			region.setRegionName(vo.getRegionName());
			region.setRegionCode(vo.getRegionCode());
			region.setShortName(vo.getShortName());
			region.setDescription(vo.getDescription());
			
			region.setVisible(vo.isVisible());
			region.setStatus(vo.getStatus());
			region.setModificationDate(new Date());
			region.setRemark(vo.getRemark());
			
			if(StringUtils.isBlank(region.getId())){
				User creator = new User();
				creator.setId(vo.getCreatorId());
				region.setCreator(creator);
				region.setCreationDate(new Date());
			}else{
				User modifier = new User();
				modifier.setId(vo.getModifierId());
				region.setModifier(modifier);
				region.setModificationDate(new Date());
			}
		}
		this.regionDao.update(region);
		
	}





}

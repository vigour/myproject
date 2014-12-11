package com.keitsen.demo.module.function.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keitsen.demo.basic.BasicConstants;
import com.keitsen.demo.basic.entity.PageModel;
import com.keitsen.demo.basic.service.impl.BasicService;
import com.keitsen.demo.module.function.dao.IFunctionDao;
import com.keitsen.demo.module.function.entity.Function;
import com.keitsen.demo.module.function.service.IFunctionService;
import com.keitsen.demo.module.function.vo.FunctionTreeVO;
import com.keitsen.demo.module.function.vo.FunctionVO;
import com.keitsen.demo.module.user.entity.User;

@Service(IFunctionService.SERVICE_NAME)
@Transactional(rollbackFor = Exception.class)
public class FunctionServiceImpl extends BasicService<Function, String>
		implements IFunctionService {

	private IFunctionDao functionDao;

	public IFunctionDao getFunctionDao() {
		return functionDao;
	}

	@Resource(name = IFunctionDao.DAO_NAME)
	public void setFunctionDao(IFunctionDao functionDao) {
		this.functionDao = functionDao;
	}

	
	/**
	 * 封装用于前台显示的树
	 * @param functions
	 * @return
	 */
	private  List<FunctionTreeVO> encapsulationTreeVO(List<Function> functions){
		// 初始化树
		List<FunctionTreeVO> functionTreesVO = new ArrayList<FunctionTreeVO>();
		for (Function function : functions) {
			FunctionTreeVO functionTreeVO = new FunctionTreeVO();
			functionTreeVO.setId(function.getId());
			functionTreeVO.setText(function.getFunctionName());
			functionTreeVO.setUrl(function.getUrl());

			// 当节点中不是叶子节点时,给定指定的图标
			if (function.getChildFunction().size() > 0) {
				functionTreeVO.setState(BasicConstants.TREE_STATE_CLOSE);
			} else {
				functionTreeVO.setIconCls(function.getIcon());
				functionTreeVO.setState(BasicConstants.TREE_STATE_OPEN);
			}

			functionTreeVO.setChecked(0);
			functionTreeVO.setParent_id(function.getParentFunction().getId());

			functionTreesVO.add(functionTreeVO);
		}
		return functionTreesVO;
	}
	
	
	@Override
	public List<FunctionTreeVO> getFunctionTreeByParentId(String id) {
		
		List<Function> functions = this.functionDao
				.getChildrenFunction(id == null ? BasicConstants.TREE_ROOT_NODE_ID
						: id);
		
		return encapsulationTreeVO(functions);
	}

	
	@Override
	public List<FunctionTreeVO> getAllFunctionTree() {
		List<Function> functions = this.functionDao.getAll();
		return encapsulationTreeVO(functions);
	}
	
	@Override
	public PageModel<FunctionVO> getChildFunction(PageModel<FunctionVO> pager,
			String id) {
		List<FunctionVO> functionVOs = new ArrayList<FunctionVO>();
		LinkedHashMap<Object, Object> equalFields = new LinkedHashMap<Object, Object>();
		equalFields.put("parentFunction.id", id);
		List<Function> functions = this.functionDao.findResultList(equalFields, null, null, null, null, null, pager.getFistResult(), pager.getMaxResults());
		for(Function f : functions){
			FunctionVO vo = new FunctionVO();	
			vo = (FunctionVO) f.getVO();
			functionVOs.add(vo);
		}
		pager.setList(functionVOs);
		pager.setTotalRecords(this.functionDao.getTotalCount(equalFields, null, null, null, null));
		
		return pager;
	}

	
	/**
	 * 新建模块
	 */
	@Override
	public void createFunction(FunctionVO vo) {
		Function entity = new Function();
		entity = (Function) vo.getModule();
		
		//一对多双向关系维护
		Function parent = this.get(vo.getParentFunction());
		entity.setParentFunction(parent);
		
		Set<Function> children = parent.getChildFunction();
		children.add(entity);
		parent.setChildFunction(children);
		
		this.save(entity);
	}

	
	/**
	 * 修改模块
	 */
	@Override
	public void modifierFunction(FunctionVO vo) {
		// TODO Auto-generated method stub
		Function function = this.get(vo.getId());
		if(function != null){
			function.setFunctionName(vo.getFunctionName());
			function.setUrl(vo.getUrl());
			function.setIcon(vo.getIcon());
			function.setShowOrder(vo.getShowOrder());
			
			function.setVisible(vo.isVisible());
			function.setStatus(vo.getStatus());
			function.setModificationDate(new Date());
			function.setRemark(vo.getRemark());
			if(StringUtils.isBlank(function.getId())){
				User creator = new User();
				creator.setId(vo.getCreatorId());
				function.setCreator(creator);
				function.setCreationDate(new Date());
			}else{
				User modifier = new User();
				modifier.setId(vo.getModifierId());
				function.setModifier(modifier);
				function.setModificationDate(new Date());
			}
			
			//一对多双向关系维护
			Function parent = this.get(vo.getParentFunction());
			function.setParentFunction(parent);
			
			Set<Function> children = parent.getChildFunction();
			children.add(function);
			parent.setChildFunction(children);
		}
		
		this.update(function);
	}


}

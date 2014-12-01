package com.keitsen.demo.module.function.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.keitsen.demo.basic.BasicConstants;
import com.keitsen.demo.basic.service.impl.BasicService;
import com.keitsen.demo.module.function.dao.IFunctionDao;
import com.keitsen.demo.module.function.entity.Function;
import com.keitsen.demo.module.function.service.IFunctionService;
import com.keitsen.demo.module.function.vo.FunctionTreeVO;

@Service(IFunctionService.SERVICE_NAME)
public class FunctionServiceImpl extends BasicService<Function, String> implements IFunctionService{

	private IFunctionDao functionDao;

	
	public IFunctionDao getFunctionDao() {
		return functionDao;
	}
	
	@Resource(name = IFunctionDao.DAO_NAME)
	public void setFunctionDao(IFunctionDao functionDao) {
		this.functionDao = functionDao;
	}

	@Override
	public List<FunctionTreeVO> getFunctionTreeByParentId(String id) {
		List<FunctionTreeVO> functionTreesVO = new ArrayList<FunctionTreeVO>();
		
		List<Function> functions = this.functionDao.getChildrenFunction(id==null?BasicConstants.TREE_ROOT_NODE_ID:id);
		for(Function function : functions){
			FunctionTreeVO functionTreeVO = new FunctionTreeVO();
			functionTreeVO.setId(function.getId());
			functionTreeVO.setText(function.getFunctionName());
			functionTreeVO.setUrl(function.getUrl());
			
			//当节点中不是叶子节点时,给定指定的图标
			if(function.getChildFunction().size()>0){
				functionTreeVO.setState(BasicConstants.TREE_STATE_CLOSE);
			}else{
				functionTreeVO.setIconCls(function.getIcon());
				functionTreeVO.setState(BasicConstants.TREE_STATE_OPEN);
			}
			
			functionTreeVO.setChecked(0);
			functionTreeVO.setParent_id(function.getParentFunction().getId());
			
			functionTreesVO.add(functionTreeVO);
		}
		return functionTreesVO;
	}

	
	

	
}

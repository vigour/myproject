package com.keitsen.demo.basic.util;

import java.util.ArrayList;
import java.util.List;

import com.keitsen.demo.basic.entity.Module;
import com.keitsen.demo.basic.vo.VO;


public class ModuleUtil {

	/**
	 * 将Entity实例对象集合转换为实体对应的VO对象集合
	 * 
	 * @author Miles XP
	 *
	 */
	public static List<VO> transformToVO(List<Object> list) {
		List<VO> voList = new ArrayList<VO>();
		if (list != null) {
			for(int i=0,len=list.size(); i<len; i++){
				Module module = (Module) list.get(i);
				voList.add(module.getVO());
			}
		}
		return voList;
	}
}

package com.keitsen.demo.module.user.action;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.stereotype.Controller;

import com.keitsen.demo.basic.BasicConstants;
import com.keitsen.demo.basic.action.BasicAction;
import com.keitsen.demo.module.user.service.IUserService;
import com.keitsen.demo.module.user.vo.UserVO;

@Controller
@Namespace("/sys/user")
public class UserAction extends BasicAction<UserVO>{

	private static final long serialVersionUID = 2868562624590504086L;

	
	private IUserService userService;
	
	@Resource(name = IUserService.SERVICE_NAME)
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}


	/**
	 * 页面跳转
	 */
	public String execute() throws Exception {
		getLog().info("进入用户模块列表");
		return BasicConstants.MODULE_LIST;
	}
	
	
	/**
	 * 分页获取所有用户
	 * @return
	 * @throws Exception
	 */
	public String getAllUser() throws Exception{
		getLog().info("分页获取当前数据库用户信息");
		pager = userService.getAllUserPage(pager);
		renderPageModel(pager);
		return null;
	}
	

	
	/**
	 * 添加用户
	 * @return
	 * @throws Exception
	 */
	public String addUser() throws Exception{
		getLog().info("保存新增的用户信息");
		vo.setCreatorId(getLoginVO().getLoginId());
		vo.setCreationDate(new Date());
		this.userService.createUser(vo);
		result.setStatus("ok");
		result.setType(BasicConstants.RESULT_TYPE_SUCCESS);
		result.setMessage(BasicConstants.CREATE_SUCCESS_MESSAGE);
		renderJson(result.toJsonString());
		return null;
	}
	
	
	/**
	 * 修改用户
	 * @return
	 * @throws Exception
	 */
	public String updateUser() throws Exception{
		getLog().info("保存修改的用户信息");
		vo.setModifierId(getLoginVO().getLoginId());
		vo.setModificationDate(new Date());
		this.userService.modifierUser(vo);
		result.setStatus("ok");
		result.setType(BasicConstants.RESULT_TYPE_SUCCESS);
		result.setMessage(BasicConstants.EDIT_SUCCESS_MESSAGE);
		renderJson(result.toJsonString());
		return null;
	}
	
	
	
	/**
	 * 删除用户
	 * @return
	 * @throws Exception
	 */
	public String deleteUsers() throws Exception{
		getLog().info("删除用户信息");
		String ids[] = request.getParameter("ids").split(",");
		this.userService.deleteByIds(ids);
		result.setStatus("ok");
		result.setType(BasicConstants.RESULT_TYPE_SUCCESS);
		result.setMessage(BasicConstants.DELETE_SUCCESS_MESSAGE);
		renderJson(result.toJsonString());
		return null;
	}
	
	
}

package com.keitsen.demo.module.user.action;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.stereotype.Controller;

import com.keitsen.demo.basic.BasicConstants;
import com.keitsen.demo.basic.action.BasicAction;
import com.keitsen.demo.module.user.service.IUserService;
import com.keitsen.demo.module.user.vo.UserVO;

@Controller
@Namespace("/sys/user")
@Results({ 
	@Result(name = BasicConstants.MODULE_LIST, location = "user_list.jsp") ,
	}
	)
public class UserAction extends BasicAction<UserVO>{

	private static final long serialVersionUID = 2868562624590504086L;

	
	private IUserService userService;
	
	@Resource(name = IUserService.SERVICE_NAME)
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}


	public String execute() throws Exception {
		getLog().info("进入用户模块列表");
		return BasicConstants.MODULE_LIST;
	}
	
	public String getAllUser() throws Exception{
		getLog().info("分页获取当前数据库用户信息");
		pager = userService.getAllUserPage(pager);
		renderPageModel(pager);
		return null;
	}
	
//	protected void writeJson(Object object){
//		PrintWriter pw = null;
//		try {
//			String json = JSONArray.
//			
//		 	response.setContentType("textml;charset=utf-8");
//			pw = response.getWriter();
//			pw.write(json);
//			pw.flush();
//		} catch (Exception e) {
//			throw new Exception("信息返回出错!");
//		}
//		finally{
//			if(pw != null){
//				pw.close();
//			}
//		}
//	}

	
	public String save() throws Exception{
		getLog().info("保存新增的用户信息");
		vo.setCreatorId(getLoginVO().getLoginId());
		vo.setCreationDate(new Date());
		this.userService.createUser(vo);
		result.setType(BasicConstants.RESULT_TYPE_SUCCESS);
		result.setMessage(BasicConstants.CREATE_SUCCESS_MESSAGE);
		renderJson(result.toJsonString());
		return null;
	}
}

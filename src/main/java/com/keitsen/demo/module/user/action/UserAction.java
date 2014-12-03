package com.keitsen.demo.module.user.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.stereotype.Controller;

import com.keitsen.demo.basic.BasicConstants;
import com.keitsen.demo.basic.action.BasicAction;
import com.keitsen.demo.module.user.service.IUserService;
import com.keitsen.demo.module.user.vo.UserVO;

@Controller
@Namespace("/user")
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
		List<UserVO> users = userService.getAllUser();
		
		//Struts2Util.buildPagingJson(renderJsonArray(users), users.size());
		return BasicConstants.MODULE_LIST;
	}
}

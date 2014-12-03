package com.keitsen.demo.module.user.action;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.stereotype.Controller;

import com.keitsen.demo.basic.BasicConstants;
import com.keitsen.demo.basic.action.BasicAction;
import com.keitsen.demo.module.user.vo.UserVO;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Namespace("/user")
@Results({ 
	@Result(name = BasicConstants.MODULE_LIST, location = "user_list.jsp") ,
	}
	)
public class UserAction extends ActionSupport{

	private static final long serialVersionUID = 2868562624590504086L;

	public String execute() throws Exception {
		
		return BasicConstants.MODULE_LIST;
	}
}

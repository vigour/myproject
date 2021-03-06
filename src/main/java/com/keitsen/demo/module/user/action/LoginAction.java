package com.keitsen.demo.module.user.action;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.stereotype.Controller;

import com.keitsen.demo.basic.BasicConstants;
import com.keitsen.demo.basic.util.PathUtil;
import com.keitsen.demo.module.Constants;
import com.keitsen.demo.module.user.entity.User;
import com.keitsen.demo.module.user.service.IUserService;
import com.keitsen.demo.module.user.vo.LoginVO;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Namespace("/")
@Results({ 
	@Result(name = Action.INPUT, location = "login.jsp") ,
	@Result(name = BasicConstants.MAIN_PAGE,  location= "main.jsp")
	}
	)
public class LoginAction extends ActionSupport {

	private static final long serialVersionUID = 2638309282519192796L;

	private final Log logger = LogFactory.getLog(getClass());

	private IUserService userService;

	private LoginVO loginVO = new LoginVO();
	
	private String username;
	
	private String password;

	
	@Resource(name = IUserService.SERVICE_NAME)
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}


	public LoginVO getLoginVO() {
		return loginVO;
	}

	public void setLoginVO(LoginVO loginVO) {
		this.loginVO = loginVO;
	}
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String execute() throws Exception {
		logger.info("项目的根路径：" + PathUtil.root());
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String, Object> session = ActionContext.getContext().getSession();
		if (StringUtils.isEmpty(getUsername()) || StringUtils.isEmpty(getPassword())) {
			request.setAttribute("loginResult", "请输入用户名和密码！");
			return INPUT;
		}

		User user = userService.getUserLogin(username, password);
		if (user != null) {
			loginVO.setLoginId(user.getId());
			loginVO.setUsername(user.getUsername());
			session.put(Constants.CURRENT_LOGIN_USER, getUsername());
			session.put(Constants.CURRENT_LOGIN_VO, loginVO);
			logger.info("登陆成功！！！,用户名为=" + getUsername());
			return SUCCESS;
		}

		logger.info("登陆失败！！！");
		request.setAttribute("loginResult", "用户名或密码错误！");
		return INPUT;
	}
	
	public String mainPage() throws Exception{
		logger.info("用户进入主页面");
		return BasicConstants.MAIN_PAGE;
	}
}

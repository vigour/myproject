package com.keitsen.demo.basic.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.keitsen.demo.basic.entity.OperateResult;
import com.keitsen.demo.basic.entity.PageModel;
import com.keitsen.demo.basic.util.DateJsonValueProcessor;
import com.keitsen.demo.basic.util.DateUtil;
import com.keitsen.demo.basic.util.ReflectionUtil;
import com.keitsen.demo.module.Constants;
import com.keitsen.demo.module.user.vo.LoginVO;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * Struts2中典型CRUD Action的抽象基类.
 * 
 * 主要定义了对Preparable,ModelDriven接口的使用,以及CRUD函数和返回值的命名.
 *
 * @param <T> CRUDAction所管理的对象类型.
 * 
 * @author  
 */
public abstract class BasicAction<T> extends ActionSupport implements ModelDriven<T>, Preparable, ServletRequestAware, ServletResponseAware{

	private static final long serialVersionUID = 521973244692611994L;

	protected  Log logger;// = LogFactory.getLog(getClass());

	protected HttpServletRequest request;
	
	protected HttpServletResponse response;
	
	protected HttpSession session; 
	
	protected T vo;
	
	protected PageModel<T> pager = new PageModel<T>();
	
	//Easy UI分页参数
	protected int page;
	
	protected int rows;
	
	protected String order;	//排序的方式
	
	protected String sort;	//排序的字段
	
	protected LoginVO loginVO;//VO
	
	protected OperateResult result = new OperateResult();
	
	@SuppressWarnings("unchecked")
	public BasicAction(){
		try {
			Class<?> clazz = ReflectionUtil.getSuperClassGenricType(getClass());
			if(clazz != null){
				vo = (T)clazz.newInstance();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;  
        this.session = request.getSession();  
	}

	
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * 实现空的prepare()函数,屏蔽了所有Action函数都会执行的公共的二次绑定.
	 */
	public void prepare() throws Exception {
		
	}
	
	/**
	 * 将数据渲染到前台页面
	 * @param str
	 */
	public void renderString(String str) {
		try {
			response.setContentType("application/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			getLog().info(str);
			out.write(str);
			// 清空缓存
			out.flush();
			// 关闭流
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void renderJsonString(String str) {
		getLog().info(str);
		renderString(str);
	}
	
	//读取Json，防止死循环错误以及时间字段的格式化
	private JsonConfig jsoncfg(String pattern){//yyyy-MM-dd HH:mm:ss
		JsonConfig cfg = new JsonConfig();
		cfg.setIgnoreDefaultExcludes(false);
		cfg.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		cfg.registerJsonValueProcessor(Date.class,new DateJsonValueProcessor(pattern)); //date processor register
		return cfg;
	}
	
	public void renderJson(Object obj) {
		final String pattern = DateUtil.YYMMDDHHMMSS_EN;
		getLog().info(JSONObject.fromObject(obj,jsoncfg(pattern)).toString());
		renderJsonString(JSONObject.fromObject(obj,jsoncfg(pattern)).toString());
	}
	public void renderJson(Object obj,String pattern) {
		getLog().info(JSONObject.fromObject(obj,jsoncfg(pattern)).toString());
		renderJsonString(JSONObject.fromObject(obj,jsoncfg(pattern)).toString());
	}

	public void renderJsonArray(Object array) {
		final String pattern = DateUtil.YYMMDDHHMMSS_EN;
		getLog().info(JSONArray.fromObject(array,jsoncfg(pattern)).toString());
		renderString(JSONArray.fromObject(array,jsoncfg(pattern)).toString());
	}
	
	public void renderJsonArray(Object array,String pattern) {
		getLog().info(JSONArray.fromObject(array,jsoncfg(pattern)).toString());
		renderString(JSONArray.fromObject(array,jsoncfg(pattern)).toString());
	}
	
	/**
	 * 渲染jQuery EasyUI 分页json
	 * @param page
	 */
	public void renderPageModel(PageModel<T> page){
		//此处需要去除自调用的属性
		JsonConfig jsonConfig = new JsonConfig();  
	    jsonConfig.setExcludes(new String[] { "module" });
		String json = "{\"total\":"+ page.getTotalRecords()+",\"rows\":"+JSONArray.fromObject(page.getList(),jsonConfig).toString() +"}";
		renderString(json);
	}
	
	
	/**
	 * 清空Session
	 */
	public void clearSession() {
		for (Enumeration<String> items = session.getAttributeNames(); items
				.hasMoreElements();) {
			String item = (String) items.nextElement();
			getLog().info(item);
			session.removeAttribute(item);
		}
	}

	public T getVo() {
		if(loginVO != null){
			
		}
		return vo;
	}

	public void setVo(T vo) {
		this.vo = vo;
	}
	
	
	public PageModel<T> getPager() {
		return pager;
	}

	public void setPager(PageModel<T> pager) {
		this.pager = pager;
	}

	public int getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = Integer.parseInt(page);
		pager.setPageNum(this.page );
	}
	

	public int getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = Integer.parseInt(rows);
		pager.setPageSize(this.rows);
	}
	
	
	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
		pager.setOrderDirection(order);
	}

	
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
		pager.setOrderField(sort);
	}
	
	protected LoginVO getLoginVO() throws Exception {
		LoginVO loginVO = (LoginVO) this.session.getAttribute(
				Constants.CURRENT_LOGIN_VO);
		if(loginVO == null){
			throw new Exception("当前用户没有登录，或者登录超时 ,请重新登录");
		}
		return loginVO;
	}

	@Override
	public T getModel() {
		return null;
	}

	public Log getLog() {
		return LogFactory.getLog(this.getClass());
	}

	public OperateResult getResult() {
		return result;
	}

	public void setResult(OperateResult result) {
		this.result = result;
	}

	
}

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
import org.apache.struts2.ServletActionContext;

import com.keitsen.demo.basic.entity.PageModel;
import com.keitsen.demo.basic.logger.Logger;
import com.keitsen.demo.basic.util.DateJsonValueProcessor;
import com.keitsen.demo.basic.util.ReflectionUtil;
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
@SuppressWarnings({ "unchecked", "serial" })
public class BasicAction<T>  extends ActionSupport implements ModelDriven<T>, Preparable{

	@Logger
    protected  Log logger;// = LogFactory.getLog(getClass());

    protected Integer pageNum = 1; // 当前是第几页

    protected Integer pageSize = 20; // 每页显示多少条

    protected String orderField; // 排序字段

    protected String orderDirection; // 排序方向

    protected PageModel<Object> pageModel = new PageModel<Object>();

    protected HttpServletRequest request = ServletActionContext.getRequest();

    protected HttpServletResponse response = ServletActionContext.getResponse();

    private String[] ids; // id数组

    protected T vo;
    
    
    private int start;
    
    private int limit;
    
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

    // 将处理信息发送到客户端
    protected void outMsg(String msg) {

        response.setCharacterEncoding("utf-8");
        try {
            PrintWriter out = response.getWriter();
            out.print(msg);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    

    public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}


    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
        pageModel.setPageNum(pageNum);
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
        pageModel.setOrderField(orderField);
    }

    public String getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
        pageModel.setOrderDirection(orderDirection);
    }
    

	public Log getLog() {
		return LogFactory.getLog(this.getClass());
	}

	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	public HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}


    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }

    public T getVo() {
		return vo;
	}

	public void setVo(T vo) {
		this.vo = vo;
	}
	
	
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
		pageModel.setStart(start);
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
		pageModel.setLimit(limit);
		pageModel.setPageSize(limit);
	}

	public void outJsonString(String str) {
		getLog().info(str);
		outString(str);
	}

	public void clearSession() {
		for (Enumeration<String> items = getSession().getAttributeNames(); items
				.hasMoreElements();) {
			String item = (String) items.nextElement();
			getLog().info(item);
			getSession().removeAttribute(item);
		}
	}
	
	//读取Json，防止死循环错误以及时间字段的格式化
	private JsonConfig jsoncfg(String pattern){//yyyy-MM-dd HH:mm:ss
		JsonConfig cfg = new JsonConfig();
		cfg.setIgnoreDefaultExcludes(false);
		cfg.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		cfg.registerJsonValueProcessor(Date.class,new DateJsonValueProcessor(pattern)); //date processor register
		return cfg;
	}
	
	public void outJson(Object obj,String pattern) {
		getLog().info(JSONObject.fromObject(obj,jsoncfg(pattern)).toString());
		outJsonString(JSONObject.fromObject(obj,jsoncfg(pattern)).toString());
	}

	public void outJsonArray(Object array,String pattern) {
		getLog().info(JSONArray.fromObject(array,jsoncfg(pattern)).toString());
		outString(JSONArray.fromObject(array,jsoncfg(pattern)).toString());
	}

	public void outString(String str) {
		try {
			getResponse().setContentType("application/json;charset=UTF-8");
	        getResponse().setCharacterEncoding("UTF-8");
			PrintWriter out = getResponse().getWriter();
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

	/**
	 * 实现空的prepare()函数,屏蔽了所有Action函数都会执行的公共的二次绑定.
	 */
	@Override
	public void prepare() throws Exception {
		
	}

	@Override
	public T getModel() {
		return null;
	}
}

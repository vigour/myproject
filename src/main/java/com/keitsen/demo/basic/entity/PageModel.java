package com.keitsen.demo.basic.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public class PageModel<E> implements Serializable {

	private static final long serialVersionUID = -1517302591113813725L;

	public final static String ORDER_DIRECTION_ASC = "ASC";
	public final static String ORDER_DIRECTION_DESC = "DESC";

	/**
	 * 默认每页记录数
	 */
	public static final int DEFAULT_PAGE_SIZE = 20;

	/**
	 * 结果集
	 */
	private List<E> list = new ArrayList<E>();

	/**
	 * 查询记录数
	 */
	private Long totalRecords;
	
	/**
	 * 总记录数
	 */
	private Long total;
	

	/**
	 * 当前页码
	 */
	protected int pageNum = 1;

	/**
	 * 每页显示多少条
	 */
	protected int pageSize = DEFAULT_PAGE_SIZE;
	
	protected int start;
	
	protected int limit;
	

	// 默认按照id倒序排列
	private String orderField = "";
	private String orderDirection = "";

	//QBC 分页
	private Criterion[] criterions;
	private Order[] orders;

	public List<E> getList() {
		return list;
	}

	public void setList(final List<E> list) {
		this.list = list;
	}

	public Long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}

	/**
	 * 获得当前页的页号,序号从1开始,默认为1.
	 */
	public int getPageNum() {
		return pageNum;
	}
	
	/**
	 * 设置当前页的页号,序号从1开始,低于1时自动调整为1.
	 */
	public void setPageNum(final int pageNum) {
		this.pageNum = pageNum;
		if (pageNum < 1) {
			this.pageNum = 1;
		}
	}

	/**
	 * 获得每页的记录数量, 默认为20.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页的记录数量.
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * 返回Page对象自身的setPageSize函数,可用于连续设置。
	 */
	public PageModel<E> pageSize(final int thePageSize) {
		setPageSize(thePageSize);
		return this;
	}

	/**
	 * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
	 */
	public int getFirst() {
		return ((pageNum - 1) * pageSize) + 1;
	}
	
	
	/**
	 * 根据pageSize与totalCount计算总页数, 默认值为-1.
	 */
	public long getTotalPages() {
		if (totalRecords < 0) {
			return -1;
		}

		long count = totalRecords / pageSize;
		if (totalRecords % pageSize > 0) {
			count++;
		}
		return count;
	}

	/**
	 * 是否还有下一页.
	 */
	public boolean isHasNext() {
		return (pageNum + 1 <= getTotalPages());
	}

	/**
	 * 取得下页的页号, 序号从1开始.
	 * 当前页为尾页时仍返回尾页序号.
	 */
	public int getNextPage() {
		if (isHasNext()) {
			return pageNum + 1;
		} else {
			return pageNum;
		}
	}

	/**
	 * 是否还有上一页.
	 */
	public boolean isHasPre() {
		return (pageNum - 1 >= 1);
	}

	/**
	 * 取得上页的页号, 序号从1开始.
	 * 当前页为首页时返回首页序号.
	 */
	public int getPrePage() {
		if (isHasPre()) {
			return pageNum - 1;
		} else {
			return pageNum;
		}
	}
	
	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}
	
	/**
	 * 返回Page对象自身的setOrderBy函数,可用于连续设置。
	 */
	public PageModel<E> orderField(final String orderField) {
		setOrderField(orderField);
		return this;
	}

	public String getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}
	
	/**
	 * 返回Page对象自身的setOrder函数,可用于连续设置。
	 */
	public PageModel<E> orderDirection(final String theOrder) {
		setOrderDirection(theOrder);
		return this;
	}
	
	public Criterion[] getCriterions() {
		return criterions;
	}

	public void setCriterions(Criterion[] criterions) {
		this.criterions = criterions;
	}

	public Order[] getOrders() {
		return orders;
	}

	public void setOrders(Order[] orders) {
		this.orders = orders;
	}

	
	//-- 访问查询结果函数 --//


	@Override
	public String toString() {
		return pageNum + pageSize + orderField + orderDirection;
	}

}

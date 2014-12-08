package com.keitsen.demo.basic.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

import com.keitsen.demo.basic.entity.PageModel;

public interface IBasicDao<M extends Serializable, PK extends Serializable> {

	// 基本CRUD
	public M save(M model);

	public void saveOrUpdate(M model);

	public void update(M model);

	public void merge(M model);

	public void delete(PK id);
	
	public void deleteByIds(@SuppressWarnings("unchecked") PK ... ids);
	
	public void deleteObject(M model);

//	public void deleteObjectList(List<M> list);

	public void delete(Class<?> entityClass,
			LinkedHashMap<Object, Object> equalFields, String whereJpql);

	public void deleteArray(PK id[]);

	public boolean exists(PK id);

	public M get(PK id);

	public M get(LinkedHashMap<Object, Object> equalFields,
			LinkedHashMap<Object, Object> notEqualFields,
			LinkedHashMap<String, String> LikeFields,
			LinkedHashMap<String, String> nullFields, String whereJpql);

	// QBC查询
	public List<M> get(final Collection<PK> ids);

	public List<M> getAll();

	public List<M> getAll(String orderByProperty, boolean isAsc);

	public List<M> findBy(final String propertyName, final Object value);

	public M findUniqueBy(final String propertyName, final Object value);

	public List<M> find(final Criterion... criterions);

	public List<M> find(final Criterion[] criterions, final Order... orders);

	public M findUnique(final Criterion... criterions);
	
	
	//HQL查询
	public <X> List<X> find(final String hql, final Object... values);

	public <X> List<X> find(final String hql, final Map<String, ?> values);

	public <X> List<X> findByIds(final Collection<X> ids);
	
	public <X> X findUnique(final String hql, final Object... values);

	public <X> X findUnique(final String hql, final Map<String, ?> values);

	public int batchExecute(final String hql, final Object... values);

	public int batchExecute(final String hql, final Map<String, ?> values);

	public String getIdName();

	public boolean isPropertyUnique(final String propertyName,
			final Object newValue, final Object oldValue);

	public void batchDelete(Collection<M> collection);

	// =============获取List========================
	public List<M> findResultList(LinkedHashMap<Object, Object> equalFields,
			LinkedHashMap<Object, Object> notEqualFields,
			LinkedHashMap<String, String> LikeFields,
			LinkedHashMap<String, String> nullFields,
			LinkedHashMap<String, String> orderByFields, String whereHql);

	public Long getTotalCount(LinkedHashMap<Object, Object> equalFields,
			LinkedHashMap<Object, Object> notEqualFields,
			LinkedHashMap<String, String> LikeFields,
			LinkedHashMap<String, String> nullFields, String whereJpql);

	public List<M> findResultList(LinkedHashMap<Object, Object> equalFields,
			LinkedHashMap<Object, Object> notEqualFields,
			LinkedHashMap<String, String> LikeFields,
			LinkedHashMap<String, String> nullFields,
			LinkedHashMap<String, String> orderByFields, String whereJpql,
			int firstResult, int maxResult);
	
	public  List<M> findResultList(String whereJpql,
			int firstResult, int maxResult);

	// 获取pageModel
	public PageModel<M> findPage(PageModel<M> page);
	

	public PageModel<M> queryPageModel(
			LinkedHashMap<Object, Object> equalFields,
			LinkedHashMap<Object, Object> notEqualFields,
			LinkedHashMap<String, String> LikeFields,
			LinkedHashMap<String, String> nullFields,
			LinkedHashMap<String, String> orderByFields, String whereJpql,
			PageModel<M> pageModel);

	public PageModel<M> queryPageModelByEqual(
			LinkedHashMap<Object, Object> equalFields, PageModel<M> pageModel,
			LinkedHashMap<String, String> orderByFields);

	public PageModel<M> queryPageModelByLike(
			LinkedHashMap<String, String> likeFields, PageModel<M> pageModel,
			LinkedHashMap<String, String> orderByFields);

	public PageModel<M> queryPageModelByLikeAndEqual(
			LinkedHashMap<Object, Object> equalFields,
			LinkedHashMap<String, String> likeFields,
			LinkedHashMap<String, String> orderByFields, PageModel<M> pageModel);
	
}

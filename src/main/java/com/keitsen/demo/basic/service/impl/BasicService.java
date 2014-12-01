package com.keitsen.demo.basic.service.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.keitsen.demo.basic.dao.IBasicDao;
import com.keitsen.demo.basic.entity.PageModel;
import com.keitsen.demo.basic.service.IBasicService;
import com.keitsen.demo.basic.util.ReflectionUtil;

public  class BasicService <M extends Serializable, PK extends Serializable> implements IBasicService<M, PK>{
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected IBasicDao<M, PK> basicDao;
	
	protected Class<M> entityClass;
	
	public BasicService() {
		this.entityClass = ReflectionUtil.getSuperClassGenricType(getClass());
		logger.info(entityClass.getName());
	}
	
	@SuppressWarnings("unchecked")
	private IBasicDao<M, PK> getBasicDao() {
		if (basicDao == null) {
			Field[] fields = this.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				String entityClassName = entityClass.getName();
				String getMethodName = "get"
						+ entityClassName
								.substring(
										entityClassName.lastIndexOf(".") + 1,
										entityClassName.length()) + "Dao";
				try {
					Method method = this.getClass().getMethod(getMethodName);
					if(method != null){
						basicDao = (IBasicDao<M, PK>) method.invoke(this);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
		return basicDao;
	}
    
	
	//public abstract IBasicDao<M, PK> getBasicDao();
	
	
//    public abstract void setBasicDao(IBasicDao<M, PK> basicDao);
    
    

//	public IBasicDao<M, PK> getBasicDao() {
//		return basicDao;
//	}

	public void setBasicDao(IBasicDao<M, PK> basicDao) {
		this.basicDao = getBasicDao();
	}

	@Override
    public M save(M model) {
		getBasicDao().save(model);
        return model;
    }

    @Override
    public void saveOrUpdate(M model) {
    	getBasicDao().saveOrUpdate(model);
    }

    @Override
    public void update(M model) {
    	getBasicDao().update(model);
    }

    @Override
    public void merge(M model) {
    	getBasicDao().merge(model);
    }

    @Override
    public void delete(PK id) {
    	getBasicDao().delete(id);
    }

    @Override
    public void deleteObject(M model) {
    	getBasicDao().deleteObject(model);
    }

    @Override
    public void deleteArray(PK[] id) {
    	getBasicDao().deleteArray(id);
    }

    @Override
    public boolean exists(PK id) {
        return getBasicDao().exists(id);
    }

    @Override
    public M get(PK id) {
        return getBasicDao().get(id);
    }

    @Override
    public M get(LinkedHashMap<Object, Object> equalFields, LinkedHashMap<Object, Object> notEqualFields, LinkedHashMap<String, String> LikeFields, LinkedHashMap<String, String> nullFields, String whereJpql) {
        return getBasicDao().get(equalFields,notEqualFields,LikeFields,nullFields,whereJpql);
    }

    @Override
    public Long getTotalCount(LinkedHashMap<Object, Object> equalFields, LinkedHashMap<Object, Object> notEqualFields, LinkedHashMap<String, String> LikeFields, LinkedHashMap<String, String> nullFields, String whereJpql) {
        return getBasicDao().getTotalCount(equalFields, notEqualFields, LikeFields, nullFields, whereJpql);
    }

    @Override
    public List<M> findResultList(LinkedHashMap<Object, Object> equalFields, LinkedHashMap<Object, Object> notEqualFields, LinkedHashMap<String, String> LikeFields, LinkedHashMap<String, String> nullFields, LinkedHashMap<String, String> orderByFields, String whereJpql, int firstResult, int maxResult) {
        return getBasicDao().findResultList(equalFields,notEqualFields,LikeFields,nullFields,orderByFields,whereJpql,firstResult,maxResult);
    }

    @Override
    public PageModel<M> queryPageModel(LinkedHashMap<Object, Object> equalFields, LinkedHashMap<Object, Object> notEqualFields, LinkedHashMap<String, String> LikeFields, LinkedHashMap<String, String> nullFields, LinkedHashMap<String, String> orderByFields, String whereJpql, PageModel<M> pageModel) {
        return getBasicDao().queryPageModel(equalFields,notEqualFields,LikeFields,nullFields,orderByFields,whereJpql,pageModel);
    }

    @Override
    public PageModel<M> queryPageModelByEqual(LinkedHashMap<Object, Object> equalFields, PageModel<M> pageModel, LinkedHashMap<String, String> orderByFields) {
        return getBasicDao().queryPageModel(equalFields, null, null, null,
                orderByFields, null, pageModel);
    }

    @Override
    public PageModel<M> queryPageModelByLike(LinkedHashMap<String, String> likeFields, PageModel<M> pageModel, LinkedHashMap<String, String> orderByFields) {
        return getBasicDao().queryPageModel(null, null, likeFields, null,
                orderByFields, null, pageModel);
    }

    @Override
    public PageModel<M> queryPageModelByLikeAndEqual(LinkedHashMap<Object, Object> equalFields, LinkedHashMap<String, String> likeFields, LinkedHashMap<String, String> orderByFields, PageModel<M> pageModel) {
        return getBasicDao().queryPageModel(equalFields, null, likeFields, null,
                orderByFields, null, pageModel);
    }

	@Override
	public List<M> get(Collection<PK> ids) {
		return getBasicDao().get(ids);
	}

	@Override
	public List<M> getAll() {
		return getBasicDao().getAll();
	}

	@Override
	public List<M> getAll(String orderByProperty, boolean isAsc) {
		return getBasicDao().getAll(orderByProperty, isAsc);
	}

	@Override
	public List<M> findBy(String propertyName, Object value) {
		return getBasicDao().findBy(propertyName, value);
	}

	@Override
	public M findUniqueBy(String propertyName, Object value) {
		return getBasicDao().findUniqueBy(propertyName, value);
	}

	@Override
	public List<M> find(Criterion... criterions) {
		return getBasicDao().find(criterions);
	}

	@Override
	public List<M> find(Criterion[] criterions, Order... orders) {
		return getBasicDao().find(criterions, orders);
	}

	@Override
	public M findUnique(Criterion... criterions) {
		return getBasicDao().findUnique(criterions);
	}

	@Override
	public <X> List<X> find(String hql, Object... values) {
		return getBasicDao().find(hql, values);
	}

	@Override
	public <X> List<X> find(String hql, Map<String, ?> values) {
		return getBasicDao().find(hql, values);
	}

	@Override
	public <X> X findUnique(String hql, Object... values) {
		return getBasicDao().findUnique(hql, values);
	}

	@Override
	public <X> X findUnique(String hql, Map<String, ?> values) {
		return getBasicDao().findUnique(hql, values);
	}

	@Override
	public int batchExecute(String hql, Object... values) {
		return getBasicDao().batchExecute(hql, values);
	}

	@Override
	public int batchExecute(String hql, Map<String, ?> values) {
		return getBasicDao().batchExecute(hql, values);
	}


	@Override
	public String getIdName() {
		return getBasicDao().getIdName();
	}

	@Override
	public boolean isPropertyUnique(String propertyName, Object newValue,
			Object oldValue) {
		return getBasicDao().isPropertyUnique(propertyName, newValue, oldValue);
	}

	@Override
	public void batchDelete(Collection<M> collection) {
		getBasicDao().batchDelete(collection);
	}
}

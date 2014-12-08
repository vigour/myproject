package com.keitsen.demo.basic.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.keitsen.demo.basic.dao.HibernateDaoUtil;
import com.keitsen.demo.basic.dao.IBasicDao;
import com.keitsen.demo.basic.entity.PageModel;
import com.keitsen.demo.basic.util.ReflectionUtil;

@SuppressWarnings("unchecked")
public abstract  class HibernateBasicDao<M extends Serializable, PK extends Serializable> implements IBasicDao<M,PK> {

    protected Logger logger = LoggerFactory.getLogger(getClass());
	
    protected SessionFactory sessionFactory;

    protected Class<M> entityClass;
    
    /**
	 * 用于Dao层子类使用的构造函数.
	 * 通过子类的泛型定义取得对象类型Class.
	 * eg.
	 * public class UserDao extends SimpleHibernateDao<User, Long>
	 */
    public HibernateBasicDao() {
        this.entityClass = ReflectionUtil.getSuperClassGenricType(getClass());
        logger.error(entityClass.getName());
    }

    /**
	 * 用于用于省略Dao层, 在Service层直接使用通用SimpleHibernateDao的构造函数.
	 * 在构造函数中定义对象类型Class.
	 * eg.
	 * SimpleHibernateDao<User, Long> userDao = new SimpleHibernateDao<User, Long>(sessionFactory, User.class);
	 */
	public HibernateBasicDao(final SessionFactory sessionFactory, final Class<M> entityClass) {
		this.sessionFactory = sessionFactory;
		this.entityClass = entityClass;
	}
	
    @Resource
    public void setSessionFactory(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    
    public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * 取得sessionFactory.
	 * 事务必须是开启的(Required)，否则获取不到
	 */
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }


	
    //基本CRUD
    @Override
    public M save(M model) {
    	Assert.notNull(model, "entity不能为空");
    	logger.debug("save model: {}", model);
        return (M) getSession().save(model);
    }
    @Override
    public void saveOrUpdate(M model) {
    	Assert.notNull(model, "model不能为空");
        getSession().saveOrUpdate(model);
        logger.debug("saveOrUpdate model: {}", model);
    }
    @Override
    public void update(M model) {
    	Assert.notNull(model, "model不能为空");
        getSession().update(model);
        logger.debug("update model: {}", model);
    }
    @Override
    public void merge(M model) {
    	Assert.notNull(model, "model不能为空");
        getSession().merge(model);
        logger.debug("merge model: {}", model);
    }
    @Override
    public void delete(PK id) {
    	Assert.notNull(id, "id不能为空");
        getSession().delete(this.get(id));
        logger.debug("delete entity {},id is {}", entityClass.getSimpleName(), id);
    }

    @Override
    public void delete(Class<?> entityClass, LinkedHashMap<Object, Object> equalFields,
                       String whereJpql) {
        Query query = getSession().createQuery("delete from " + entityClass.getSimpleName()
                + " as o "
                + HibernateDaoUtil.buildWhereJpql(equalFields, null, null, null, null, whereJpql));
        query = HibernateDaoUtil.SetQueryParameter(query, equalFields, null, null);
        query.executeUpdate();
    }


    @Override
    public void deleteByIds(PK... ids) {
    	if(ids != null && ids.length>0){
    		for(PK id : ids){
    			M m = this.get(id);
    			if(m == null){
    				throw new RuntimeException("查找的[" + id +"]不存在！");
    			}
    			this.getSession().delete(m);
    		}
    	}
    }
    

    @Override
    public void deleteObject(M model) {
        getSession().delete(model);

    }

    
//	@Override
//    public void deleteObjectList(List<M> list) {
//    	final int size =list.size();
//    	PK[] ids = (PK[])list.toArray(new Object[size]);
//    	deleteArray(ids);
//    }
    
    @Override
    public void deleteArray(PK[] ids) {
        if (ids != null && ids.length >0) {
            StringBuffer sbDelete = new StringBuffer();
            sbDelete.append("delete from ").append(entityClass.getSimpleName())
                    .append(" as o where o.id in (:ids)");
            Query query = getSession().createQuery(sbDelete.toString())
            		.setParameterList("ids", ids);
            query.executeUpdate();
        }


    }
    
	/**
	 * 批量删除
	 * @param collection
	 */
    @Override
	public void batchDelete(Collection<M> collection) {
		Iterator<M> iterator = collection.iterator();
		int count = 0;
		while(iterator.hasNext()){
			this.getSession().delete(iterator.next());
			count ++;
			if(count%30 == 0){
				this.getSession().flush();
			}
		}
		this.getSession().flush();
	}

    @Override
    public boolean exists(PK id) {
    	Assert.notNull(id, "id不能为空");
        return get(id) != null;
    }
    
    @Override
    
    public M get(PK id) {
    	Assert.notNull(id, "id不能为空");
        return (M) getSession().get(this.entityClass,id);
    }

	@Override
	@SuppressWarnings({ "rawtypes" })
    public M get(LinkedHashMap<Object, Object> equalFields,
                 LinkedHashMap<Object, Object> notEqualFields,
                 LinkedHashMap<String, String> LikeFields,
                 LinkedHashMap<String, String> nullFields, String whereHql) {
        // 获取实体名
        String entityName = entityClass.getSimpleName();

        Query query = getSession().createQuery("select o from "
                + entityName
                + " as o "
                + HibernateDaoUtil.buildWhereJpql(equalFields, notEqualFields, LikeFields, nullFields,
                null, whereHql));

        // 给查询参数赋值
        query = HibernateDaoUtil.SetQueryParameter(query, equalFields, notEqualFields, LikeFields);

        //query.setHint("org.hibernate.cacheable", true);
        List list= query.list();
        if(list!=null && list.size()>0)
        {
            return (M) query.list().get(0);
        }else{
            return null;
        }

    }

    // =============获取List========================

    @Override
    public  List<M> findResultList(
            LinkedHashMap<Object, Object> equalFields,
            LinkedHashMap<Object, Object> notEqualFields,
            LinkedHashMap<String, String> LikeFields,
            LinkedHashMap<String, String> nullFields,
            LinkedHashMap<String, String> orderByFields, String whereHql){
      return this.findResultList(equalFields,notEqualFields,LikeFields,nullFields,orderByFields,whereHql,0,0);
    }

    @Override
    public Long getTotalCount(
                                  LinkedHashMap<Object, Object> equalFields,
                                  LinkedHashMap<Object, Object> notEqualFields,
                                  LinkedHashMap<String, String> LikeFields,
                                  LinkedHashMap<String, String> nullFields, String whereHql) {
        // 获取实体名
        String entityName = entityClass.getSimpleName();

        // 获得query，并构建查询条件，排序条件
        Query query = getSession().createQuery("select count(o) from "
                + entityName
                + " as o "
                + HibernateDaoUtil.buildWhereJpql(equalFields, notEqualFields, LikeFields, nullFields,
                null, whereHql));

        // 给查询参数赋值
        query = HibernateDaoUtil.SetQueryParameter(query, equalFields, notEqualFields, LikeFields);

        //query.setHint("org.hibernate.cacheable", true);

        // 查询，返回list
        return (Long) query.list().get(0);
    }
	@Override
    public  List<M> findResultList(
                                      LinkedHashMap<Object, Object> equalFields,
                                      LinkedHashMap<Object, Object> notEqualFields,
                                      LinkedHashMap<String, String> LikeFields,
                                      LinkedHashMap<String, String> nullFields,
                                      LinkedHashMap<String, String> orderByFields, String whereHql,
                                      int firstResult, int maxResult) {
        // 获取实体名
        String entityName = entityClass.getSimpleName();

        // 获得query，并构建查询条件，排序条件
        Query query = getSession().createQuery("select o from "
                + entityName
                + " as o "
                + HibernateDaoUtil.buildWhereJpql(equalFields, notEqualFields, LikeFields, nullFields,
                orderByFields, whereHql));

        // 给查询参数赋值
        query = HibernateDaoUtil.SetQueryParameter(query, equalFields, notEqualFields, LikeFields);

        //query.setHint("org.hibernate.cacheable", true);

        // 分页条件
        if (maxResult != 0) {
            query.setMaxResults(maxResult).setFirstResult(firstResult);
        }

        // 查询，返回list
        return (List<M>) query.list();
    }
	
	/**
	 * 根据HQL语句分页
	 */
	public  List<M>  findResultList(String whereJpql,
			int firstResult, int maxResult){
		return this.findResultList(null, null, null, null,
                null, whereJpql, firstResult, maxResult);
	}


    //获取pageModel
	@Override
    public PageModel<M> queryPageModel(
                                        LinkedHashMap<Object, Object> equalFields,
                                        LinkedHashMap<Object, Object> notEqualFields,
                                        LinkedHashMap<String, String> LikeFields,
                                        LinkedHashMap<String, String> nullFields,
                                        LinkedHashMap<String, String> orderByFields, String whereHql,
                                        PageModel<M> pageModel) {
        int pageNo = pageModel.getPageNum();
        int pageSize = pageModel.getPageSize();
        if(pageModel.getOrderField()!=null && !"".equals(pageModel.getOrderField()) && pageModel.getOrderDirection()!=null && !"".equals(pageModel.getOrderDirection())){
            orderByFields=new LinkedHashMap<String, String>();
            orderByFields.put(pageModel.getOrderField(), pageModel.getOrderDirection());
        }else {
            //第一次查询返回的结果是有顺序的，所以要保留这两个值，在jsp端处理时，能看出是用什么排序的。
            if(orderByFields!=null)
            {
                for (String key : orderByFields.keySet()) {
                    pageModel.setOrderField(key);
                    pageModel.setOrderDirection(orderByFields.get(key));
                }
            }

        }
        // 获取数据总数
        Long totalCount = getTotalCount( equalFields, notEqualFields,
                LikeFields, nullFields, whereHql);

        // 根据查询条件获取数据
        int firstResult = pageModel.getFistResult();
//        if (firstResult < 0) {
//            firstResult = 0;
//        }
        List<M> list = findResultList( equalFields, notEqualFields, LikeFields,
                nullFields, orderByFields, whereHql, firstResult, pageSize);
        // 设置pageModel
        pageModel.setList(list);
        pageModel.setTotalRecords(totalCount);
        pageModel.setPageSize(pageSize);
        pageModel.setPageNum(pageNo);

        return pageModel;
    }
    
    
    @Override
    public  PageModel<M> queryPageModelByEqual(
                                               LinkedHashMap<Object, Object> equalFields, PageModel<M> pageModel,
                                               LinkedHashMap<String, String> orderByFields) {

        return this.queryPageModel( equalFields, null, null, null,
                orderByFields, null, pageModel);
    }
    
    
    @Override
    public  PageModel<M> queryPageModelByLike(
                                              LinkedHashMap<String, String> likeFields, PageModel<M> pageModel,
                                              LinkedHashMap<String, String> orderByFields) {
        return this.queryPageModel( null, null, likeFields, null,
                orderByFields, null, pageModel);
    }
    
    
    @Override
    public  PageModel<M> queryPageModelByLikeAndEqual(
                                                      LinkedHashMap<Object, Object> equalFields,
                                                      LinkedHashMap<String, String> likeFields,
                                                      LinkedHashMap<String, String> orderByFields, PageModel<M> pageModel) {
        return this.queryPageModel(equalFields, null, likeFields, null,
                orderByFields, null, pageModel);
    }

	
    
    /***
     * QBC查询程序段
     */
    /**
	 * 根据Criterion条件创建Criteria.
	 * 与find()函数可进行更加灵活的操作.
	 * 
	 * @param criterions 数量可变的Criterion.
	 */
	public Criteria createCriteria(final Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}
	
	
	/**
	 * 按Criteria查询对象列表.
	 * 
	 * @param criterions 数量可变的Criterion.
	 */
	@Override
	public List<M> find(final Criterion... criterions) {
		return createCriteria(criterions).list();
	}
	
	@Override
	public List<M> get(final Collection<PK> ids) {
		if(ids == null || ids.size() < 1){
			return Collections.EMPTY_LIST;
		}
		return find(Restrictions.in(getIdName(), ids));
	}

	@Override
	public List<M> getAll() {
		return find();
	}

	@Override
	public List<M> getAll(final String orderByProperty, boolean isAsc) {
		Criteria c = createCriteria();
		if (isAsc) {
			c.addOrder(Order.asc(orderByProperty));
		} else {
			c.addOrder(Order.desc(orderByProperty));
		}
		return c.list();
	}

	@Override
	public List<M> findBy(final String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return find(criterion);
	}

	@Override
	public M findUniqueBy(final String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return (M) createCriteria(criterion).uniqueResult();
	}

	/**
	 * 按Criteria查询对象列表.
	 * 
	 * @param criterions 数量可变的Criterion.
	 */
	@Override
	public List<M> find(final Criterion[] criterions, final Order... orders) {
		Criteria criteria =  this.createCriteria(criterions);
		if(orders != null){
			for(Order O : orders){
				criteria.addOrder(O);
			}
		}
		
		return criteria.list();
	}

	/**
	 * 按Criteria查询唯一对象.
	 * 
	 * @param criterions 数量可变的Criterion.
	 */
	@Override
	public M findUnique(final Criterion... criterions) {
		return (M) createCriteria(criterions).uniqueResult();
	}
	
	/**
	 * 为Criteria添加distinct transformer.
	 * 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
	 */
	public Criteria distinct(Criteria criteria) {
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria;
	}
	
	/**
	 * 判断对象的属性值在数据库内是否唯一.
	 * 
	 * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
	 */
	@Override
	public boolean isPropertyUnique(String propertyName, Object newValue,
			Object oldValue) {
		if (newValue == null || newValue.equals(oldValue)) {
			return true;
		}
		Object object = findUniqueBy(propertyName, newValue);
		return (object == null);
	}

	
	
	
	/**
	 * 按HQL查询对象列表
	 */
	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * 与find()函数可进行更加灵活的操作.
	 * 
	 * @param values 数量可变的参数,按顺序绑定.
	 */
	public Query createQuery(final String queryString, final Object... values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * 与find()函数可进行更加灵活的操作.
	 * 
	 * @param values 命名参数,按名称绑定.
	 */
	public Query createQuery(final String queryString, final Map<String, ?> values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}
	
	@Override
	public <X> List<X> find(final String hql, Object... values) {
		return createQuery(hql, values).list();
	}

	@Override
	public <X> List<X> find(final String hql, Map<String, ?> values) {
		return createQuery(hql, values).list();
	}
	
	@Override
	public <X> List<X> findByIds(final Collection<X> ids){
		if(ids == null||ids.size() < 1){
			return Collections.EMPTY_LIST;
		}
		StringBuffer hql = new StringBuffer(" from ");
		hql.append(this.entityClass.getSimpleName());
		hql.append(" o where o.id in(:ids)");
		 Query query = getSession().createQuery(hql.toString())
         		.setParameterList("ids", ids);
		return query.list();
	}

	@Override
	public <X> X findUnique(final String hql, Object... values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	@Override
	public <X> X findUnique(final String hql, Map<String, ?> values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	/**
	 * 执行HQL进行批量修改/删除操作.
	 * 
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return 更新记录数.
	 */
	@Override
	public int batchExecute(final String hql, Object... values) {
		return createQuery(hql, values).executeUpdate();
	}

	/**
	 * 执行HQL进行批量修改/删除操作.
	 * 
	 * @param values 命名参数,按名称绑定.
	 * @return 更新记录数.
	 */
	@Override
	public int batchExecute(final String hql, Map<String, ?> values) {
		return createQuery(hql, values).executeUpdate();
	}


	/**
	 * 为Query添加distinct transformer.
	 * 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
	 */
	public Query distinct(Query query) {
		query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return query;
	}

	/**
	 * 取得对象的主键名.
	 */
	@Override
	public String getIdName() {
		ClassMetadata meta = getSessionFactory().getClassMetadata(entityClass);
		return meta.getIdentifierPropertyName();
	}

	
	
	
	/**
	 * 分页查询
	 */
	
	protected long countHqlResult(org.hibernate.criterion.Criterion[] criterions){
		Criteria C =  this.getSession().createCriteria(this.entityClass);
		if(criterions!=null){
			for(Criterion criterion : criterions){
				if(criterion!=null)
					C.add(criterion);
			}
		}
		return (Long)C.setProjection(Projections.rowCount()).uniqueResult();
	}
	/**
	 * 分页 
	 * @param page
	 * @return
	 */
	@Override
	public PageModel<M> findPage(PageModel<M> page) {
		Criteria criteria =  this.getSession().createCriteria(this.entityClass);
		Criterion[] criterions = page.getCriterions();
		Order[] orders = page.getOrders(); 
		page.setTotalRecords(this.countHqlResult(criterions));
		
		if(criterions!=null){
			for(Criterion criterion : criterions){
				if(criterion!=null)
					criteria.add(criterion);
			}
		}
		if(orders != null ){
			for(Order order : orders){
				if(order!=null)
					criteria.addOrder(order);
			}
		}
		criteria.setMaxResults(page.getLimit());
		criteria.setFirstResult(page.getStart());
		page.setList(criteria.list());
		return page;
	}
	

}

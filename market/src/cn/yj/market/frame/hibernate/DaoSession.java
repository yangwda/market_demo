package cn.yj.market.frame.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.internal.CriteriaImpl.OrderEntry;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.Assert;

import cn.yj.market.frame.hibernate.query.QueryHelper;
import cn.yj.market.frame.page.DefaultPage;
import cn.yj.market.frame.page.Page;
import cn.yj.market.frame.util.ObjectUtils;

public class DaoSession {
	
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
    @Resource
    private SessionFactory sessionFactory;

    @Autowired
    @Required
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    public Session getSession() throws HibernateException {
        Session sess = getSessionFactory().getCurrentSession();
        if (sess == null) {
            sess = getSessionFactory().openSession();
        }
        return sess;
    }
    
    /**
	 * 
	 */
	protected Session getCurrentSession() {
		return getSession() ;
	}

    
    
    //    public Object saveObj(Object obj) {
    //        getSession().save(obj);
    //        return obj;
    //    }

    //    public Object updateObj(Object obj) {
    //        getSession().update(obj);
    //        return obj;
    //    }

    /**
     * 根据SQL获取数据并组装为List<Map<String, Object>>
     * @return List<Map<String, Object>>
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getListBySql(String sql) {
        Query query = getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }
    
    public <R> List<R> find(QueryHelper queryHelper) {
		return this.find(queryHelper.getQueryString(), queryHelper.getQueryParametersAsArray());
	}
	
	public <R> List<R> find(String query, Object... params) {
		final Query q = buildQuery(query, params);
		return list(q);
	}
	
	public <R> List<R> find(String query, Object params[], int start, int limit) {
		final Query q = buildQuery(query, params, start, limit);
		return list(q);
	}
	
	public void clear() {
		getCurrentSession().clear();
	}
	
	public void flush() {
		getCurrentSession().flush();
	}
	
	public int update(String query, Object params[]) {
		final Query q = buildQuery(query, params);
		return q.executeUpdate();
	}
	
	public <R> Page<R> paginate(String counter, String query, Object params[], int pageNo, int pageSize) {
		return paginate(counter, query, params, pageNo, pageSize, null);
	}
	
	private <R> Page<R> paginate(String counter, String query, Object params[], int pageNo, int pageSize, ResultTransformer transformer) {
		// Precondition checking
		if(pageNo <= 0) {
			throw new IllegalArgumentException("invalid parameter pageNo: " + pageNo);
		}
		if(pageSize <= 0) {
			throw new IllegalArgumentException("invalid parameter pageSize: " + pageSize);
		}
		
		// Count
		DefaultPage<R> page = new DefaultPage<R>(pageNo, pageSize);
		final Query q1 = buildQuery(counter, params);
		page.setTotal(((Number)(q1.uniqueResult())).longValue());
		
		// Query
		final int start = (pageNo - 1) * pageSize;
		final Query q2 = buildQuery(query, params);
		q2.setFirstResult(start);
		q2.setMaxResults(pageSize);
		if(transformer != null) {
			q2.setResultTransformer(transformer);
		}
		List<R> r = list(q2);
		page.setRecords(r);
		return page;
	}
	
	public int updateBySql(String query, Object params[]) {
		final Query q = buildSqlQuery(query, params);
		return q.executeUpdate();
	}
	
	public <R> List<R> findBySql(String query, Object params[]) {
		final Query q = buildSqlQuery(query, params, null, null, null);
		return list(q);
	}
	
	public <R> List<R> findBySql(String query, Object params[], ResultTransformer transformer) {
		final Query q = buildSqlQuery(query, params, null, null, transformer);
		return list(q);
	}
	
	/**
	 * sql中预留参数是命名参数，Map<String ,Object> params //key=参数名，value=参数值（可以为Collection）<br>
	 * @param <R>
	 * @param Stringquery
	 * @param Map<String ,Object> params //key=参数名，value=参数值（可以为Collection）
	 * @param ResultTransformer transformer
	 * @return List<R>
	 */
	public <R> List<R> findBySql(String query, ResultTransformer transformer, Map<String ,Object> params) {
		final Query q = buildSqlQuery(query, params, null, null, transformer);
		return list(q);
	}
	
	public <R> List<R> findBySql(String query, Object params[], int start, int limit) {
		final Query q = buildSqlQuery(query, params, start, limit, null);
		return list(q);
	}
	
	public <R> List<R> findBySql(String query, Object params[], int start, int limit, ResultTransformer rt) {
		final Query q = buildSqlQuery(query, params, start, limit, rt);
		return list(q);
	}
	
	// 分页查询相关方法
	
	/**
	 *  SQL分页查询
	 * @param query
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public <R> Page<R> paginateBySql(String query, Object params[], int pageNo, int pageSize) {
		return paginateBySql(query, params, pageNo, pageSize, null);
	}
	
	/**
	 * SQL分页查询
	 * @param query
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @param rt
	 * @return
	 */
	public <R> Page<R> paginateBySql(String query, Object params[], int pageNo, int pageSize, ResultTransformer rt) {
		// Precondition checking
		if(pageNo <= 0) {
			throw new IllegalArgumentException("invalid parameter pageNo: " + pageNo);
		}
		if(pageSize <= 0) {
			throw new IllegalArgumentException("invalid parameter pageSize: " + pageSize);
		}
		
		// Count
		DefaultPage<R> page = new DefaultPage<R>(pageNo, pageSize);
		StringBuilder counter = new StringBuilder();
		counter.append("SELECT COUNT(*) FROM (").append(query).append(") T");
		final Query q1 = buildSqlQuery(counter.toString(), params);
		page.setTotal(((Number)(q1.uniqueResult())).longValue());
		
		// Query
		final int start = (pageNo - 1) * pageSize;
		final Query q2 = buildSqlQuery(query, params, start, pageSize, rt);
		List<R> r = list(q2);
		page.setRecords(r);
		return page;
	}
	
	public <R> Page<R> paginateBySql(String query, int pageNo, int pageSize, ResultTransformer rt ,Map<String ,Object> params) {
		// Precondition checking
		if(pageNo <= 0) {
			throw new IllegalArgumentException("invalid parameter pageNo: " + pageNo);
		}
		if(pageSize <= 0) {
			throw new IllegalArgumentException("invalid parameter pageSize: " + pageSize);
		}
		
		// Count
		DefaultPage<R> page = new DefaultPage<R>(pageNo, pageSize);
		StringBuilder counter = new StringBuilder();
		counter.append("SELECT COUNT(*) FROM (").append(query).append(") T");
		final Query q1 = buildSqlQuery(counter.toString(), params);
		page.setTotal(((Number)(q1.uniqueResult())).longValue());
		
		// Query
		final int start = (pageNo - 1) * pageSize;
		final Query q2 = buildSqlQuery(query, params, start, pageSize, rt);
		List<R> r = list(q2);
		page.setRecords(r);
		return page;
	}
	

	/**
	 * SQL分页查询。
	 * 
	 * @param sql
	 * @param entityMap
	 * @param pageNo
	 * @param pageSize
	 * @param args
	 * @return Page
	 */
	public <R> Page<R> pagedSQLQuery(String sql, LinkedHashMap<String, Class<?>> entityMap, int pageNo,
			int pageSize, Object[] args) {

		if (pageNo <= 0 || pageSize <= 0)
			return pagedSQLQuery(sql, entityMap, args); // 返回全部结果

		String countQueryString;

		if (entityMap != null && !entityMap.isEmpty()) {
			countQueryString = " SELECT COUNT(*) " + removeSelect(removeOrders(sql));
		} else {
			countQueryString = " SELECT COUNT(*) FROM (" + sql + ") pagedSQLQuery";
		}

		SQLQuery querycount = getCurrentSession().createSQLQuery(countQueryString);

		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				querycount.setParameter(i, args[i]);
			}
		}

		int totalCount = ((Number) (querycount.uniqueResult())).intValue();

		if (totalCount < 1)
		{
			return new DefaultPage<R>(0, 0, 0, new ArrayList<R>());
		}

		SQLQuery query = getCurrentSession().createSQLQuery(sql);

		if (entityMap != null && !entityMap.isEmpty()) {

			Set<Entry<String, Class<?>>> entrySet = entityMap.entrySet();

			for (Entry<String, Class<?>> entry : entrySet) {
				query.addEntity(entry.getKey(), entry.getValue());
			}
		}

		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i, args[i]);
			}
		}

		int startIndex = DefaultPage.getStartOfPage(pageNo, pageSize);
		List<R> list = list(query.setFirstResult(startIndex).setMaxResults(pageSize));

		return new DefaultPage<R>(startIndex, totalCount, pageSize, list);
	}
    
	/**
	 * SQL分页查询。
	 * @param sql
	 * @param transformerEntity
	 * @param args
	 * @return
	 */
	private <R> Page<R> pagedSQLQuery(String sql, Class<?> transformerEntity, Map<String, Object> args) {

        Assert.hasText(sql);

        SQLQuery query = getCurrentSession().createSQLQuery(sql);

        if (transformerEntity != null) {
            query.setResultTransformer(Transformers.aliasToBean(transformerEntity));
        }

        if (args != null) {
            for (Entry<String, Object> element : args.entrySet()) {
                if (element.getValue() instanceof Collection) {
                    query.setParameterList(element.getKey(), (Collection<?>) element.getValue());
                } else {
                    query.setParameter(element.getKey(), element.getValue());
                }
            }
        }

        List<R> list = list(query);
        return new DefaultPage<R>(0, list.size(), list.size(), list);
    }
	
    /**
     * SQL分页查询。
     * @param sql
     * @param countSql
     * @param transformerEntity
     * @param pageNo
     * @param pageSize
     * @param args
     * @return
     */
	@SuppressWarnings("rawtypes")
	public <R> Page<R> pagedSQLQuery(String sql, String countSql, Class<?> transformerEntity, int pageNo, int pageSize,
            Map<String, Object> args) {

        if (pageNo <= 0 || pageSize <= 0) {
            return pagedSQLQuery(sql, transformerEntity, args); // 返回全部结果
        }

        SQLQuery querycount = getCurrentSession().createSQLQuery(countSql);

        if (args != null) {
            for (Entry<String, Object> element : args.entrySet()) {
                if (element.getValue() instanceof Collection) {
                    querycount.setParameterList(element.getKey(), (Collection) element.getValue());
                } else {
                    querycount.setParameter(element.getKey(), element.getValue());
                }
            }
        }

        int totalCount = ((Number) (querycount.uniqueResult())).intValue();

        if (totalCount < 0) {
            return new DefaultPage<R>(0, 0);
        }

        SQLQuery query = getCurrentSession().createSQLQuery(sql);

        if (transformerEntity != null) {
            query.setResultTransformer(Transformers.aliasToBean(transformerEntity));
        }

        if (args != null) {
            for (Entry<String, Object> element : args.entrySet()) {
                if (element.getValue() instanceof Collection) {
                    query.setParameterList(element.getKey(), (Collection) element.getValue());
                } else {
                    query.setParameter(element.getKey(), element.getValue());
                }
            }
        }

        int startIndex = DefaultPage.getStartOfPage(pageNo, pageSize);
        List<R> list = list(query.setFirstResult(startIndex).setMaxResults(pageSize));

        return new DefaultPage<R>(startIndex, totalCount, pageSize, list);
    }
	
    /**
     *  SQL分页查询。
     * @param sql
     * @param entityMap
     * @param args
     * @return
     */
	private <R> Page<R> pagedSQLQuery(String sql, LinkedHashMap<String, Class<?>> entityMap, Object[] args) {

		Assert.hasText(sql);

		SQLQuery query = getCurrentSession().createSQLQuery(sql);

		if (entityMap != null && !entityMap.isEmpty()) {

			Set<Entry<String, Class<?>>> entrySet = entityMap.entrySet();

			for (Entry<String, Class<?>> entry : entrySet) {
				query.addEntity(entry.getKey(), entry.getValue());
			}
		}

		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i, args[i]);
			}
		}

		List<R> list = list(query);

		return new DefaultPage<R>(0, list.size(), list.size(), list);
	}
	
	/**
	 * SQL分页查询。
	 * @param sql
	 * @param query
	 * @param countSql
	 * @param pageNo
	 * @param pageSize
	 * @param args
	 * @param collectionMap
	 * @return
	 */
	public <R> Page<R> pagedSQLQuery(String sql, Query query, String countSql, int pageNo, int pageSize,
			Object[] args, Map<String, ? extends Collection<?>> collectionMap) {

		if (query == null) {
			query = getCurrentSession().createSQLQuery(sql);
		}

		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i, args[i]);
			}
		}

		if (collectionMap != null && !collectionMap.isEmpty()) {
			for (Entry<String, ? extends Collection<?>> entry : collectionMap.entrySet()) {
				query.setParameterList(entry.getKey(), entry.getValue());
			}
		}

		String countQueryString;

		if (countSql != null && countSql.length() > 0) {
			countQueryString = countSql;
		} else {
			countQueryString = " SELECT COUNT(1) FROM (" + sql + ") pagedSQLQuery";
		}

		Query querycount = getCurrentSession().createSQLQuery(countQueryString);

		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				querycount.setParameter(i, args[i]);
			}
		}

		if (collectionMap != null && !collectionMap.isEmpty()) {
			for (Entry<String, ? extends Collection<?>> entry : collectionMap.entrySet()) {
				querycount.setParameterList(entry.getKey(), entry.getValue());
			}
		}

		int totalCount = ((Long) (querycount.uniqueResult())).intValue();

		if (totalCount < 1)
		{
			return new DefaultPage<R>(0, 0,0, new ArrayList<R>());
		}

		List<R> list = null;
		int startIndex = 0;

		if (pageNo <= 0 || pageSize <= 0) {
			list = list(query); // 返回全部结果
			pageSize = totalCount;
		} else {
			startIndex = DefaultPage.getStartOfPage(pageNo, pageSize);
			list = list(query.setFirstResult(startIndex).setMaxResults(pageSize));
		}
		return new DefaultPage<R>(startIndex, totalCount, pageSize, list);
	}
	
	/**
	 * SQL分页查询
	 * 
	 * @param sql
	 * @param pageNo
	 * @param pageSize
	 * @param transformerEntity
	 * @param args
	 * @return
	 */
	public <R> Page<R> pagedSQLQuery(String sql, int pageNo, int pageSize, Class<R> transformerEntity, Object[] args) {

        if (pageNo <= 0 || pageSize <= 0)
            return pagedSQLQuery(sql, args); // 返回全部结果

        SQLQuery query = getCurrentSession().createSQLQuery(sql);
        if (transformerEntity != null) {
            query.setResultTransformer(Transformers.aliasToBean(transformerEntity));
        }
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                query.setParameter(i, args[i]);
            }
        }

        String countQueryString = " SELECT COUNT(*) FROM (" + sql + ") pagedSQLQuery";
        Query querycount = getCurrentSession().createSQLQuery(countQueryString);
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                querycount.setParameter(i, args[i]);
            }
        }

        int totalCount = ((Number) (querycount.uniqueResult())).intValue();

        if (totalCount < 1)
			return new DefaultPage<R>(0, 0, 0, new ArrayList<R>());

        List<R> list;
        int startIndex = 0;

        if (pageNo <= 0 || pageSize <= 0) {
            list = list(query); // 返回全部结果
            pageSize = totalCount;
        } else {
            startIndex =  DefaultPage.getStartOfPage(pageNo, pageSize);
            list = list(query.setFirstResult(startIndex).setMaxResults(pageSize));
        }

        return new DefaultPage<R>(startIndex, totalCount, pageSize, list);
    }
    
    /**
     * SQL分页查询，返回全部结果
     * 
     * @param sql
     * @param args
     * @return Page
     */
    private <R> Page<R> pagedSQLQuery(String sql, Object[] args) {

        Assert.hasText(sql);
        Query query = getCurrentSession().createSQLQuery(sql);

        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                query.setParameter(i, args[i]);
            }
        }

        List<R> list = list(query);
        return new DefaultPage<R>(0, list.size(), list.size(), list);
    }


	/**
	 * HQL分页查询
	 * 
	 * @param hql
	 * @param pageNo
	 * @param pageSize
	 * @param args
	 * @return
	 */
	public <R> Page<R> pagedQuery(String hql, Object[] args, int pageNo, int pageSize) {

		if (pageNo <= 0 || pageSize <= 0)
			return pagedQuery(hql, args); // 返回全部结果

		Assert.hasText(hql);
		Query query = getCurrentSession().createQuery(hql);
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i, args[i]);
			}
		}

		String countQueryString = " SELECT count (*) " + removeSelect(removeOrders(hql));
		Query querycount = getCurrentSession().createQuery(countQueryString);
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				querycount.setParameter(i, args[i]);
			}
		}
		List<?> countlist = querycount.list();
		int totalCount = ((Long) countlist.get(0)).intValue();

		if (totalCount < 1)
		{
			return new DefaultPage<R>(0, 0, 0, new ArrayList<R>());
		}
		int startIndex = DefaultPage.getStartOfPage(pageNo, pageSize);
		List<R> list = list(query.setFirstResult(startIndex).setMaxResults(pageSize));

		return new DefaultPage<R>(startIndex, totalCount, pageSize, list);

	}

	/**
	 * HQL 分页查询 全部结果
	 * @param hql
	 * @param args
	 * @return
	 */
	private <R> Page<R> pagedQuery(String hql, Object[] args) {

		Assert.hasText(hql);
		Query query = getCurrentSession().createQuery(hql);

		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i, args[i]);
			}
		}

		List<R> list = list(query);
		return new DefaultPage<R>(0, list.size(), list.size(), list);
	}

	/**
	 * Criteria分页查询
	 */
	@SuppressWarnings("unchecked")
	public <R> Page<R> pagedQuery(Criteria criteria, int pageNo, int pageSize) {
		CriteriaImpl impl;
		if (criteria instanceof CriteriaImpl) {
			impl = (CriteriaImpl) criteria;
		} else {
			throw new InternalError(" Runtime Exception impossibility can't throw ");
		}
		Projection projection = impl.getProjection();
		List<OrderEntry> orderEntries;
		try {
			orderEntries = (List<OrderEntry>) ObjectUtils.getPrivateProperty(impl, "orderEntries");
			ObjectUtils.setPrivateProperty(impl, "orderEntries", new ArrayList<R>());
		} catch (Exception e) {
			throw new InternalError(" Runtime Exception impossibility can't throw ");
		}

		// 执行查询
		Object integerCount = criteria.setProjection(Projections.rowCount())
				.uniqueResult();
		int totalCount = 0;
		if (integerCount != null) {
			if (integerCount instanceof Integer) {
				totalCount = ((Integer) integerCount).intValue() ;
			}
			else if (integerCount instanceof Long) {
				totalCount = Integer.parseInt(((Long)integerCount).toString()) ;
			}
			else {
				totalCount = Integer.MAX_VALUE;
			}
		}

		// 将之前的Projection和OrderBy条件重新设回去
		criteria.setProjection(projection);
		if (projection == null) {
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}

		try {
			List<OrderEntry> innerOrderEntries = (List<OrderEntry>) ObjectUtils.getPrivateProperty(impl, "orderEntries");
			for (OrderEntry orderEntry : orderEntries) {
				innerOrderEntries.add(orderEntry);
			}
		} catch (Exception e) {
			throw new InternalError(" Runtime Exception impossibility can't throw ");
		}
		if (totalCount < 1)
		{
			return new DefaultPage<R>(0, 0, 0, new ArrayList<R>());
		}

		List<R> list = null;
		int startIndex = 0;

		if (pageNo <= 0 || pageSize <= 0) {
			list = list(criteria); // 返回全部结果
			pageSize = totalCount;
		} else {
			startIndex = DefaultPage.getStartOfPage(pageNo, pageSize);
			list = list(criteria.setFirstResult(startIndex).setMaxResults(pageSize));
		}

		return new DefaultPage<R>(startIndex, totalCount, pageSize, list);

	}
	
	// 分页查询相关方法
	
	protected Query buildQuery(String query, Object params[]) {
		return buildQuery(query, params, null, null);
	}
	
	private Query buildQuery(String query, Object params[], Integer start, Integer limit) {
		Query r = getCurrentSession().createQuery(query);
		if(start != null) r.setFirstResult(start);
		if(limit != null) r.setMaxResults(limit);
		if(params != null) {
			for (int i = 0; i < params.length; i++) {
				r.setParameter(i, params[i]);
			}
		}
		return r;
	}
	
	protected Query buildSqlQuery(String query, Object params[]) {
		return buildSqlQuery(query, params, null, null, null);
	}
	
	protected Query buildSqlQuery(String query, Map<String ,Object> params) {
		return buildSqlQuery(query, params, null, null, null);
	}
	
	protected Query buildSqlQuery(String query, Object params[], Integer start, Integer limit, ResultTransformer rt) {
		Query r = getCurrentSession().createSQLQuery(query);
		if(start != null) r.setFirstResult(start);
		if(limit != null) r.setMaxResults(limit);
		if(rt != null) r.setResultTransformer(rt);
		if(params != null) {
			for (int i = 0; i < params.length; i++) {
				r.setParameter(i, params[i]);
			}
		}
		return r;
	}
	
	@SuppressWarnings("rawtypes")
	protected Query buildSqlQuery(String query, Map<String ,Object> params, Integer start, Integer limit, ResultTransformer rt) {
		Query r = getCurrentSession().createSQLQuery(query);
		if(start != null) r.setFirstResult(start);
		if(limit != null) r.setMaxResults(limit);
		if(rt != null) r.setResultTransformer(rt);
		if (params != null) {
            for (Entry<String, Object> element : params.entrySet()) {
                if (element.getValue() instanceof Collection) {
                    r.setParameterList(element.getKey(), (Collection) element.getValue());
                } else {
                    r.setParameter(element.getKey(), element.getValue());
                }
            }
        }
		
		return r;
	}
	
	@SuppressWarnings("unchecked")
	protected <R> List<R> list(Criteria c){
		return (List<R>)c.list();
	}
	@SuppressWarnings("unchecked")
	protected <R> List<R> list(Query q){
		return (List<R>)q.list();
	}
	
	/**
	 * 去除select 子句. 注意：不适用于union的情况
	 */
	private static String removeSelect(String hql) {

		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().lastIndexOf("from");
		Assert.isTrue(beginPos != -1, " hql : " + hql + " must has a keyword 'from'");
		return hql.substring(beginPos);
	}

	/**
	 * 去除orderby 子句
	 */
	private static String removeOrders(String hql) {

		Assert.hasText(hql);
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	public int update(QueryHelper queryHelper) {
		return update(queryHelper.getQueryString(), queryHelper.getQueryParametersAsArray());
	}
}

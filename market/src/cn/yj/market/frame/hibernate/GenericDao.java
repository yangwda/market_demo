package cn.yj.market.frame.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.yj.market.frame.page.Page;

/**
 * 
 * 
 */
public abstract class GenericDao<T extends BasePojo> extends DaoSession implements Dao<T> {
    //
    private static final Logger LOGGER = LoggerFactory.getLogger(GenericDao.class);

    //
    protected final Class<T> actualType;

    protected final AtomicBoolean verbose;

    protected final static String ACTIVE_FLAG = "activeFlag";

    /**
     * 
     */
    public GenericDao() {

        this.actualType = detectActualType();
        this.verbose = new AtomicBoolean(false);

    }

    /**
     * 
     */
    public final boolean isVerbose() {
        return verbose.get();
    }

    public final void setVerbose(boolean verbose) {
        this.verbose.set(verbose);
    }

    public final Class<? extends BasePojo> getActualType() {
        return actualType;
    }

    public final String getIdName() {
        ClassMetadata cm = getSessionFactory().getClassMetadata(getActualType());
        return cm.getIdentifierPropertyName();
    }

    public final String getEntityName() {
        ClassMetadata cm = getSessionFactory().getClassMetadata(getActualType());
        return cm.getEntityName();
    }

    public final String getEntityName(Class<? extends BasePojo> clazz) {
        ClassMetadata cm = getSessionFactory().getClassMetadata(clazz);
        LOGGER.info("----------->> ClassMetadata.getEntityName=" + cm.getEntityName());
        return cm.getEntityName();
    }

    /**
     * 
     */
    public List<T> getAll() {
        return getAll(null);
    }

    public List<T> getAll(Comparator<T> comparator) {
        //
        final Criteria c = getCurrentSession().createCriteria(getActualType());
        List<T> list = list(c);

        //
        if (comparator != null) {
            Collections.sort(list, comparator);
        }
        return list;
    }

    public T get(Serializable id) {
        @SuppressWarnings("unchecked")
        T r = (T) getCurrentSession().get(getActualType(), id);
        //
        if (r == null)
            return null;
        //
        return r;
    }

    public T get(Serializable id, LockMode mod) {
        @SuppressWarnings({"unchecked", "deprecation"})
        T r = (T) getCurrentSession().get(getActualType(), id, mod);
        return r;
    }

    @SuppressWarnings("unchecked")
    public T load(Serializable id) {
        T r = (T) getCurrentSession().get(getActualType(), id);
        if (r == null) {
            r = (T) getCurrentSession().load(getActualType(), id);
        } else {
            getCurrentSession().refresh(r);
        }
        return r;
    }

    @SuppressWarnings({"unchecked", "deprecation"})
    public T loadAndLock(Serializable id) {

        T r = (T) getCurrentSession().get(getActualType(), id);
        if (r == null) {
            r = (T) getCurrentSession().load(getActualType(), id, LockMode.UPGRADE_NOWAIT);
        } else {
            if (!LockMode.UPGRADE.equals(getSession().getCurrentLockMode(r))
                && !LockMode.UPGRADE_NOWAIT.equals(getSession().getCurrentLockMode(r)))
                getCurrentSession().refresh(r, LockMode.UPGRADE_NOWAIT);
        }
        return r;
    }

    @SuppressWarnings({"unchecked", "deprecation"})
    public T refreshAndLock(Serializable id) {
        T r = (T) getCurrentSession().get(getActualType(), id);
        if (r == null) {
            r = (T) getCurrentSession().load(getActualType(), id, LockMode.UPGRADE_NOWAIT);
        } else {
            getCurrentSession().refresh(r, LockMode.UPGRADE_NOWAIT);
        }
        return r;
    }

    @SuppressWarnings({"unchecked", "deprecation"})
    public T loadAndLockNowait(Serializable id) {
        T r = (T) getCurrentSession().get(getActualType(), id);
        if (r == null) {
            r = (T) getCurrentSession().load(getActualType(), id, LockMode.UPGRADE_NOWAIT);
        } else {
            if (!LockMode.UPGRADE.equals(getSession().getCurrentLockMode(r))
                && !LockMode.UPGRADE_NOWAIT.equals(getSession().getCurrentLockMode(r)))
                getCurrentSession().refresh(r, LockMode.UPGRADE_NOWAIT);
        }
        return r;
    }

    public List<T> loadAndLockNowait(Collection<? extends Serializable> ids) {
        // Precondition checking
        if (ids == null || ids.size() == 0) {
            return new ArrayList<T>(0);
        }

        //
        final Criteria c = getCurrentSession().createCriteria(getActualType());
        c.add(Property.forName(getIdName()).in(ids));
        c.setLockMode(LockMode.UPGRADE_NOWAIT);
        return list(c);
    }

    public boolean exists(Serializable id) {
        final Criteria c = getCurrentSession().createCriteria(getActualType());
        c.add(Property.forName(getIdName()).eq(id));
        final Integer count = (Integer) c.setProjection(Projections.rowCount()).uniqueResult();
        return count > 0;
    }

    public List<T> get(Collection<? extends Serializable> ids) {
        // Precondition checking
        if (ids == null || ids.size() == 0) {
            return new ArrayList<T>(0);
        }

        //
        final Criteria c = getCurrentSession().createCriteria(getActualType());
        c.add(Property.forName(getIdName()).in(ids));
        return list(c);
    }

    public void refresh(T entity) {
        getCurrentSession().refresh(entity);
    }

    public void evict(T entity) {
        getCurrentSession().evict(entity);
    }

    public Serializable save(T entity) {
        return getCurrentSession().save(entity);
    }

    public void update(T entity) {
        beforeUpdate(entity);
        getCurrentSession().update(entity);
    }

    public void merge(T entity) {
        beforeUpdate(entity);
        getCurrentSession().merge(entity);
    }

    public void delete(T entity) {
        getCurrentSession().delete(entity);
    }

    public void remove(T entity) {
        getCurrentSession().delete(entity);
    }

    protected void beforeUpdate(T entity) {

    }

    /**
     * 
     */
    public <P> P getProperty(Serializable id, Class<P> propertyType, String propertyName) {
        //
        StringBuilder query = new StringBuilder();
        query.append("select ").append(propertyName).append(" from ").append(getEntityName());
        query.append(" where ").append(getIdName()).append(" = ?");

        //
        @SuppressWarnings("unchecked")
        List<P> r = (List<P>) find(query.toString(), new Object[] {id});
        if (r == null || r.size() == 0) {
            return null;
        } else if (r.size() == 1) {
            return r.get(0);
        } else {
            throw new RuntimeException(
                "assertion failed, duplicated records in " + getEntityName() + ", id: " + id);
        }
    }

    /**
     * 
     */
    @SuppressWarnings("unchecked")
    private Class<T> detectActualType() {
        //
        Class<?> clazz = getClass();
        while (clazz.getSuperclass() != GenericDao.class) {
            clazz = clazz.getSuperclass();
        }

        //
        Type type = clazz.getGenericSuperclass();
        return (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
    }

    // 分页查询相关方法
    protected Criteria buildCriteria(Criterion... criteria) {
        Criteria r = getCurrentSession().createCriteria(getActualType());

        if (criteria == null || criteria.length == 0)
            return r;

        for (Criterion c : criteria) {
            r.add(c);
        }
        return r;
    }

    protected Criteria buildCriteria(Collection<Criterion> criteria) {
        return buildCriteria(criteria,null);
    }
    
    
    protected Criteria buildCriteria(Collection<Criterion> criteria,Set<Order> orderSet) {
        Criteria r = getCurrentSession().createCriteria(getActualType());
        
        if(orderSet != null && !orderSet.isEmpty()){
            for(Order o:orderSet){
                r.addOrder(o);
            }
        }
        
        if (criteria == null || criteria.size() == 0)
            return r;

        for (Criterion c : criteria) {
            r.add(c);
        }
        return r;
    }

    public List<T> getListByCriteria(Criterion... criteria) {
        return buildCriteria(criteria).list();
    }

    public List<T> getListByCriteria(Collection<Criterion> c) {
        return buildCriteria(c).list();
    }
    
    public List<T> getListByCriteria(Collection<Criterion> c,Set<Order> orderSet) {
        return buildCriteria(c,orderSet).list();
    }

    public <R> Page<R> pagedQuery(int pageNo, int pageSize, Criterion... criteria) {

        return pagedQuery(buildCriteria(criteria), pageNo, pageSize);

    }

    public <R> Page<R> pagedQuery(Set<Criterion> criteriaSet, int pageNo, int pageSize) {

        return pagedQuery(buildCriteria(criteriaSet), pageNo, pageSize);

    }

    @SuppressWarnings("unchecked")
    public List<T> criteriaQuery(DetachedCriteria criteria) {
        if (criteria == null) {
            return null;
        }
        return criteria.getExecutableCriteria(getSession()).list();
    }

    protected final SQLQuery getSqlQuery(String sql) {

        SQLQuery sqlQuery = getSession().createSQLQuery(sql);

        sqlQuery.setResultTransformer(new ResultTransformer() {

            @Override
            public Object transformTuple(Object[] values, String[] columns) {
                Map<String, Object> map = new LinkedHashMap<String, Object>(1);
                int i = 0;
                for (String column : columns) {
                    map.put(column, values[i++]);
                }
                return map;
            }

            @Override
            public List transformList(List list) {
                return list;
            }
        });

        return sqlQuery;
    }

}
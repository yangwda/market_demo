package cn.yj.market.frame.hibernate;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.criterion.DetachedCriteria;


/**
 * 
 * 
 */
public interface Dao<T extends BasePojo> {
	
	/**
	 * 
	 */
	void clear();
	
	void flush();
	
	void update(T entity);
	
	void delete(T entity);
	
	void remove(T entity);
	
	void refresh(T entity);
	
	Serializable save(T entity);
	
	int update(String query, Object params[]);
	
	/**
	 * 
	 */
	List<T> getAll();
	
	T get(Serializable id);
	
	T get(Serializable id,LockMode mod);
	
	boolean exists(Serializable id);
	
	List<T> getAll(Comparator<T> comparator);
	
	List<T> get(Collection<? extends Serializable> ids);
	
	<P> List<P> find(String query, Object... params);
	
	<P> List<P> find(String query, Object params[], int start, int count);
	
	T load(Serializable id);
	
	T loadAndLock(Serializable id);
	
	T refreshAndLock(Serializable id);
	
	T loadAndLockNowait(Serializable id);
	
	List<T> loadAndLockNowait(Collection<? extends Serializable> ids);
	
	List<T> criteriaQuery(DetachedCriteria criteria) ;
}

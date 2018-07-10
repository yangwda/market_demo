package cn.yj.market.frame.page;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 
 */
public interface Page<T> extends Serializable {

	/**
	 * 
	 */
	boolean hasMore();
	
	/**
	 * 
	 */
	long getTotal();
	
	int getPageNo();
	
	int getPageSize();
	
	List<T> getRecords();
	
	void setRecords(List<T> records);
	
	int getStart();
}

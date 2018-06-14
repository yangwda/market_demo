package cn.yj.market.frame.hibernate.query;

import java.util.List;

/**
 * 
 */
public interface QueryHelper {
	
	public String getQueryString();

	public List<Object> getQueryParameters();

	public Object[] getQueryParametersAsArray();
}

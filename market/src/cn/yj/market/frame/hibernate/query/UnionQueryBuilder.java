package cn.yj.market.frame.hibernate.query;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 
 */
public final class UnionQueryBuilder implements QueryHelper {

	//
	private final Formatter queryString;
	private final List<Object> queryParameters;
	private boolean isFirst = true;

	/**
	 * 
	 */
	public UnionQueryBuilder() {
		this.queryString = new Formatter();
		this.queryParameters = new ArrayList<Object>(20);
	}

	/**
	 * 
	 */
	@SuppressWarnings("resource")
	public String toString() {
		final Formatter formatter = new Formatter();
		formatter.format(queryString.toString().replaceAll("\\?", "%s"), getQueryParametersAsArray());
		return formatter.toString();
	}

	/**
	 * From ${clazz.getSimpleName()}
	 */
	public UnionQueryBuilder appendFrom(Class<?> clazz) {
		//
		queryString.format(" FROM %s ", clazz.getSimpleName());
		//
		return this;
	}

	/**
	 * no need WHERE , AND
	 */
	public UnionQueryBuilder appendQuery(String expression, Object... params) {
		this.queryString.format(" %s %s ", getPrefix(), expression);

		for (Object param : params) {
			appendParameter(param);
		}

		//
		return this;
	}

	public UnionQueryBuilder appendQueryIn(String property, Object ... params) {
		//
		this.queryString.format(" %s %s IN (%s)", getPrefix(), property, StringUtils.join(params, ','));
		//
		return this;
	}

	public UnionQueryBuilder appendQueryForUpdate() {
		//
		this.queryString.format(" FOR UPDATE ");
		//
		return this;
	}

	public UnionQueryBuilder appendQueryIfNotNull(String expression, Object param) {
		//
		if (param != null) {
			this.appendQuery(expression, param);
		}

		//
		return this;
	}

	/**
	 * 
	 */
	public String getQueryString() {
		String queryString = this.queryString.toString();
		return queryString;
	}

	public List<Object> getQueryParameters() {
		return this.queryParameters;
	}

	public Object[] getQueryParametersAsArray() {
		return this.queryParameters.toArray(new Object[0]);
	}

	private String getPrefix() {
		if (isFirst) {
			isFirst = false;
			return "WHERE";
		} else {
			return "AND";
		}
	}

	/**
	 * 
	 */
	protected void appendParameter(Object param) {
		this.queryParameters.add(param);
	}
}

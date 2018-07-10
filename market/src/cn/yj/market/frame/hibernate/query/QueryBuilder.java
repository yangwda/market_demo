package cn.yj.market.frame.hibernate.query;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;

/**
 * 
 */
public final class QueryBuilder implements QueryHelper {
	//
	private static final char PADDING = ' ';
	private static final String IN = " in (";
	private static final String PARAM = "?";
	private static final String PARAM_CHAR = "'?'";
	private static final String COMMA = ", ";
	private static final String BRACKET = ")";
	private static final String NOTIN = " not in (";

	//
	private final StringBuilder queryString;
	private final List<Object> queryParameters;

	/**
	 * 
	 */
	public QueryBuilder() {
		this.queryString = new StringBuilder(200);
		this.queryParameters = new ArrayList<Object>(20);
	}

	/**
	 * 
	 */
	@SuppressWarnings("resource")
	public String toString() {
		final Formatter formatter = new Formatter();
		formatter.format(queryString.toString().replaceAll("\\?", "%s"),
				getQueryParametersAsArray());
		return formatter.toString();
	}

	/**
	 * 
	 */
	public QueryBuilder append(String query, Object... params) {
		//
		this.queryString.append(PADDING).append(query);
		for (Object param : params) {
			appendParameter(param);
		}

		//
		return this;
	}

	public QueryBuilder appendIfNotNull(String query, Object param) {
		//
		if (param != null && param.toString().length() > 0) {
			this.queryString.append(PADDING).append(query);
			appendParameter(param);
		}

		//
		return this;
	}

	public QueryBuilder in(String property, Object[] params) {
		//
		if (params.length > 0) {
			this.queryString.append(PADDING).append(property);
			this.queryString.append(IN);
			for (int i = 0; i < params.length; i++) {
				//'?'
				this.queryString.append(PARAM);
				if (i != params.length - 1) {
					this.queryString.append(COMMA);
				}

				//
				appendParameter(params[i]);
			}
			this.queryString.append(BRACKET);
		}

		//
		return this;
	}
	public QueryBuilder in(String property, List<String> params) {
		if (params!=null&&!params.isEmpty()) {
			this.queryString.append(PADDING).append(property);
			this.queryString.append(IN);
			for (Iterator<String> iterator = params.iterator(); iterator.hasNext();) {
				this.queryString.append(PARAM_CHAR);
				appendParameter( iterator.next());
				if (iterator.hasNext()) {
					this.queryString.append(COMMA);
				}
			}
			this.queryString.append(BRACKET);
		}
		return this;
	}

	public QueryBuilder notin(String property, Object[] params) {
		//
		if (params.length > 0) {
			this.queryString.append(PADDING).append(property);
			this.queryString.append(NOTIN);
			for (int i = 0; i < params.length; i++) {
				//
				this.queryString.append(PARAM);
				if (i != params.length - 1) {
					this.queryString.append(COMMA);
				}

				//
				appendParameter(params[i]);
			}
			this.queryString.append(BRACKET);
		}

		//
		return this;
	}

	/**
	 * 
	 */
	public String getQueryString() {
		return this.queryString.toString();
	}

	public List<Object> getQueryParameters() {
		return this.queryParameters;
	}

	public Object[] getQueryParametersAsArray() {
		return this.queryParameters.toArray(new Object[0]);
	}

	/**
	 * 
	 */
	protected void appendParameter(Object param) {
		this.queryParameters.add(param);
	}
}

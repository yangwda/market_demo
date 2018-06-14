
package cn.yj.market.frame.exception;

import java.util.List;

/**
 * 
 * 运行时抛出的业务异常；
 * property：资源文件中的业务异常主键；
 * values：用于替换资源文件中的补位value；
 */

public class RunException extends RuntimeException {

	private static final long serialVersionUID = -1226349860021183845L;

	private String property;

	private String[] values;

	public RunException() {
		super();
	}

	public RunException(String message) {
		super(message);
	}

	public RunException(String message, String property) {
		super(message);
		this.property = property;
	}

	public RunException(String message, String[] values) {
		super(message);
		this.values = values;
	}
	
	public RunException(String message, String[] values, Throwable cause) {
		super(message, cause);
		this.values = values;
	}
	
	public RunException(String message, List<String> values) {
		super(message);
		if (values != null && !values.isEmpty()) {
			this.values = values.toArray(new String[0]);
		}
	}
	
	public RunException(String message, List<String> values, Throwable cause) {
		super(message, cause);
		if (values != null && !values.isEmpty()) {
			this.values = values.toArray(new String[0]);
		}
	}

	public RunException(String message, Throwable cause) {
		super(message, cause);
	}

	public RunException(Throwable cause) {
		super(cause);
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}

}
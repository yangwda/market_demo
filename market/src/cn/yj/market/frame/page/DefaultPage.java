package cn.yj.market.frame.page;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * 
 */
public final class DefaultPage<T> implements Page<T> {
	//
	private static final long serialVersionUID = -5809680583529730511L;
	
	//
	private long total;
	private int start;
	private int pageNo;
	private int pageSize;
	private List<T> records;
	
	/**
	 * 
	 */
	public DefaultPage(){}
	public DefaultPage(int pageNo, int pageSize) {
		//
		if(pageNo <= 0) {
			throw new IllegalArgumentException("invalid parameter pageNo");
		}
		if(pageSize <= 0) {
			throw new IllegalArgumentException("invalid parameter pageSize");
		}
		
		//
		this.total = 0;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}
	
	public DefaultPage(int start, int totalSize, int pageSize, List<T> records) {

		this.pageSize = pageSize;
		this.start = start;
		this.total = totalSize;
		this.records = records;
	}

	
	/**
	 * 
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
		.append("total", total)
		.append("pageNo", pageNo)
		.append("pageSize", pageSize)
		.append("records", records).toString();
	}
	
	/**
	 * 
	 */
	public static int getStartOfPage(int pageNo, int pageSize) {

		return (pageNo - 1) * pageSize;
	}
	
	/**
	 * 
	 */
	public boolean hasMore() {
		int count = (pageNo - 1) * pageSize;
		if(records != null) {
			count += records.size();
		}
		return total - count > 0;
	}
	
	/**
	 * 
	 */
	public long getTotal() {
		return total;
	}
	
	public void setTotal(long total) {
		this.total = total;
	}
	
	public int getPageNo() {
		return pageNo;
	}
	
	public void setPageNo(int start) {
		this.pageNo = start;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public List<T> getRecords() {
		return records;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}
	
	public int getStart() {
		return start;
	}
}

package cn.yj.market.frame.page;

import java.util.Collections;
import java.util.List;


/**
 * 
 * 
 */
public final class Pages {
	
	/**
	 * 
	 */
	public static <T> Page<T> unmodifiablePage(Page<T> page) {
		return new UnmodifiablePage<T>(page);
	}
	
	/**
	 * 
	 */
	public static <T> Page<T> valueOf(List<T> list, int pageNo, int pageSize) {
		// Precondition checking
		if(list == null) {
			throw new IllegalArgumentException("invalid parameter list");
		}
		if(pageNo <= 0) {
			throw new IllegalArgumentException("invalid parameter pageNo");
		}
		if(pageSize <= 0) {
			throw new IllegalArgumentException("invalid parameter pageSize");
		}
		
		//
		DefaultPage<T> page = new DefaultPage<T>(pageNo, pageSize);
		page.setTotal(list.size());
		
		//
		int from = (pageNo - 1) * pageSize;
		int to = pageNo * pageSize; 
		if(to > list.size()) {
			to = list.size();
		}
		page.setRecords(list.subList(from, to));
		return page;
	}
	
	/**
	 * 
	 */
	private static class UnmodifiablePage<T> implements Page<T> {
		//
		private static final long serialVersionUID = -6033089126355559862L;
		
		//
		private final Page<T> page;

		/**
		 * 
		 */
		public UnmodifiablePage(Page<T> page) {
			this.page = page;
		}

		/**
		 * 
		 */
		public boolean hasMore() {
			return page.hasMore();
		}
		
		public long getTotal() {
			return page.getTotal();
		}

		public int getPageNo() {
			return page.getPageNo();
		}

		public int getPageSize() {
			return page.getPageSize();
		}

		public List<T> getRecords() {
			return Collections.unmodifiableList(page.getRecords());
		}

		public void setRecords(List<T> records) {
			throw new RuntimeException("setRecords is not allowed with unmodifiable page");
		}

		@Override
		public int getStart() {
			return page.getStart();
		}
	}
}

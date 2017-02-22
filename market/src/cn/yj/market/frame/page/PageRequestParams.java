package cn.yj.market.frame.page;

/**
 *	分页请求，保存分页请求参数
 */
public final class PageRequestParams {
	
	public static final int PAGE_NUM_DEFALT = 1 ;
	public static final int PAGE_SIZE_DEFALT = 30 ;
	
	private int pageNum = PAGE_NUM_DEFALT;
	private int pageSize = PAGE_SIZE_DEFALT;
	
	public PageRequestParams(int pageNum ,int pageSize) {
		if (pageNum <= 1) {
			pageNum = PAGE_NUM_DEFALT ;
		}
		if (pageSize <= 1) {
			pageSize = PAGE_SIZE_DEFALT ;
		}
		this.pageNum = pageNum ;
		this.pageSize = pageSize ;
	}
	public PageRequestParams() {}
	
	public int getPageNum() {
		return pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	
}

package cn.yj.market.frame.hibernate;

import java.util.List;

/**
 * DB操作通用接口
 */
public interface DBExcute {

	/**
	 * DB操作执行接口
	 * @return
	 */
	boolean excute() ;
	
	/**
	 * 获取DB操作结果集
	 * @return
	 */
	<R> List<R> getResults() ;
}

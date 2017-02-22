package cn.yj.market.frame.hibernate;

import java.io.Serializable;

/**
 * 
 */
public abstract class BasePojo implements Serializable {

	private static final long serialVersionUID = 5373066910198124156L;
	
	/**
	 * pojo初始化
	 */
	public abstract void initialize() ;
}

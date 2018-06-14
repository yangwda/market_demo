package cn.yj.market.frame.bean;

import java.io.Serializable;

import org.springframework.beans.BeanUtils;

public abstract class BaseModalBean<T> implements Serializable {

	private static final long serialVersionUID = -8991325391793261443L;
	
	/**
	 * 创建一个空对象
	 * @return
	 */
	protected abstract T getVObject() ;
	
	public T getVo() {
		T vo = getVObject() ;
		BeanUtils.copyProperties(this, vo);
		return vo ;
	}
	
	public void setVo(T vo) {
		BeanUtils.copyProperties(vo, this);
	}
}

package cn.yj.market.frame.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import cn.yj.market.frame.exception.RunException;

/**
 * Spring 工具类 ,通过Spring容器做对象注入，不能进行实例化，直接使用静态方法
 */
public final class SpringUtils implements ApplicationContextAware {
	
	private SpringUtils() {}
	
	private static final Log  logger = LogFactory.getLog(SpringUtils.class) ; 

	private static ApplicationContext context;//声明一个静态变量保存
	
	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		logger.info("SpringUtils application context init OK...........");
		SpringUtils.context = context ;
	}
	
	/**
	 * 获取Spring Context
	 * @return
	 */
	public static ApplicationContext getContext() {
		return context ;
	}

	/**
	 * 指定bean id，获取Spring 容器中的Bean
	 * @param beanId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanId) {
		if (context == null) {
			throw new RunException("系统运行错误") ;
		}
		if (StringUtils.isBlank(beanId)) {
			return null ;
		}
		return (T)context.getBean(beanId) ;
	}

	/**
	 * 指定class ，获取spring容器中的Bean
	 * @param clazz
	 * @return
	 */
	public static <T> T getBean(Class<T> clazz) {
		if (clazz == null) {
			return null ;
		}
		return (T)context.getBean(clazz) ;
	}

}

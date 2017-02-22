package cn.yj.market.frame.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.yj.market.frame.constants.FrameConstants;
import cn.yj.market.frame.exception.RunException;

public final class AuthInterceptor implements HandlerInterceptor {
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object obj, Exception exception)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj, ModelAndView mav)
			throws Exception {

	}

	/**
	 * 权限过滤，抛出异常 ，<br>
	 * 此处处理异常，可以解决消息国际化问题
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj) throws Exception {
		
		//进行权限验证过滤
		
		// 权限状态在shiro中判断过了，此处只做拦截，抛出异常

		/* 直接进行登录状态判断，没有登录，抛出异常 */
		if (FrameConstants.RESPONSE_DATA_FLAG_NOLOGIN.equals((String) request
				.getAttribute(FrameConstants.AUTHENTICATION_KEY))) {
			throw new RunException(FrameConstants.RESPONSE_DATA_FLAG_NOLOGIN);
		}

		/* 判断是否有访问资源的权限，没有抛出异常 */
		if (FrameConstants.RESPONSE_DATA_FLAG_NOAUTH.equals((String) request
				.getAttribute(FrameConstants.AUTHENTICATION_KEY))) {
			throw new RunException(FrameConstants.RESPONSE_DATA_FLAG_NOAUTH);
		}
		
		return true;
	}
}

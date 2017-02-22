package cn.yj.market.frame.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import cn.yj.market.frame.bean.ResponseJsonData;
import cn.yj.market.frame.constants.FrameConstants;
import cn.yj.market.frame.exception.RunException;
import cn.yj.market.frame.util.ResponseJsonUtils;

public class MarketExceptionHandler implements HandlerExceptionResolver {
	
	private static final Log logger = LogFactory.getLog(MarketExceptionHandler.class) ;
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object obj, Exception exception) {
		
		logger.error("== RunException ==", exception);
		
		String responseDataType = request.getParameter(FrameConstants.RESPONSE_DATA_TYPE_PARAMNAME) ;
		
		/* 返回json */
		if (FrameConstants.RESPONSE_DATA_TYPE_JSON.equalsIgnoreCase(responseDataType)) {
			ResponseJsonData rj = new ResponseJsonData() ;
			rj.setStatus(FrameConstants.RESPONSE_DATA_FLAG_ERROR);
			String errMsg = exception.getMessage();
			if (exception instanceof RunException) {
				if (FrameConstants.RESPONSE_DATA_FLAG_NOAUTH.equals(exception.getMessage())) {
					errMsg = "没有权限执行该操作！";
					rj.setStatus(FrameConstants.RESPONSE_DATA_FLAG_NOAUTH);
				}
				else if (FrameConstants.RESPONSE_DATA_FLAG_NOLOGIN.equals(exception.getMessage())) {
					errMsg = "未登录或登录超时，请先登录系统！" ;
					rj.setStatus(FrameConstants.RESPONSE_DATA_FLAG_NOLOGIN);
				}
				else {
					errMsg = exception.getMessage();
				}
			}
			rj.setMessage(errMsg);
			ResponseJsonUtils.responseJson(response, rj.getResult());
			return null;
		}
		/* 返回html */
		else {
			ModelAndView mav = new ModelAndView("common/sysError") ;
			 String errMsg = exception.getMessage() ;
			if (exception instanceof RunException) {
				if (FrameConstants.RESPONSE_DATA_FLAG_NOAUTH.equals(exception.getMessage())) {
					errMsg = "没有权限执行该操作！";
				}
				else if (FrameConstants.RESPONSE_DATA_FLAG_NOLOGIN.equals(exception.getMessage())) {
					errMsg = "未登录或登录超时，请先登录系统！";
					mav.addObject("errStatus", FrameConstants.RESPONSE_DATA_FLAG_NOLOGIN) ;
				}
			}
			mav.addObject("errMsg", errMsg) ;
			return mav ;
		}
	}
	

}

package cn.yj.market.frame.controller;


import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.ModelAndView;

import cn.yj.market.frame.bean.ResponseJsonData;
import cn.yj.market.frame.constants.FrameConstants;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


/**
 * 基础Controller，所有controller继承于此，提供通用方法
 */
public abstract class BaseController
{

	protected final Logger			logger		= LoggerFactory.getLogger(getClass());

	// 返回数据标识：正常返回
	public static final String	SUCESS		=
													FrameConstants.RESPONSE_DATA_FLAG_SUCCESS;

	// 返回数据标识：异常返回
	public static final String	ERROR		=
													FrameConstants.RESPONSE_DATA_FLAG_ERROR;

	// 返回数据标识：表单数据，校验不通过返回
	public static final String	VFAILED		=
													FrameConstants.RESPONSE_DATA_FLAG_VFAILED;

	// 返回数据标识：没有登录
	public static final String	NOLOGIN		=
													FrameConstants.RESPONSE_DATA_FLAG_NOLOGIN;

	// 返回数据标识：没有权限
	public static final String	NOAUTH		=
													FrameConstants.RESPONSE_DATA_FLAG_NOAUTH;

	// 页面换行符
	public static final String	HTML_TAG_BR	= "<br>";

	// /**
	// * 获取web请求用户，或者后台启动程序的操作者
	// * @return
	// */
	// public TmsUser getUser() {
	// return UserContext.getUser() ;
	// }

	/**
	 * 国际化，获取资源文言
	 * 
	 * @param key
	 * @param args
	 * @return
	 */
	protected ResponseJsonData validateFialedResponseJson(
			BindingResult validResult ) {
		ResponseJsonData rjData = new ResponseJsonData();
		if(validResult == null || !validResult.hasErrors())
		{
			rjData.setStatus(ERROR);
			rjData
				.setMessage("输入有误！");
		}
		else
		{
			rjData.setStatus(VFAILED);
			StringBuilder msgStringAll = new StringBuilder(HTML_TAG_BR);
			JSONObject errInfo = new JSONObject();
			for( FieldError error : validResult.getFieldErrors() )
			{
				String fieldName = error.getField();
				String msgString = "输入有误！";
				JSONArray ja = (JSONArray) errInfo.get(fieldName);
				if(ja == null)
				{
					ja = new JSONArray();
				}
				ja.add(msgString);
				errInfo.put(fieldName, ja);
				msgStringAll.append(msgString).append(HTML_TAG_BR);
			}
			rjData.setRetData(errInfo);
			rjData.setMessage(msgStringAll.toString());
		}

		return rjData;
	}

	protected ResponseJsonData responseErr( String err ) {
		ResponseJsonData rjd = new ResponseJsonData();
		rjd.setStatus(ERROR);
		if(StringUtils.isNotBlank(err))
		{
			rjd.setMessage(err);
		}
		else
		{
			rjd.setMessage("未知错误，请联系系统管理员。");
		}
		return rjd;
	}

	/**
	 * 加载页面共通方法，子类覆盖此方法同时加上注解，然后调用此方法即可
	 * 
	 * @param request
	 * @param pn
	 *            页面路径
	 * @return
	 */
	protected ModelAndView loadContent( HttpServletRequest request, String pn ) {
		Map<String, String> params = new HashMap<String, String>();
		Enumeration<String> pns = request.getParameterNames();
		while(pns.hasMoreElements())
		{
			String paramName = (String) pns.nextElement();
			params.put(paramName, request.getParameter(paramName));
		}
		if(StringUtils.isBlank(pn))
		{
			return null;
		}
		ModelAndView mv = new ModelAndView(pn, params);
		return mv;
	}
}

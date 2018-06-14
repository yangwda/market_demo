package cn.yj.market.frame.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.yj.market.frame.bean.ResponseJsonData;
import cn.yj.market.frame.constants.FrameConstants;
import cn.yj.market.frame.util.ResponseJsonUtils;

/**
 * 全局异常处理，如果spring mvc的异常拦截没有处理，或者其他servlet抛出的异常，在此统一处理
 * @author weidong.yang
 *
 */
public class SysExceptionHandler extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5923311362278747748L;
	
	private static final String SERVLET_NAME_WMSWEB = "default" ;

	private static final Logger logger = LoggerFactory.getLogger(SysExceptionHandler.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.error("## ERROR ,web app globle err......");
		String servletName = (String) request
				.getAttribute("javax.servlet.error.servlet_name");
		if (servletName == null) {
			servletName = SERVLET_NAME_WMSWEB;
		}
		if (SERVLET_NAME_WMSWEB.equals(servletName)) {
			wmswebException(request, response);
			return ;
		}
		responseHtmlError(response);
	}

	private void responseHtmlError(HttpServletResponse response){
		try {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			String title = "Error/Exception Information";
			String docType = "<!doctype html public \"-//w3c//dtd html 4.0 "
					+ "transitional//en\">\n";
			out.println(docType + "<html>\n" + "<head><title>" + title
					+ "</title></head>\n" + "<body bgcolor=\"#f0f0f0\">\n");
			out.println("<p>					<strong>系统异常，请联系系统管理员！</strong></p>" );
			out.println("</body>");
			out.println("</html>");
			out.flush(); 
			out.close();
		} catch (IOException e) {
			logger.error("## ERROR in global sys error handler ......" ,e);
		}
	}
	
	private void wmswebException(HttpServletRequest request, HttpServletResponse response){
		
		String responseDataType = request.getParameter(FrameConstants.RESPONSE_DATA_TYPE_PARAMNAME) ;
		if (FrameConstants.RESPONSE_DATA_TYPE_JSON.equalsIgnoreCase(responseDataType)) {
			ResponseJsonData rj = new ResponseJsonData() ;
			rj.setStatus(FrameConstants.RESPONSE_DATA_FLAG_ERROR);
			rj.setMessage("系统异常，请联系系统管理员！");
			response.setCharacterEncoding("UTF-8");
			ResponseJsonUtils.responseJson(response, rj.getResult());
		}
		else {
			responseHtmlError(response) ;
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

package cn.yj.market.frame.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import cn.yj.market.frame.constants.FrameConstants;
import cn.yj.market.frame.util.SessionUtil;

/**
 * ******* web功能的权限验证逻辑切换至com.dsp.wms.frame.interceptor.AuthInterceptor，此过滤器暂时废弃 ********
 * 用户登陆及资源访问验证过滤器
 */
public class SecurityAuthFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(SecurityAuthFilter.class);

    /**
     * 对url进行匹配
     */
    private PathMatcher matcher = new AntPathMatcher();

    @Override
    public void init(FilterConfig filterConfig)
        throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest,
        ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        /* 如果登录成功，设置UserContext */
    	/* 将Session中的UserContext放到 UserContext中 */
		SessionUtil.setUserContext();
		
        // 请求访问的路径资源
        String url = request.getServletPath();
        // logger.info("request url ---------------------->> " + url);
        // logger.info("request url ---------------------->> " +
        // request.getRequestURL().toString());
        // 判定是否为不需要登录及权限验证的资源
        boolean urlFlg = false;
        for (String noSecurityUrl : FrameConstants.NO_SECURITY_AUTH_URLS) {
            if (matcher.match(noSecurityUrl, url)) {
                urlFlg = true;
                break;
            }
        }
        /* 如果登录成功，设置UserContext */
        /* 将Session中的UserContext放到 UserContext中 */
        SessionUtil.setUserContext();
        // UserContext.get().setIpAddress(CoreUtils.getIpAddr(request));
        if (urlFlg) { // 不需要登录及权限验证的资源直接通过
            filterChain.doFilter(request, response);
        } else {
            // 判定是否登录
            // 已登录的继续进行资源的权限验证
            if (FrameConstants.LOGIN_OK.equals(SessionUtil.getLonginStatus())) {
                // 根据访问资源，匹配到所有
                if (decideAuth(url)) {
                    filterChain.doFilter(request, response);
                } else {// 没有该资源的访问权限则跳转至无权限提示页面
                    // logger.warn("----------------->> 没有访问权限 : url=" + url + ",user=" +
                    // SessionUtil.getCurrentUser().getLoginname());
                    /* 设置request属性，处理逻辑交给权限拦截器，如果在此处处理，国际化问题没法解决 */
                    request.setAttribute(FrameConstants.AUTHENTICATION_KEY,
                        FrameConstants.RESPONSE_DATA_FLAG_NOAUTH);
                    filterChain.doFilter(request, response);
                }
            } else {// 没有登录的直接跳转至登录页面
                logger.info("----------------->> 没有登录，跳转到登录页面");
                /* 设置request属性，处理逻辑交给权限拦截器，如果在此处处理，国际化问题没法解决 */
                request.setAttribute(FrameConstants.AUTHENTICATION_KEY,
                    FrameConstants.RESPONSE_DATA_FLAG_NOLOGIN);
                filterChain.doFilter(request, response);
            }
        }
    }

    @Override
    public void destroy() {
    }

    /**
     * 判定该用户所访问是否具有访问该资源的权限
     */
    public boolean decideAuth(String url) {
    	//不做访问控制，登录即有访问权限
        return true;
    }
}

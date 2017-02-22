package cn.yj.market.frame.util;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import cn.yj.market.frame.constants.FrameConstants;
import cn.yj.market.frame.exception.RunException;
import cn.yj.market.frame.page.PageRequestParams;
import cn.yj.market.frame.vo.MarketUser;

/**
 * SESSION使用工具类，方便在controller、bo、dao等各层随时调用,无需传入request
 * 需要在web.xml中配置listener:
 * org.springframework.web.context.request.RequestContextListener
 */
public abstract class SessionUtil {
    /**
     * 取得request
     * 
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        return ((ServletRequestAttributes) ra).getRequest();
    }

    /**
     * 返回request.getParameter(String) ，如果getParameter为空，返回""
     * 
     * @param key
     * @return
     */
    public static String getRequestParamiter(String key) {
        if (StringUtils.isBlank(key)) {
            return "";
        }
        String pv = getRequest().getParameter(key);
        if (StringUtils.isBlank(pv)) {
            return "";
        }
        return pv;
    }

    /**
     * 保存 Request 变量
     * 
     * @param key
     * @param attributeValue
     */
    public static void setRequestAttribute(String key, Object attributeValue) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        if (attributeValue == null) {
            return;
        }
        getRequest().setAttribute(key, attributeValue);
    }

    /**
     * 取得session
     * 
     * @return HttpSession
     */
    public static HttpSession getSession() {
         RequestAttributes ra = RequestContextHolder.getRequestAttributes();
         HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
         return request.getSession();
    }

    /**
     * 保存Session变量
     * 
     * @param key
     * @param attributeValue
     */
    public static void saveSessionAttribute(String key, Object attributeValue) {
        if (StringUtils.isBlank((String) key)) {
            return;
        }
        if (attributeValue == null) {
            return;
        }
        getSession().setAttribute(key, attributeValue);
    }

    /**
     * 删除保存在Session中的变量
     * 
     * @param key
     */
    public static void removeSessionAttribute(String key) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        getSession().removeAttribute(key);
    }

    /**
     * 获取保存在Session中的变量
     * 
     * @param key
     * @return
     */
    public static Object getSessionAttribute(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return getSession().getAttribute(key);
    }

    /**
     * 删除Session
     */
    public static void invalidateUserSession() {
        SessionUtil.removeSessionAttribute(FrameConstants.SECURITY_LOGIN_KEY);
        SessionUtil.removeSessionAttribute(FrameConstants.CURRENT_USER);
        UserContext.invalidate();
    }

    /**
     * 初始化用户Session环境
     */
    public static void initUserSession() {
        if (UserContext.getUser() == null) {
            throw new RunException("未登录或登录超时，请先登录系统！");
        }
        /*
         * 将 UserContext保存到Session中，
         * 当有新的request时，
         * 直接使用Session中的UserContext
         */
        saveSessionAttribute(FrameConstants.CURRENT_USER, UserContext.get());
        saveSessionAttribute(FrameConstants.SECURITY_LOGIN_KEY,
            FrameConstants.LOGIN_OK);
    }

    /**
     * 取得当前登录者信息
     * 
     * @return HttpSession
     */
    public static MarketUser getCurrentUser() {
        return UserContext.getUser();
    }

    /**
     * 取得当前登录者信息
     * 
     * @return HttpSession
     */
    public static UserContext getSessionUserContext() {
        return (UserContext) getSessionAttribute(FrameConstants.CURRENT_USER);
    }

    /**
     * 取得当前登录者信息
     * 
     * @return HttpSession
     */
    public static void setUserContext() {
        UserContext uc = getSessionUserContext();
        if (uc != null) {
            UserContext.set(uc);
        } else {
            UserContext.init(null);
        }
    }

    /**
     * 取得当前Session登录状态
     * 
     * @return HttpSession
     */
    public static String getLonginStatus() {
        return (String) getSession().getAttribute(
            FrameConstants.SECURITY_LOGIN_KEY);
    }

    /**
     * 获取request中的分页参数
     * 
     * @return
     */
    public static PageRequestParams getPageRequestParams() {
        if (getRequest() == null) {
            return new PageRequestParams();
        }
        String page = getRequest().getParameter("page");
        String rows = getRequest().getParameter("rows");
        int pageNum = (CoreUtils.isInteger(page) ? Integer.parseInt(page) : -1);
        int pageSize =
            (CoreUtils.isInteger(rows) ? Integer.parseInt(rows) : -1);
        return new PageRequestParams(pageNum, pageSize);
    }
    
    public static void i18nHandler(HttpServletRequest request,
            HttpServletResponse response) {
            String i18nCode = request.getParameter(FrameConstants.I18N_CODE);
            if (StringUtils.isNotBlank(i18nCode)) {
                try {
                    String[] i18n = i18nCode.split("_");
                    if (i18n.length != 2) {
                        throw new Exception();
                    }
                    Locale locale = new Locale(i18n[0], i18n[1]);
                    request.getSession()
                        .setAttribute(
                            SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
                            locale);
                } catch (Exception e) {
                    request.getSession().setAttribute(
                        SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
                        new Locale("zh", "CN"));
                }
            } else {
                if (request.getSession().getAttribute(
                    SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME) == null) {
                    Locale locale = new Locale("zh", "CN");
                    request.getSession()
                        .setAttribute(
                            SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
                            locale);
                }
            }
        }
}

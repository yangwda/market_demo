package cn.yj.market.frame.constants;

/**
 * 框架常量类
 * 
 */
public final class FrameConstants {
    private FrameConstants() {
    }

    /**
     * File separator from System properties
     */
    // public static final String FILE_SEP = System.getProperty("file.separator");
    public static final String FILE_SEP = "/";

    /** 不需要登录及权限验证的资源 */
    public static final String[] NO_SECURITY_AUTH_URLS = {"/index.jsp",
        "/login.jsp", "/common/**", "/uploadify/**", "/images/**",
        "/styles/**", "/commonselect/**", "/get/**", "/scripts/**",
        "/services/**", "/main/loginSystem", "/main/updatePassword",
        "/main/logOut", "/file/**", "/itfc/**", "/utils/**"};

    /** 登录后，对每个用户，默认追加的访问权限 */
    public static final String[] DEFAULT_AUTHORIZED_MENULIST = {"/welcome",
        "/jump"};

    /** 登录验证失败跳转页面 */
    public static final String SECURITY_REDIRECT_URL = "/login.jsp";

    /** 权限Session的KEY */
    public static final String AUTHENTICATION_KEY = "AUTHENTICATION_KEY";

    /** 登录标志放入Session的KEY */
    public static final String SECURITY_LOGIN_KEY = "SECURITY_LOGIN_KEY";

    /** 已经登录标记 */
    public static final String LOGIN_OK = "LOGIN_OK";

    /** 当前登录用户 */
    public static final String CURRENT_USER = "CURRENT_USER";

    /** 个人角色列表 */
    public static final String USER_ROLES = "USER_ROLES";

    /** 登录的人员信息 SessionKEY */
    public static final String LOGIN_PERSON_ID = "LOGIN_PERSON_ID";

    /** 成功 **/
    public static final String RESPONSE_DATA_FLAG_SUCCESS = "success";

    /** 错误 **/
    public static final String RESPONSE_DATA_FLAG_ERROR = "error";

    /** 校验失败 **/
    public static final String RESPONSE_DATA_FLAG_VFAILED = "vfailed";

    /** 没有登录 **/
    public static final String RESPONSE_DATA_FLAG_NOLOGIN = "nologin";

    /** 没有权限 **/
    public static final String RESPONSE_DATA_FLAG_NOAUTH = "noauth";

    /** 附件路径定义 **/
    public static final String ATTR_PATH = "D:/upload/files";

    /* 请求参数，如果requestType=json，异常处理时返回json */
    public static final String RESPONSE_DATA_TYPE_PARAMNAME =
        "responseDataType";

    public static final String RESPONSE_DATA_TYPE_JSON = "json";

    public static final String RESPONSE_DATA_TYPE_HTML = "html";

    /* 请求参数，国际化使用 */
    public static final String I18N_CODE = "i18nCode";

    /* 系统名称 */
    public static final String SYSTEM_NAME = "WMSWEB";
}

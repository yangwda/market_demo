<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%-- 
<link rel="stylesheet" type="text/css" href="/styles/themes/default/easyui.css" id="themesCss">
--%>
<link rel="stylesheet" type="text/css" href="/styles/themes/icon.css">
<link rel="stylesheet" type="text/css" href="/styles/ea.css">
<link rel="shortcut icon" href="/images/yj.ico"  type="image/x-icon" />

<script type="text/javascript" src="/scripts/jquery.min.js"></script>
<script type="text/javascript" src="/scripts/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="/scripts/ajaxfileupload.js"></script>
<script type="text/javascript" src="/scripts/JSONUtil.js"></script>
<script type="text/javascript" src="/scripts/common.js"></script>

<% request.setAttribute("RESPONSE_DATA_FLAG_SUCCESS",cn.yj.market.frame.controller.BaseController.SUCESS); %>
<% request.setAttribute("RESPONSE_DATA_FLAG_ERROR",cn.yj.market.frame.controller.BaseController.ERROR); %>
<% request.setAttribute("RESPONSE_DATA_FLAG_VFAILED",cn.yj.market.frame.controller.BaseController.VFAILED); %> 
<% request.setAttribute("RESPONSE_DATA_FLAG_NOLOGIN",cn.yj.market.frame.controller.BaseController.NOLOGIN); %> 
<% request.setAttribute("RESPONSE_DATA_FLAG_NOAUTH",cn.yj.market.frame.controller.BaseController.NOAUTH); %> 

<% request.setAttribute("RESPONSE_DATA_TYPE_PARAMNAME",cn.yj.market.frame.constants.FrameConstants.RESPONSE_DATA_TYPE_PARAMNAME); %> 
<% request.setAttribute("RESPONSE_DATA_TYPE_JSON",cn.yj.market.frame.constants.FrameConstants.RESPONSE_DATA_TYPE_JSON); %> 
<% request.setAttribute("RESPONSE_DATA_TYPE_HTML",cn.yj.market.frame.constants.FrameConstants.RESPONSE_DATA_TYPE_HTML); %> 

<% request.setAttribute("GLOBLE_REQUEST_URL",request.getRequestURL()); %> 

<script type="text/javascript">
  var ctx = '';
  var GLOBLE_REQUEST_URL = '/' ;
  
  <%-- å¨å±JSåé --%>
  var RESPONSE_DATA_FLAG_SUCCESS = '${RESPONSE_DATA_FLAG_SUCCESS}';
  var RESPONSE_DATA_FLAG_ERROR = '${RESPONSE_DATA_FLAG_ERROR}';
  var RESPONSE_DATA_FLAG_VFAILED = '${RESPONSE_DATA_FLAG_VFAILED}';
  var RESPONSE_DATA_FLAG_NOLOGIN = '${RESPONSE_DATA_FLAG_NOLOGIN}';
  var RESPONSE_DATA_FLAG_NOAUTH = '${RESPONSE_DATA_FLAG_NOAUTH}';
  
  var RESPONSE_DATA_TYPE_PARAMNAME = '${RESPONSE_DATA_TYPE_PARAMNAME}';
  var RESPONSE_DATA_TYPE_JSON = '${RESPONSE_DATA_TYPE_JSON}';
  var RESPONSE_DATA_TYPE_HTML = '${RESPONSE_DATA_TYPE_HTML}';
  
  var GLOBLE_MSG_EXCEPTION_UNKNOW = '发生系统异常，请联系管理员!' ;
  var GLOBLE_MSG_LOADDATA_PROMPT = '提示' ;
  var GLOBLE_STR_MSGTITLE_ALERT = '消息' ;
  var GLOBLE_STR_MSGTITLE_ERROR = '错误' ;
  var GLOBLE_STR_MSGTITLE_QUESTION = '确认' ;
  var GLOBLE_STR_MSGTITLE_INFO = '信息' ;
  var GLOBLE_STR_MSGTITLE_WARNING = '警告' ;
  
</script>
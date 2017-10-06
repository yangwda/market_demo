<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<!DOCTYPE html>
<html>
  <head>
    <%@ include file="/common/meta.jsp"%>
    <title>MARGON</title>
    <link rel="stylesheet" type="text/css" href="/styles/themes/lightblue/easyui.css" id="themesCss">
<link rel="stylesheet" type="text/css" href="/styles/themes/icon.css">
<link rel="stylesheet" type="text/css" href="/styles/ea.css">
<link rel="shortcut icon" href="/images/yj.ico"  type="image/x-icon" />

<script type="text/javascript" src="/scripts/jquery.min.js"></script>
<script type="text/javascript" src="/scripts/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/scripts/easyui-lang-zh_CN.js"></script>
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
  
  ///
  $(document).ready(function(){
	  doRefreshDataGrid() ;
    });
    
    var doRefreshDataGrid = function(){
		$('#callbackGrid').datagrid('loadData', { total: 0, rows: [] }); 
		doSearch();
	};

	// 查询
	function doSearch(){
	    $('#callbackGrid').datagrid({
	    	loader: function(param, success, error) {
		      	boDataLoader('${ctx}/callBack/getCallBackList', {}, success, error);
		   }
	    });//.datagrid('load' ,params);
	}
	function memberTelFormator(value,row,index){
		return row.memberTel + "," + row.memberPhone ;
	}
</script>

    <script type="text/javascript" src="/scripts/jquery.portal.js"></script>
    <script type="text/javascript" src="/scripts/jshash-2.2/md5-min.js"></script>
    <script type="text/javascript"> 
	    $(function(){
			$('#portalBody').portal({
				border:false,
				fit:true
			});
		});
		function remove(){
			$('#portalBody').portal('resize');
		}
    </script>
  </head>
  <body class="easyui-layout" data-options="fit:true" style="margin: -1px;">
  		<div id="portalBody" style="position:relative">
			<div style="width:50%;">
<!-- 			    <div title="日历" collapsible="false" closable="false" style="height:200px;padding:5px;"> -->
<!-- 			    	<div class="easyui-calendar" fit="true"></div> -->
<!-- 			    </div> -->
				<div class="easyui-calendar" closable="false" style="height:200px;"></div>
			    <div id="pgrid" title="备忘录" closable="false" style="height:200px;">
					<table class="easyui-datagrid" style="width:650px;height:auto"
							fit="true" border="false"
							singleSelect="true"
							idField="itemid" url="">
						<thead>
							<tr>
								<th field="itemid" width="20%">日期</th>
								<th field="productid" width="70%">备忘</th>
								<th field="status" width="10%" align="center">处理</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
			<div style="width:50%;">
				<div id="pgrid" title="回访提示" closable="false" style="height:407px;">
					<table class="easyui-datagrid" id="callbackGrid" style="width:650px;height:auto"
							fit="true" border="false"
							singleSelect="true" nowrap="false"
							idField="itemid" url="">
						<thead>
							<tr>
<!-- 								<th field="createTime" width="15%">日期</th> -->
								<th field="memberName" width="20%">客户</th>
								<th field="memberPhone" formatter="memberTelFormator" width="35%">电话</th>
								<th field="callBackRemarks" width="45%">备注</th>
<!-- 								<th field="callBackOver" width="10%" align="center">回访</th> -->
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
  </body>
</html>
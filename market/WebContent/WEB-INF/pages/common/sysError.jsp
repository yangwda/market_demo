<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/common/meta.jsp"%>
<title>MARGON</title>
<link rel="stylesheet" type="text/css" href="/styles/themes/default/easyui.css" id="themesCss">
<%@ include file="/common/cssAndJs.jsp"%>
<script type="text/javascript">
	
$(document).ready(function() {
	if(RESPONSE_DATA_FLAG_NOLOGIN == '${errStatus}'){
		msg_info(GLOBLE_STR_MSGTITLE_INFO ,'${errMsg }' ,function(){reLogin()}) ;
	}
});
	
</script>
</head>
<body>

	<div id="p" class="easyui-panel"
		style="width: 100%; height: 100%; padding: 10px; background: #fafafa;"
		data-options="closable:false,border:false,fix:true,
    		collapsible:true,minimizable:false,maximizable:false">
		<p></p>
		<p></p>
		<p></p>
		<p></p>
		<p></p>
		<p></p>
		<p></p>
		<p></p>
		<p></p>
		<p></p>
		<p>					<strong>${errMsg }</strong></p>
		<p></p>
		<p></p>
		<p></p>
		<p></p>
		<p></p>
		<p></p>
	</div>
</body>

</html>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
  <head>
    <%@ include file="/common/meta.jsp"%>
    <title>购物单据</title>
    <link rel="stylesheet" type="text/css" href="/styles/themes/lightblue/easyui.css" id="themesCss">
	<%@ include file="/common/cssAndJs.jsp"%>
    <script type="text/javascript"> 
    
    // 页面键盘屏蔽
    $(document).keydown(function(event){
      if(event.keyCode==116) return false;  //屏蔽F5刷新键
    });
    
    $(document).ready(function(){
    	
    });
    
	function printPage(){
		window.print(); 
	}
    </script>

  </head>
  <body class="easyui-layout">
  <div data-options="region:'center',border:false,fit:true"  style="padding: 0 20px;overflow-y:'auto'">

<table>
	<tr><td width="127"></td><td width="127"></td><td width="127"></td><td width="127"></tr>
	<tr><td align="left">付款方式</td><td align="right">付款时间</td><td align="right">付款金额</td><td align="right">付款类型</td></tr>
	<tr><td align="left"><hr></td><td><hr></td><td><hr></td><td><hr></td></tr>
	<c:forEach var="pdi" items="${payoffs}">
		<tr style="font-weight:bold">
			<td align="left" >${pdi.payOffWay }</td>
			<td align="right">${pdi.payOffTime }</td>
			<td align="right">${pdi.payOffMoney }</td>
			<td align="right">${pdi.payOffType }</td>
		</tr>
	</c:forEach>
	
	<tr>
		<td colspan="1"><hr></td>
		<td colspan="1"><hr></td>
		<td colspan="1"><hr></td>
		<td colspan="1"><hr></td>
	</tr>
	<tr style="font-weight:bold">
		<td colspan="2" align="right">总计：</td>
		<td colspan="1" align="right">${ttmny }</td>
		<td align="right">&nbsp;</td>
	</tr>
</table>

</div>
</body>
</html>
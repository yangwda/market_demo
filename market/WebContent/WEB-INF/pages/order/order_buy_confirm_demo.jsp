<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<!DOCTYPE html>
<html>
  <head>
    <%@ include file="/common/meta.jsp"%>
    <title>单据确认</title>
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
	function orderPayoff(){
		$("#orderPayoff").submit() ;
	}
    </script>

  </head>
  <body style="padding: 0 20px;">
  <form id="orderPayoff" method="post" action="${ctx}/order/orderPayoff">
  	<table>
  		<tr><td width="60"></td><td width="60"></td><td width="60"></td><td width="60"></td><td width="60"></td><td width="60"></td><td width="60"></td></tr>
		<tr>
			<td colspan="2" align="left">会员：贝克汉姆</td>
			<td colspan="2" align="left">会员号：2017080100009</td>
			<td colspan="3" align="left">电话：041183430001,13241790505</td>
		</tr>
		<tr>
			<td colspan="7" align="left">地址：城子坦大陆村16号</td>
		</tr>
		<tr><td colspan="7">&nbsp;</td></tr>
		<tr style="">
			<td colspan="5" align="left" >陇西兰州牛肉拉面+羊肉大串儿</td>
			<td align="right">5大碗儿</td>
			<td align="right">72.00</td>
		</tr>
		<tr style="">
			<td colspan="5" align="left" >陇西兰州牛肉拉面+羊肉大串儿+陇西兰州牛肉拉面+羊肉大串儿+陇西兰州牛肉拉面+羊肉大串儿+陇西兰州牛肉拉面+羊肉大串儿+</td>
			<td align="right">5大碗儿</td>
			<td align="right">72.00</td>
		</tr>
		<tr style="color:blue">
			<td></td>
			<td colspan="5" align="left">送小花碗儿一个</td>
		</tr>
		<tr style="">
			<td colspan="5" align="left" >陇西兰州牛肉拉面+羊肉大串儿</td>
			<td align="right">5大碗儿</td>
			<td align="right">72.00</td>
		</tr>
		<tr style="">
			<td colspan="5" align="left" >陇西兰州牛肉拉面+羊肉大串儿</td>
			<td align="right">5大碗儿</td>
			<td align="right">72.00</td>
		</tr>
		<tr style="">
			<td colspan="5" align="left" >陇西兰州牛肉拉面+羊肉大串儿</td>
			<td align="right">5大碗儿</td>
			<td align="right">72.00</td>
		</tr>
		<tr style="color:blue">
			<td></td>
			<td colspan="5" align="left">买6送3</td>
		</tr>
		<tr style="">
			<td colspan="5" align="left" >陇西兰州牛肉拉面+羊肉大串儿</td>
			<td align="right">5大碗儿</td>
			<td align="right">72.00</td>
		</tr>
		<tr style="">
			<td colspan="5" align="left" >陇西兰州牛肉拉面+羊肉大串儿</td>
			<td align="right">5大碗儿</td>
			<td align="right">72.00</td>
		</tr>
		
		<tr><td colspan="7">&nbsp;</td></tr>
		<tr style=""><td colspan="6" align="right">金额总计</td><td align="right">1888.00</td></tr>
		<tr style=""><td colspan="6" align="right">可用代金券</td><td align="right">15.00</td></tr>
		<tr style=""><td colspan="6" align="right">支付金额</td><td align="right">
			<input type="text" value="1873.00" class="easyui-textbox" style="width:72px" /></td></tr>
		<tr><td colspan="7">&nbsp;</td></tr>
		<tr style="color:blue">
			<td colspan="7" align="right">代金券1张，50元。</td>
		</tr>
		<tr><td colspan="7">&nbsp;</td></tr>
		<tr >
			<td colspan="7" align="right"><input class="easyui-textbox" name="callBackRemarks" data-options="multiline:true,width:720,prompt:'回访备注'" style="height:60px"></input></td>
		</tr>
		<tr >
			<td colspan="7" align="right"><input class="easyui-textbox" name="orderRemarks" data-options="multiline:true,width:720,prompt:'单据备注'" style="height:60px"></input></td>
		</tr>
		<tr><td colspan="7">&nbsp;</td></tr>
		<tr><td colspan="7" align="right" style="padding: 0 20px;">
			<a href="#" class="" data-options="iconCls:'icon-ok'" onclick="orderPayoff();" style="width:15%">付款</a>
			</td></tr>
	</table>
  	
  </form>
  </body>
</html>

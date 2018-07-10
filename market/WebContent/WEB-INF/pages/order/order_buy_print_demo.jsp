<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

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
  <body style="padding: 0 20px;">
  	<table>
  		<tr><td width="60"></td><td width="60"></td><td width="60"></td><td width="60"></td><td width="60"></td><td width="60"></td><td width="60"></td></tr>
		<tr>
			<td colspan="2" align="left" style="color:blue;cursor:pointer" onclick="$('#printLink').show() ;">2017-09-01 08:08:08</td>
			<td colspan="2" align="left">诚意药房（城子坦店）</td>
			<td colspan="3" align="left">李哥，13009459378</td>
		</tr>
		<tr><td colspan="7"><hr></td></tr>
		<tr>
			<td colspan="2" align="left" style="">会员：贝克汉姆</td>
			<td colspan="2" align="left">会员号：2017080100009</td>
			<td colspan="3" align="left">电话：041183430001,13241790505</td>
		</tr>
		<tr>
			<td colspan="7" align="left">地址：城子坦大陆村16号</td>
		</tr>
		<tr><td colspan="7"><hr></td></tr>
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
		
		<tr><td colspan="5">&nbsp;</td><td colspan="2"><hr></td></tr>
		<tr style=""><td colspan="6" align="right">金额总计</td><td align="right">1888.00</td></tr>
		<tr style=""><td colspan="6" align="right">可用代金券</td><td align="right">15.00</td></tr>
		<tr style=""><td colspan="6" align="right">支付金额</td><td align="right">1873.00</td></tr>
		<tr><td colspan="7"><hr></td></tr>
		<tr style="color:blue">
			<td colspan="7" align="left"><b>惠赠：</b></td>
		</tr>
		<tr style="color:blue;">
			<td colspan="7" align="left" style="padding: 0 20px;">送小花碗儿一个</td>
		</tr>
		<tr style="color:blue;">
			<td colspan="7" align="left" style="padding: 0 20px;">送消炎药5包</td>
		</tr>
		<tr style="color:blue;">
			<td colspan="7" align="left" style="padding: 0 20px;">代金券1张，50元。</td>
		</tr>
		<tr><td colspan="7"><hr></td></tr>
		<tr><td colspan="7">&nbsp;</td></tr>
		<tr style="color:green">
			<td colspan="7" align="left"><b>会员累积信息：</b></td>
		</tr>
		<tr style="color:green">
			<td colspan="7" align="left" style="padding: 0 20px;">15元代金券，2张；</td>
		</tr>
		<tr style="color:green">
			<td colspan="7" align="left" style="padding: 0 20px;">肥肥肥肥肥，累积5包（累积满10包送热水壶1个）；</td>
		</tr>
		<tr><td colspan="7">&nbsp;</td></tr>
		<tr><td colspan="7" align="left" style="padding: 0 30px;">&nbsp;</td></tr>
		<tr><td colspan="7" align="right">
			<a href="#" class="" id="printLink" onclick="$(this).hide() ;printPage();" style="width:15%">打印</a>
			</td></tr>
	</table>
  </body>
</html>

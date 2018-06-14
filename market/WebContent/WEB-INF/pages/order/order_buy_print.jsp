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
  		<tr><td style="width:88px;"></td><td style="width:88px;"></td><td style="width:99px;"></td><td style="width:88px;"></td><td style="width:88px;"></td><td style="width:88px;"></td><td style="width:88px;"></td></tr>
		<tr>
			<td colspan="2" align="left" style="color:blue;cursor:pointer" onclick="$('#printLink').show() ;">${printDate }</td>
			<td colspan="2" align="left">诚意药房（城子坦店）</td>
			<td colspan="3" align="left">李哥，13009459378</td>
		</tr>
		<tr><td colspan="7"><hr></td></tr>
		<tr>
			<td colspan="2" align="left" style="">会员：${member.memberName }</td>
			<td colspan="2" align="left">会员号：${member.memberNo }</td>
			<td colspan="3" align="left">电话：${member.memberTel },${member.memberPhone }</td>
		</tr>
		<tr>
			<td colspan="7" align="left">地址：${member.memberAddress }</td>
		</tr>
		<tr><td colspan="7"><hr></td></tr>
		<c:forEach var="ol" items="${lineList}">
			<tr style="">
				<td colspan="5" align="left" >${ol.goodsName }</td>
				<td align="right">${ol.goodsCount } &nbsp; ${ol.goodsCountUnit }</td>
				<td align="right">${ol.goodsOrderPrice }</td>
			</tr>
		</c:forEach>
		<tr><td colspan="5">&nbsp;</td><td colspan="2"><hr></td></tr>
		<tr style=""><td colspan="6" align="right">金额总计</td><td align="right">${order.orderTotalMoney }</td></tr>
		<tr style=""><td colspan="6" align="right">代金券</td><td align="right">${order.payOffVoucherTotalMoney }</td></tr>
		<tr style=""><td colspan="6" align="right">已支付金额</td><td align="right">${order.payOffCashTotalMoney }</td></tr>
		<c:if test="${cutMoney  != 0}">
			<tr style=""><td colspan="6" align="right">抹零</td><td align="right">${order.orderCutMoney }</td></tr>
		</c:if>
		<tr style=""><td colspan="6" align="right">未支付金额</td><td align="right">${charge }</td></tr>
		<tr><td colspan="7"><hr></td></tr>
		<tr style="color:blue">
			<td colspan="7" align="left"><b>惠赠：</b></td>
		</tr>
		
		<c:forEach var="gl" items="${giftLineList}">
			<tr style="color:blue;">
				<td colspan="7" align="left" style="padding: 0 20px;">${gl }</td>
			</tr>
		</c:forEach>
		
		<tr><td colspan="7"><hr></td></tr>
		<tr><td colspan="7">&nbsp;</td></tr>
		<tr style="color:green">
			<td colspan="7" align="left"><b>会员累积信息：</b></td>
		</tr>
		<tr style="color:green">
			<td colspan="7" align="left" style="padding: 0 20px;">代金券累积：${totalVoucher }</td>
		</tr>
		<tr style="color:green">
			<td colspan="7" align="left" style="padding: 0 20px;">等值商品累积：${memberGiftAcc }</td>
		</tr>
		<tr><td colspan="7">&nbsp;</td></tr>
		<tr><td colspan="7" align="left" style="padding: 0 30px;">&nbsp;</td></tr>
		<tr><td colspan="7" align="right">
			<a href="#" class="" id="printLink" onclick="$(this).hide() ;printPage();" style="width:15%">打印</a>
			</td></tr>
	</table>
  </div>
  </body>
</html>

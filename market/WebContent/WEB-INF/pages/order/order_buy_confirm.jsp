<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
    
    var drfDiffAmount = '${drfDiffAmount}' ;
    
    $(document).ready(function(){
    	
    });
    
	function printPage(){
		window.print(); 
	}
	function orderPayoff(){
		var params = $("#orderPayoff").serializeArray();
    	execAjax("${ctx}/order/payOrder", params, false, function(retData){
    		if(!retData){
    			msg_error("错误", "系统错误，请联系管理员！") ;
    			return ;
    		}
    		if(retData.flag == "OK"){
    			$("#orderPayoff").submit() ;
    			return ;
    		}
    		if(retData.msg){
    			msg_warning("提示",retData.msg) ;
    			return ;
    		}
    		msg_error("错误", "系统错误，请联系管理员！") ;
	    });
	}
	<c:if test="${order.payOffStatus  == '未付款'}">
	function orderNew(){
		$("#buyConfirmForm").submit() ;
	}
	</c:if>
	var onceBuyConfig = [] ;
	<c:forEach var="onceBuy" items="${onceBuyList}">
		onceBuyConfig.push({onceById: "${onceBuy.onceById}" ,beginAmount: "${onceBuy.beginAmount}" ,endAmount: "${onceBuy.endAmount}" ,perRate: "${onceBuy.perRate}"}) ;
	</c:forEach>
	function changeCutMoney(newValue,oldValue){
		if(!oldValue){
			oldValue = 0 ;
		}
		if(!newValue){
			newValue = 0 ;
		}
		var cutMoney = parseFloat(newValue).toFixed(2) ;
		$("#cutMoneyInput").textbox('setValue',cutMoney) ;
		var charge = $("#initChargeMoney").val() ;
		var voucherMoney = $("#voucherInput").val() ;
		var newCharge = charge - cutMoney - voucherMoney ;
		newCharge = parseFloat(newCharge).toFixed(2) ;
		$("#chargeInput").textbox('setValue',newCharge) ;
		$("#chargeTextTd").html(newCharge) ;
	}
	function changeVoucherMoney(newValue,oldValue){
		if(!oldValue){
			oldValue = 0 ;
		}
		if(!newValue){
			newValue = 0 ;
		}
		var voucherMoney = parseFloat(newValue).toFixed(2) ;
		if(voucherMoney - parseFloat('${totalVoucher }').toFixed(2) > 0){
			msg_warning("提示","使用代金券金额，不能超过可用代金券金额！") ;
			$("#voucherInput").textbox('setValue',"0.00") ;
			return ;
		}
		$("#voucherInput").textbox('setValue',voucherMoney) ;
		var charge = $("#initChargeMoney").val() ;
		var cutMoney = $("#cutMoneyInput").val() ;
		var newCharge = charge - voucherMoney - cutMoney ;
		newCharge = parseFloat(newCharge).toFixed(2) ;
		$("#chargeInput").textbox('setValue',newCharge) ;
		$("#chargeTextTd").html(newCharge) ;
	}
	function changeChargeMoney(newValue){
		var obc = 0 ;
		var pay = parseFloat(newValue) ;
		pay = pay - parseFloat(drfDiffAmount) ;
		for(var i=0;i<onceBuyConfig.length;i++){
			var tt = onceBuyConfig[i] ;
			if(pay >= tt.beginAmount && pay <= tt.endAmount){
				obc = tt ;
				break ;
			}
		}
		if(obc){
			//单次购买，${onceBuy.beginAmount} ~ ${onceBuy.endAmount}元，惠赠${onceBuy.perRate}%的代金券或等值商品！！
			var html = "单次购买，"+obc.beginAmount+" ~ "+obc.endAmount+"元，惠赠"+obc.perRate+"%的代金券或等值商品！！<br>" ;
			html += '<label> <input name="onceBuyGift" type="radio" value="'+(obc.onceById + '__D')+'" checked> 代金券</label>' ;
			html += '<label> <input name="onceBuyGift" type="radio" value="'+(obc.onceById + '__C')+'"> 兑现产品</label>' ;
			$("#onceBuyGiftTextTd").html(html) ;
		}
		else{
			$("#onceBuyGiftTextTd").html("") ;
		}
	}
    </script>

  </head>
  <body class="easyui-layout">
  <c:if test="${order.payOffStatus  == '未付款'}">
   <div style="display:none;">
	 <form id="buyConfirmForm" method="post" action="${ctx}/order/editOrder">
	 	<input type="hidden" name="orderId" value="${order.orderId }" />
  	 </form>
  </div>
  </c:if>
  <div data-options="region:'center',border:false,fit:true"  style="padding: 0 20px;overflow-y:'auto'">
  <form id="orderPayoff" method="post" action="${ctx}/order/orderPrint">
  	<table>
  		<tr><td width="60"></td><td width="60"></td><td width="60"></td><td width="60"></td><td width="60"></td><td width="60"></td><td width="60"></td><td width="60"></td><td width="60"></td></tr>
		<tr>
			<td colspan="2" align="left">会员：${member.memberName }</td>
			<td colspan="2" align="left">会员号：${member.memberNo }</td>
			<td colspan="3" align="left">电话：${member.memberTel },${member.memberPhone }</td>
		</tr>
		<tr>
			<td colspan="4" align="left">地址：${member.memberAddress }</td>
			<td colspan="3" align="left"><strong>可用代金券：${totalVoucher }</strong></td>
		</tr>
		<tr><td colspan="7">&nbsp;</td></tr>
		<tr><td colspan="5" align="left">商品名称</td><td align="right">购买数量</td><td align="right">购买金额</td><td colspan="2" align="right">等值产品金额</td></tr>
		<tr><td colspan="5" align="left"><hr></td><td><hr></td><td><hr></td><td colspan="2"><hr></td></tr>
		<c:forEach var="ol" items="${lineList}">
			<tr style="">
				<td colspan="5" align="left" >${ol.goodsName }</td>
				<td align="right">${ol.goodsCount } &nbsp; ${ol.goodsCountUnit }</td>
				<td align="right">${ol.goodsOrderPrice }</td>
				<td colspan="2" align="right">${ol.giftAmount }</td>
			</tr>
<%-- 			<c:if test="${ol.giftInfo  != null}"> --%>
<%-- 				<c:forEach var="gl" items="${ol.giftInfo}"> --%>
<!-- 					<tr style="color:blue"> -->
<!-- 						<td></td> -->
<%-- 						<td colspan="5" align="left">${gl}</td> --%>
<!-- 					</tr> -->
<%-- 				</c:forEach> --%>
<%-- 			</c:if> --%>
		</c:forEach>
		
		<tr><td colspan="7">&nbsp;</td></tr>
		<tr><td colspan="5">&nbsp;</td><td colspan="2"><hr></td><td colspan="2"><hr></td></tr>
		<tr style=""><td colspan="6" align="right">金额总计</td><td align="right">${order.orderTotalMoney }</td><td colspan="2" align="right">${order.orderTotalGiftAmount }</td></tr>
		<tr style="">
			<td colspan="6" align="right"><strong>代金券：</strong></td>
			<td align="right">
				<input type="text" value="${order.payOffVoucherTotalMoney }" id="voucherInput" name="voucherMoney" class="easyui-textbox" data-options="onChange:changeVoucherMoney" style="width:72px;text-align:right" />
			</td>
		</tr>
		<tr style=""><td colspan="6" align="right">已支付</td><td align="right">${order.payOffCashTotalMoney }</td></tr>
		<tr style="">
			<td colspan="6" align="right"><strong>抹零：</strong></td>
			<c:if test="${cutMoney  == 0}">
				<td align="right"><input type="text" id="cutMoneyInput" name="cutMoney" value="${order.orderCutMoney }" data-options="onChange:changeCutMoney" 
				class="easyui-textbox" style="width:72px;text-align:right" /></td>
			</c:if>
			<c:if test="${cutMoney  != 0}">
				<td align="right">
					<input type="hidden" name="cutMoney" value="${order.orderCutMoney }" />
					${order.orderCutMoney }
					<input type="hidden" id="cutMoneyInput" name="cutMoneyInput" value="0" />
				</td>
			</c:if>
			<td colspan="2" align="right" style="color:yellow;">
				&nbsp;
				代乳粉差：${drfDiffAmount }
			</td>
		</tr>
		<tr style="">
			<td colspan="6" align="right">待支付<input type="hidden" id="initChargeMoney" value="${charge }" /></td>
			<td align="right" id="chargeTextTd">
				${charge }
			</td>
			<td colspan="2" align="right" style="color:blue;">
				&nbsp;
				<c:if test="${firstPay == 1}">
					<label>兑换商品<input type="radio" name="giftFlag" value="G" checked="checked"></label>
				</c:if>
			</td>
		</tr>
		<tr style="">
			<td colspan="6" align="right"><strong>付款：</strong></td>
			<td align="right" id="">
				<input type="text" value="0.00" id="chargeInput" name="chargeMoney" class="easyui-textbox" data-options="onChange:changeChargeMoney" style="width:72px;text-align:right" />
			</td>
			<td colspan="2" align="right" style="color:blue;">
				&nbsp;
				<c:if test="${firstPay == 1}">
					<label>赠代金券<input type="radio" name="giftFlag" value="V"></label>
				</c:if>
			</td>
		</tr>
		<tr><td colspan="7">&nbsp;</td></tr>
		<tr style="color:blue">
			<td></td>
			<td colspan="5" align="left" id="onceBuyGiftTextTd"></td>
		</tr>
		<tr><td colspan="7">&nbsp;</td></tr>
		<tr >
			<td colspan="7" align="right">
				<input class="easyui-textbox" name="callBackRemarks" data-options="multiline:true,width:720,prompt:'回访备注'" style="height:60px"></input>
			</td>
		</tr>
		<tr >
			<td colspan="7" align="right"><input class="easyui-textbox" name="orderRemarks" data-options="multiline:true,width:720,prompt:'单据备注'" style="height:60px"></input></td>
		</tr>
		<tr><td colspan="7">&nbsp;</td></tr>
		<tr><td colspan="7" align="right" style="padding: 0 20px;">
			<input type="hidden" name="orderId" value="${order.orderId }" />
			<a href="#" class="" data-options="iconCls:'icon-ok'" onclick="orderPayoff();" style="width:15%">付款</a>
			<c:if test="${order.payOffStatus  == '未付款'}">
			&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" class="" data-options="iconCls:'icon-edit'" onclick="orderNew();" style="width:15%">改单</a>
			</c:if>
			</td></tr>
	</table>
  	
  </form>
  </div>
  </body>
</html>

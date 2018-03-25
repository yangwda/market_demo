<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table>
	<tr><td width="72"></td><td width="72"></td><td width="72"></td><td width="72"></td><td width="72"></td><td width="72"></td><td width="72"></td><td width="72"></td><td width="72"></td></tr>
	<tr><td colspan="5" align="left">商品名称</td><td align="right">购买数量</td><td align="right">购买金额</td><td colspan="2" align="right">等值产品金额</td></tr>
	<tr><td colspan="5" align="left"><hr></td><td><hr></td><td><hr></td><td colspan="2"><hr></td></tr>
	<c:forEach var="pdi" items="${pdiList}">
		<tr style="font-weight:bold">
			<td colspan="5" align="left" >${pdi.pname }</td>
			<td align="right">${pdi.pct } &nbsp; ${pdi.pctu }</td>
			<td align="right">${pdi.payment }</td>
			<td colspan="2" align="right">${pdi.giftAmount }</td>
		</tr>
<%-- 		<c:if test="${pdi.gift != null}"> --%>
<!-- 			<tr style="color:lightblue"> -->
<!-- 				<td></td> -->
<!-- 				<td colspan="5" align="left"> -->
<%-- 					<c:forEach var="giftConfig" items="${pdi.gift.configLine}"> --%>
<%-- 						<label> <input name="GC___${giftConfig.nm }" type="radio" value="${giftConfig.vl }" onclick="checkGiftItem(this);" ${giftConfig.ck }> ${giftConfig.common }</label><br> --%>
<%-- 					</c:forEach> --%>
<%-- 					<c:if test="${pdi.gift.common != null}"> --%>
<%-- 						${pdi.gift.common } --%>
<%-- 					</c:if> --%>
<!-- 				</td> -->
<!-- 			</tr> -->
<%-- 		</c:if> --%>
	</c:forEach>
	
	<tr><td colspan="7">&nbsp;</td></tr>
	<tr>
		<td colspan="4">&nbsp;</td>
		<td colspan="1"><hr></td>
		<td colspan="1"><hr></td>
		<td colspan="1"><hr></td>
		<td colspan="2"><hr></td>
	</tr>
	<tr style="font-weight:bold">
		<td colspan="4" align="right">&nbsp;</td>
		<td colspan="1" align="right">总计：</td>
		<td colspan="1" align="right">${totalBuyCount }&nbsp;件</td>
		<td align="right">${totalPayment}</td>
		<td colspan="2" align="right">${totalGiftAmount}</td>
	</tr>
<%-- 	<tr style="font-weight:bold"><td colspan="6" align="right">可用代金券：</td><td align="right">${voucher }</td></tr> --%>
<%-- 	<tr style="font-weight:bold"><td colspan="6" align="right">待支付金额：</td><td align="right">${pay }</td></tr> --%>
	<tr><td colspan="7">&nbsp;</td></tr>
	<c:forEach var="onceBuy" items="${onceBuyList }">
		<tr style="color:lightblue">
			<td colspan="7" align="left">单次购买，${onceBuy.beginAmount} ~ ${onceBuy.endAmount}元，惠赠${onceBuy.perRate}%的代金券或等值商品！！</td>
		</tr>
	</c:forEach>
</table>

<script type="text/javascript"> 
	var drugGiftList = [] ;
	<c:forEach var="dgc" items="${pdiList}">
		drugGiftList.push({gamid : "${dgc.gamid}" ,pid : "${dgc.pid}" ,giftConfigLineId: "${dgc.giftConfigLineId}" ,payment: "${dgc.payment}",unitPrice: "${dgc.unitPrice}"}) ;
	</c:forEach>
	var prl = $("#addFormBuyGoodsList").datagrid('getRows') ;
	if(prl && prl.length){
		if(drugGiftList.length){
			for(var j=0;j<drugGiftList.length;j++){
				var dgc = drugGiftList[j] ;
				for(var i=0;i<prl.length;i++){//{gid:gid,gn: gn,gct:gct,gctu:gctu} ;
					var r = prl[i] ;
					if(r.gamid == dgc.gamid){
// 						ppp += "@@@" + r.gid+"___" + r.gct + "___" + r.gctu + "___" + r.gftcfg ;
						r.payment = dgc.payment ;
						r.unitPrice = dgc.unitPrice ;
						r.gftcfg = dgc.giftConfigLineId ;
					}
				}
			}
		}
// 		for(var i=0;i<prl.length;i++){//{gid:gid,gn: gn,gct:gct,gctu:gctu} ;
// 			var r = prl[i] ;
// 			console.log(r) ;
// 		}
	}
</script>
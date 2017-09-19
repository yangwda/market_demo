<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
  <head>
    <%@ include file="/common/meta.jsp"%>
    <title>新建销售单据信息</title>
    <link rel="stylesheet" type="text/css" href="/styles/themes/lightblue/easyui.css" id="themesCss">
	<%@ include file="/common/cssAndJs.jsp"%>
    <script type="text/javascript"> 
    
    // 页面键盘屏蔽
    $(document).keydown(function(event){
      if(event.keyCode==116) return false;  //屏蔽F5刷新键
    });
    
    $(document).ready(function(){
    	$('#merberInfoInputBox').combobox({
			prompt : '会员信息',
			required : false,
			mode : 'remote',
			width: 150 ,
			url : ajaxJsonUrl("${ctx}/autofill/getMemberInfo"),
			editable : true,
			hasDownArrow : false,
			valueField : 'memberName',
			textField : 'memberName',
			onBeforeLoad : function(param) {
				var value = $(this).combobox('getText');
				if (value) {
					param.likeStr = value;
				}
			},
			onSelect: function(record){
				$("#memberId").val(record.memberId) ;
				$("#memberNo").html(record.memberNo) ;
				$("#memberTel").html(record.memberTel + "," + record.memberPhone) ;
				$("#memberAds").html(record.memberAddress) ;
				getMemberAcmBuyInfo() ;
			},
			onChange: function(newValue, oldValue){
				$("#memberId").val('') ;
				$("#memberNo").html('') ;
				$("#memberTel").html('') ;
				$("#memberAds").html('') ;
				$("#acmBuyInfo").html('') ;
			}
		});
    	$('#addFormBuyGoodsName').combobox({
    		prompt : '货品信息',
    		required : false,
    		mode : 'remote',
    		url : ajaxJsonUrl("${ctx}/autofill/getBuyGoodsAutofill"),
    		editable : true,
    		hasDownArrow : false,
    		valueField : 'goodsName',
    		textField : 'goodsName',
    		onBeforeLoad : function(param) {
    			var value = $(this).combobox('getText');
    			if (value) {
    				param.goodsName = value;
    			}
    		},
    		onSelect: function(record){
    			var dl = [] ;
    			var sl = {} ;
    			if(record.punit2){
    				var pl = record.punit2.split("*") ;
    				for(var i=0;i<pl.length;i++){
    					sl = {punit: pl[i]} ;
    					dl.push(sl) ;
    				}
    			}
    			else{
    				sl = {punit : '个'} ;
    				dl.push(sl) ;
    			}
    			$("#addFormBuyGoodsPUnitStr").val(record.punit2) ;
    			$("#addFormBuyGoodsCountUnit").combobox('loadData',dl) ;
    			$("#addFormBuyGoodsCountUnit").combobox('select',sl.punit) ;
    			$("#addFormBuyGoodsId").val(record.goodsId) ;
    		},
    		onChange: function(newValue, oldValue){
    			if(EDIT_GOODS_DETAIL){
    				addGoodsDetail() ;
    			}
    			cleanAddForm() ;
    		}
    	});
    	
    	//load 
    	var ORDER_ID = "${order.orderId}" ;
    	if(ORDER_ID){
			$('#merberInfoInputBox').combobox("setValue" ,"${member.memberName}") ;
			$('#merberInfoInputBox').combobox("setText" ,"${member.memberName}") ;
    		$("#memberId").val("${member.memberId}") ;
			$("#memberNo").html("${member.memberNo}") ;
			$("#memberTel").html("${member.memberTel},${member.memberPhone}") ;
			$("#memberAds").html("${member.memberAddress}") ;
			getMemberAcmBuyInfo() ;
			var rows = [] ;
			<c:forEach var="ol" items="${lineList}">
				rows.push({gid:"${ol.gid}",gn: "${ol.gn}",gct: "${ol.gct}",gctu: "${ol.gctu}", gftcfg: "${ol.gftcfg}", punitstr2: "${ol.punitstr2}"}) 
			</c:forEach>
			$("#addFormBuyGoodsList").datagrid( 'loadData',rows) ;
			$("#confirmFormOrderId").val(ORDER_ID) ;
    	}
    	
    	loadBuyInfoDetail() ;
    });
    
    function getMemberAcmBuyInfo(){
    	var params = {memberId:$("#memberId").val() } ;
    	execAjax("${ctx}/order/getMemberAcmBuyInfo", params, false, function(retData){
    		$("#acmBuyInfo").html(retData.info) ;
	    });
    }
    function showMemberWin(){
    	$("#newMemberInfoWin").window('open').window('center');
    }
    function saveMemberInfo(){
	      var params = $("#memberInfoForm").serializeArray();
	      execAjax("${ctx}/member/saveMemberInfo", params, true, function(retData){
	    	  closeNewMemberInfoWin() ;
	      });
	}
    function closeNewMemberInfoWin(){
    	$("#newMemberInfoWin").window('close')
    }
    function loadBuyInfoDetail(){
    	var mid = $("#memberId").val() ;
    	if(!mid){
    		mid = -1 ;
    	}
    	var ppp = "mermberId="+mid ;
    	var prl = $("#addFormBuyGoodsList").datagrid('getRows') ;
    	if(prl && prl.length){
    		for(var i=0;i<prl.length;i++){//{gid:gid,gn: gn,gct:gct,gctu:gctu} ;
    			var r = prl[i] ;
    			ppp += "@@@" + r.gid+"___" + r.gct + "___" + r.gctu + "___" + r.gftcfg ;
    		}
    	}
    	$('#buyDetailInfoPanel').panel('refresh', '${ctx}/order/loadBuyInfoDetail?ppp='+ppp);
    }
    function checkGiftItem(rdo){
    	var gid = rdo.name.replace("GC___" ,"") ;
    	var prl = $("#addFormBuyGoodsList").datagrid('getRows') ;
    	if(prl && prl.length){
    		for(var i=0;i<prl.length;i++){
    			var r = prl[i] ;
    			if(r.gid == gid){
    				r.gftcfg = rdo.value ;
    			}
    		}
    	}
    }
	function printPage(){
		window.print(); 
	}
	function orderConfirm(){
		var mid = $("#memberId").val() ;
		var ppp = "" ;
		var prl = $("#addFormBuyGoodsList").datagrid('getRows') ;
    	if(prl && prl.length){
    		for(var i=0;i<prl.length;i++){//{gid:gid,gn: gn,gct:gct,gctu:gctu} ;
    			var r = prl[i] ;
    			if(ppp != ""){
    				ppp += "@@@" ;
    			}
    			if(!r.gftcfg){
    				r.gftcfg = 0 ;
    			}
    			ppp += r.gid+"___" + r.gct + "___" + r.gctu + "___" + r.gftcfg + "___" + r.payment + "___" + r.gn + "___" + r.unitPrice;
    		}
    	}
    	var orderId = $("#confirmFormOrderId").val() ;
    	var params = {} ;
    	params.orderId = orderId ;
    	params.memberId = mid ;
    	params.orderLineInfo = ppp ;
    	execAjax("${ctx}/order/saveOrder", params, false, function(retData){
    		if(!retData){
    			msg_error("错误", "系统错误，请联系管理员！") ;
    			return ;
    		}
    		if(retData.flag == "OK"){
    			 $("#confirmFormOrderId").val(retData.orderId) ;
    			$("#buyConfirmForm").submit() ;
    			return ;
    		}
    		if(retData.msg){
    			msg_warning("提示",retData.msg) ;
    			return ;
    		}
    		msg_error("错误", "系统错误，请联系管理员！") ;
	    });
	}
	function cleanAddForm(){
		$("#addFormBuyGoodsCountUnit").combobox('clear' ,'') ;
 			$("#addFormBuyGoodsCountUnit").combobox('setValue' ,'') ;
 			$("#addFormBuyGoodsCountUnit").combobox('loadData',[]) ;
 			$("#addFormBuyGoodsCount").textbox('setValue','') ;
 			$("#addFormBuyGoodsId").val('') ;
 			EDIT_GOODS_DETAIL = false ;
	}
	function addGoodsDetail(){
		var gn = $("#addFormBuyGoodsName").combobox('getValue') ;
		var gid = $("#addFormBuyGoodsId").val() ;
		var gct = $("#addFormBuyGoodsCount").textbox('getValue') ;
		var gctu = $("#addFormBuyGoodsCountUnit").combobox('getValue') ;
		$("#addFormBuyGoodsName").combobox('setValue','') ;
		$("#addFormBuyGoodsName").combobox('setText','') ;
		cleanAddForm() ;
		if(!gid){
			return ;
		}
		if(!isInteger(gct)){
			return ;
		}
		if(gct < 1){return ;}
		if(gct > 100000){return ;}
		var punitstr2 = $("#addFormBuyGoodsPUnitStr").val() ;
		var prl = $("#addFormBuyGoodsList").datagrid('getRows') ;
    	if(prl && prl.length){
    		var gg = false ;
    		for(var i=0;i<prl.length;i++){
    			var r = prl[i] ;
    			if(r.gid == gid){
    				var rcd = r ;
    				rcd.gct = gct ;
    				rcd.gctu = gctu ;
    				$("#addFormBuyGoodsList").datagrid( 'refreshRow',$("#addFormBuyGoodsList").datagrid('getRowIndex' ,r)) ;
    				gg = true ;
    			}
    		}
   			if(!gg){
   				var rcd = {gid:gid,gn: gn,gct:gct,gctu:gctu, gftcfg:0, punitstr2:punitstr2} ;
   				$("#addFormBuyGoodsList").datagrid( 'appendRow',rcd) ;
   			}
    	}
    	else{
			var rcd = {gid:gid,gn: gn,gct:gct,gctu:gctu, gftcfg:0, punitstr2:punitstr2} ;
			$("#addFormBuyGoodsList").datagrid( 'appendRow',rcd) ;
    	}
		loadBuyInfoDetail() ;
	}
	var EDIT_GOODS_DETAIL = false ;
	function selectPL(index,row){
		var gid = $("#addFormBuyGoodsId").val() ;
		if(gid){
			addGoodsDetail() ;
		}
		var dl = [] ;
		var sl = {} ;
		if(row.punitstr2){
			var pl = row.punitstr2.split("*") ;
			for(var i=0;i<pl.length;i++){
				sl = {punit: pl[i]} ;
				dl.push(sl) ;
			}
		}
		else{
			sl = {punit : '个'} ;
			dl.push(sl) ;
		}
		$("#addFormBuyGoodsName").combobox('setValue',row.gn) ;
		$("#addFormBuyGoodsName").combobox('setText',row.gn) ;
		$("#addFormBuyGoodsPUnitStr").val(row.punitstr2) ;
		$("#addFormBuyGoodsCountUnit").combobox('loadData',dl) ;
		$("#addFormBuyGoodsCountUnit").combobox('select',row.gctu) ;
		$("#addFormBuyGoodsId").val(row.gid) ;
		$("#addFormBuyGoodsCount").textbox('setValue',row.gct) ;
		row.gct=0;
		$("#addFormBuyGoodsList").datagrid( 'refreshRow',index) ;
		EDIT_GOODS_DETAIL = true ;
	}
	function addGoodsFormGctFormater(value,row,index){
    	return row.gct + "  "  + row.gctu ;
    } 
	function removeGoodsDetail(){
		var r = $("#addFormBuyGoodsList").datagrid( 'getSelected') ;
		var idx = $("#addFormBuyGoodsList").datagrid( 'getRowIndex' ,r) ;
		$("#addFormBuyGoodsList").datagrid('deleteRow',idx) ;
		cleanAddForm() ;
		$("#addFormBuyGoodsName").combobox('setValue','') ;
		$("#addFormBuyGoodsName").combobox('setText','') ;
		loadBuyInfoDetail() ;
	}
    </script>

  </head>
  <body class="easyui-layout">
  <div style="display:none;">
	 <form id="buyConfirmForm" method="post" action="${ctx}/order/Order">
	 	<input type="hidden" name="orderId" id="confirmFormOrderId">
  	 </form>
  </div>
	<div data-options="region:'east',split:true,collapsed:true,title:'商品明细'" style="width:500px;padding:0px;">
		<table class="easyui-datagrid" id="addFormBuyGoodsList"
			data-options="rownumbers:true,singleSelect:true,fit:true,border:false,url:'',onSelect:selectPL,method:'get',toolbar:'#goodsInputBar'">
			<thead>
				<tr>
					<th data-options="field:'gn',width:200">货品</th>
					<th data-options="field:'gct',formatter:addGoodsFormGctFormater,width:100">数量</th>
				</tr>
			</thead>
		</table>
		<div id="goodsInputBar" style="padding:0px;">
			<input class="" id="addFormBuyGoodsName" data-options="width:270"></input>
			<input class="" type="hidden" id="addFormBuyGoodsId" ></input>
			<input class="" type="hidden" id="addFormBuyGoodsPUnitStr" ></input>
			<input class="easyui-combobox" id="addFormBuyGoodsCountUnit" 
	    					data-options="panelHeight:90,editable:false ,valueField:'punit' ,textField:'punit' ,width:70,prompt:'单位'"></input>
			<input class="easyui-textbox" id="addFormBuyGoodsCount" data-options="width:50,prompt:'数量'"></input>
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addGoodsDetail()"></a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeGoodsDetail()"></a>
		</div>
			
	</div>
	<div data-options="region:'north',border:false" style="height:72px;padding:10px;">
		<form id="ff" method="post">
	    	<table cellpadding="2">
	    		<tr>
	    			<td><a href="#" onclick="showMemberWin();"><strong>会员姓名:</strong></a></td>
	    			<td id="memberName" style="width:150px;" >
	    				<input type="text" id="merberInfoInputBox" name="memberName"/>
	    				<input type="hidden" name="memberId" id="memberId"/>
	    			</td>
	    			<td><strong>会员号:</strong></td>
	    			<td id="memberNo" style="width:200px;"></td>
	    			<td><strong>电话:</strong></td>
	    			<td id="memberTel" style="width:200px;"></td>
	    			<td><strong>地址:</strong></td>
	    			<td id="memberAds"></td>
	    		</tr>
	    		<tr>
	    			<td><strong>累积信息:</strong></td>
	    			<td colspan="5" id="acmBuyInfo"></td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	<div data-options="region:'center',border:false">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" >
				<div id="buyDetailInfoPanel" class="easyui-panel" style="padding:20px;" data-options="fit:true,border:false"> </div>
			</div>
			<div data-options="region:'south',border:false" style="height:30px;padding:0 10px" align="left">
				<a href="#" class="" data-options="iconCls:'icon-ok'" onclick="orderConfirm();" style="width:15%">确认</a>
			</div>
		</div>
	</div>
	<div id="newMemberInfoWin" class="easyui-window" title="新增会员" 
			data-options="modal:true,closed:true,iconCls:'icon-add',cache:false,minimizable:false,maximizable:false,collapsible:false,resizable:false
						 ,href:''" style="width:600px;height:490px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" >
				<form id="memberInfoForm" class="easyui-form" method="post" data-options="">
			    	<table cellpadding="5" style="width:100%">
			    		<tr>
			    			<td>姓名:</td>
			    			<td><input class="easyui-textbox" type="text" name="memberName" data-options="width:345"></input></td>
			    		</tr>
			    		<tr>
			    			<td>手机:</td>
			    			<td><input class="easyui-textbox" type="text" name="memberPhone" data-options="width:345"></input></td>
			    		</tr>
			    		<tr>
			    			<td>固定电话:</td>
			    			<td><input class="easyui-textbox" type="text" name="memberTel" data-options="width:345"></input></td>
			    		</tr>
			    		<tr>
			    			<td>QQ:</td>
			    			<td><input class="easyui-textbox"  type="text" name="memberQQ" data-options="width:345" ></input></td>
			    		</tr>
			    		<tr>
			    			<td>微信:</td>
			    			<td><input class="easyui-textbox"  type="text" name="memberWeixin" data-options="width:345" ></input></td>
			    		</tr>
			    		<tr>
			    			<td>地址:</td>
			    			<td><input class="easyui-textbox" name="memberAddress" data-options="multiline:true,width:345" style="height:60px"></input></td>
			    		</tr>
			    		<tr>
			    			<td>养殖备注:</td>
			    			<td><input class="easyui-textbox" name="memberBusiRemark" data-options="multiline:true,width:345" style="height:60px"></input></td>
			    		</tr>
			    		<tr>
			    			<td>其他备注:</td>
			    			<td><input class="easyui-textbox" name="common" data-options="multiline:true,width:345" style="height:60px"></input></td>
			    		</tr>
			    	</table>
			    </form>
				
			</div>
			<div data-options="region:'south',border:false" style="height:30px" align="center">
				<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="saveMemberInfo();" style="width:15%">保存</a>
				<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="closeNewMemberInfoWin();" style="width:15%">取消</a>
			</div>
		</div>
	</div>
  </body>
</html>

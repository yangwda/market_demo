<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<!DOCTYPE html>
<html>
  <head>
    <%@ include file="/common/meta.jsp"%>
    <title>饲料赠品信息</title>
    <link rel="stylesheet" type="text/css" href="/styles/themes/lightblue/easyui.css" id="themesCss">
	<%@ include file="/common/cssAndJs.jsp"%>
    <script type="text/javascript"> 
    
    // 页面键盘屏蔽
    $(document).keydown(function(event){
      if(event.keyCode==116) return false;  //屏蔽F5刷新键
    });
    
    $(document).ready(function(){
    	$('#feedGiftPayoffListTable').datagrid({
		    fit: true,
		    fitColumns: true,
		    rownumbers: true,
		    pagination: true,
		    pageSize: 20,
		    pageList: [20,30,40,50,100],
		    singleSelect: true,
		    loadMsg: '加载中，请稍后......',
		    nowrap: false,
		    columns:[[
		      {title:'饲料编号', field:'goodsNo', width:120},
		      {title:'饲料名称', field:'goodsName', width:120},
		      {title:'赠品活动描述', field:'giftConfigDesc', width:200},
		      {title:'活动时间', field:'giftConfigBeginTime',formatter:giftTimeFormater, width:150},
		      {title:'等值产品金额', field:'giftAmount', width:150},
		      {title:'活动备注', field:'giftConfigRemarks', width:200}
		    ]]
//     		,onSelect: function(index,row){
// 		    	var msg = "" ;
// 		    	if(row.lineList && row.lineList.length > 0){
// 		    		for(var i=0;i<row.lineList.length ;i++){
// 		    			var line = row.lineList[i] ;
// 		    			var ct = line.checkType ;
// 		    			var act = "买" ;
// 		    			if(ct == "累积"){
// 		    				act = "累积" ;
// 		    			}
// 		    			var info = "<strong>" + ct + "</strong>[ "+act + " " + line.buyLimit + " " + line.buyLimitPunit + 
// 		    			                   " ，送 " + line.giftGoodsName+" " +line.giftGoodsCount + line.giftGoodsCountUnit+" ]<br>" ; 
// 		    			msg += info ;
// 		    		}
// 		    	}
// 		    	if(msg == ""){
// 		    		msg = "无" ;
// 		    	}
// 		    	$("#giftDetailsInfoPanel").html(msg) ;
// 		    }
		  });
    });
    
    var giftTimeFormater = function(value,row,index){
    	return row.giftConfigBeginTime + " ~ "  + row.giftConfigEndTime ;
    } ;
    
    var doRefreshDataGrid = function(){
		$('#feedGiftPayoffListTable').datagrid('loadData', { total: 0, rows: [] }); 
		doSearch();
	};

	// 查询
	function doSearch(){
	    $('#feedGiftPayoffListTable').datagrid({
	    	loader: function(param, success, error) {
				$("#giftDetailsInfoPanel").html("") ;
				var params = $("#feedGiftPayoffSearchForm").serializeArray() || [] ;
	    		var pa = [] ;
				pa.push( {name: "page" ,value: param.page} ) ;
				pa.push({name: "rows" ,value: param.rows}) ;
				pa.push({name:"order",value:param.order});
				pa.push({name:"sort",value:param.sort});
				param = $.merge(pa, params);
		      	boDataLoader('${ctx}/feedGift/getFeedGiftPageList', param, success, error);
		   }
	    });//.datagrid('load' ,params);
	}

	//弹出新增饲料赠品页面
	function newFeedGiftPayoff(){
		$("#newFeedGiftPayoffWin").window({ href:'' ,iconCls : 'icon-add'}).window('open');
		$("#newFeedGiftPayoffWin").window('center').window('setTitle' ,'新增饲料赠品');
		$("#newFeedGiftPayoffWin").window('refresh' ,'${ctx}/feedGift/loadContent?pn=gift/new_feed_gift_payoff_win');
	}

	//弹出修改饲料赠品信息页面
	function modify(){
		var row = $('#feedGiftPayoffListTable').datagrid('getSelected');
		if(!row){
			msg_warning('提示' ,'请选择需要修改的饲料赠品。');
			return ;
		}
		$("#newFeedGiftPayoffWin").window({ href:'' ,iconCls : 'icon-edit' }).window('open');
		$('#newFeedGiftPayoffWin').window('setTitle', '修改饲料赠品信息').window('center');
		$("#newFeedGiftPayoffWin").window('refresh' ,'${ctx}/feedGift/loadContent?pn=gift/modify_feed_gift_payoff_win' );
	}

	function printPage(){
		window.print(); 
	}
    </script>

  </head>
  <body class="easyui-layout">
	<div id="queryDiv" class="easyui-panel" data-options="region:'north',border:false"
		style="background: #B3DFDA; height: 30px; padding: 2px; overflow: hidden">
		<form id="feedGiftPayoffSearchForm">
			<table cellpadding="0" cellspacing="1" border="0" width="100%">
				<tr>
					<td width="3px"></td>
					<td align="left"><span>饲料编号:</span></td>
					<td><input class="easyui-textbox" name="goodsNo" id="goodsNo"
						style="width: 100px"></td>
					<td align="left"><span>饲料名称:</span></td>
					<td><input class="easyui-textbox" name="goodsName" id="goodsName" data-options=""
						style="width: 196px"></td>
					<td align="left"><span>在用/过期/未开始:</span></td>
					<td><input class="easyui-textbox" name="dateCondition" id="dateCondition" data-options=""
						style="width: 196px"></td>
					<td align="right"><a href="#" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-search'"
						onclick="doSearch();">查询</a> 
						<a href="#" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-add'"
						style="padding-left: 14px" onclick="newFeedGiftPayoff();">新增饲料赠品</a>
						<a href="#" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-edit'" onclick="modify();">修改</a>
<!-- 						<a href="#" class="easyui-linkbutton" -->
<!-- 						data-options="plain:true,iconCls:'icon-edit'" onclick="printPage();">打印测试</a> -->
						</td>
					<td width="10px"></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false" style="padding:0px;" id="tableDiv">
		<div class="easyui-layout" data-options="fit:true,border:false">
<!-- 			<div data-options="region:'east',split:false" id="giftDetailsInfoPanel" title="活动规则" style="width:360px;padding: 10px;"> -->
<!-- 				当时兑现：买 1 件，送 2 袋；<br> -->
<!-- 				当时兑现：买 1 件，送 热水器；<br> -->
<!-- 				累积：满 10 桶，送 1 桶；<br> -->
<!-- 				累积：满 20 桶，送 电磁炉；<br> -->
<!-- 			</div> -->
			<div data-options="region:'center',border:false">
		  		<table id="feedGiftPayoffListTable"></table>
			</div>
		</div>
	</div>
	<div id="newFeedGiftPayoffWin" class="easyui-window" title="新增饲料赠品" 
			data-options="modal:true,closed:true,iconCls:'icon-add',cache:false,minimizable:false,maximizable:false,collapsible:false,resizable:false
						 ,href:''" style="width:500px;height:460px;padding:5px;">
		</div>
  </body>
</html>

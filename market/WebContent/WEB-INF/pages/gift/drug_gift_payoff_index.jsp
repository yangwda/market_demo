<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<!DOCTYPE html>
<html>
  <head>
    <%@ include file="/common/meta.jsp"%>
    <title>药品赠品信息</title>
    <link rel="stylesheet" type="text/css" href="/styles/themes/lightblue/easyui.css" id="themesCss">
	<%@ include file="/common/cssAndJs.jsp"%>
    <script type="text/javascript"> 
    
    // 页面键盘屏蔽
    $(document).keydown(function(event){
      if(event.keyCode==116) return false;  //屏蔽F5刷新键
    });
    
    $(document).ready(function(){
    	$('#drugGiftPayoffListTable').datagrid({
		    fit: true,
// 		    fitColumns: true,
		    rownumbers: true,
		    pagination: true,
		    pageSize: 20,
		    pageList: [20,30,40,50,100],
		    singleSelect: true,
		    loadMsg: '加载中，请稍后......',
		    nowrap: false,
		    columns:[[
		      {title:'药品编号', field:'goodsNo', width:120},
		      {title:'药品名称', field:'goodsName', width:120},
		      {title:'赠品活动描述', field:'giftConfigDesc', width:150},
		      {title:'开始日期', field:'giftConfigBeginTime', width:100},
		      {title:'结束日期', field:'giftConfigEndTime', width:100},
		      {title:'达到金额', field:'buyLimit', width:100},
		      {title:'赠品名称', field:'line.giftGoodsName',formatter:giftNameFormater, width:120},
		      {title:'赠品数量', field:'line.giftGoodsCount',formatter:giftCountFormater, width:100},
		      {title:'活动备注', field:'giftConfigRemarks', width:172}
		    ]]
		  });
    });
    
    var giftNameFormater = function(value,row,index){
    	return row.line.giftGoodsName ;
    } ;
    var giftCountFormater = function(value,row,index){
    	return row.line.giftGoodsCount + "  "  + row.line.giftGoodsCountUnit ;
    } ;
    
    var doRefreshDataGrid = function(){
		$('#drugGiftPayoffListTable').datagrid('loadData', { total: 0, rows: [] }); 
		doSearch();
	};

	// 查询
	function doSearch(){
		var params = $("#drugGiftPayoffSearchForm").serialize();
	    $('#drugGiftPayoffListTable').datagrid({
	    	loader: function(param, success, error) {
		      	boDataLoader('${ctx}/drugGift/getDrugGiftPageList', params, success, error);
		   }
	    });//.datagrid('load' ,params);
	}

	//弹出新增药品赠品页面
	function newDrugGiftPayoff(){
		$("#newDrugGiftPayoffWin").window({ href:'' ,iconCls : 'icon-add'}).window('open');
		$("#newDrugGiftPayoffWin").window('center').window('setTitle' ,'新增药品赠品');
		$("#newDrugGiftPayoffWin").window('refresh' ,'${ctx}/drugGift/loadContent?pn=gift/new_drug_gift_payoff_win');
	}

	//弹出修改药品赠品信息页面
	function modify(){
		var row = $('#drugGiftPayoffListTable').datagrid('getSelected');
		if(!row){
			msg_warning('提示' ,'请选择需要修改的药品赠品。');
			return ;
		}
		$("#newDrugGiftPayoffWin").window({ href:'' ,iconCls : 'icon-edit' }).window('open');
		$('#newDrugGiftPayoffWin').window('setTitle', '修改药品赠品信息').window('center');
		$("#newDrugGiftPayoffWin").window('refresh' ,'${ctx}/drugGift/loadContent?pn=gift/modify_drug_gift_payoff_win' );
	}

    </script>

  </head>
  <body class="easyui-layout">
	<div id="queryDiv" class="easyui-panel" data-options="region:'north'"
		style="background: #B3DFDA; height: 30px; padding: 2px; overflow: hidden">
		<form id="drugGiftPayoffSearchForm">
			<table cellpadding="0" cellspacing="1" border="0" width="100%">
				<tr>
					<td width="3px"></td>
					<td align="left"><span>药品编号:</span></td>
					<td><input class="easyui-textbox" name="goodsNo" id="goodsNo"
						style="width: 100px"></td>
					<td align="left"><span>药品名称:</span></td>
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
						style="padding-left: 14px" onclick="newDrugGiftPayoff();">新增药品赠品</a>
						<a href="#" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-edit'" onclick="modify();">修改</a>
						</td>
					<td width="10px"></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false" style="padding:0px;" id="tableDiv">
  		<table id="drugGiftPayoffListTable"></table>
	</div>
	<div id="newDrugGiftPayoffWin" class="easyui-window" title="新增药品赠品" 
			data-options="modal:true,closed:true,iconCls:'icon-add',cache:false,minimizable:false,maximizable:false,collapsible:false,resizable:false
						 ,href:''" style="width:600px;height:490px;padding:5px;">
		</div>
  </body>
</html>

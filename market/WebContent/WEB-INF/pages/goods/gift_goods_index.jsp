<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<!DOCTYPE html>
<html>
  <head>
    <%@ include file="/common/meta.jsp"%>
    <title>赠品信息</title>
    <link rel="stylesheet" type="text/css" href="/styles/themes/lightblue/easyui.css" id="themesCss">
	<%@ include file="/common/cssAndJs.jsp"%>
    <script type="text/javascript"> 
    
    // 页面键盘屏蔽
    $(document).keydown(function(event){
      if(event.keyCode==116) return false;  //屏蔽F5刷新键
    });
    
    $(document).ready(function(){
    	$('#giftGoodsListTable').datagrid({
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
		      {title:'赠品编号', field:'goodsNo', width:160},
		      {title:'赠品名称', field:'goodsName', width:80},
		      {title:'厂商信息', field:'goodsManufacturer', width:130},
		      {title:'使用说明', field:'goodsUsage', width:80},
		      {title:'在售/停售', field:'goodsStatus', width:250},
		      {title:'赠品特性', field:'goodsRemark', width:150},
		      {title:'备注', field:'common', width:80},
		      {title:'创建时间', field:'createTime', width:80}
		    ]]
		  });
    });
    
    var doRefreshDataGrid = function(){
		$('#giftGoodsListTable').datagrid('loadData', { total: 0, rows: [] }); 
		doSearch();
	};

	// 查询
	function doSearch(){
		var params = $("#giftGoodsSearchForm").serialize();
	    $('#giftGoodsListTable').datagrid({
	    	loader: function(param, success, error) {
		      	boDataLoader('${ctx}/gift/getGoodsPageList', params, success, error);
		   }
	    });//.datagrid('load' ,params);
	}

	//弹出新增赠品页面
	function newGiftGoods(){
		$("#newGiftGoodsWin").window({ href:'' ,iconCls : 'icon-add'}).window('open');
		$("#newGiftGoodsWin").window('center').window('setTitle' ,'新增赠品');
		$("#newGiftGoodsWin").window('refresh' ,'${ctx}/gift/loadContent?pn=goods/new_gift_goods_win');
	}

	//弹出修改赠品信息页面
	function modify(){
		var row = $('#giftGoodsListTable').datagrid('getSelected');
		if(!row){
			msg_warning('提示' ,'请选择需要修改的赠品。');
			return ;
		}
		$("#newGiftGoodsWin").window({ href:'' ,iconCls : 'icon-edit' }).window('open');
		$('#newGiftGoodsWin').window('setTitle', '修改赠品信息').window('center');
		$("#newGiftGoodsWin").window('refresh' ,'${ctx}/gift/loadContent?pn=goods/modify_gift_goods_win&goodsId=' + row.goodsId );
	}

    </script>

  </head>
  <body class="easyui-layout">
	<div id="queryDiv" class="easyui-panel" data-options="region:'north'"
		style="background: #B3DFDA; height: 30px; padding: 2px; overflow: hidden">
		<form id="giftGoodsSearchForm">
			<table cellpadding="0" cellspacing="1" border="0" width="100%">
				<tr>
					<td width="3px"><input type="hidden" name="goodsType" value="赠品"/></td>
					<td align="left"><span>赠品编号:</span></td>
					<td><input class="easyui-textbox" name="memberNo" id="memberNo"
						style="width: 100px"></td>
					<td align="left"><span>赠品名称:</span></td>
					<td><input class="easyui-textbox" name="memberName" id="memberName" data-options=""
						style="width: 196px"></td>
					<td align="left"><span>厂商/备注:</span></td>
					<td><input class="easyui-textbox" name="memberName" id="memberName" data-options=""
						style="width: 196px"></td>
					<td align="left"><span>在售/停售：</span></td>
					<td><input class="easyui-textbox" id="memberPhone" name="memberPhone"
						style="width: 100px" /></td>
					<td align="right"><a href="#" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-search'"
						onclick="doSearch();">查询</a> 
						<a href="#" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-add'"
						style="padding-left: 14px" onclick="newGiftGoods();">新增赠品</a>
						<a href="#" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-edit'" onclick="modify();">修改</a>
						</td>
					<td width="10px"></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center'" style="padding:0px;" id="tableDiv">
  		<table id="giftGoodsListTable"></table>
	</div>
	<div id="newGiftGoodsWin" class="easyui-window" title="新增赠品" 
			data-options="modal:true,closed:true,iconCls:'icon-add',cache:false,minimizable:false,maximizable:false,collapsible:false,resizable:false
						 ,href:''" style="width:600px;height:480px;padding:5px;">
		</div>
  </body>
</html>

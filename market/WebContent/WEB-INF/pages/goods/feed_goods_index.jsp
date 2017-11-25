<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<!DOCTYPE html>
<html>
  <head>
    <%@ include file="/common/meta.jsp"%>
    <title>饲料信息</title>
    <link rel="stylesheet" type="text/css" href="/styles/themes/lightblue/easyui.css" id="themesCss">
	<%@ include file="/common/cssAndJs.jsp"%>
    <script type="text/javascript"> 
    
    // 页面键盘屏蔽
    $(document).keydown(function(event){
      if(event.keyCode==116) return false;  //屏蔽F5刷新键
    });
    
    $(document).ready(function(){
    	$('#feedGoodsListTable').datagrid({
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
		      {title:'饲料编号', field:'goodsNo', width:160},
		      {title:'饲料名称', field:'goodsName', width:80},
		      {title:'厂商信息', field:'goodsManufacturer', width:130},
		      {title:'使用说明', field:'goodsUsage', width:80},
		      {title:'在售/停售', field:'goodsStatus', width:250},
		      {title:'规格/单价', field:'punit1' ,formatter: unitPriceFormater, width:120},
		      {title:'饲料特性', field:'goodsRemark', width:150},
		      {title:'备注', field:'common', width:80},
		      {title:'创建时间', field:'createTime', width:80}
		    ]]
		  });
    });
    
    function unitPriceFormater(value,row,index) {
    	return value + "<br>" + row.punit2 + "<br>" + row.punit3 ;
    }
    
    var doRefreshDataGrid = function(){
		$('#feedGoodsListTable').datagrid('loadData', { total: 0, rows: [] }); 
		doSearch();
	};

	// 查询
	function doSearch(){
	    $('#feedGoodsListTable').datagrid({
	    	loader: function(param, success, error) {
				var params = $("#feedGoodsSearchForm").serializeArray() || [] ;
	    		var pa = [] ;
				pa.push( {name: "page" ,value: param.page} ) ;
				pa.push({name: "rows" ,value: param.rows}) ;
				pa.push({name:"order",value:param.order});
				pa.push({name:"sort",value:param.sort});
				param = $.merge(pa, params);
		      	boDataLoader('${ctx}/feed/getGoodsPageList', param, success, error);
		   }
	    });//.datagrid('load' ,params);
	}

	//弹出新增饲料页面
	function newFeedGoods(){
		$("#newFeedGoodsWin").window({ href:'' ,iconCls : 'icon-add'}).window('open');
		$("#newFeedGoodsWin").window('center').window('setTitle' ,'新增饲料');
		$("#newFeedGoodsWin").window('refresh' ,'${ctx}/feed/loadContent?pn=goods/new_feed_goods_win');
	}

	//弹出修改饲料信息页面
	function modify(){
		var row = $('#feedGoodsListTable').datagrid('getSelected');
		if(!row){
			msg_warning('提示' ,'请选择需要修改的饲料。');
			return ;
		}
		$("#newFeedGoodsWin").window({ href:'' ,iconCls : 'icon-edit' }).window('open');
		$('#newFeedGoodsWin').window('setTitle', '修改饲料信息').window('center');
		$("#newFeedGoodsWin").window('refresh' ,'${ctx}/feed/loadContent?pn=goods/modify_feed_goods_win&goodsId=' + row.goodsId );
	}

    </script>

  </head>
  <body class="easyui-layout">
	<div id="queryDiv" class="easyui-panel" data-options="region:'north'"
		style="background: #B3DFDA; height: 30px; padding: 2px; overflow: hidden">
		<form id="feedGoodsSearchForm">
			<table cellpadding="0" cellspacing="1" border="0" width="100%">
				<tr>
					<td width="3px"><input type="hidden" name="goodsType" value="饲料"/></td>
					<td align="left"><span>饲料编号:</span></td>
					<td><input class="easyui-textbox" name="goodsNo" id="goodsNo"
						style="width: 100px"></td>
					<td align="left"><span>饲料名称:</span></td>
					<td><input class="easyui-textbox" name="goodsName" id="goodsName" data-options=""
						style="width: 196px"></td>
					<td align="left"><span>厂商/备注:</span></td>
					<td><input class="easyui-textbox" name="common" id="common" data-options=""
						style="width: 196px"></td>
					<td align="left"><span>在售/停售：</span></td>
					<td><input class="easyui-textbox" id="goodsStatus" name="goodsStatus"
						style="width: 100px" /></td>
					<td align="right"><a href="#" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-search'"
						onclick="doSearch();">查询</a> 
						<a href="#" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-add'"
						style="padding-left: 14px" onclick="newFeedGoods();">新增饲料</a>
						<a href="#" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-edit'" onclick="modify();">修改</a>
						</td>
					<td width="10px"></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false" style="padding:0px;" id="tableDiv">
  		<table id="feedGoodsListTable"></table>
	</div>
	<div id="newFeedGoodsWin" class="easyui-window" title="新增饲料" 
			data-options="modal:true,closed:true,iconCls:'icon-add',cache:false,minimizable:false,maximizable:false,collapsible:false,resizable:false
						 ,href:''" style="width:600px;height:490px;padding:5px;">
		</div>
  </body>
</html>

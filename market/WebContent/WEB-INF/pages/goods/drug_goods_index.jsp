<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<!DOCTYPE html>
<html>
  <head>
    <%@ include file="/common/meta.jsp"%>
    <title>药品信息</title>
    <link rel="stylesheet" type="text/css" href="/styles/themes/lightblue/easyui.css" id="themesCss">
	<%@ include file="/common/cssAndJs.jsp"%>
    <script type="text/javascript"> 
    
    // 页面键盘屏蔽
    $(document).keydown(function(event){
      if(event.keyCode==116) return false;  //屏蔽F5刷新键
    });
    
    $(document).ready(function(){
    	$('#drugGoodsListTable').datagrid({
		    fit: true,
// 		    fitColumns: true,
		    rownumbers: true,
		    pagination: true,
		    pageSize: 20,
		    pageNumber:1,   
		    pageList: [20,30,40,50,100],
		    singleSelect: true,
		    loadMsg: '加载中，请稍后......',
		    nowrap: false,
		    columns:[[
		      {title:'药品编号', field:'goodsNo', width:160},
		      {title:'药品名称', field:'goodsName', width:120},
		      {title:'厂商信息', field:'goodsManufacturer', width:130},
		      {title:'使用说明', field:'goodsUsage', width:160},
		      {title:'在售/停售', field:'goodsStatus', width:80},
		      {title:'规格/单价', field:'punit1' ,formatter: unitPriceFormater, width:120},
		      {title:'药品特性', field:'goodsRemark', width:150},
		      {title:'备注', field:'common', width:80},
		      {title:'创建时间', field:'createTime', width:80}
		    ]]
		  });
    });
    
    function unitPriceFormater(value,row,index) {
    	return value + "<br>" + row.punit2 + "<br>" + row.punit3 ;
    }
    
    
    var doRefreshDataGrid = function(){
		$('#drugGoodsListTable').datagrid('loadData', { total: 0, rows: [] }); 
		doSearch();
	};

	// 查询
	function doSearch(){
	    $('#drugGoodsListTable').datagrid({
	    	loader: function(param, success, error) {
				var params = $("#drugGoodsSearchForm").serializeArray() || [] ;
	    		var pa = [] ;
				pa.push( {name: "page" ,value: param.page} ) ;
				pa.push({name: "rows" ,value: param.rows}) ;
				pa.push({name:"order",value:param.order});
				pa.push({name:"sort",value:param.sort});
				param = $.merge(pa, params);
		      	boDataLoader('${ctx}/drug/getGoodsPageList', param, success, error);
		   }
	    });//.datagrid('load' ,params);
	}

	//弹出新增药品页面
	function newDrugGoods(){
		$("#newDrugGoodsWin").window({ href:'' ,iconCls : 'icon-add'}).window('open');
		$("#newDrugGoodsWin").window('center').window('setTitle' ,'新增药品');
		$("#newDrugGoodsWin").window('refresh' ,'${ctx}/drug/loadContent?pn=goods/new_goods_win');
	}

	//弹出修改药品信息页面
	function modify(){
		var row = $('#drugGoodsListTable').datagrid('getSelected');
		if(!row){
			msg_warning('提示' ,'请选择需要修改的药品。');
			return ;
		}
		$("#newDrugGoodsWin").window({ href:'' ,iconCls : 'icon-edit' }).window('open');
		$('#newDrugGoodsWin').window('setTitle', '修改药品信息').window('center');
		$("#newDrugGoodsWin").window('refresh' ,'${ctx}/drug/loadContent?pn=goods/modify_goods_win&goodsId=' + row.goodsId );
	}

    </script>

  </head>
  <body class="easyui-layout">
	<div id="queryDiv" class="easyui-panel" data-options="region:'north'"
		style="background: #B3DFDA; height: 30px; padding: 2px; overflow: hidden">
		<form id="drugGoodsSearchForm">
			<table cellpadding="0" cellspacing="1" border="0" width="100%">
				<tr>
					<td width="3px"><input type="hidden" name="goodsType" value="药品"/></td>
					<td align="left"><span>药品编号:</span></td>
					<td><input class="easyui-textbox" name="goodsNo" id="goodsNo"
						style="width: 100px"></td>
					<td align="left"><span>药品名称:</span></td>
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
						style="padding-left: 14px" onclick="newDrugGoods();">新增药品</a>
						<a href="#" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-edit'" onclick="modify();">修改</a>
						</td>
					<td width="10px"></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false" style="padding:0px;" id="tableDiv">
  		<table id="drugGoodsListTable"></table>
	</div>
	<div id="newDrugGoodsWin" class="easyui-window" title="新增药品" 
			data-options="modal:true,closed:true,iconCls:'icon-add',cache:false,minimizable:false,maximizable:false,collapsible:false,resizable:false
						 ,href:''" style="width:600px;height:490px;padding:5px;">
		</div>
  </body>
</html>

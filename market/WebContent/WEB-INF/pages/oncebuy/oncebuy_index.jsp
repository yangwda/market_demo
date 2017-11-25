<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<!DOCTYPE html>
<html>
  <head>
    <%@ include file="/common/meta.jsp"%>
    <title>单次购买优惠信息</title>
    <link rel="stylesheet" type="text/css" href="/styles/themes/lightblue/easyui.css" id="themesCss">
	<%@ include file="/common/cssAndJs.jsp"%>
    <script type="text/javascript"> 
    
    // 页面键盘屏蔽
    $(document).keydown(function(event){
      if(event.keyCode==116) return false;  //屏蔽F5刷新键
    });
    
    $(document).ready(function(){
    	$('#oncebuyListTable').datagrid({
		    fit: true,
// 		    fitColumns: true,
		    rownumbers: true,
		    pagination: false,
		    singleSelect: true,
		    loadMsg: '加载中，请稍后......',
		    nowrap: false,
		    columns:[[
		      {title:'单次购买优惠条目', field:'beginAmount',formatter:giftItemFormater, width:272},
		      {title:'优惠力度（%）', field:'perRate', width:128}
		    ]]
		  });
    });
    
    var giftItemFormater = function(value,row,index){
    	return row.beginAmount + " ~ "  + row.endAmount ;
    } ;
    
    var doRefreshDataGrid = function(){
		$('#oncebuyListTable').datagrid('loadData', { total: 0, rows: [] }); 
		doSearch();
	};

	// 查询
	function doSearch(){
	    $('#oncebuyListTable').datagrid({
	    	loader: function(param, success, error) {
				var params = $("#oncebuySearchForm").serializeArray() || [] ;
				var pa = [] ;
				pa.push( {name: "page" ,value: param.page} ) ;
				pa.push({name: "rows" ,value: param.rows}) ;
				pa.push({name:"order",value:param.order});
				pa.push({name:"sort",value:param.sort});
				param = $.merge(pa, params);
		      	boDataLoader('${ctx}/oncebuy/getOncebuyPageList', param, success, error);
		   }
	    });//.datagrid('load' ,params);
	}

	//弹出新增单次购买优惠页面
	function newOncebuy(){
		$("#newOncebuyWin").window({ href:'' ,iconCls : 'icon-add'}).window('open');
		$("#newOncebuyWin").window('center').window('setTitle' ,'新增单次购买优惠');
		$("#newOncebuyWin").window('refresh' ,'${ctx}/oncebuy/loadContent?pn=oncebuy/new_oncebuy_win');
	}

	//弹出修改单次购买优惠信息页面
	function modify(){
		var row = $('#oncebuyListTable').datagrid('getSelected');
		if(!row){
			msg_warning('提示' ,'请选择需要修改的单次购买优惠。');
			return ;
		}
		$("#newOncebuyWin").window({ href:'' ,iconCls : 'icon-edit' }).window('open');
		$('#newOncebuyWin').window('setTitle', '修改单次购买优惠信息').window('center');
		$("#newOncebuyWin").window('refresh' ,'${ctx}/oncebuy/loadContent?pn=oncebuy/modify_oncebuy_win&oncebuyId=' + row.oncebuyId );
	}

    </script>

  </head>
  <body class="easyui-layout">
	<div id="queryDiv" class="easyui-panel" data-options="region:'north'"
		style="background: #B3DFDA; height: 30px; padding: 2px; overflow: hidden">
		<form id="oncebuySearchForm">
			<table cellpadding="0" cellspacing="1" border="0" width="100%">
				<tr>
					<td width="3px"></td>
					<td align="right"><a href="#" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-search'"
						onclick="doSearch();">查询</a> 
						<a href="#" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-add'"
						style="padding-left: 14px" onclick="newOncebuy();">条目</a>
						<a href="#" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-edit'" onclick="modify();">修改</a>
						</td>
					<td width="10px"></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false" style="padding:0px;" id="tableDiv">
  		<table id="oncebuyListTable"></table>
	</div>
	<div id="newOncebuyWin" class="easyui-window" title="新增单次购买优惠" 
			data-options="modal:true,closed:true,iconCls:'icon-add',cache:false,minimizable:false,maximizable:false,collapsible:false,resizable:false
						 ,href:''" style="width:600px;height:272px;padding:5px;">
		</div>
  </body>
</html>

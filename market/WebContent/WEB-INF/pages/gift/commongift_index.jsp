<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<!DOCTYPE html>
<html>
  <head>
    <%@ include file="/common/meta.jsp"%>
    <title>惠赠商品信息</title>
    <link rel="stylesheet" type="text/css" href="/styles/themes/lightblue/easyui.css" id="themesCss">
	<%@ include file="/common/cssAndJs.jsp"%>
    <script type="text/javascript"> 
    
    // 页面键盘屏蔽
    $(document).keydown(function(event){
      if(event.keyCode==116) return false;  //屏蔽F5刷新键
    });
    
    $(document).ready(function(){
    	$('#illnessListTable').datagrid({
		    fit: true,
// 		    fitColumns: true,
		    rownumbers: true,
		    pagination: false,
		    pageSize: 50,
		    pageList: [50,100],
		    singleSelect: true,
		    loadMsg: '加载中，请稍后......',
		    nowrap: false,
		    columns:[[
		      {title:'惠赠商品名称', field:'commonGiftName', width:240},
		      {title:'包装规格', field:'commonGiftUnit', width:180}
		    ]]
		  });
    });
    
    var doRefreshDataGrid = function(){
		$('#illnessListTable').datagrid('loadData', { total: 0, rows: [] }); 
		doSearch();
	};

	// 查询
	function doSearch(){
	    $('#illnessListTable').datagrid({
	    	loader: function(param, success, error) {
		      	boDataLoader('${ctx}/commonGift/getCommonGiftPageList', param, success, error);
		   }
	    });//.datagrid('load' ,params);
	}

	//弹出新增疾病页面
	function newCommonGift(){
		$("#newIllnessWin").window({ href:'' ,iconCls : 'icon-add'}).window('open');
		$("#newIllnessWin").window('center').window('setTitle' ,'新增惠赠商品');
		$("#newIllnessWin").window('refresh' ,'${ctx}/commonGift/loadContent?pn=gift/new_commongift_win');
	}
	
	function deleteCommonGift(){
		var row = $('#illnessListTable').datagrid('getSelected');
		if(!row){
			msg_warning('提示' ,'请选择需要删除的惠赠商品。');
			return ;
		} 
		msg_confirm("确认", "确定要删除所选的惠赠商品信息？", function(){
			var params = {commonGiftId : row.commonGiftId} ;
			execAjax("${ctx}/commonGift/deleteCommonGiftInfo", params, true, function(retData){
		    	doRefreshDataGrid() ;
		    });
		})
		
	}
    </script>

  </head>
  <body class="easyui-layout">
	<div id="queryDiv" class="easyui-panel" data-options="region:'north'"
		style="background: #B3DFDA; height: 30px; padding: 2px; overflow: hidden">
		<form id="illnessSearchForm">
			<table cellpadding="0" cellspacing="1" border="0" width="100%">
				<tr>
					<td align="right"><a href="#" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-search'"
						onclick="doSearch();">查询</a> 
						<a href="#" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-add'"
						style="padding-left: 14px" onclick="newCommonGift();">新增</a>
						<a href="#" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-remove'" onclick="deleteCommonGift();">删除</a>
						</td>
					<td width="10px"></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false" style="padding:0px;" id="tableDiv">
  		<table id="illnessListTable"></table>
	</div>
	<div id="newIllnessWin" class="easyui-window" title="新增疾病" 
			data-options="modal:true,closed:true,iconCls:'icon-add',cache:false,minimizable:false,maximizable:false,collapsible:false,resizable:false
						 ,href:''" style="width:600px;height:480px;padding:5px;">
		</div>
  </body>
</html>

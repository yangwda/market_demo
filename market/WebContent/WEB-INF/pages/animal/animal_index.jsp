<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<!DOCTYPE html>
<html>
  <head>
    <%@ include file="/common/meta.jsp"%>
    <title>兽种类信息</title>
    <link rel="stylesheet" type="text/css" href="/styles/themes/lightblue/easyui.css" id="themesCss">
	<%@ include file="/common/cssAndJs.jsp"%>
    <script type="text/javascript"> 
    
    // 页面键盘屏蔽
    $(document).keydown(function(event){
      if(event.keyCode==116) return false;  //屏蔽F5刷新键
    });
    
    $(document).ready(function(){
    	$('#animalListTable').datagrid({
		    fit: true,
// 		    fitColumns: true,
		    rownumbers: true,
		    pagination: true,
		    pageSize: 50,
		    pageList: [50,100],
		    singleSelect: true,
		    loadMsg: '加载中，请稍后......',
		    nowrap: false,
		    columns:[[
		      {title:'兽种类名称', field:'animalName', width:240}
		    ]]
		  });
    });
    
    var doRefreshDataGrid = function(){
		$('#animalListTable').datagrid('loadData', { total: 0, rows: [] }); 
		doSearch();
	};

	// 查询
	function doSearch(){
	    $('#animalListTable').datagrid({
	    	loader: function(param, success, error) {
				var params = $("#animalSearchForm").serializeArray() || [] ;
				var pa = [] ;
				pa.push( {name: "page" ,value: param.page} ) ;
				pa.push({name: "rows" ,value: param.rows}) ;
				pa.push({name:"order",value:param.order});
				pa.push({name:"sort",value:param.sort});
				param = $.merge(pa, params);
		      	boDataLoader('${ctx}/animal/getAnimalPageList', param, success, error);
		   }
	    });//.datagrid('load' ,params);
	}

	//弹出新增兽种类页面
	function newAnimal(){
		$("#newAnimalWin").window({ href:'' ,iconCls : 'icon-add'}).window('open');
		$("#newAnimalWin").window('center').window('setTitle' ,'新增兽种类');
		$("#newAnimalWin").window('refresh' ,'${ctx}/animal/loadContent?pn=animal/new_animal_win');
	}

	//弹出修改兽种类信息页面
	function modify(){
		var row = $('#animalListTable').datagrid('getSelected');
		if(!row){
			msg_warning('提示' ,'请选择需要修改的兽种类。');
			return ;
		}
		$("#newAnimalWin").window({ href:'' ,iconCls : 'icon-edit' }).window('open');
		$('#newAnimalWin').window('setTitle', '修改兽种类信息').window('center');
		$("#newAnimalWin").window('refresh' ,'${ctx}/animal/loadContent?pn=animal/modify_animal_win&animalId=' + row.animalId );
	}

    </script>

  </head>
  <body class="easyui-layout">
	<div id="queryDiv" class="easyui-panel" data-options="region:'north'"
		style="background: #B3DFDA; height: 30px; padding: 2px; overflow: hidden">
		<form id="animalSearchForm">
			<table cellpadding="0" cellspacing="1" border="0" width="100%">
				<tr>
					<td width="3px"></td>
					<td align="left"><span>兽种类名称:</span></td>
					<td><input class="easyui-textbox" name="animalName" id="animalName" data-options=""
						style="width: 196px"></td>
					<td align="right"><a href="#" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-search'"
						onclick="doSearch();">查询</a> 
						<a href="#" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-add'"
						style="padding-left: 14px" onclick="newAnimal();">新增兽种类</a>
						<a href="#" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-edit'" onclick="modify();">修改</a>
						</td>
					<td width="10px"></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false" style="padding:0px;" id="tableDiv">
  		<table id="animalListTable"></table>
	</div>
	<div id="newAnimalWin" class="easyui-window" title="新增兽种类" 
			data-options="modal:true,closed:true,iconCls:'icon-add',cache:false,minimizable:false,maximizable:false,collapsible:false,resizable:false
						 ,href:''" style="width:600px;height:480px;padding:5px;">
		</div>
  </body>
</html>

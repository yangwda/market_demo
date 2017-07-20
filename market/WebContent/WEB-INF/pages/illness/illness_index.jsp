<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<!DOCTYPE html>
<html>
  <head>
    <%@ include file="/common/meta.jsp"%>
    <title>疾病信息</title>
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
		    pagination: true,
		    pageSize: 50,
		    pageList: [50,100],
		    singleSelect: true,
		    loadMsg: '加载中，请稍后......',
		    nowrap: false,
		    columns:[[
		      {title:'疾病名称', field:'illnessName', width:240},
		      {title:'是否回访', field:'callBack', width:180}
		    ]]
		  });
    });
    
    var doRefreshDataGrid = function(){
		$('#illnessListTable').datagrid('loadData', { total: 0, rows: [] }); 
		doSearch();
	};

	// 查询
	function doSearch(){
		var params = $("#illnessSearchForm").serialize();
	    $('#illnessListTable').datagrid({
	    	loader: function(param, success, error) {
		      	boDataLoader('${ctx}/illness/getIllnessPageList', params, success, error);
		   }
	    });//.datagrid('load' ,params);
	}

	//弹出新增疾病页面
	function newIllness(){
		$("#newIllnessWin").window({ href:'' ,iconCls : 'icon-add'}).window('open');
		$("#newIllnessWin").window('center').window('setTitle' ,'新增疾病');
		$("#newIllnessWin").window('refresh' ,'${ctx}/illness/loadContent?pn=illness/new_illness_win');
	}

	//弹出修改疾病信息页面
	function modify(){
		var row = $('#illnessListTable').datagrid('getSelected');
		if(!row){
			msg_warning('提示' ,'请选择需要修改的疾病。');
			return ;
		}
		$("#newIllnessWin").window({ href:'' ,iconCls : 'icon-edit' }).window('open');
		$('#newIllnessWin').window('setTitle', '修改疾病信息').window('center');
		$("#newIllnessWin").window('refresh' ,'${ctx}/illness/loadContent?pn=illness/modify_illness_win&illnessId=' + row.illnessId );
	}

    </script>

  </head>
  <body class="easyui-layout">
	<div id="queryDiv" class="easyui-panel" data-options="region:'north'"
		style="background: #B3DFDA; height: 30px; padding: 2px; overflow: hidden">
		<form id="illnessSearchForm">
			<table cellpadding="0" cellspacing="1" border="0" width="100%">
				<tr>
					<td width="3px"></td>
					<td align="left"><span>疾病名称:</span></td>
					<td><input class="easyui-textbox" name="illnessName" id="illnessName" data-options=""
						style="width: 196px"></td>
					<td align="left"><span>是否回访：</span></td>
					<td><input class="easyui-textbox" id="callBack" name="callBack"
						style="width: 100px" /></td>
					<td align="right"><a href="#" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-search'"
						onclick="doSearch();">查询</a> 
						<a href="#" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-add'"
						style="padding-left: 14px" onclick="newIllness();">新增疾病</a>
						<a href="#" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-edit'" onclick="modify();">修改</a>
						</td>
					<td width="10px"></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center'" style="padding:0px;" id="tableDiv">
  		<table id="illnessListTable"></table>
	</div>
	<div id="newIllnessWin" class="easyui-window" title="新增疾病" 
			data-options="modal:true,closed:true,iconCls:'icon-add',cache:false,minimizable:false,maximizable:false,collapsible:false,resizable:false
						 ,href:''" style="width:600px;height:480px;padding:5px;">
		</div>
  </body>
</html>

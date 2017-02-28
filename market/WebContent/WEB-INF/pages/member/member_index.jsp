<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<!DOCTYPE html>
<html>
  <head>
    <%@ include file="/common/meta.jsp"%>
    <title>会员信息</title>
    <link rel="stylesheet" type="text/css" href="/styles/themes/lightblue/easyui.css" id="themesCss">
	<%@ include file="/common/cssAndJs.jsp"%>
    <script type="text/javascript"> 
    
    // 页面键盘屏蔽
    $(document).keydown(function(event){
      if(event.keyCode==116) return false;  //屏蔽F5刷新键
    });
    
    $(document).ready(function(){
    	$('#memberListTable').datagrid({
		    fit: true,
		    rownumbers: true,
		    pagination: true,
		    pageSize: 20,
		    pageList: [20,30,40,50,100],
		    singleSelect: true,
		    loadMsg: '加载中，请稍后......',
		    nowrap: false,
		    columns:[[
		      {title:'会员编号', field:'memberNo', width:80},
		      {title:'会员名称', field:'memberName', width:80},
		      {title:'地址', field:'memberAddress', width:130},
		      {title:'手机', field:'memberPhone', width:80},
		      {title:'经营备注', field:'memberBusiRemark', width:250},
		      {title:'其他备注', field:'common', width:150},
		      {title:'创建时间', field:'createTime', width:80},
		      {title:'电话', field:'memberTel', width:80},
		      {title:'QQ', field:'memberQQ', width:80},
		      {title:'微信', field:'memberWeixin', width:80}
		    ]]
		  });
    });
    
    var doRefreshDataGrid = function(){
		$('#memberListTable').datagrid('loadData', { total: 0, rows: [] }); 
		doSearch();
	};

	// 查询
	function doSearch(){
		var params = $("#memberSearchForm").serialize();
	    $('#memberListTable').datagrid({
	    	loader: function(param, success, error) {
		      	boDataLoader('${ctx}/member/getMemberPageList', params, success, error);
		   }
	    });//.datagrid('load' ,params);
	}

	//弹出新增会员页面
	function newMember(){
		$("#newMemberWin").window({ href:'' ,iconCls : 'icon-add'}).window('open');
		$("#newMemberWin").window('center').window('setTitle' ,'新增会员');
		$("#newMemberWin").window('refresh' ,'${ctx}/member/loadContent?pn=member/new_member_win');
	}

	//弹出修改会员信息页面
	function modify(){
		var row = $('#memberListTable').datagrid('getSelected');
		if(!row){
			msg_warning('提示' ,'请选择需要修改的会员。');
			return ;
		}
		$("#newMemberWin").window({ href:'' ,iconCls : 'icon-edit' }).window('open');
		$('#newMemberWin').window('setTitle', '修改会员信息').window('center');
		$("#newMemberWin").window('refresh' ,'${ctx}/member/loadContent?pn=member/modify_member_win&memberId=' + row.memberId );
	}

    </script>

  </head>
  <body class="easyui-layout">
	<div id="queryDiv" class="easyui-panel" data-options="region:'north'"
		style="background: #B3DFDA; height: 30px; padding: 2px; overflow: hidden">
		<form id="memberSearchForm">
			<table cellpadding="0" cellspacing="1" border="0" width="100%">
				<tr>
					<td width="3px"></td>
					<td align="right"><span>会员编号:</span></td>
					<td><input class="easyui-textbox" name="id" id="id"
						style="width: 100px"></td>
					<td align="right"><span>会员姓名:</span></td>
					<td><input class="easyui-textbox" name="whId" id="whId" data-options=""
						style="width: 196px"></td>
					<td align="right"><span>手机号：</span></td>
					<td><input class="easyui-textbox" id="incode1" name="incode1"
						style="width: 100px" /></td>
					<td align="right"><a href="#" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-search'"
						onclick="doSearch();">查询</a> 
						<a href="#" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-add'"
						style="padding-left: 14px" onclick="newMember();">新增会员</a>
						<a href="#" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-edit'" onclick="modify();">修改</a>
						</td>
					<td width="10px"></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center'" style="padding:0px;" id="tableDiv">
  		<table id="memberListTable"></table>
	</div>
	<div id="newMemberWin" class="easyui-window" title="新增会员" 
			data-options="modal:true,closed:true,iconCls:'icon-add',cache:false,minimizable:false,maximizable:false,collapsible:false,resizable:false
						 ,href:''" style="width:600px;height:480px;padding:5px;">
		</div>
  </body>
</html>

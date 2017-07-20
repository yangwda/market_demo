<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	function closeWin(){
		$("#newIllnessWin").window('close') ;
	}
	function savellnessInfo(){
	      var params = $("#illnessInfoForm").serializeArray();
	      execAjax("${ctx}/illness/saveIllnessInfo", params, true, function(retData){
	    	  doRefreshDataGrid() ;
	    	  closeWin() ;
	      });
	}
	$(document).ready(function(){
		var row = $('#illnessListTable').datagrid('getSelected');
		if(!row){
			msg_error('错误' ,'无法获取需要修改的疾病信息。');
			closeWin() ;
			return ;
		}
	 	$('#illnessInfoForm').form('load',{
	 		illnessId: row.illnessId ,
	 		illnessName: row.illnessName ,
	 		callBack: row.callBack
	 	});
	});
</script>

<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false" >
		<form id="illnessInfoForm" class="easyui-form" method="post" data-options="">
	    	<table cellpadding="5" style="width:100%">
	    		<tr>
	    			<td>疾病名称:</td>
	    			<td>
	    				<input type="hidden" name="illnessId" />
	    				<input class="easyui-textbox" type="text" name="illnessName" data-options="width:345"></input>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>是否回访:</td>
	    			<td><input class="easyui-textbox" type="text" name="callBack" data-options="width:345"></input></td>
	    		</tr>
	    	</table>
	    </form>
		
	</div>
	<div data-options="region:'south',border:false" style="height:30px" align="center">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="savellnessInfo();" style="width:15%">保存</a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="closeWin();" style="width:15%">取消</a>
	</div>
</div>


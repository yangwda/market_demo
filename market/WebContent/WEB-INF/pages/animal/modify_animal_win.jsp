<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	function closeWin(){
		$("#newAnimalWin").window('close') ;
	}
	function saveAnimalInfo(){
	      var params = $("#animalInfoForm").serializeArray();
	      execAjax("${ctx}/animal/saveAnimalInfo", params, true, function(retData){
	    	  doRefreshDataGrid() ;
	    	  closeWin() ;
	      });
	}
	$(document).ready(function(){
		var row = $('#animalListTable').datagrid('getSelected');
		if(!row){
			msg_error('错误' ,'无法获取需要修改的兽种类信息。');
			closeWin() ;
			return ;
		}
	 	$('#animalInfoForm').form('load',{
	 		animalId: row.animalId ,
	 		animalName: row.animalName 
	 	});
	});
</script>

<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false" >
		<form id="animalInfoForm" class="easyui-form" method="post" data-options="">
	    	<table cellpadding="5" style="width:100%">
	    		<tr>
	    			<td>兽种类名称:</td>
	    			<td>
	    				<input type="hidden" name="animalId" />
	    				<input class="easyui-textbox" type="text" name="animalName" data-options="width:345"></input>
	    			</td>
	    		</tr>
	    	</table>
	    </form>
		
	</div>
	<div data-options="region:'south',border:false" style="height:30px" align="center">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="saveAnimalInfo();" style="width:15%">保存</a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="closeWin();" style="width:15%">取消</a>
	</div>
</div>


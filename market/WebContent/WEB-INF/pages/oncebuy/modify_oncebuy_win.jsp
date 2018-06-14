<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	function closeWin(){
		$("#newOncebuyWin").window('close') ;
	}
	function saveOncebuyInfo(){
	      var params = $("#oncebuyInfoForm").serializeArray();
	      execAjax("${ctx}/oncebuy/saveOncebuyInfo", params, true, function(retData){
	    	  doRefreshDataGrid() ;
	    	  closeWin() ;
	      });
	}
	$(document).ready(function(){
		var row = $('#oncebuyListTable').datagrid('getSelected');
		if(!row){
			msg_error('错误' ,'无法获取需要修改的单次购买优惠信息。');
			closeWin() ;
			return ;
		}
	 	$('#oncebuyInfoForm').form('load',{
	 		onceById: row.onceById ,
	 		beginAmount: row.beginAmount, 
	 		endAmount: row.endAmount ,
	 		perRate: row.perRate 
	 	});
	});
</script>

<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false" >
		<form id="oncebuyInfoForm" class="easyui-form" method="post" data-options="">
	    	<table cellpadding="5" style="width:100%">
	    		<tr>
	    			<td>单次购买额度起始:</td>
	    			<td>
	    				<input type="hidden" name="onceById" />
	    				<input class="easyui-textbox" type="text" name="beginAmount" data-options="width:345"></input>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>单次购买额度结束:</td>
	    			<td><input class="easyui-textbox" type="text" name="endAmount" data-options="width:345"></input></td>
	    		</tr>
	    		<tr>
	    			<td>优惠力度（%）:</td>
	    			<td><input class="easyui-textbox" type="text" name="perRate" data-options="width:345"></input></td>
	    		</tr>
	    	</table>
	    </form>
		
	</div>
	<div data-options="region:'south',border:false" style="height:30px" align="center">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="saveOncebuyInfo();" style="width:15%">保存</a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="closeWin();" style="width:15%">取消</a>
	</div>
</div>


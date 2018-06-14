<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	function closeWin(){
		$("#newIllnessWin").window('close') ;
	}
	function saveIllness(){
	      var params = $("#illnessInfoForm").serializeArray();
	      execAjax("${ctx}/commonGift/saveCommonGiftInfo", params, true, function(retData){
	    	  doRefreshDataGrid() ;
	    	  closeWin() ;
	      });
	}
</script>

<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false" >
		<form id="illnessInfoForm" class="easyui-form" method="post" data-options="">
	    	<table cellpadding="5" style="width:100%">
	    		<tr>
	    			<td>惠赠商品名称:</td>
	    			<td><input class="easyui-textbox" type="text" name="commonGiftName" data-options="width:345"></input></td>
	    		</tr>
	    		<tr>
	    			<td>包装规格:</td>
	    			<td><input class="easyui-textbox" type="text" name="commonGiftUnit" data-options="width:345"></input><br>（如：袋*捆*双  ；如：个；）</td>
	    		</tr>
	    	</table>
	    </form>
		
	</div>
	<div data-options="region:'south',border:false" style="height:30px" align="center">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="saveIllness();" style="width:15%">保存</a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="closeWin();" style="width:15%">取消</a>
	</div>
</div>



<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	function closeWin(){
		$("#newMemberWin").window('close') ;
	}
	function saveMemberInfo(){
	      var params = $("#memberInfoForm").serializeArray();
	      execAjax("${ctx}/member/saveMemberInfo", params, true, function(retData){
	    	  doRefreshDataGrid() ;
	    	  closeWin() ;
	      });
	}
</script>

<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false" >
		<form id="memberInfoForm" class="easyui-form" method="post" data-options="">
	    	<table cellpadding="5" style="width:100%">
	    		<tr>
	    			<td>姓名:</td>
	    			<td><input class="easyui-textbox" type="text" name="memberName" data-options="width:345"></input></td>
	    		</tr>
	    		<tr>
	    			<td>手机:</td>
	    			<td><input class="easyui-textbox" type="text" name="memberPhone" data-options="width:345"></input></td>
	    		</tr>
	    		<tr>
	    			<td>固定电话:</td>
	    			<td><input class="easyui-textbox" type="text" name="memberTel" data-options="width:345"></input></td>
	    		</tr>
	    		<tr>
	    			<td>QQ:</td>
	    			<td><input class="easyui-textbox"  type="text" name="memberQQ" data-options="width:345" ></input></td>
	    		</tr>
	    		<tr>
	    			<td>微信:</td>
	    			<td><input class="easyui-textbox"  type="text" name="memberWeixin" data-options="width:345" ></input></td>
	    		</tr>
	    		<tr>
	    			<td>地址:</td>
	    			<td><input class="easyui-textbox" name="memberAddress" data-options="multiline:true,width:345" style="height:60px"></input></td>
	    		</tr>
	    		<tr>
	    			<td>养殖备注:</td>
	    			<td><input class="easyui-textbox" name="memberBusiRemark" data-options="multiline:true,width:345" style="height:60px"></input></td>
	    		</tr>
	    		<tr>
	    			<td>其他备注:</td>
	    			<td><input class="easyui-textbox" name="common" data-options="multiline:true,width:345" style="height:60px"></input></td>
	    		</tr>
	    	</table>
	    </form>
		
	</div>
	<div data-options="region:'south',border:false" style="height:30px" align="center">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="saveMemberInfo();" style="width:15%">保存</a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="closeWin();" style="width:15%">取消</a>
	</div>
</div>



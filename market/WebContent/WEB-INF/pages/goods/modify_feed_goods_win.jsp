<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	function closeWin(){
		$("#newFeedGoodsWin").window('close') ;
	}
	function saveMemberInfo(){
	      var params = $("#feedGoodsInfoForm").serializeArray();
	      execAjax("${ctx}/feed/saveGoodsInfo", params, true, function(retData){
	    	  doRefreshDataGrid() ;
	    	  closeWin() ;
	      });
	}
	$(document).ready(function(){
		var row = $('#feedGoodsListTable').datagrid('getSelected');
		if(!row){
			msg_error('错误' ,'无法获取需要修改的饲料信息。');
			closeWin() ;
			return ;
		}
	 	$('#feedGoodsInfoForm').form('load',{
	 		goodsId: row.goodsId ,
	 		goodsName: row.goodsName ,
	 		goodsNo: row.goodsNo ,
	 		goodsManufacturer: row.goodsManufacturer ,
	 		goodsUsage: row.goodsUsage ,
	 		goodsStatus: row.goodsStatus ,
	 		goodsRemark: row.goodsRemark ,
	 		common: row.common ,
	 		memberWeixin: row.memberWeixin 
	 	});
		$('#memberNo').text(row.memberNo) ;
	});
</script>

<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false" >
		<form id="feedGoodsInfoForm" class="easyui-form" method="post" data-options="">
	    	<table cellpadding="5" style="width:100%">
	    		<tr>
	    			<td>饲料名称:</td>
	    			<td>
	    				<input type="hidden" name="goodsId" />
	    				<input class="easyui-textbox" type="text" name="goodsName" data-options="width:345"></input>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>饲料编号:</td>
	    			<td><input class="easyui-textbox" type="text" name="goodsNo" data-options="width:345"></input></td>
	    		</tr>
	    		<tr>
	    			<td>厂商信息:</td>
	    			<td><input class="easyui-textbox" type="text" name="goodsManufacturer" data-options="width:345"></input></td>
	    		</tr>
	    		<tr>
	    			<td>饲料功效:</td>
	    			<td><input class="easyui-textbox"  type="text" name="goodsUsage" data-options="width:345" ></input></td>
	    		</tr>
	    		<tr>
	    			<td>销售状态:</td>
	    			<td><input class="easyui-textbox"  type="text" name="goodsStatus" data-options="width:345" ></input></td>
	    		</tr>
	    		<tr>
	    			<td>饲料备注:</td>
	    			<td><input class="easyui-textbox" name="goodsRemark" data-options="multiline:true,width:345" style="height:60px"></input></td>
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

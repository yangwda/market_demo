<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	function closeWin(){
		$("#newDrugGoodsWin").window('close') ;
	}
	function saveGoodsInfo(){
	      var params = $("#drugGoodsInfoForm").serializeArray();
	      execAjax("${ctx}/drug/saveGoodsInfo", params, true, function(retData){
	    	  doRefreshDataGrid() ;
	    	  closeWin() ;
	      });
	}
	$(document).ready(function(){
		var row = $('#drugGoodsListTable').datagrid('getSelected');
		if(!row){
			msg_error('错误' ,'无法获取需要修改的药品信息。');
			closeWin() ;
			return ;
		}
	 	$('#drugGoodsInfoForm').form('load',{
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
		<form id="drugGoodsInfoForm" class="easyui-form" method="post" data-options="">
	    	<table cellpadding="5" style="width:100%">
	    		<tr>
	    			<td>药品名称:</td>
	    			<td>
	    				<input type="hidden" name="goodsId" />
	    				<input class="easyui-textbox" type="text" name="goodsName" data-options="width:345"></input>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>药品编号:</td>
	    			<td><input class="easyui-textbox" type="text" name="goodsNo" data-options="width:345"></input></td>
	    		</tr>
	    		<tr>
	    			<td>厂商信息:</td>
	    			<td><input class="easyui-textbox" type="text" name="goodsManufacturer" data-options="width:345"></input></td>
	    		</tr>
	    		<tr>
	    			<td>药品功效:</td>
	    			<td><input class="easyui-textbox"  type="text" name="goodsUsage" data-options="width:345" ></input></td>
	    		</tr>
	    		<tr>
	    			<td>销售状态:</td>
	    			<td><input class="easyui-textbox"  type="text" name="goodsStatus" data-options="width:345" ></input></td>
	    		</tr>
	    		<tr>
	    			<td>药品备注:</td>
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
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="saveGoodsInfo();" style="width:15%">保存</a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="closeWin();" style="width:15%">取消</a>
	</div>
</div>


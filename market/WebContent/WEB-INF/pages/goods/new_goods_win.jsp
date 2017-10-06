<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	function closeWin(){
		$("#newDrugGoodsWin").window('close') ;
	}
	function saveDrugGoods(){
	      var params = $("#memberInfoForm").serializeArray();
	      execAjax("${ctx}/drug/saveGoodsInfo", params, true, function(retData){
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
	    			<td>药品名称:</td>
	    			<td><input class="easyui-textbox" type="text" name="goodsName" data-options="width:345"></input></td>
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
	    			<td>在售/停售:</td>
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
	    		<tr>
	    			<td>包装/单价:</td>
	    			<td>
	    				<input class="easyui-textbox" name="punit1" data-options="width:345"></input>例：1*4*5<br>
	    				<input class="easyui-textbox" name="punit2" data-options="width:345"></input>例：箱*包*袋<br>
	    				<input class="easyui-textbox" name="punit3" data-options="width:345"></input>例：245*70*15<br>
	    				示例表示：1箱4包，每包5袋；1袋15块钱，1包70块钱，一箱245块钱
	    			</td>
	    		</tr>
	    	</table>
	    </form>
		
	</div>
	<div data-options="region:'south',border:false" style="height:30px" align="center">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="saveDrugGoods();" style="width:15%">保存</a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="closeWin();" style="width:15%">取消</a>
	</div>
</div>



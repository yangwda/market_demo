<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	function closeWin(){
		$("#newDrugGiftPayoffWin").window('close') ;
	}
	function saveGoodsInfo(){
	      var params = $("#drugGiftPayoffEditForm").serializeArray();
	      execAjax("${ctx}/drugGift/saveDrugGiftPayoff", params, true, function(retData){
	    	  doRefreshDataGrid() ;
	    	  closeWin() ;
	      });
	}
	$(document).ready(function(){
		var row = $('#drugGiftPayoffListTable').datagrid('getSelected');
		if(!row){
			msg_error('错误' ,'无法获取需要修改的药品赠品活动信息。');
			closeWin() ;
			return ;
		}
	 	$('#drugGiftPayoffEditForm').form('load',{
	 		giftConfigId: row.giftConfigId ,
	 		giftConfigDesc: row.giftConfigDesc ,
	 		giftConfigType: row.giftConfigType ,
	 		giftConfigBeginTime: row.giftConfigBeginTime.replace(" 00:00:00","") ,
	 		giftConfigEndTime: row.giftConfigEndTime.replace(" 00:00:00","") ,
	 		giftConfigRemarks: row.giftConfigRemarks ,
	 		goodsId: row.goodsId ,
	 		goodsName: row.goodsName ,
	 		goodsNo: row.goodsNo ,
	 		buyLimit: row.buyLimit ,
	 		giftConfigLineId: row.line.giftConfigLineId ,
	 		giftGoodsId: row.line.giftGoodsId ,
	 		giftGoodsName: row.line.giftGoodsName ,
	 		giftGoodsNo: row.line.giftGoodsNo ,
	 		giftGoodsCount: row.line.giftGoodsCount ,
	 		giftGoodsCountUnit: row.line.giftGoodsCountUnit 
	 	});
	});
</script>

<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false" >
		<form id="drugGiftPayoffEditForm" class="easyui-form" method="post" data-options="">
	    	<table cellpadding="5" style="width:100%">
	    		<tr>
	    			<td>药品名称:</td>
	    			<td>
	    				<input class="easyui-textbox" type="text" name="goodsName" id="editFormGoodsName" data-options="width:345,editable:false"></input>
	    				<input class="" type="hidden" name="goodsId" id="editFormGoodsId" data-options="width:345"></input>
	    				<input type="hidden" name="giftConfigId" />
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>药品编号:</td>
	    			<td><input class="easyui-textbox" type="text" name="goodsNo" id="editFormGoodsNo" data-options="width:345,editable:false"></input></td>
	    		</tr>
	    		<tr>
	    			<td>赠品活动描述:</td>
	    			<td><input class="easyui-textbox" type="text" name="giftConfigDesc" data-options="width:345"></input></td>
	    		</tr>
	    		<tr>
	    			<td>开始日期:</td>
	    			<td><input class="easyui-textbox"  type="text" name="giftConfigBeginTime" data-options="width:345,prompt:'yyyy-mm-dd'" ></input></td>
	    		</tr>
	    		<tr>
	    			<td>结束日期:</td>
	    			<td><input class="easyui-textbox"  type="text" name="giftConfigEndTime" data-options="width:345,prompt:'yyyy-mm-dd'" ></input></td>
	    		</tr>
	    		<tr>
	    			<td>达到金额(元):</td>
	    			<td><input class="easyui-textbox" name="buyLimit" data-options="width:345" style=""></input></td>
	    		</tr>
	    		<tr>
	    			<td>活动备注:</td>
	    			<td><input class="easyui-textbox" name="giftConfigRemarks" data-options="multiline:true,width:345" style="height:60px"></input></td>
	    		</tr>
	    		<tr>
	    			<td>赠品:</td>
	    			<td>
	    				<input class="" name="giftGoodsName" id="editFormGiftGoodsName" data-options="width:345"></input>
	    				<input class="" type="hidden" name="giftGoodsId" id="editFormGiftGoodsId" ></input>
	    				<input class="" type="hidden" name="giftGoodsNo" id="editFormGiftGoodsNo" ></input>
	    				<input class="easyui-textbox" name="giftGoodsCount" id="editFormGiftGoodsCount" data-options="width:170,prompt:'赠品数量'"></input>
	    				<input class="easyui-combobox" name="giftGoodsCountUnit" id="editFormGiftGoodsCountUnit" 
	    					data-options="panelHeight:90,editable:false ,valueField:'punit' ,textField:'punit' ,width:172,prompt:'赠品数量单位'"></input>
	    				<input type="hidden" name="giftConfigLineId" />
	    			</td>
	    		</tr>
	    	</table>
	    </form>
		
	</div>
	<div data-options="region:'south',border:false" style="height:30px" align="center">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="saveGoodsInfo();" style="width:15%">保存</a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="closeWin();" style="width:15%">取消</a>
	</div>
</div>

<script type="text/javascript">
$(document).ready(function(){
	$('#editFormGiftGoodsName').combobox({
		prompt : '赠品名称',
		required : false,
		mode : 'remote',
		width: 345 ,
		url : ajaxJsonUrl("${ctx}/autofill/getGiftGoodsAutofill"),
		editable : true,
		hasDownArrow : false,
		valueField : 'goodsName',
		textField : 'goodsName',
		onBeforeLoad : function(param) {
			var value = $(this).combobox('getText');
			if (value) {
				param.goodsName = value;
			}
		},
		onSelect: function(record){
			var dl = [] ;
			var sl = {} ;
			if(record.punit2){
				var pl = record.punit2.split("*") ;
				for(var i=0;i<pl.length;i++){
					sl = {punit: pl[i]} ;
					dl.push(sl) ;
				}
			}
			else{
				sl = {punit : '个'} ;
				dl.push(sl) ;
			}
			$("#editFormGiftGoodsCountUnit").combobox('loadData',dl) ;
			$("#editFormGiftGoodsCountUnit").combobox('select',sl.punit) ;
			$("#editFormGiftGoodsId").val(record.goodsId) ;
			$("#editFormGiftGoodsNo").val(record.goodsNo) ;
		},
		onChange: function(newValue, oldValue){
			$("#editFormGiftGoodsCountUnit").combobox('clear' ,'') ;
			$("#editFormGiftGoodsCountUnit").combobox('setValue' ,'') ;
			$("#editFormGiftGoodsCountUnit").combobox('loadData',[]) ;
			$("#editFormGiftGoodsCount").textbox('setValue','') ;
			$("#editFormGiftGoodsId").val('') ;
			$("#editFormGiftGoodsNo").val("") ;
		}
	});
});
</script>

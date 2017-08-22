<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	function closeWin(){
		$("#newFeedGiftPayoffWin").window('close') ;
	}
	function saveFeedGiftPayoff(){
	      var params = $("#feedGiftPayoffEditForm").serializeArray();
			var dl = $('#giftDetailListTable').datagrid('getRows') ;
			if(!dl || dl.length < 1){
				msg_alert("提示", "请添加优惠明细！") ;
				return ; 
			}
			for(var i=0;i<dl.length;i++){
				var rcd = dl[i] ;
				var checkType = rcd.checkType ;
				if(!checkType){
					checkType="NULL" ;
				}
				var buyLimit = rcd.buyLimit ;
				if(!buyLimit){
					buyLimit="NULL" ;
				}
				var buyLimitPunit = rcd.buyLimitPunit ;
				if(!buyLimitPunit){
					buyLimitPunit="NULL" ;
				}
				var goodsId = rcd.goodsId ;
				if(!goodsId){
					goodsId="NULL" ;
				}
				var goodsName = rcd.goodsName ;
				if(!goodsName){
					goodsName="NULL" ;
				}
				var goodsNo = rcd.goodsNo ;
				if(!goodsNo){
					goodsNo="NULL" ;
				}
				var giftGoodsCount = rcd.giftGoodsCount ;
				if(!giftGoodsCount){
					giftGoodsCount="NULL" ;
				}
				var giftGoodsCountUnit = rcd.giftGoodsCountUnit ;
				if(!giftGoodsCountUnit){
					giftGoodsCountUnit="NULL" ;
				}
				
				var ss = checkType + "@@@" + buyLimit + "@@@" + buyLimitPunit + "@@@" 
					+ goodsId + "@@@" + goodsName + "@@@" + goodsNo + "@@@" + giftGoodsCount + "@@@" + giftGoodsCountUnit ;
				
				params.push({name: 'lineStrArr' ,value: ss}) ;
			}
			
	      execAjax("${ctx}/feedGift/saveFeedGiftPayoff", params, true, function(retData){
	    	  doRefreshDataGrid() ;
	    	  closeWin() ;
	      });
	}
	
</script>

<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false" >
		<div class="easyui-layout" data-options="fit:true,border:false">
			<div data-options="region:'east',split:false,border:false,collapsible:false,tools:'#tt'" title="活动规则" style="width:560px;padding: 10px;">
				<table id="giftDetailListTable"></table>
			</div>
			<div data-options="region:'center',border:false">
		  		<form id="feedGiftPayoffEditForm" class="easyui-form" method="post" data-options="">
			    	<table cellpadding="5" style="width:100%">
			    		<tr>
			    			<td>饲料名称:</td>
			    			<td>
			    				<input class="easyui-textbox" type="text" name="goodsName" id="editFormGoodsName" data-options="width:300,editable:false"></input>
			    				<input class="" type="hidden" name="goodsId" id="editFormGoodsId" data-options="width:300"></input>
			    				<input class="" type="hidden" name="giftConfigId" id="editFormGiftConfigId" data-options="width:300"></input>
			    			</td>
			    		</tr>
			    		<tr>
			    			<td>饲料编号:</td>
			    			<td><input class="easyui-textbox" type="text" name="goodsNo" id="editFormGoodsNo" data-options="width:300,editable:false"></input></td>
			    		</tr>
			    		<tr>
			    			<td>赠品活动描述:</td>
			    			<td><input class="easyui-textbox" type="text" name="giftConfigDesc" data-options="width:300"></input></td>
			    		</tr>
			    		<tr>
			    			<td>开始日期:</td>
			    			<td><input class="easyui-textbox"  type="text" name="giftConfigBeginTime" data-options="width:300,prompt:'yyyy-mm-dd'" ></input></td>
			    		</tr>
			    		<tr>
			    			<td>结束日期:</td>
			    			<td><input class="easyui-textbox"  type="text" name="giftConfigEndTime" data-options="width:300,prompt:'yyyy-mm-dd'" ></input></td>
			    		</tr>
			    		<tr>
			    			<td>活动备注:</td>
			    			<td><input class="easyui-textbox" name="giftConfigRemarks" data-options="multiline:true,width:300" style="height:100px"></input></td>
			    		</tr>
			    	</table>
			    </form>
			</div>
		</div>
	</div>
	<div id="tt">
		<a href="javascript:void(0)" class="icon-add" title="添加明细" onclick="javascript:editGiftDetails()"></a>
<!-- 		<a href="javascript:void(0)" class="icon-edit" title="修改明细" onclick="javascript:editGiftDetails()"></a> -->
		<a href="javascript:void(0)" class="icon-remove" title="删除明细" onclick="javascript:deleteGiftDetails()"></a>
	</div>
	<div data-options="region:'south',border:false" style="height:30px" align="center">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="saveFeedGiftPayoff();" style="width:15%">保存</a>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="closeWin();" style="width:15%">取消</a>
	</div>
</div>
<div id="editGiftDetailWin" class="easyui-window" title="新增饲料赠品明细" 
			data-options="modal:true,closed:true,iconCls:'icon-excel',cache:false,minimizable:false,maximizable:false,collapsible:false,resizable:false
						 ,href:''" style="width:500px;height:272px;padding:5px;">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:false" >
			<table border="0">
		   		<tr>
		   			<td>优惠方式:</td>
		   			<td>
		   				<select class="easyui-combobox" name="checkType" id="editGiftDetail_checkType" data-options="panelHeight:57,editable:false">
		   					<option value="当时兑现">当时兑现</option><option value="累积">累积</option>
		   				</select>
		   			</td>
		   		</tr>
		   		<tr>
		   			<td>购买额度:</td>
		   			<td>
		   				<input class="easyui-textbox" type="text" name="buyLimit" id="editGiftDetail_buyLimit" data-options="required:true,width:72"></input>
		   				<select class="easyui-combobox" name="buyLimitPunit" id="editFormFeedGoodsCountUnit" 
		   					data-options="panelHeight:100,valueField:'punit',textField:'punit',width:72,editable:false">
		   				</select>
		   			</td>
		   		</tr>
		   		<tr>
		   			<td>赠品:</td>
		   			<td>
		   				<input type="hidden" name="giftGoodsId" id="editGiftDetail_giftGoodsId" />
		   				<input type="hidden" name="giftGoodsNo" id="editGiftDetail_giftGoodsNo" />
		   				<input class="easyui-textbox" type="text" name="giftGoodsName" id="editFormGiftGoodsName" data-options="required:true,width:270"></input>
		   			</td>
		   		</tr>
		   		<tr>
		   			<td>赠送数量:</td>
		   			<td>
		   				<input class="easyui-textbox" type="text" name="giftGoodsCount" id="editGiftDetail_giftGoodsCount" data-options="required:true,width:72"></input>
		   				<input type="hidden" name="giftGoodsPunit2" id="editGiftDetail_giftGoodsPunit2" />
		   				<select class="easyui-combobox" name="giftGoodsCountUnit" id="editFormGiftGoodsCountUnit" 
		   					data-options="panelHeight:100,valueField:'punit',textField:'punit',width:72,editable:false">
		   				</select>
		   			</td>
		   		</tr>
		   	</table>
		</div>
		<div data-options="region:'south',border:false" style="height:30px" align="center">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="editGiftDetailToList();" style="width:15%">确定</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-back'" onclick="backToParent();" style="width:15%">返回</a>
		</div>
	</div>
</div>

<script type="text/javascript">
function backToParent(){
	$("#editGiftDetailWin").window('close') ;
}
function editGiftDetailToList(){
    var giftGoodsPunit2 = $("#editGiftDetail_giftGoodsPunit2").val() ;
    var goodsNo = $("#editGiftDetail_giftGoodsNo").val() ;
    var goodsId = $("#editGiftDetail_giftGoodsId").val() ;
    var goodsName = $("#editFormGiftGoodsName").textbox('getValue') ;
    var checkType = $("#editGiftDetail_checkType").combobox('getValue') ;
    var buyLimit = $("#editGiftDetail_buyLimit").textbox('getValue') ;
    var editFormFeedGoodsCountUnit = $("#editFormFeedGoodsCountUnit").combobox('getValue') ;
    var giftGoodsCount = $("#editGiftDetail_giftGoodsCount").textbox('getValue') ;
    var editFormGiftGoodsCountUnit = $("#editFormGiftGoodsCountUnit").combobox('getValue') ;
	var rcd = {} ;
	rcd.goodsNo = goodsNo ;
	rcd.goodsId =  goodsId;
	rcd.goodsName =  goodsName;
	rcd.checkType =  checkType;
	rcd.buyLimit =  buyLimit;
	rcd.buyLimitPunit = editFormFeedGoodsCountUnit ;
	rcd.giftGoodsCount = giftGoodsCount ;
	rcd.giftGoodsCountUnit = editFormGiftGoodsCountUnit ;
	rcd.giftGoodsPunit2 = giftGoodsPunit2 ;

	$('#giftDetailListTable').datagrid('appendRow' ,rcd);
}
function cleanGiftDetailForm(){
	$("#editGiftDetail_giftGoodsPunit2").val('') ;
    $("#editGiftDetail_giftGoodsNo").val('') ;
    $("#editGiftDetail_giftGoodsId").val('') ;
    $("#editFormGiftGoodsName").textbox('setValue', '') ;
//     $("#editGiftDetail_checkType").combobox('setValue', '') ;
//     $("#editGiftDetail_checkType").combobox('setText', '') ;
    $("#editGiftDetail_buyLimit").textbox('setValue', '') ;
//     $("#editFormFeedGoodsCountUnit").combobox('setValue', '') ;
//     $("#editFormFeedGoodsCountUnit").combobox('setText', '') ;
    $("#editGiftDetail_giftGoodsCount").textbox('setValue', '') ;
    $("#editFormGiftGoodsCountUnit").combobox('setValue', '') ;
    $("#editFormGiftGoodsCountUnit").combobox('setText', '') ;
    $("#editFormGiftGoodsCountUnit").combobox('loadData',[]) ;
}
function deleteGiftDetails(){
	var rcd = $('#giftDetailListTable').datagrid('getSelected' ); 
	if(!rcd){
		msg_alert("提示", "请选择要删除的优惠明细行！") ;
		return ;
	}
	msg_confirm("确认", "确认要删除选定的明细行？", function(){
		$('#giftDetailListTable').datagrid('deleteRow' ,$('#giftDetailListTable').datagrid('getRowIndex' ,rcd));
	});
}
// function editGiftDetails(){
// 	var rcd = $('#giftDetailListTable').datagrid('getSelected' ); 
// 	if(!rcd){
// 		msg_alert("提示", "请选择要修改的优惠明细行！") ;
// 		return ;
// 	}
// 	cleanGiftDetailForm() ;
// 	$("#editGiftDetailWin").window('open');
// }
function editGiftDetails(){
	if(!$("#editFormGoodsId").val()){
		msg_alert("提示", "请先填写饲料信息！") ;
		return ;
	}
	cleanGiftDetailForm() ;
	$("#editGiftDetailWin").window('open');
}
function giftCountFormatter(value,row,index){
	return row.giftGoodsCount + " " + row.giftGoodsCountUnit ;
}
function buyLimmitFormatter(value,row,index){
	var preFix = "<font color='red' >买</font>" ;
	if(row.checkType == '累积'){
		preFix = "<font color='red' >累积</font>" ;
	}
	return preFix + " " + row.buyLimit + " " + row.buyLimitPunit ;
}

$(document).ready(function(){
	
	$('#editFormGiftGoodsName').combobox({
		prompt : '赠品名称',
		required : false,
		mode : 'remote',
		width: 300 ,
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
			var pus = "" ;
			if(record.punit2){
				pus = record.punit2;
				var pl = record.punit2.split("*") ;
				for(var i=0;i<pl.length;i++){
					sl = {punit: pl[i]} ;
					dl.push(sl) ;
				}
			}
			else{
				pus = "个" ;
				sl = {punit : '个'} ;
				dl.push(sl) ;
			}
			$("#editFormGiftGoodsCountUnit").combobox('loadData',dl) ;
			$("#editFormGiftGoodsCountUnit").combobox('select',sl.punit) ;
			$("#editGiftDetail_giftGoodsPunit2").val(pus) ;
			$("#editGiftDetail_giftGoodsId").val(record.goodsId) ;
			$("#editGiftDetail_giftGoodsNo").val(record.goodsNo) ;
		},
		onChange: function(newValue, oldValue){
			$("#editFormGiftGoodsCountUnit").combobox('clear' ,'') ;
			$("#editFormGiftGoodsCountUnit").combobox('setValue' ,'') ;
			$("#editFormGiftGoodsCountUnit").combobox('loadData',[]) ;
			$("#editFormGiftGoodsCount").textbox('setValue','') ;
			$("#editGiftDetail_giftGoodsId").val('') ;
			$("#editGiftDetail_giftGoodsNo").val("") ;
			$("#editGiftDetail_giftGoodsPunit2").val("") ;
		}
	});
	
	$('#giftDetailListTable').datagrid({
	    fit: true,
	    fitColumns: true,
	    rownumbers: true,
	    pagination: false,
	    singleSelect: true,
	    loadMsg: '加载中，请稍后......',
	    nowrap: false,
	    columns:[[
	      {title:'优惠方式', field:'checkType', width:72},
	      {title:'购买额度', field:'buyLimit', width:100,formatter:buyLimmitFormatter},
	      {title:'赠品', field:'goodsName', width:200},
	      {title:'赠送数量', field:'giftGoodsCount', width:100,formatter:giftCountFormatter}
	    ]]
	  });
	
	//--init
	var row = $('#feedGiftPayoffListTable').datagrid('getSelected');
	if(!row){
		msg_error('错误' ,'无法获取需要修改的饲料赠品活动信息。');
		closeWin() ;
		return ;
	}
 	$('#feedGiftPayoffEditForm').form('load',{
 		giftConfigId: row.giftConfigId ,
 		goodsId: row.goodsId ,
		goodsName: row.goodsName ,
		goodsNo: row.goodsNo ,
 		giftConfigDesc: row.giftConfigDesc ,
 		giftConfigBeginTime: row.giftConfigBeginTime.replace(" 00:00:00","") ,
 		giftConfigEndTime: row.giftConfigEndTime.replace(" 00:00:00","") ,
 		giftConfigRemarks: row.giftConfigRemarks ,
 	});
 	if(row.lineList && row.lineList.length > 0){
 		var fgdl = [] ;
 		for(var j=0;j<row.lineList.length;j++){
 			var gd = row.lineList[j] ;
 			var gp = {} ;
 			gp.buyLimit = gd.buyLimit ;
 			gp.buyLimitPunit = gd.buyLimitPunit ;
 			gp.checkType = gd.checkType ;
 			gp.giftConfigId = gd.giftConfigId ;
 			gp.giftConfigLineId = gd.giftConfigLineId ;
 			gp.giftGoodsCount = gd.giftGoodsCount ;
 			gp.giftGoodsCountUnit = gd.giftGoodsCountUnit ;
 			gp.goodsId = gd.giftGoodsId ;
 			gp.goodsName = gd.giftGoodsName ;
 			gp.goodsNo = gd.giftGoodsNo ;
 			fgdl.push(gp) ;
 		}
 		$('#giftDetailListTable').datagrid('loadData' ,fgdl) ;
	}
 	$.ajax({
 	    type:"POST",
 	    url: ajaxJsonUrl("${ctx}/autofill/loadGoodsPunitList"),
 	    dataType: 'json',
 	    data: {goodsId:row.goodsId },
 	    success: function(data){
 	    	var pul = data ;
 	 		if(!pul){
 	 			pul = [] ;
 	 			pul.push({punit :'个'}) ;
 	 		}
 	 		$("#editFormFeedGoodsCountUnit").combobox('loadData',pul) ;
 			$("#editFormFeedGoodsCountUnit").combobox('select',pul[0].punit) ;
 	    },
 	    error: function(){ 
 	    	msg_error('错误' ,'无法获取需要修改的饲料赠品活动明细信息。');
 			closeWin() ;
 			return ;
 	    }
 	  });
});
</script>

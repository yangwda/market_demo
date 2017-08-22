<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	function closeWin(){
		$("#newFeedGiftPayoffWin").window('close') ;
	}
	function saveFeedGiftPayoff(){
	      var params = $("#feedGiftPayoffForm").serializeArray();
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
		  		<form id="feedGiftPayoffForm" class="easyui-form" method="post" data-options="">
			    	<table cellpadding="5" style="width:100%">
			    		<tr>
			    			<td>饲料名称:</td>
			    			<td>
			    				<select class="" type="text" name="goodsName" id="addFormGoodsName"></select>
			    				<input class="" type="hidden" name="goodsId" id="addFormGoodsId" data-options="width:300"></input>
			    			</td>
			    		</tr>
			    		<tr>
			    			<td>饲料编号:</td>
			    			<td><input class="easyui-textbox" type="text" name="goodsNo" id="addFormGoodsNo" data-options="width:300,editable:false"></input></td>
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
		<a href="javascript:void(0)" class="icon-add" title="添加明细" onclick="javascript:addGiftDetails()"></a>
<!-- 		<a href="javascript:void(0)" class="icon-edit" title="修改明细" onclick="javascript:editGiftDetails()"></a> -->
		<a href="javascript:void(0)" class="icon-remove" title="删除明细" onclick="javascript:deleteGiftDetails()"></a>
	</div>
	<div data-options="region:'south',border:false" style="height:30px" align="center">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="saveFeedGiftPayoff();" style="width:15%">保存</a>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="closeWin();" style="width:15%">取消</a>
	</div>
</div>
<div id="addGiftDetailWin" class="easyui-window" title="新增饲料赠品明细" 
			data-options="modal:true,closed:true,iconCls:'icon-excel',cache:false,minimizable:false,maximizable:false,collapsible:false,resizable:false
						 ,href:''" style="width:500px;height:272px;padding:5px;">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:false" >
			<table border="0">
		   		<tr>
		   			<td>优惠方式:</td>
		   			<td>
		   				<select class="easyui-combobox" name="checkType" id="addGiftDetail_checkType" data-options="panelHeight:57,editable:false">
		   					<option value="当时兑现">当时兑现</option><option value="累积">累积</option>
		   				</select>
		   			</td>
		   		</tr>
		   		<tr>
		   			<td>购买额度:</td>
		   			<td>
		   				<input class="easyui-textbox" type="text" name="buyLimit" id="addGiftDetail_buyLimit" data-options="required:true,width:72"></input>
		   				<select class="easyui-combobox" name="buyLimitPunit" id="addFormFeedGoodsCountUnit" 
		   					data-options="panelHeight:100,valueField:'punit',textField:'punit',width:72,editable:false">
		   				</select>
		   			</td>
		   		</tr>
		   		<tr>
		   			<td>赠品:</td>
		   			<td>
		   				<input type="hidden" name="giftGoodsId" id="addGiftDetail_giftGoodsId" />
		   				<input type="hidden" name="giftGoodsNo" id="addGiftDetail_giftGoodsNo" />
		   				<input class="easyui-textbox" type="text" name="giftGoodsName" id="addFormGiftGoodsName" data-options="required:true,width:270"></input>
		   			</td>
		   		</tr>
		   		<tr>
		   			<td>赠送数量:</td>
		   			<td>
		   				<input class="easyui-textbox" type="text" name="giftGoodsCount" id="addGiftDetail_giftGoodsCount" data-options="required:true,width:72"></input>
		   				<input type="hidden" name="giftGoodsPunit2" id="addGiftDetail_giftGoodsPunit2" />
		   				<select class="easyui-combobox" name="giftGoodsCountUnit" id="addFormGiftGoodsCountUnit" 
		   					data-options="panelHeight:100,valueField:'punit',textField:'punit',width:72,editable:false">
		   				</select>
		   			</td>
		   		</tr>
		   	</table>
		</div>
		<div data-options="region:'south',border:false" style="height:30px" align="center">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="addGiftDetailToList();" style="width:15%">确定</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-back'" onclick="backToParent();" style="width:15%">返回</a>
		</div>
	</div>
</div>

<script type="text/javascript">
function backToParent(){
	$("#addGiftDetailWin").window('close') ;
}
function addGiftDetailToList(){
    var giftGoodsPunit2 = $("#addGiftDetail_giftGoodsPunit2").val() ;
    var goodsNo = $("#addGiftDetail_giftGoodsNo").val() ;
    var goodsId = $("#addGiftDetail_giftGoodsId").val() ;
    var goodsName = $("#addFormGiftGoodsName").textbox('getValue') ;
    var checkType = $("#addGiftDetail_checkType").combobox('getValue') ;
    var buyLimit = $("#addGiftDetail_buyLimit").textbox('getValue') ;
    var addFormFeedGoodsCountUnit = $("#addFormFeedGoodsCountUnit").combobox('getValue') ;
    var giftGoodsCount = $("#addGiftDetail_giftGoodsCount").textbox('getValue') ;
    var addFormGiftGoodsCountUnit = $("#addFormGiftGoodsCountUnit").combobox('getValue') ;
	var rcd = {} ;
	rcd.goodsNo = goodsNo ;
	rcd.goodsId =  goodsId;
	rcd.goodsName =  goodsName;
	rcd.checkType =  checkType;
	rcd.buyLimit =  buyLimit;
	rcd.buyLimitPunit = addFormFeedGoodsCountUnit ;
	rcd.giftGoodsCount = giftGoodsCount ;
	rcd.giftGoodsCountUnit = addFormGiftGoodsCountUnit ;
	rcd.giftGoodsPunit2 = giftGoodsPunit2 ;

	$('#giftDetailListTable').datagrid('appendRow' ,rcd);
}
function cleanGiftDetailForm(){
	$("#addGiftDetail_giftGoodsPunit2").val('') ;
    $("#addGiftDetail_giftGoodsNo").val('') ;
    $("#addGiftDetail_giftGoodsId").val('') ;
    $("#addFormGiftGoodsName").textbox('setValue', '') ;
//     $("#addGiftDetail_checkType").combobox('setValue', '') ;
//     $("#addGiftDetail_checkType").combobox('setText', '') ;
    $("#addGiftDetail_buyLimit").textbox('setValue', '') ;
//     $("#addFormFeedGoodsCountUnit").combobox('setValue', '') ;
//     $("#addFormFeedGoodsCountUnit").combobox('setText', '') ;
    $("#addGiftDetail_giftGoodsCount").textbox('setValue', '') ;
    $("#addFormGiftGoodsCountUnit").combobox('setValue', '') ;
    $("#addFormGiftGoodsCountUnit").combobox('setText', '') ;
    $("#addFormGiftGoodsCountUnit").combobox('loadData',[]) ;
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
// 	$("#addGiftDetailWin").window('open');
// }
function addGiftDetails(){
	if(!$("#addFormGoodsId").val()){
		msg_alert("提示", "请先填写饲料信息！") ;
		return ;
	}
	cleanGiftDetailForm() ;
	$("#addGiftDetailWin").window('open');
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
	$('#addFormGoodsName').combobox({
		prompt : '饲料名称',
		required : false,
		mode : 'remote',
		width: 300 ,
		url : ajaxJsonUrl("${ctx}/autofill/getFeedGoodsAutofill"),
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
			$("#addFormGoodsNo").textbox('setValue' ,record.goodsNo) ;
			$("#addFormGoodsId").val(record.goodsId) ;
			//
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
			$("#addFormFeedGoodsCountUnit").combobox('loadData',dl) ;
			$("#addFormFeedGoodsCountUnit").combobox('select',sl.punit) ;
		},
		onChange: function(newValue, oldValue){
			$("#addFormGoodsNo").textbox('setValue' ,'') ;
			$("#addFormGoodsId").val('') ;
			//
			$("#addFormFeedGoodsCountUnit").combobox('clear' ,'') ;
			$("#addFormFeedGoodsCountUnit").combobox('setValue' ,'') ;
			$("#addFormFeedGoodsCountUnit").combobox('loadData',[]) ;
		}
	});
	$('#addFormGiftGoodsName').combobox({
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
			$("#addFormGiftGoodsCountUnit").combobox('loadData',dl) ;
			$("#addFormGiftGoodsCountUnit").combobox('select',sl.punit) ;
			$("#addGiftDetail_giftGoodsPunit2").val(pus) ;
			$("#addGiftDetail_giftGoodsId").val(record.goodsId) ;
			$("#addGiftDetail_giftGoodsNo").val(record.goodsNo) ;
		},
		onChange: function(newValue, oldValue){
			$("#addFormGiftGoodsCountUnit").combobox('clear' ,'') ;
			$("#addFormGiftGoodsCountUnit").combobox('setValue' ,'') ;
			$("#addFormGiftGoodsCountUnit").combobox('loadData',[]) ;
			$("#addFormGiftGoodsCount").textbox('setValue','') ;
			$("#addGiftDetail_giftGoodsId").val('') ;
			$("#addGiftDetail_giftGoodsNo").val("") ;
			$("#addGiftDetail_giftGoodsPunit2").val("") ;
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
});
</script>

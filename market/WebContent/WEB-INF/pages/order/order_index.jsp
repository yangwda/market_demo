<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<!DOCTYPE html>
<html>
  <head>
    <%@ include file="/common/meta.jsp"%>
    <title>销售单据信息</title>
    <link rel="stylesheet" type="text/css" href="/styles/themes/lightblue/easyui.css" id="themesCss">
	<%@ include file="/common/cssAndJs.jsp"%>
    <script type="text/javascript"> 
    
    // 页面键盘屏蔽
    $(document).keydown(function(event){
      if(event.keyCode==116) return false;  //屏蔽F5刷新键
    });
    
    function formatMoney(money){
    	if(!money){
    		money = 0 ;
    	}
    	return parseFloat(money).toFixed(2) ;
    }
    function formatTTMoney(value,row,index){
    	return formatMoney(row.orderTotalMoney) ;
    }
    function formatCTMoney(value,row,index){
    	return formatMoney(row.orderCutMoney) ;
    }
    function formatPOMoney(value,row,index){
    	return formatMoney(row.payOffCashTotalMoney) ;
    }
    $(document).ready(function(){
    	$('#orderListTable').datagrid({
		    fit: true,
		    fitColumns: true,
		    rownumbers: true,
		    pagination: true,
		    pageSize: 20,
		    pageList: [20,30,40,50,100],
		    singleSelect: true,
		    loadMsg: '加载中，请稍后......',
		    nowrap: false,
		    columns:[[
		      {title:'会员名称', field:'memberName', width:72},
		      {title:'会员号', field:'memberNo', width:100},
		      {title:'销售时间', field:'createTime', width:100},
		      {title:'付款状态', field:'payOffStatus', width:100},
		      {title:'备注', field:'orderRemark', width:150},
		      {title:'总金额（元）', field:'orderTotalMoney', width:72,align:'right',formatter:formatTTMoney},
		      {title:'实收金额（元）', field:'payOffCashTotalMoney', width:72,align:'right',formatter:formatPOMoney},
		      {title:'抹零（元）', field:'orderCutMoney', width:72,align:'right',formatter:formatCTMoney}
		    ]],
		    onSelect: function(index,row){}
		  });
    });
    
    var giftTimeFormater = function(value,row,index){
    	return row.giftConfigBeginTime + " ~ "  + row.giftConfigEndTime ;
    } ;
    
    var doRefreshDataGrid = function(){
		$('#orderListTable').datagrid('loadData', { total: 0, rows: [] }); 
		doSearch();
	};

	// 查询
	function doSearch(){
	    $('#orderListTable').datagrid({
	    	loader: function(param, success, error) {
				var params = $("#orderSearchForm").serializeArray() || [] ;
	    		var pa = [] ;
				pa.push( {name: "page" ,value: param.page} ) ;
				pa.push({name: "rows" ,value: param.rows}) ;
				pa.push({name:"order",value:param.order});
				pa.push({name:"sort",value:param.sort});
				param = $.merge(pa, params);
		      	boDataLoader('${ctx}/order/getOrderPageList', param, success, error);
		   }
	    });//.datagrid('load' ,params);
	}

	//弹出新增销售单据页面
	function newOrder(){
		parent.parent.openMWinAndMax(1, "销售单据", "${ctx}/order/Order", 10, 60) ;
	}
	function orderDetail(){
		var row = $('#orderListTable').datagrid('getSelected');
		if(!row){
			msg_warning('提示' ,'请选择要查看的单据。');
			return ;
		}
		parent.parent.openMWinAndMax(1, "销售单据", "${ctx}/order/Order?orderId="+row.orderId, 10, 60) ;
	}
	

	function printPage(){
		window.print(); 
	}
    </script>

  </head>
  <body class="easyui-layout">
	<div id="queryDiv" class="easyui-panel" data-options="region:'north',border:false"
		style="background: #B3DFDA; height: 30px; padding: 2px; overflow: hidden">
		<form id="orderSearchForm">
			<table cellpadding="0" cellspacing="1" border="0" width="100%">
				<tr>
					<td width="3px"></td>
					<td align="left"><span>会员:</span></td>
					<td><input class="easyui-textbox" name="memberNo" id="memberNo"
						style="width: 100px"></td>
					<td align="left"><span>时间:</span></td>
					<td>
						<input class="easyui-textbox" name="beginDate" id="beginDate" data-options="" style="width: 100px">
						~
						<input class="easyui-textbox" name="endDate" id="endDate" data-options="" style="width: 100px">
					</td>
					<td align="left"><span>备注:</span></td>
					<td><input class="easyui-textbox" name="orderRemark" id="orderRemark" data-options=""
						style="width: 196px"></td>
					<td align="right"><a href="#" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-search'"
						onclick="doSearch();">查询</a> 
						<a href="#" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-add'"
						style="padding-left: 14px" onclick="newOrder();">销售</a>
						<a href="#" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-excel'"
						style="padding-left: 14px" onclick="orderDetail();">查看</a>
						</td>
					<td width="10px"></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false" style="padding:0px;" id="tableDiv">
		<div class="easyui-layout" data-options="fit:true,border:false">
			<table id="orderListTable"></table>
		</div>
	</div>
	<div id="newOrderWin" class="easyui-window" title="销售单据" 
			data-options="modal:true,closed:true,iconCls:'icon-excel',cache:false,minimizable:false,maximizable:false,collapsible:false,resizable:false
						 ,href:''" style="width:1000px;height:490px;padding:0x;">
		</div>
  </body>
</html>

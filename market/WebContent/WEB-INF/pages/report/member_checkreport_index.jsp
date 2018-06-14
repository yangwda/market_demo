<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<!DOCTYPE html>
<html>
  <head>
    <%@ include file="/common/meta.jsp"%>
    <title>会员信息</title>
    <link rel="stylesheet" type="text/css" href="/styles/themes/lightblue/easyui.css" id="themesCss">
	<%@ include file="/common/cssAndJs.jsp"%>
    <script type="text/javascript"> 
    
    // 页面键盘屏蔽
    $(document).keydown(function(event){
      if(event.keyCode==116) return false;  //屏蔽F5刷新键
    });
    
    $(document).ready(function(){
    	$('#merberInfoInputBox').combobox({
			prompt : '会员信息',
			required : false,
			mode : 'remote',
			width: 150 ,
			url : ajaxJsonUrl("${ctx}/autofill/getMemberInfo"),
			editable : true,
			hasDownArrow : false,
			valueField : 'memberName',
			textField : 'memberName',
			onBeforeLoad : function(param) {
				var value = $(this).combobox('getText');
				if (value) {
					param.likeStr = value;
				}
			},
			onSelect: function(record){
				$("#memberId").val(record.memberId) ;
			},
			onChange: function(newValue, oldValue){
				$("#memberId").val('') ;
			}
		});
    	
    	$('#memberListTable').datagrid({
		    fit: true,
// 		    fitColumns: true,
		    rownumbers: false,
		    pagination: false,
		    pageSize: 20,
		    pageList: [20,30,40,50,100],
		    singleSelect: true,
		    loadMsg: '加载中，请稍后......',
		    nowrap: false,
		    columns:[[
		      {title:'会员编号', field:'memberNo', width:160},
		      {title:'会员名称', field:'memberName', width:80},
		      {title:'地址', field:'memberAddress', width:130},
		      {title:'手机', field:'memberPhone', width:100},
		      {title:'电话', field:'memberTel', width:100},
		      
		      {title:'累积销售额', field:'ljxse', width:80 ,align:'right',formatter:formatMoney},
		      {title:'累积付款额', field:'ljfke', width:80 ,align:'right',formatter:formatMoney},
		      {title:'累积抹零', field:'ljml', width:80 ,align:'right',formatter:formatMoney},
		      {title:'累积使用代金券', field:'ljsydjq', width:90 ,align:'right',formatter:formatMoney},
		      {title:'累积代金券总额', field:'ljdjqze', width:90 ,align:'right',formatter:formatMoney},
		      {title:'累积未使用代金券', field:'ljwsydjq', width:100 ,align:'right',formatter:formatMoney},
		      {title:'年末累积', field:'nmlj', width:90 ,align:'right',formatter:formatMoney},
		      {title:'等值商品累积总额', field:'dzspljze', width:100 ,align:'right',formatter:formatMoney},
		      {title:'等值商品累积已兑换', field:'dzspljydh', width:115 ,align:'right',formatter:formatMoney},
		      {title:'等值商品累积未兑换', field:'dzspljwdh', width:115 ,align:'right',formatter:formatMoney}
		    ]]
		  });
    });
    
    function formatMoney(value,row,index){
    	var money = value ;
    	if(!money){
    		money = 0 ;
    	}
    	return parseFloat(money).toFixed(2) ;
    }
    
	// 查询
	function doSearch(){
	    $('#memberListTable').datagrid({
	    	loader: function(param, success, error) {
				var params = $("#memberSearchForm").serializeArray() || [] ;
		      	boDataLoader('${ctx}/memberCheckReport/getMemberCheckReport', params, success, error);
		   }
	    });//.datagrid('load' ,params);
	}

    </script>

  </head>
  <body class="easyui-layout" style="padding-right:5px;">
	<div id="queryDiv" class="easyui-panel" data-options="region:'north'"
		style="background: #B3DFDA; height: 30px; padding: 2px; overflow: hidden">
		<form id="memberSearchForm">
			<table cellpadding="0" cellspacing="1" border="0" width="100%">
				<tr>
					<td width="3px"></td>
					<td align="left">
						<span>会员姓名:</span>
						<input type="text" id="merberInfoInputBox" name="memberName"/>
	    				<input type="hidden" name="memberId" id="memberId"/>
	    				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    				<a href="#" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-search'"
						onclick="doSearch();">查询</a> 
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false" style="padding:0px;" id="tableDiv">
  		<table id="memberListTable"></table>
	</div>
  </body>
</html>

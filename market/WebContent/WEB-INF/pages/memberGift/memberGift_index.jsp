<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<!DOCTYPE html>
<html>
  <head>
    <%@ include file="/common/meta.jsp"%>
    <title>会员惠赠查询</title>
    <link rel="stylesheet" type="text/css" href="/styles/themes/lightblue/easyui.css" id="themesCss">
	<%@ include file="/common/cssAndJs.jsp"%>
    <script type="text/javascript"> 
    
    // 页面键盘屏蔽
    $(document).keydown(function(event){
      if(event.keyCode==116) return false;  //屏蔽F5刷新键
    });
    
    $(document).ready(function(){
    	$('#memberGiftListTable').datagrid({
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
		      {title:'会员编号', field:'memberNo', width:100},
		      {title:'会员名称', field:'memberName', width:80},
		      {title:'销售单号', field:'orderNo', width:80},
		      {title:'兑换时间', field:'createTime', width:80},
		      {title:'兑换金额（元）', field:'giftCheckAmount',align:'right', width:80},
		      {title:'兑换备注', field:'giftCheckRemark', width:200}
		    ]]
		  });
    });
    
    var doRefreshDataGrid = function(){
		$('#memberGiftListTable').datagrid('loadData', { total: 0, rows: [] }); 
		doSearch();
	};

	// 查询
	function doSearch(){
	    $('#memberGiftListTable').datagrid({
	    	loader: function(param, success, error) {
				var params = $("#memberSearchForm").serializeArray() || [] ;
	    		var pa = [] ;
				pa.push( {name: "page" ,value: param.page} ) ;
				pa.push({name: "rows" ,value: param.rows}) ;
				pa.push({name:"order",value:param.order});
				pa.push({name:"sort",value:param.sort});
				param = $.merge(pa, params);
		      	boDataLoader('${ctx}/memberGift/getMemberGiftCheckPageList', param, success, error);
		   }
	    });//.datagrid('load' ,params);
	}

    </script>

  </head>
  <body class="easyui-layout">
	<div id="queryDiv" class="easyui-panel" data-options="region:'north'"
		style="background: #B3DFDA; height: 30px; padding: 2px; overflow: hidden">
		<form id="memberSearchForm">
			<table cellpadding="0" cellspacing="1" border="0" width="100%">
				<tr>
					<td width="3px"></td>
					<td align="left"><span>会员编号:</span></td>
					<td><input class="easyui-textbox" name="memberNo" id="memberNo"
						style="width: 100px"></td>
					<td align="left"><span>会员姓名:</span></td>
					<td><input class="easyui-textbox" name="memberName" id="memberName" data-options=""
						style="width: 196px"></td>
					<td align="right"><a href="#" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-search'"
						onclick="doSearch();">查询</a> 
					</td>
					<td width="10px"></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false" style="padding:0px;" id="tableDiv">
  		<table id="memberGiftListTable"></table>
	</div>
  </body>
</html>

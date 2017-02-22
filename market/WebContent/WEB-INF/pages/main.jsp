<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<!DOCTYPE html>
<html>
  <head>
    <%@ include file="/common/meta.jsp"%>
    <title>MARGON</title>
    <link rel="stylesheet" type="text/css" href="/styles/themes/default/easyui.css" id="themesCss">
    <%@ include file="/common/cssAndJs.jsp"%>
    <script type="text/javascript" src="/scripts/jshash-2.2/md5-min.js"></script>
	<%-- 调用百度map api --%>
    <script type="text/javascript"> 
    $.extend($.fn.validatebox.defaults.rules, {
        /*必须和某个字段相等*/
        equalTo: { validator: function (value, param) { return $(param[0]).val() == value; }, message: '字段不匹配' }
       });
    
    // 页面键盘屏蔽
    $(document).keydown(function(event){
      if(event.keyCode==116) return false;  //屏蔽F5刷新键
    });
    /**
     * 打开一个TAB页
     * @param subtitle 标题
     * @param url 链接地址
     * @param icon 图标样式
     * @param flg 是否重新打开此页面 true:关闭后重新打开  false:不关闭且不重新打开
     */
    function addTab(subtitle, url, icon, flg) {
      // 已经具有打开的此Tab页，判定条件为标题名称
      if ($('#tabs').tabs('exists', subtitle)) {
        if(flg){ // 关闭后重新打开
          $('#tabs').tabs('close', subtitle);
          // 打开新的Tab页
          //$('#tabs').tabs('add', {title:subtitle, content:createFrame(url), closable:true, icon:icon} );
          newTab(subtitle, url, icon, flg);
        }else{ // 不关闭且不重新打开
          $('#tabs').tabs('select', subtitle);
        }
      }else{
        // 判定打开的TAB数量
        var tabcount = $('#tabs').tabs('tabs').length;
        if(tabcount > 10){
          // 提示 你打开的窗口过多
          alert("你打开的窗口过多!");
          return;
        }
        // 打开新的Tab页
        //$('#tabs').tabs('add', {title:subtitle, content:createFrame(url), closable:true, icon:icon} );
        newTab(subtitle, url, icon, flg) ;
      }
    }
    
    function newTab(subtitle, url, icon, flg){
    	$('#tabs').tabs('add', {title:subtitle, content: createFrameBlank(url), closable:true, icon:icon} );
    	refreshTab(url) ;
    }

    // 创建TAB页面
    function createFrame(url) {
      var s = '<iframe scrolling="auto" frameborder="0" src="' + url + '" style="width:100%;height:99%;padding: 0 0 0px 0;"></iframe>';
      return s;
    }
    // 创建空TAB页面
    function createFrameBlank() {
      var s = '<iframe scrolling="auto" frameborder="0" src="" style="width:100%;height:99%;padding: 0 0 0px 0;"></iframe>';
      return s;
    }
    //刷新当前tab
    function refreshTab(url){  
        var tPanle = $('#tabs').tabs('getSelected');  
        if(tPanle && tPanle.find('iframe').length > 0){  
        	var rIframe = tPanle.find('iframe')[0];  
        	rIframe.contentWindow.location.href=url;  
        }  
    }  
    
    var themes = [
          {value:'default',text:'Default',group:'Base'},
          {value:'gray',text:'Gray',group:'Base'},
          {value:'metro',text:'Metro',group:'Base'},
          {value:'bootstrap',text:'Bootstrap',group:'Base'},
          {value:'black',text:'Black',group:'Base'},
      ];
    
    $(document).ready(function() {
//       $('#tt').tree({
//         onClick: function(node){
//           if("model" == node.type){
//             openMWin(1, node.text, node.url, node.width, node.height);
//           }else if("" == node.url){
//             return;  
//           }else{
//             addTab(node.text, node.url, 'icon-reload', true);
//           }
//         }
//       });
      
      $('#cb-theme').combobox({
          groupField:'group',
          data: themes,
          editable:false,
          panelHeight:'auto',
          onChange:onChangeTheme,
          onLoadSuccess:function(){
            $(this).combobox('setValue', 'gray');
            $(this).combobox('setText', 'Gray');
          }
      });

//       $('#taskList').datagrid({
//         fit: true,
//         rownumbers: true,
//         pagination: false,
// //         pageSize: 20,
// //         pageList: [20,30,40,50,100],
//         singleSelect: true,
//         loadMsg: '加载中，请稍后......',
//         nowrap: false,
//         columns:[[
//           {title:'', field:'taskId', checkbox:true},
//           {title:'任务名称', field:'taskName', width:290},
//           {title:'任务类型', field:'taskType', width:90},
//           {title:'发起人', field:'xxx', width:90},
//           {title:'发起时间', field:'xxxx', width:120},
//           {title:'状态', field:'status', width:100},
//           {title:'要求时限', field:'createDate', width:120}
//         ]],
//         data: [
//           {taskId:'1111', taskName:'入库单审核', taskType:'入库单', status:'待审核', createDate:'2014-12-12 12:12'},

//         ]
//       });

      
    });
    
    function onChangeTheme(theme){
      $('#themesCss').attr('href', '/styles/themes/'+theme+'/easyui.css');
      var tabs = $('#tabs').tabs('tabs');
      for(var i=1; i<tabs.length; i++){
        var tab = tabs[i].find("iframe").contents().find('#themesCss');
        if(undefined != tab.attr('href'))
          tab.attr('href', '/styles/themes/'+theme+'/easyui.css');
      }
    }
    
    // 打开模态窗体
    function openMWin(index, title, url, width, height) {
      $('#win'+index).window({
        width:width, height:height, title:title, 
        modal:true, resizable:true, shadow:true, maximizable:false,
        closable:true, collapsible:false, minimizable:false, draggable:true
      });
      
      var content = '<iframe scrolling="no" frameborder="0" src="' + url + '" style="width:100%;height:99%;"></iframe>';
      $('#win'+index).html(content);  
      $('#win'+index).window('open');
      $('#win'+index).window('center');
//       var ii = $('#win'+index).find("iframe").attr("src");  
      //$('#win'+index).window('refresh', url);
    }
    // 关闭模态窗体
    function closeMWin(index) {
      $('#win'+index).window('close');
    }
 	
    // 打开非模态窗体
    function openCWin(index, title, url, width, height) {
      $('#win'+index).window({
        width:width, height:height, title:title, 
        modal:false, resizable:true, shadow:true, maximizable:true,
        closable:true, collapsible:true, minimizable:false, draggable:true,
        onMinimize: function () {     
        	$('#win'+index).window('move', {left: "5%",top: "90%"}).window('collapse').window('open'); 
        },
        onMaximize: function(){
        	$('#win'+index).window('expand'); 
        }
      });
      
      var content = '<iframe scrolling="no" frameborder="0" src="' + url + '" style="width:100%;height:99%;"></iframe>';
      $('#win'+index).html(content);  
      $('#win'+index).window('open');
      $('#win'+index).window('center');
//       var ii = $('#win'+index).find("iframe").attr("src");  
      //$('#win'+index).window('refresh', url);
    }
    // 关闭非模态窗体
    function closeCWin(index) {
      $('#win'+index).window('clear');
      $('#win'+index).window('close');
    }
    
    // 模态窗体返回定义
    var transData1 = null;
    var transData2 = null;
    var transData3 = null;
    
    var sessionFlg = 'sessionFlg';

    
    // 退出系统
    function exitWeb(){
      $.messager.confirm('提示', '您确认退出系统?',
        function(r){
          if (r){// 确认关闭
            $.get("/main/logOut",function(data,status){
              location.href = "/";
            });
          }
      });
    }
    
    $.extend($.fn.layout.methods, {
    	full : function (jq) {
    		return jq.each(function () {
    			var layout = $(this);
    			var center = layout.layout('panel', 'center');
    			center.panel('maximize');
    			center.parent().css('z-index', 10);
    			$(window).on('resize.full', function () {
    				layout.layout('unFull').layout('resize');
    			});
    		});
    	},
    	unFull : function (jq) {
    		return jq.each(function () {
    			var center = $(this).layout('panel', 'center');
    			center.parent().css('z-index', 'inherit');
    			center.panel('restore');
    			$(window).off('resize.full');
    		});
    	}
    });
    function full() {
    	$("#maxButton").linkbutton({
    		iconCls: 'icon-collapse'
    	});
    	$('#maxButton').unbind('click', full);
    	$('#maxButton').bind('click', unFull);
    	$("#mainLayOut").layout("full");
    }
    function unFull() {
    	$("#maxButton").linkbutton({
    		iconCls: 'icon-expand'
    	});
    	$('#maxButton').unbind('click' ,unFull);
    	$('#maxButton').bind('click', full);
    	$("#mainLayOut").layout("unFull");
    }
    $(document).ready(function(){
    	$('#maxButton').bind('click', full);
    });
    
    function updatePsw(){
    	$('#oldpassword').val("");
        $('#password').val("");
        $('#repassword').val("");
    	
        $('#winUpdatePsw').window('open');
    }
    
    function savePassword(){
    	// 表单校验
        if( !($('#updatePswForm').form("validate")) ){
          return;
        }
    	
        $.post(ajaxJsonUrl("/main/updatePassword"),
                {
    			  password:hex_md5( $("#password").val() ),
                  oldpassword:hex_md5( $("#oldpassword").val() )
                },
                function(data,status){
                  if(status == 'success'){
                	  if(data.status == 'erroPsw'){
                		  $.messager.alert('密码修改', "旧密码输入错误！", 'error');
                	  }else if(data.status == 'ok'){
                		  $.messager.alert('密码修改', "旧密修改成功,请重新登陆！", 'success',function(){
                			  $('#winUpdatePsw').window('close');
                    		  $.get("/main/logOut",function(data,status){
                                  location.href = "/";
                                });
                		  });
                		  
                	  }
                	  else{
                		  msg_error('错误' ,'未知错误，请联系系统管理员') ;
                	  }
                	  
                  }else{
                    $.messager.alert('警告','发生未知错误，请联系系统管理员!','warning');
                  }
                }
              );
        
    	
    }
    
    var CURRENT_TAB ;
    var CURRENT_IDX ;
    function changeTab(title,index){
    	if(CURRENT_TAB && CURRENT_IDX != 0){
    		$('#tabs').tabs('update' ,{
    			tab: CURRENT_TAB,
    			type: 'header' ,
    			options: {
    				iconCls: 'icon-tab-normal'
		    	}
			});
    	}
    	CURRENT_TAB = $('#tabs').tabs('getTab' ,index);
    	CURRENT_IDX = index ;
    	if(index == 0){
    		return ;
    	}
    	$('#tabs').tabs('update' ,{
			tab: CURRENT_TAB,
			type: 'header' ,
			options: {
				iconCls: 'icon-tab-light'
	    	}
		});
    }

    </script>

  </head>
  <body>
  <div class="easyui-layout" data-options="fit:true" id="mainLayOut">
    <div data-options="region:'north',split:true" style="height:72px;background:url(../images/navgmcs.png) repeat-x top #8bacd7;">
      <span style="float:left; padding-left:0px;font-weight:bold;font-size:18px;">
        <img src="/images/sign.png" style="height:65px" align="absmiddle" />
      </span>
      
      <span style="float:right; padding-right:5px;height:95%;">
<!--         &nbsp;风格:<select id="cb-theme" style="width:120px"></select>&nbsp;&nbsp; -->
<%--         <a href="#" onclick="">登陆平台:${dept}</a>&nbsp;|&nbsp; --%>
<%--         <a href="#" title="<span>张三(电话:13912345678)</span><BR><span>李四(电话:13912345678)</span>" --%>
<!--            class="easyui-tooltip">本日值班运维</a>&nbsp;|&nbsp; -->
		<table width="100%" height="100%">
			<tr><td valign="bottom" style="font-size:14px;color:white;">
				<strong>当前用户：${user.userName}&nbsp;&nbsp;|&nbsp;</strong>
				<a href="#" onclick="updatePsw()" style="font-size:14px;color:white;"><strong>修改密码</strong></a>
				<strong>&nbsp;|&nbsp;</strong>
        		<a href="#" onclick="exitWeb()" style="font-size:14px;color:white;"><strong>退出</strong></a>
			</td></tr>
		</table>
      </span>
    </div>
    <div data-options="region:'west',split:true" title="" style="width:160px;padding: 0px 0px 5px 0px;">
      <div class="easyui-accordion" data-options="fit:true,border:false" style="padding: 0px 0px 9px 0px;">
        <div title="日常业务" style="padding:1px;" data-options="iconCls:'icon-daily-busi'">
          <ul class="easyui-tree">
            <li data-options="iconCls:'icon-menu-link'"><a href="#" onclick="addTab('药料销售', '/jump/sysBuilding', 'icon-tab-light', false)" >药料销售</a></li>
          </ul>
        </div>
        <div title="优惠促销" style="padding:1px;" data-options="iconCls:'icon-favorable'">
          <ul class="easyui-tree">
            <li data-options="iconCls:'icon-menu-link'"><a href="#" onclick="addTab('赠品活动', '/jump/sysBuilding', 'icon-tab-light', false)" >赠品活动</a></li>
            <li data-options="iconCls:'icon-menu-link'"><a href="#" onclick="addTab('单次购买活动', '/jump/sysBuilding', 'icon-tab-light', false)" >单次购买活动</a></li>
          </ul>
        </div>
        <div title="会员管理" style="padding:1px;" data-options="iconCls:'icon-member'">
          <ul class="easyui-tree">
            <li data-options="iconCls:'icon-menu-link'"><a href="#" onclick="addTab('会员信息', '/member/', 'icon-tab-light', false)" >会员信息</a></li>
            <li data-options="iconCls:'icon-menu-link'"><a href="#" onclick="addTab('会员对账单', '/jump/sysBuilding', 'icon-tab-light', false)" >会员对账单</a></li>
            <li data-options="iconCls:'icon-menu-link'"><a href="#" onclick="addTab('年末累积', '/jump/sysBuilding', 'icon-tab-light', false)" >年末累积</a></li>
          </ul>
        </div>
        <div title="药料管理" style="padding:1px;" data-options="iconCls:'icon-product'">
          <ul class="easyui-tree">
            <li data-options="iconCls:'icon-menu-link'"><a href="#" onclick="addTab('兽种类', '/jump/sysBuilding', 'icon-tab-light', false)" >兽种类</a></li>
            <li data-options="iconCls:'icon-menu-link'"><a href="#" onclick="addTab('疾病种类', '/jump/sysBuilding', 'icon-tab-light', false)" >疾病种类</a></li>
            <li data-options="iconCls:'icon-menu-link'"><a href="#" onclick="addTab('其他赠品管理', '/jump/sysBuilding', 'icon-tab-light', false)" >其他赠品管理</a></li>
            <li data-options="iconCls:'icon-menu-link'"><a href="#" onclick="addTab('药品管理', '/jump/sysBuilding', 'icon-tab-light', false)" >药品管理</a></li>
            <li data-options="iconCls:'icon-menu-link'"><a href="#" onclick="addTab('饲料管理', '/jump/sysBuilding', 'icon-tab-light', false)" >饲料管理</a></li>
          </ul>
        </div>
        <div title="统计报表" style="padding:1px;" data-options="iconCls:'icon-statis-report'">
          <ul class="easyui-tree">
            <li data-options="iconCls:'icon-menu-link'"><a href="#" onclick="addTab('销售报表', '/jump/sysBuilding', 'icon-tab-light', false)" >销售报表</a></li>
            <li data-options="iconCls:'icon-menu-link'"><a href="#" onclick="addTab('会员消费报表', '/jump/sysBuilding', 'icon-tab-light', false)" >会员消费报表</a></li>
            <li data-options="iconCls:'icon-menu-link'"><a href="#" onclick="addTab('药料销量统计', '/jump/sysBuilding', 'icon-tab-light', false)" >药料销量统计</a></li>
          </ul>
        </div>
       </div>
    </div>
    <div data-options="region:'center', border:false" style="padding:0px;">
      <div id="tabs" class="easyui-tabs" data-options="fit:true,plain:false,tools:'#tab-tools',onSelect:changeTab" border="false" style="padding:0px;" >
        <div title="" style="padding:2px;width:100%;height:100% " data-options="iconCls:'icon-home-page'" closable="false" id="indexM">
        	<iframe scrolling="auto" frameborder="0" src="/portal" style="width:100%;height:99%;padding: 0 0 0px 0;"></iframe>
        </div>
      </div>
      <div id="tab-tools">
	  	<a href="javascript:void(0)" id="maxButton" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-expand'"></a>
	  </div>
    </div>
    </div>
<!--     <div data-options="region:'south',border:false" style="height:18px;padding:0px;width:100%;"> -->
<!--       <span style="float:left; padding-right:8px;margin-top:2px; width: 1080px"><marquee>这里是公告这里是公告这里是公告这里是公告这里是公告这里是公告</marquee></span> -->
<!--       <span style="float:right; padding-right:8px;margin-top:2px;">版本:2.0  © 2014 深圳市怡亚通供应链股份有限公司</span> -->
<!--     </div> -->
    <div id="win1"></div>
    <div id="win2"></div>
    <div id="win3"></div>
    
    <div id="winUpdatePsw" class="easyui-window" title="修改密码" 
       data-options="modal:true, closed:true, minimizable:false, maximizable:false, collapsible: false "
       style="width:450px;height:280px;padding:10px;vertical-align: middle;">
       <form id="updatePswForm">
    <table cellpadding="3" cellspacing="0" border="0" width="95%" >
    <tr>
        <td width="150px" align="right" class="t_sld r_sld b_sld l_sld">旧密码:</td>
        <td class="b_sld t_sld r_sld">
          <input id="oldpassword" name="oldpassword" validType="length[4,32]" class="easyui-validatebox" required="true" type="password" value=""/>
        </td>
      </tr>
      <tr>
        <td width="150px" align="right" class="t_sld r_sld b_sld l_sld">密码:</td>
        <td class="b_sld t_sld r_sld">
          <input id="password" name="password" validType="length[4,32]" class="easyui-validatebox" required="true" type="password" value=""/>
        </td>
      </tr>
      <tr>
        <td align="right" class="r_sld b_sld l_sld">确认密码:</td>
        <td class="b_sld r_sld">
          <input type="password" name="repassword" id="repassword" required="true" class="easyui-validatebox"  validType="equalTo['#password']" invalidMessage="两次输入密码不匹配"/>
        </td>
      </tr>
      <tr><td colspan="2" height="8px"></td></tr>
      <tr>
        <td align="center" colspan="2">
          <a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'" onclick="savePassword()">修改</a>
        </td>
      </tr>
    </table>
    </form>
  </div>
  </body>
</html>

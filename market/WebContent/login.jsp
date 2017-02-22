<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<html>
  <head>
    <title>MARGON CRM</title>
    <%@ include file="/common/meta.jsp" %>

    <%@ include file="/common/cssAndJs.jsp" %>
    <script type="text/javascript" src="/scripts/jshash-2.2/md5-min.js"></script>
    
    <link rel="stylesheet" href="/styles/login.css">
    <script type="text/javascript">
      $(document).ready(function(){
        // 如session过期，子页面跳转到登录页面时，直接刷新父页面
        var sessionFlg = window.parent.sessionFlg;
        if('sessionFlg' == sessionFlg){ window.parent.location.reload(); }
        
        $("#loginName").focus();
        $("form").submit(function(){
          // 非空验证,前后空格已在失去焦点时去除
//           if($("#loginName").val().length<5 || $("#password").val().length<5){
//             $.messager.alert('登陆提示','用户名或密码不能为空，并多于五个字符!','warning');
//             return false;
//           };
       	  // 发送登陆验证
          $.post(ajaxJsonUrl("/main/loginSystem"),
            {
              username:$("#loginName").val(),
              password:hex_md5( $("#password").val() )
              //password:$("#password").val() 
            },
            function(data,status){
              if(status == 'success'){
            	  if(data.status){
            		  if(data.status == 'error'){
            			  msg_error(GLOBLE_STR_MSGTITLE_ERROR ,data.message) ;
            			  return ;
            		  }
            	  }
	              if(data.loginStatus == 'LOGIN_OK'){
	                location.href = "/welcome/";
	              }else if(data.loginStatus == 'noName'){
	                $.messager.alert('登陆警告','用户名或密码错误!','warning');
	              }else if(data.loginStatus == 'lock'){
	                $.messager.alert('登陆警告','该用户已被锁定!','warning');
	              }else if(data.loginStatus == 'limit'){
	                $.messager.alert('登陆警告','该用户已被限制登录!','warning');
	              }else if(data.loginStatus == 'errPD'){
	                $.messager.alert('登陆警告','请输入正确的密码!','warning');
	              }
	              else if(data.loginStatus == 'except'){
		                $.messager.alert('登陆警告','系统异常，请联系管理员!','warning');
		              }
              }else{
                $.messager.alert('登陆警告','发生未知错误，请联系系统管理员!','warning');
              }
            }
          );
          return false;
        });

      });

    </script>

  </head>
	<body style="background:url(images/log_bg.jpg) top center no-repeat;">
    	<div class="logo"></div>
            <div class="login_box" style="width:552px;height:422px;padding:0px;">
                <h1>销售管理系统</h1>
                <form>
                <ul>
                    <li class="txt_bg">
                        <label class="disName">用&nbsp;户：</label>
                        <span>
                        <input type="text" class="InputStyle" id="loginName" tabindex="1" maxlength=18 onblur="$(this).val($.trim($(this).val()))" />
                        </span>
                    </li>
                    <li class="txt_bg">
                        <label class="disName">密&nbsp;码：</label>
                        <span>
                        <input type="password" class="InputStyle" id="password" tabindex="2" onblur="$(this).val($.trim($(this).val()))" />
                        </span>
                    </li>
                    <li>
                        <input type="submit" id="login" value="登 录" class="button_login" tabindex="3" />
                    </li>
					</ul>
                </form>

		</div>
	</body>
</html>

//2016/11/29


//alert插件  可变信息类型infoType
var sdNewAlert = function (infoType, msg) {
	createHtml("alert", infoType, msg);
	btnOk();
	btnNo();
}

//alert插件  固定信息类型msgWarning
var sdNewAlert2 = function (msg) {
	createHtml("alert", "msgWarning", msg);
	btnOk();
	btnNo();
}
//alert插件  固定信息类型msgWarning   不透明背景  针对打印设计页面
var sdNewAlert3 = function (msg) {
	createHtml2("alert", "msgWarning", msg);
	btnOk();
	btnNo();
}
//alert插件  固定信息类型msgWarning   alert带回调
var sdNewAlert4 = function (msg, callback) {
	createHtml("alert", "msgWarning", msg);
	btnOk(callback);
	btnNo(callback);
}

//confirm插件  可变信息类型infoType
var sdNewConfirm = function (infoType, msg, callback) {
	createHtml("confirm",infoType, msg);
	btnOk(callback);
	btnNo();
}

//confirm插件  固定信息类型msgInfo
var sdNewConfirm2 = function (msg, callback) {
	createHtml("confirm","msgInfo", msg);
	btnOk(callback);
	btnNo();
}


//confirm插件  固定信息类型msgInfo  两个回调函数
var sdNewConfirm3 = function (msg, callback ,callback2) {
	createHtml("confirm","msgInfo", msg);
	btnOk(callback);
	btnNo(callback2);
}
//confirm插件  固定信息类型msgInfo  两个回调函数    不透明背景  针对打印设计页面
var sdNewConfirm4 = function (msg, callback ,callback2) {
	createHtml2("confirm","msgInfo", msg);
	btnOk(callback);
	btnNo(callback2);
}
	
//生成Html
var createHtml = function (type, infoType, msg) {
	var _html = ""; 
	_html += '<div id="dialogBg" class="dialogBgH"></div><div id="dialogWrap"><div id="dialogBody">';
	_html += '<div class="dialogHead"><button class="btnMsgClose">×</button></div>';
	_html += '<div class="dialogCnt"><div class="dialogMsg '+infoType+'">' + msg + '</div></div>';

	if (type == "alert") {
		_html += '<div class="dialogFoot"><button class="btnMsgOk">确定</button></div>';
	}
	if (type == "confirm") {
		_html += '<div class="dialogFoot"><button class="btnMsgOk">确定</button><button class="btnMsgCancel">取消</button></div>';
	}
	_html += '</div></div>';

	$("body").append(_html);
	createCss();
}
var createHtml2 = function (type, infoType, msg) {
	var _html = ""; 
	_html += '<div id="dialogBg" class="dialogBgA"><iframe style="position: absolute; z-index: -1; width: 100%; height: 100%; top: 0; left: 0; scrolling: no;" frameborder="0"></iframe></div><div id="dialogWrap"><div id="dialogBody">';
	_html += '<div class="dialogHead"><button class="btnMsgClose">×</button></div>';
	_html += '<div class="dialogCnt"><div class="dialogMsg '+infoType+'">' + msg + '</div></div>';

	if (type == "alert") {
		_html += '<div class="dialogFoot"><button class="btnMsgOk">确定</button></div>';
	}
	if (type == "confirm") {
		_html += '<div class="dialogFoot"><button class="btnMsgOk">确定</button><button class="btnMsgCancel">取消</button></div>';
	}
	_html += '</div></div>';

	$("body").append(_html);
	createCss();
}

//生成Css
var createCss = function () {
	//var winW = $(window).width();
	//var winH = $(window).height();
	//var pageW = $(document).width();
	//var pageH = $(document).height();
	
	$("#dialogBg").css({
		position: 'fixed',
		width: '100%',
		height: '100%',
		top: '0',
		left: '0',
		bottom:'0',
		right:'0',
		zIndex: '99998'
	});	
	$("#dialogBg.dialogBgA").css({
		backgroundColor: '#f1f1f1'
	});	
	$("#dialogBg.dialogBgH").css({
		backgroundColor: '#000',
		filter:'alpha(opacity=50)',
		opacity:'0.5'
	});			
	$("#dialogWrap").css({
		position: 'fixed',
		top: '0',
		right: '0',
		bottom: '0',
		left: '0',
		zIndex: '99999',
		overflow: 'hidden',
		outline: '0'
	});		
	$("#dialogBody").css({
		width: '400px',
		margin: '180px auto 30px',
		position:'relative',
		borderRadius:'4px',
		border: '1px solid #e5e5e5',
		backgroundColor:'#fff'
	});		
	$("#dialogWrap .dialogHead").css({
		padding: '10px',
		borderBottom: '1px solid #e5e5e5',
		position:'relative',
		height:'20px'
	});	
	
	$("#dialogWrap .btnMsgClose").css({
		verticalAlign:'middle',
		fontFamily: 'Arial',
		fontSize: '21px',
		fontWeight: '700',
		padding: '0',
		cursor: 'pointer',
		background:'none',
		border:'0',
		float: 'right',
		color: '#000',
		textShadow:'0 1px 0 #fff',
		filter:'alpha(opacity=20)',
		opacity:'.2'
	});
	$("#dialogWrap .dialogCnt").css({
		backgroundColor:'#f8fafb',
		padding: '20px',
		fontSize: '14px',
		textAlign: 'left'
	});
	$("#dialogWrap .dialogMsg").css({
		fontSize: '12px',
		paddingLeft:'25px',
		lineHeight:'16px'
	});	
	$("#dialogWrap .msgWarning").css({
		backgroundImage:'url(./img/r01/icon_warning.png)',
		backgroundRepeat:'no-repeat',
		backgroundPosition:'left top'
	});	
	$("#dialogWrap .msgInfo").css({
		backgroundImage:'url(./img/r01/icon_info.png)',
		backgroundRepeat:'no-repeat',
		backgroundPosition:'left top'
	});	
	$("#dialogWrap .dialogFoot").css({
		padding: '10px',
		borderTop: '1px solid #e5e5e5',
		textAlign: 'right'
	});
	$("#dialogWrap .btnMsgOk,#dialogWrap .btnMsgCancel").css({
		padding:' 5px 12px',
		margin: '0 2px',
		border: '1px solid #aaa',
		background: '#eee',
		cursor: 'pointer',
		borderRadius: '2px',
		fontSize: '14px',
		outline: '0'
	});
	

	//右上角关闭按钮hover样式
	$(".dialogHead button").hover(function () {
		$(this).css({
			textDecoration:'none',
			cursor:'pointer',
			filter:'alpha(opacity=50)',
			opacity:'.5'
		});
	}, function () {
		$(this).css({
			textDecoration:'none',
			cursor:'pointer',
			filter:'alpha(opacity=20)',
			opacity:'.2'

		});
	});
	//dialogFoot按钮hover样式
	$(".dialogFoot button").hover(function () {
		$(this).css({
			borderColor: '#bbb',
			boxShadow: '0 1px 2px #aaa',
			background: '#eaeaea'
		});
	}, function () {
		$(this).css({
			borderColor: '#aaa',
			boxShadow: 'none',
			background: '#eee'
		});
	});

	
}

//确定按钮事件
var btnOk = function (callback) {
	$(".btnMsgOk").click(function () {
		$("#dialogBg,#dialogWrap").remove();
		if (typeof (callback) == 'function') {
			callback();
		}
	});
} 

//取消按钮事件
var btnNo = function (callback) {
	$(".btnMsgClose,.btnMsgCancel").click(function () {
		$("#dialogBg,#dialogWrap").remove();
		if (typeof (callback) == 'function') {
			callback();
		}
	});
}
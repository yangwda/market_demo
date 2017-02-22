
//====页面引用CLodop云打印必须的JS文件：====

//让其它电脑的浏览器通过本机打印（适用例子）：
var oscript = document.createElement("script");
oscript.src ="/CLodopfuncs.js";
var head = document.head || document.getElementsByTagName("head")[0] || document.documentElement;
head.insertBefore( oscript,head.firstChild );
//让本机浏览器打印(更优先一点)：
oscript = document.createElement("script");
oscript.src ="http://localhost:8000/CLodopfuncs.js?priority=2";
var head = document.head || document.getElementsByTagName("head")[0] || document.documentElement;
head.insertBefore( oscript,head.firstChild );
//引用双端口(8000和18000）避免其中某个被占用：
oscript = document.createElement("script");
oscript.src ="http://localhost:18000/CLodopfuncs.js?priority=1";
var head = document.head || document.getElementsByTagName("head")[0] || document.documentElement;
head.insertBefore( oscript,head.firstChild );

//====获取CLODOP对象的主过程：====
function getLodopC(oOBJECT,oEMBED){
	var CLODOP;
	var strCLodopInstall="<br><font color='#FF00FF'>CLodop云打印服务(localhost本地)未安装启动!点击这里<a href='../scripts/lodop/CLodop_Setup_for_Win32NT_2.090.exe' target='_self'>执行安装</a>,安装后请刷新页面。</font>";
	var strCLodopReinstall="<br><font color='#FF00FF'>CLodop云打印服务(localhost本地)没有准备好!点击这里<a href='../scripts/lodop/CLodop_Setup_for_Win32NT_2.090.exe' target='_self'>重新安装</a>,安装后请刷新页面。</font>";
    var strCLodopUpdate="<br><font color='#FF00FF'>CLodop云打印服务需升级!点击这里<a href='../scripts/lodop/CLodop_Setup_for_Win32NT_2.090.exe' target='_self'>执行升级</a>,升级后请刷新页面。</font>";
	try{
		
		// 是否为IE浏览器
		var isIE = (navigator.userAgent.indexOf('MSIE')>=0) || (navigator.userAgent.indexOf('Trident')>=0);
		
		try{
			CLODOP=getCLodop();
		} catch(err) {
		}
		
		// 是否已安装
		if (!CLODOP) {
			if (isIE) {
				 document.write(strCLodopInstall);
			} else {
				document.documentElement.innerHTML=strCLodopInstall;
			}
			return;
		}
		
		// 是否已准备
		if (document.readyState !== "complete") {
			if (isIE) {
				 document.write(strCLodopInstall);
			} else {
				document.documentElement.innerHTML=strCLodopReinstall;
			}
			return;
		}

		if (oEMBED && oEMBED.parentNode) {
			oEMBED.parentNode.removeChild(oEMBED);
		}
		if (oOBJECT && oOBJECT.parentNode) {
			oOBJECT.parentNode.removeChild(oOBJECT);
		}
		
		//===如下空白位置适合调用统一功能(如注册语句、语言选择等):===
		CLODOP.SET_LICENSES("深圳市怡亚通供应链股份有限公司","459717366847573749293799219056","","");
		
		return CLODOP;

	} catch(err) {
		alert("getLodopC出错:"+err);
	};
};

//====本工程目前只使用云打印，所以直接返回true：====
function needCLodop(){
	return true;
};


/**
 * datagrid分页时，系统的默认分页设置
 */
//默认页面条数列表
var SYS_PAGE_DEFAULT_PAGELIST = [30,50,100,150] ;
//默认起始页
var SYS_PAGE_DEFAULT_PAGENUMBER = 1 ;
//默认每页的记录数
var SYS_PAGE_DEFAULT_PAGESIZE = 30 ;
//分页条布局
var SYS_PAGE_DEFAULT_LAYOUT = ['list','sep','first','prev','sep','manual','sep','next','last','sep','refresh'] ;


/**
 * 执行AJAX封装，统一返回信息处理
 * @param url 请求路径
 * @param params 请求参数 js对象{exp:exp}
 * @param mFlg 提示成功信息 true:提示
 * @param callFun 回调函数
 * @param errCallback ajax异常时，执行的回调函数
 */
function execAjax(url, params, mFlg, callFun, errCallback) {
  $.ajax({
    type:"POST",
    url: ajaxJsonUrl(url),
    dataType: 'json',
    data: params,
    success: function(data){
      if(RESPONSE_DATA_FLAG_SUCCESS == data.status){
    	  if(mFlg) {
    		  msg_info(GLOBLE_STR_MSGTITLE_INFO, data.message, function(){ 
            	  if(jQuery.isFunction(callFun)){
            		  callFun(data.retData); 
                  }
              }); 
            }else{
            	if(jQuery.isFunction(callFun)){
            		callFun(data.retData);
                }
            }
      }else if(RESPONSE_DATA_FLAG_ERROR == data.status){
    	  msg_error(GLOBLE_STR_MSGTITLE_ERROR ,data.message ,function(){
    		  if(jQuery.isFunction(errCallback)){
    	    		errCallback(); 
    	        }
    	  }) ;
      }
      else if(RESPONSE_DATA_FLAG_NOLOGIN == data.status){
    	  msg_info(GLOBLE_STR_MSGTITLE_INFO ,data.message ,function(){reLogin()}) ;
      }
      else if(RESPONSE_DATA_FLAG_NOAUTH == data.status){
    	  msg_warning(GLOBLE_STR_MSGTITLE_WARNING ,data.message ,function(){
    		  if(jQuery.isFunction(errCallback)){
    	    		errCallback(); 
    	        }
    	  }) ;
      }
    },
    error: function(){ 
    	msg_error(GLOBLE_STR_MSGTITLE_ERROR ,GLOBLE_MSG_EXCEPTION_UNKNOW ,function(){
	    	if(jQuery.isFunction(errCallback)){
	    		errCallback(); 
	        }
    	}) ; 
    }
  });
}

/**
 * 执行AJAX封装，统一返回信息处理，请求数据为json格式
 * @param url 请求路径
 * @param params 请求参数 js对象{exp:exp}
 * @param mFlg 提示成功信息 true:提示
 * @param callFun 回调函数
 * @param errCallback ajax异常时，执行的回调函数
 */
function execAjaxJson(url, params, mFlg, callFun, errCallback) {
	$.ajax({
		type:"POST",
		url: ajaxJsonUrl(url),
		dataType: 'json',
		contentType: "application/json; charset=utf-8",
		data: params,
		success: function(data){
			if(RESPONSE_DATA_FLAG_SUCCESS == data.status){
				if(mFlg) {
					msg_info(GLOBLE_STR_MSGTITLE_INFO, data.message, function(){ 
						if(jQuery.isFunction(callFun)){
							callFun(data.retData); 
						}
					}); 
				}else{
					if(jQuery.isFunction(callFun)){
						callFun(data.retData);
					}
				}
			}else if(RESPONSE_DATA_FLAG_ERROR == data.status){
				msg_error(GLOBLE_STR_MSGTITLE_ERROR ,data.message ,function(){
					if(jQuery.isFunction(errCallback)){
						errCallback(); 
					}
				}) ;
			}
			else if(RESPONSE_DATA_FLAG_NOLOGIN == data.status){
				msg_info(GLOBLE_STR_MSGTITLE_INFO ,data.message ,function(){reLogin()}) ;
			}
			else if(RESPONSE_DATA_FLAG_NOAUTH == data.status){
				msg_warning(GLOBLE_STR_MSGTITLE_WARNING ,data.message ,function(){
					if(jQuery.isFunction(errCallback)){
						errCallback(); 
					}
				}) ;
			}
		},
		error: function(){ 
			msg_error(GLOBLE_STR_MSGTITLE_ERROR ,GLOBLE_MSG_EXCEPTION_UNKNOW ,function(){
				if(jQuery.isFunction(errCallback)){
					errCallback(); 
				}
			}) ; 
		}
	});
}

/**
 * 执行AJAX封装，统一返回信息处理，解析手持接口数据用
 * @param url 请求路径
 * @param params 请求参数 js对象{exp:exp}
 * @param callFun 回调函数
 * @param errCallback ajax异常时，执行的回调函数
 */
function execAjaxOprt(url, params, callFun, errCallback) {
  $.ajax({
    type:"POST",
    url: ajaxJsonUrl(url),
    dataType: 'json',
    data: params,
    success: function(data){
      if("OK" == data.rts){ 
    	  if(jQuery.isFunction(callFun)){
    		  callFun(data.rslist);
    	  }
      }else if("NG" == data.rts){
    	  msg_error(GLOBLE_STR_MSGTITLE_ERROR ,data.errMsg ,function(){
    		  if(jQuery.isFunction(errCallback)){
    	    		errCallback(); 
    	        }
    	  }) ;
      }
    },
    error: function(){ 
    	msg_error(GLOBLE_STR_MSGTITLE_ERROR ,GLOBLE_MSG_EXCEPTION_UNKNOW ,function(){
	    	if(jQuery.isFunction(errCallback)){
	    		errCallback(); 
	        }
    	}) ; 
    }
  });
}
/**
 * 执行AJAX封装，统一返回信息处理
 * @param url 请求路径
 * @param params 请求参数 js对象{exp:exp}
 * @param mFlg 提示成功信息 true:提示
 * @param callFun 回调函数
 */
function formSubmit(url, params, mFlg, callFun) {
	$.ajax({
		type:"POST",
		url: ajaxJsonUrl(url),
		dataType: 'json',
		data: params,
		success: function(data){
			if(RESPONSE_DATA_FLAG_SUCCESS == data.status){
				if(mFlg) {
					msg_info(GLOBLE_STR_MSGTITLE_INFO ,data.message ,function(){
						if(jQuery.isFunction(callFun)){
							callFun(data.retData); 
						}
					}) ;
				}else{
					if(jQuery.isFunction(callFun)){
						callFun(data.retData);
					}
				}
			}else if(RESPONSE_DATA_FLAG_ERROR == data.status){
				msg_error(GLOBLE_STR_MSGTITLE_ERROR ,data.message) ;
			}
			else if(RESPONSE_DATA_FLAG_VFAILED == data.status){
				//$.messager.alert('Warning11', data.message);
				//form validate info will be enhanced
				msg_warning(GLOBLE_STR_MSGTITLE_WARNING ,data.message) ;
			}
			else if(RESPONSE_DATA_FLAG_NOLOGIN == data.status){
				msg_info(GLOBLE_STR_MSGTITLE_INFO ,data.message ,function(){reLogin()}) ;
			}
			else if(RESPONSE_DATA_FLAG_NOAUTH == data.status){
				msg_warning(GLOBLE_STR_MSGTITLE_WARNING ,data.message) ;
			}
			
		},
		error: function(){ 
			msg_error(GLOBLE_STR_MSGTITLE_ERROR ,GLOBLE_MSG_EXCEPTION_UNKNOW) ;
		}
	});
}

/**
 * 处理datagrid load 的数据，包含错误处理
 * @param gridJobj grid的jquery 对象
 * @param retData server response 的 json data
 * @param pagination 是否分页，true 是
 */
function gridLoadData(retData ,pagination){
	if(!retData){
		return getEmpGridData(pagination) ;
	}
	
	if(RESPONSE_DATA_FLAG_SUCCESS == retData.status){
		return retData.retData ;
	}
	else if(RESPONSE_DATA_FLAG_ERROR == retData.status){
		msg_error(GLOBLE_STR_MSGTITLE_ERROR ,retData.message) ;
	}
	else if(RESPONSE_DATA_FLAG_NOLOGIN == retData.status){
		msg_info(GLOBLE_STR_MSGTITLE_INFO ,retData.message ,function(){reLogin()}) ;
	}
	else if(RESPONSE_DATA_FLAG_NOAUTH == retData.status){
		msg_warning(GLOBLE_STR_MSGTITLE_WARNING ,retData.message) ;
	}
	return getEmpGridData(pagination) ;
}
/**
 * 获取空的grid data
 * @param pagination 是否是分页grid ，true 是
 * @returns
 */
function getEmpGridData(pagination){
	if(pagination){
		return {total: 0,rows:[]} ;
	}
	else{
		return [] ;
	}
}

/**
 * 包装url，加参数返回数据类型=json
 * @param url
 * @returns {String}
 */
function ajaxJsonUrl(url){
	if(!url){
		return "";
	}
	if(url.indexOf("?") < 0){
		if(url.indexOf("?")>=0){
			var newUrl = url+"&" + RESPONSE_DATA_TYPE_PARAMNAME + "=" + RESPONSE_DATA_TYPE_JSON ;
			return newUrl;
		}
		else{
			var newUrl = url+"?" + RESPONSE_DATA_TYPE_PARAMNAME + "=" + RESPONSE_DATA_TYPE_JSON;
			return newUrl ;
		}
	}
}

/**
 * 重新登录系统
 */
function reLogin(){
	window.parent.location.href = GLOBLE_REQUEST_URL ;
}

/**
 * 执行数据查询AJAX封装，统一返回信息处理
 * @param url 请求路径
 * @param param 请求参数 js对象{exp:exp}
 * @param success 成功回调方法息
 * @param error 失败回调方法
 */
function boDataLoader(url, param, success, error){
  $.ajax({
    type:"POST",
    url: ajaxJsonUrl(url),
    dataType: 'json',
    data: param,
    success: function(data){
      if(RESPONSE_DATA_FLAG_SUCCESS == data.status){
    	  if(jQuery.isFunction(success)){
    		  success(data.retData); 
		  }
      }else if(RESPONSE_DATA_FLAG_ERROR == data.status){
    	  msg_warning(GLOBLE_STR_MSGTITLE_WARNING ,data.message, function(){
    		  if(jQuery.isFunction(error)){
    			  error(); 
    		  }
    	  }) ;
      }
      else if(RESPONSE_DATA_FLAG_NOLOGIN == data.status){
    	  msg_info(GLOBLE_STR_MSGTITLE_INFO ,data.message ,function(){reLogin()}) ;
      }
      else if(RESPONSE_DATA_FLAG_NOAUTH == data.status){
    	  msg_warning(GLOBLE_STR_MSGTITLE_WARNING ,data.message, function(){
    		  if(jQuery.isFunction(error)){
    			  error(); 
    		  }
    	  }) ;
      }
    },
    error: function(){ msg_error(GLOBLE_STR_MSGTITLE_ERROR ,GLOBLE_MSG_EXCEPTION_UNKNOW) ; }
  });
  }

/**
 * 执行数据查询AJAX封装，统一返回信息处理，解析手持接口数据用
 * @param url 请求路径
 * @param param 请求参数 js对象{exp:exp}
 * @param success 成功回调方法息
 * @param error 失败回调方法
 */
function boDataLoaderOprt(url, param, success, error){
  $.ajax({
    type:"POST",
    url: ajaxJsonUrl(url),
    dataType: 'json',
    data: param,
    success: function(data){
      if("OK" == data.rts){
    	  if(jQuery.isFunction(success)){
    		  success(data.rslist); 
		  }
      }else if("NG" == data.rts){
    	  msg_warning(GLOBLE_STR_MSGTITLE_WARNING ,data.errMsg, function(){
    		  if(jQuery.isFunction(error)){
    			  error(); 
    		  }
    	  }) ;
      }
    },
    error: function(){ msg_error(GLOBLE_STR_MSGTITLE_ERROR ,GLOBLE_MSG_EXCEPTION_UNKNOW) ; }
  });
  }


//判断变量是否为空
function isNullOrEmpty(target) {
  var isNull = false;
  if (typeof (target) == 'undefined' || target == null || target == "" && target!= '0') {
    isNull = true;
  }
  return isNull;
}


//文件上传
//function uploadFile(fileId, fun){
//  var filePath = $('#'+fileId).val();
//  if(isNullOrEmpty(filePath)){ 
//    $.messager.alert('文件上传', "请选择需要上传的文件！", 'error');
//    return;
//  }
//  // 验证文件大小
//  var fileSize = $("#"+fileId)[0].files[0].size;
//  if((fileSize/1024)>(2*1024)){
//    $.messager.alert('文件上传', "文件大小不能超过2M！", 'error');
//    return;
//  }
//
//  $.ajaxFileUpload({
//    url: ctx + '/file/upload',
//    secureuri:false,
//    fileElementId: fileId,
//    dataType: 'text',
//    success: function (data, status){
//      if(jQuery.isFunction(fun)){
//        // 将String类型转换为JSON
//        var temp = JSONUtil.decode(data);// 文件名会乱码，所以再这里再转换
//        temp.fileName = decodeURI(temp.fileName);
//        fun( temp );
//      }
//    },
//    error: function (data, status, e) { return undefined; }
//  });
// return undefined;
//}

//文件上传 李帅后改的 好用的方法
function uploadFile(url, fileId, mFlg, callFun){
	
	 var filePath = $('#'+fileId).val();
	  if(isNullOrEmpty(filePath)){ 
	    $.messager.alert('文件上传', "请选择需要上传的文件！", 'error');
	    return;
	  }
	  // 验证文件大小
	  var fileSize = $("#"+fileId)[0].files[0].size;
	  if((fileSize/1024)>(2*1024)){
	    $.messager.alert('文件上传', "文件大小不能超过2M！", 'error');
	    return;
	  }
	    
	    $.ajaxFileUpload({
		    url: url,
		    secureuri:false,
		    fileElementId: fileId,
		    dataType: 'json',
		    success: function (data){
				if(RESPONSE_DATA_FLAG_SUCCESS == data.status){
					if(mFlg) {
						msg_info(GLOBLE_STR_MSGTITLE_INFO ,data.message ,function(){
							if(jQuery.isFunction(callFun)){
								callFun(data.retData); 
							}
						}) ;
					}else{
						if(jQuery.isFunction(callFun)){
							callFun(data.retData);
						}
					}
				}else if(RESPONSE_DATA_FLAG_ERROR == data.status){
					msg_error(GLOBLE_STR_MSGTITLE_ERROR ,data.message) ;
				}
				else if(RESPONSE_DATA_FLAG_VFAILED == data.status){
					//$.messager.alert('Warning11', data.message);
					//form validate info will be enhanced
					msg_warning(GLOBLE_STR_MSGTITLE_WARNING ,data.message) ;
				}
				else if(RESPONSE_DATA_FLAG_NOLOGIN == data.status){
					msg_info(GLOBLE_STR_MSGTITLE_INFO ,data.message ,function(){reLogin()}) ;
				}
				else if(RESPONSE_DATA_FLAG_NOAUTH == data.status){
					msg_warning(GLOBLE_STR_MSGTITLE_WARNING ,data.message) ;
				}
		    },
		    error: function (data, status, e) { return undefined; }
		  });
}




//下载方法，ajax无法处理流，所以采用iframe方式
function downLoadFile(ctx, fileNo){
  var params = {};
  // 数据封装
  params.fileName = "ssssssssssss";
  params.filePath = "dddddddddddd";
  params.fileSize = "444444";
  var ifameObj = $("<iframe/>");
  var src = ctx + "/file/downLoad";
  $(document.body).append(ifameObj);
  ifameObj.attr("src",src);
  ifameObj.hide();
}

/**
 * 弹出消息 alert
 * @title 消息对话框标题
 * @msg 弹出的消息 
 * @callback 消息弹出后，点击确定按钮时的回调函数
 */
function msg_alert(title, msg, callback){
	$.messager.alert(title, msg, '' ,callback);
}
/**
 * 弹出消息 error
 * @title 消息对话框标题
 * @msg 弹出的消息 
 * @callback 消息弹出后，点击确定按钮时的回调函数
 */
function msg_error(title, msg, callback){
	$.messager.alert(title, msg, 'error' ,callback);
}
/**
 * 弹出消息 question
 * @title 消息对话框标题
 * @msg 弹出的消息 
 * @callback 消息弹出后，点击确定按钮时的回调函数
 */
function msg_question(title, msg, callback){
	$.messager.alert(title, msg, 'question' ,callback);
}
/**
 * 弹出消息 info
 * @title 消息对话框标题
 * @msg 弹出的消息 
 * @callback 消息弹出后，点击确定按钮时的回调函数
 */
function msg_info(title, msg, callback){
	$.messager.alert(title, msg, 'info' ,callback);
}
/**
 * 弹出消息 warning
 * @title 消息对话框标题
 * @msg 弹出的消息 
 * @callback 消息弹出后，点击确定按钮时的回调函数
 */
function msg_warning(title, msg, callback){
	$.messager.alert(title, msg, 'warning' ,callback);
}
/**
 * 弹出消息 confirm
 * @title 确认对话框标题
 * @msg 弹出的消息 
 * @callback 点击确定按钮时的回调函数
 * @callbackForCancle 点击取消按钮时的回调函数
 */
function msg_confirm(title, msg, callback, callbackForCancle){
	$.messager.confirm(title, msg, function(r){
		if (r){
			if(jQuery.isFunction(callback)){
				callback() ;
			}
		}
		else{
			if(jQuery.isFunction(callbackForCancle)){
				callbackForCancle() ;
			}
		}
	});
}
/**
 * 弹出提示输入对话框 prompt
 * @title 消息对话框标题
 * @msg 弹出的消息 
 * @callback 消息弹出后，点击确定按钮时的回调函数
 */
function msg_prompt(title, msg, callback){
	$.messager.prompt(title, msg, function(inputMsg){
		if (inputMsg){
			if(jQuery.isFunction(callback)){
				callback(inputMsg) ;
			}
		}
	});
}

/**
 * 格式化js 日期对象
 * @param date
 * @returns {String}
 */
function dateFormatter(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}

/**
 * 格式化js 日期对象
 * @param date
 * @returns {String}
 */
function formatDatebox(value){
	if (value == null || value == '') {  
        return '';  
    }  
    var dt;  
    if (value instanceof Date) {  
        dt = value;  
    } else {
    	
        dt = new Date(value.replace(/-/g,"/"));  
    } 
	var y = dt.getFullYear();
	var m = dt.getMonth()+1;
	var d = dt.getDate();
	return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}

/**
 *获取字符串字节数
 */
function getStrByteLen(val){
 
     var chLen=0;// 全角
     var enLen=0;// 半角
     
     if (val != "") {
	     for(var i=0;i<val.length;i++){
	        if(val.substring(i, i + 1).match(/[^\x00-\xff]/ig) != null)
	        	chLen += 1;
	        else
	        	enLen += 1;
	     }
     }
    // 返回当前字符串字节长度
    return (chLen * 2) + enLen;
}

/**
 * 校验remote模式的下拉框是否有选择值，如果没有，清空输入内容，提示选择。
 * remote模式下，如果输入的内容没有匹配结果，或者有匹配结果但是没有选择时，控件getValue的值就是输入的内容，无法判断控件是否进行选择了，故使用此方法进行再次判断。
 * @param combboxObj easyui的下拉框控件对象
 * @param valueFieldName easyui下拉框对象的value字段名
 */
function chekcCombboxSelected(combboxObj ,valueFieldName ){
	if(!combboxObj){
		return false ;
	}
	if(!valueFieldName){
		return false ;
	}
	var dts = combboxObj.combobox('getData') ;
	if(!dts){
		return false ;
	}
	if(!dts.length){
		return false ;
	}
	var v = combboxObj.combobox('getValue') ;
	if(!v){
		return false ;
	}
	for(var i=0;i<dts.length;i++){
		var dt = dts[i] ;
		if(v == dt[valueFieldName]){
			return true ;
		}
	}
	return false ;
}

/**
 * 判断指定参数x是否是整数
 * @param x
 * @returns {Boolean}
 */
function isInteger(x) {
	var reg = /^(-|\+)?\d+$/ ;
	return reg.test(x);
}


/******************************画面调用共通方法***********************************/

function doMoreOrLess(obj,queryDivId,tableDivId,hgtM,hgtL){
	if(!hgtL){
		hgtL = 30;
	}
	var hgt = hgtL;
	if($(obj).linkbutton('options').iconCls == 'icon-down'){
		hgt = hgtM;
		$(obj).linkbutton({
		    iconCls: 'icon-up',
		    text:'<span style="color:#607495 ">收起</span>'
		});
	}else{
		hgt = hgtL;
		$(obj).linkbutton({
		    iconCls: 'icon-down',
		    text:'<span style="color:#607495 ">更多</span>'
		});
	}
	$('#'+queryDivId).panel('resize',{
		height: hgt
	});
	$('#'+tableDivId).panel('resize', {
		top : hgt,
		height:$(window).height() - hgt
	});
}


/**
 * 产品Loader函数
 * @param param 查询内容
 * @param success
 * @param error
 */
var pLoader = function(param, success, error){
    var q = param.q || '';
    if (q.length <= 1){ return false; }
    $.ajax({
      type:"POST",
      url: ctx+'/utils/getAutoPList',
      dataType: 'json',
      data: param,
      success: function(data){
//        var items = $.map(data, function(item){
//        return { deptno: item.deptno, label: item.label };
//      });
        success(data);
      },
      error: function(){ alert("未能正确获取产品联想数据，请联系管理员!"); }
    });
};


/**
 * 产品初始化函数
 * @param id  对象ID名
 * @param width 宽度
 * @param requ 必填boolean
 * @param initVal 初始Val
 * @param initTxt 初始Text
 */
function initP(id, width, requ, initVal, initTxt){
    $('#'+id).combobox({
      valueField: 'value',
      textField: 'display',
      width: width,
      loader: pLoader,
      mode: 'remote',
      required: requ,
      onHidePanel: function(){ // 必选处理
        if($(this).combobox('getText') == $(this).combobox('getValue')){
          $(this).combobox('clear');
        }
      },
      onBeforeLoad: function(param){
          var inputtext = $(this).combobox('getText');
          if(inputtext){
              param.pnameLike = inputtext;
          }
      }
    });
    // 初始值设定
    if(!isNullOrEmpty(initVal)){ $('#'+id).combobox('setValue', initVal ); }
    if(!isNullOrEmpty(initTxt)){ $('#'+id).combobox('setText', initTxt ); }
}

/**
 * 仓库Loader函数
 * @param param 查询内容
 * @param success
 * @param error
 */
var whLoader = function(param, success, error){
    var q = param.q || '';
    if (q.length <= 1){ return false; }
    $.ajax({
      type:"POST",
      url: ctx+'/utils/getAuthorizedWhList',
      dataType: 'json',
      data: param,
      success: function(data){

        success(data);
      },
      error: function(){ alert("未能正确获取仓库联想数据，请联系管理员!"); }
    });
};

/**
 * 仓库联想初始化函数
 * @param id  对象ID名
 * @param requ 必填boolean
 * @param initVal 初始Val
 * @param initTxt 初始Text
 */
function initWh(id, requ, initVal, initTxt){
    $('#'+id).combobox({
      valueField: 'id',
      textField: 'whName',
      mode: 'remote',
      loader: whLoader,
      required: requ,
      onHidePanel: function(){ // 必选处理
        if($(this).combobox('getText') == $(this).combobox('getValue')){
          $(this).combobox('clear');
        }
      }
    });
    
    // 初始值设定
    if(!isNullOrEmpty(initVal)){  $('#'+id).combobox('setValue', initVal ); }
    if(!isNullOrEmpty(initTxt)){  $('#'+id).combobox('setText', initTxt ); }
    
};

/**
 * 委托商Loader函数
 * @param param 查询内容
 * @param success
 * @param error
 */
var csgLoader = function(param, success, error){
    var q = param.q || '';
    if (q.length <= 1){ return false; }
    $.ajax({
      type:"POST",
      url: ctx+'/utils/getAutoConsignorList',
      dataType: 'json',
      data: param,
      success: function(data){

        success(data);
      },
      error: function(){ alert("未能正确获取委托商联想数据，请联系管理员!"); }
    });
};

/**
 * 委托商货主联动初始化函数
 * @param id  对象ID名
 * @param requ 必填boolean
 * @param initVal 初始Val
 * @param initTxt 初始Text
 */
function initCsg(id, id1, requ, initVal, initTxt){
    $('#'+id).combobox({
      valueField: 'id',
      textField: 'csgName',
      mode: 'remote',
      loader: csgLoader,
      required: requ,
      onHidePanel: function(){ // 必选处理
        if($(this).combobox('getText') == $(this).combobox('getValue')){
          $(this).combobox('clear');
        }
      },
      onSelect: function (roc) {
		$('#'+id1).combobox('clear');
		$('#'+id1).combobox('enable'); 
	    $('#'+id1).combobox({
          valueField: 'id',
          textField: 'label',
          multiple:false,
          loader:ownerLoader,
          mode: 'remote',
          required: requ,
          onHidePanel: function(){ // 必选处理
        	              if($(this).combobox('getText') == $(this).combobox('getValue')){
        	                $(this).combobox('clear');
        	              }
        	           },
         onBeforeLoad: function(param){ // 取得选中的委托商ID
        	    	 		param.csgId = $('#'+id).combobox('getValue');
        	           }
         });
      }	
  });
    
    // 初始值设定
    if(!isNullOrEmpty(initVal)){  $('#'+id).combobox('setValue', initVal ); }
    if(!isNullOrEmpty(initTxt)){  $('#'+id).combobox('setText', initTxt ); }
    
};


/**
 * 货主Loader函数
 * @param param 查询内容
 * @param success
 * @param error
 */
var ownerLoader = function(param, success, error){
    $.ajax({
      type:"POST",
      url: ctx+'/utils/getAuthorizedOwner',
      dataType: 'json',
      data: param,
      success: function(data){

        success(data);
      },
      error: function(){ alert("未能正确获取货主联想数据，请联系管理员!"); }
    });
};

/**
 * 仓库盘点选项
 * @param id  对象ID名
 * @param width 宽度
 * @param requ 必填boolean
 * @param enable disabled-boolean
 * @param initVal 初始Val
 * @param initTxt 初始Text
 */
function initPandianMode(id, width, requ, enable, initVal, initTxt){
	
	var chkValue='';
	
    $('#'+id).combobox({
      valueField: 'value',
      textField: 'display',
      width: width,
      panelHeight:'300px',
      multiple:true,
      editable:false,
//      mode: 'remote',
      loader: pandianModeLoader,
      required: requ,
      onHidePanel: function(){ // 必选处理
        if($(this).combobox('getText') == $(this).combobox('getValue')){
          $(this).combobox('clear');
        }
      },
      formatter: function (row) {
          var opts = $(this).combobox('options');
          var chkflg = false;
          for (var i = 0; i < 5; i++) {
        	  if(initVal.charAt(i)=='1' && row[opts.valueField].charAt(i)=='1'){
        		  chkflg = true;
        	  }
          }
          if(chkflg){
			  if (chkValue == '') {
        		  chkValue = row[opts.valueField];
        	  }else{
        		  chkValue = chkValue + "," + row[opts.valueField];
        	  }
        	  return '<input type="checkbox" class="combobox-checkbox" checked="checked">' + row[opts.textField];  
          }else{
        	  return '<input type="checkbox" class="combobox-checkbox">' + row[opts.textField];
          }
      },
      onSelect: function (row) {
          var opts = $(this).combobox('options');
          var el = opts.finder.getEl(this, row[opts.valueField]);
          el.find('input.combobox-checkbox')._propAttr('checked', true);
      },
      onUnselect: function (row) {
          var opts = $(this).combobox('options');
          var el = opts.finder.getEl(this, row[opts.valueField]);
          el.find('input.combobox-checkbox')._propAttr('checked', false);
      }
  });
    
    // 初始值设定
    if(!isNullOrEmpty(chkValue)){  $('#'+id).combobox('setValues', chkValue.split(",") ); }
    
};

/**
 * 取得仓库盘点选项名称
 * @param value  DB中的PandianMode值
 */
function getPandianModeLabel(value) {
	var retStr = '';
	var pandianMode = '';
	for (var i = 0; i < 5; i++) {
		if (value.charAt(i) == '1') {
			if (i == 0) {
				pandianMode = 'WSD待入仓的收货单数';
//			} else if (i == 1) {
//				pandianMode = '销售退货未被核销的单子数';
			} else if (i == 1) {
				pandianMode = '待上架的商品,遗漏上架的商品数';
			} else if (i == 2) {
				pandianMode = '待启动拣货波次的单子数';
			} else if (i == 3) {
				pandianMode = '正在拣货的单子数';
			} else if (i == 4) {
				pandianMode = '拣货拣完了,但是没点送货数';
			}
			// 拼接显示的文字
			if (retStr == '') {
				retStr = pandianMode;
			} else {
				retStr = retStr + '/' + pandianMode;
			}
		}
	}
	return retStr;
}

/**
 * 仓库盘点选项Loader函数
 * 
 * @param param
 *            查询内容
 * @param success
 * @param error
 */
var pandianModeLoader = function(param, success, error){
//    var q = param.q || '';
//    if (q.length <= 1){ return false; }
    $.ajax({
      type:"POST",
      url: ctx+'/utils/getPandianModeList',
      dataType: 'json',
      data: param,
      success: function(data){
        success(data);
      },
      error: function(){ alert("未能正确获取仓库盘点选项数据，请联系管理员!"); }
    });
};
/**
 * 盘点表管理用商品联想
 * @param id
 * @param width
 * @param requ
 * @param initVal
 * @param initTxt
 * @param pdplan
 */
function initPDP(id, width, requ, initVal, initTxt, pdplanId){
    $('#'+id).combobox({
      valueField: 'value',
      textField: 'display',
      width: width,
      loader: pLoader,
      mode: 'remote',
      required: requ,
      onHidePanel: function(){ // 必选处理
        if($(this).combobox('getText') == $(this).combobox('getValue')){
          $(this).combobox('clear');
        }
      },
      onBeforeLoad: function(param,pdplan){
          var inputtext = $(this).combobox('getText');
          if(inputtext){
              param.pnameLike = inputtext;
          }
          
          param.pdPlanId =   $('#'+pdplanId).val();
      }
    });
    // 初始值设定
    if(!isNullOrEmpty(initVal)){ $('#'+id).combobox('setValue', initVal ); }
    if(!isNullOrEmpty(initTxt)){ $('#'+id).combobox('setText', initTxt ); }
}


/**
 * 判断是否为空
 */
function isNullOrEmpty(target) {
  var isNull = false;
  if (typeof (target) == 'undefined' || target == null || target == "" && target!= '0') {
    isNull = true;
  }
  return isNull;
}


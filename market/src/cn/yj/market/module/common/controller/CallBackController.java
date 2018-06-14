package cn.yj.market.module.common.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.yj.market.frame.bean.ResponseJsonData;
import cn.yj.market.frame.controller.BaseController;
import cn.yj.market.frame.vo.MarketCallBack;
import cn.yj.market.module.common.bo.CallbackBO;

import com.alibaba.fastjson.JSONObject;


@Controller
@RequestMapping("/callBack")
public class CallBackController extends BaseController {
	
	@Autowired
	private CallbackBO callbackBO;
	
	
	@RequestMapping(value = "/getCallBackList", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public JSONObject getCallBackList() {
		ResponseJsonData response = new ResponseJsonData() ;
		List<MarketCallBack> callBacks = callbackBO.getCallBackList() ;
		if (callBacks == null) {
			callBacks = new ArrayList<MarketCallBack>() ;
		}
		response.pagedGridData(callBacks.size(), callBacks);
		return response.getResult() ;
	}
	
}

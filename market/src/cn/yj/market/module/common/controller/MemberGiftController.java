package cn.yj.market.module.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.yj.market.frame.bean.ResponseJsonData;
import cn.yj.market.frame.controller.BaseController;
import cn.yj.market.frame.page.Page;
import cn.yj.market.frame.util.SessionUtil;
import cn.yj.market.frame.vo.MarketOrder;
import cn.yj.market.module.common.bean.MemberGiftCheckSearchCondition;
import cn.yj.market.module.common.bo.OrderBO;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/memberGift")
public class MemberGiftController extends BaseController {
	
	@Autowired
	private OrderBO orderBO;
	
	/**
     * index 页
     * 
     * @return
     */
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView index() {
        return new ModelAndView("memberGift/memberGift_index");
    }
    
	@RequestMapping(value = "/getMemberGiftCheckPageList", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public JSONObject getMemberGiftCheckPageList(MemberGiftCheckSearchCondition condition) {
		
		ResponseJsonData response = new ResponseJsonData() ;
		Page<MarketOrder> memberPage = null;
		try {
			memberPage = orderBO.getMemberGiftCheckPageList(condition, SessionUtil.getPageRequestParams()) ;
		} catch (Exception e) {
			response.setStatus(ERROR);
			response.setMessage("系统错误，无法查询会员等值商品累积兑换信息！");
		}
		if (memberPage != null) {
			response.pagedGridData(memberPage.getTotal(), memberPage.getRecords());
		}
		else {
			response.pagedGridData(0, null);
		}
		return response.getResult() ;
	}
	
}

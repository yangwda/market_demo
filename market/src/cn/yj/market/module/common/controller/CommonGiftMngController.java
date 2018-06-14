package cn.yj.market.module.common.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.yj.market.frame.bean.ResponseJsonData;
import cn.yj.market.frame.controller.BaseController;
import cn.yj.market.frame.util.CoreUtils;
import cn.yj.market.frame.vo.MarketCommonGift;
import cn.yj.market.module.common.bo.CommonGiftBO;

import com.alibaba.fastjson.JSONObject;


@Controller
@RequestMapping("/commonGift")
public class CommonGiftMngController extends BaseController {
	
	@Autowired
	private CommonGiftBO commonGiftBO;
	
	/**
     * index 页
     * 
     * @return
     */
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView index() {
        return new ModelAndView("gift/commongift_index");
    }
    
	@RequestMapping(value = "/getCommonGiftPageList", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public JSONObject getCommonGiftPageList() {
		
		ResponseJsonData response = new ResponseJsonData() ;
		List<MarketCommonGift> illnessPage = null;
		try {
			illnessPage = commonGiftBO.getAll() ;
		} catch (Exception e) {
			response.setStatus(ERROR);
			response.setMessage("系统错误，无法查询会员信息！");
		}
		if (illnessPage != null) {
			response.pagedGridData(illnessPage.size(), illnessPage);
		}
		else {
			response.pagedGridData(0, null);
		}
		return response.getResult() ;
	}
	
	@Override
	@RequestMapping(value = "/loadContent", method = { RequestMethod.GET,
			RequestMethod.POST })
	protected ModelAndView loadContent(HttpServletRequest request, String pn) {
		return super.loadContent(request, pn);
	}
	
	@RequestMapping(value = "/saveCommonGiftInfo", method = {RequestMethod.GET,
	        RequestMethod.POST})
	@ResponseBody
	public JSONObject saveCommonGiftInfo(MarketCommonGift commonGift) {
		if (commonGift == null) {
            return ResponseJsonData.responseError("系统错误，无法惠赠商品信息！");
        }
		if (StringUtils.isBlank(commonGift.getCommonGiftName())) {
			return ResponseJsonData.responseError("请填写惠赠商品名称！") ;
		}
		if (StringUtils.isBlank(commonGift.getCommonGiftUnit())) {
			return ResponseJsonData.responseError("请填写惠赠商品包装规格！") ;
		}
		
		try {
    		commonGiftBO.saveCommonGift(commonGift);
		} catch (Exception e) {
			return ResponseJsonData.responseError("系统错误，无法保存惠赠商品信息！") ;
		}
		
        return ResponseJsonData.responseSuccess("惠赠商品信息保存成功！") ;
	}
	
	@RequestMapping(value = "/deleteCommonGiftInfo", method = {RequestMethod.GET,
			RequestMethod.POST})
	@ResponseBody
	public JSONObject deleteCommonGiftInfo(String commonGiftId) {
		if (!CoreUtils.isLong(commonGiftId)) {
			return ResponseJsonData.responseError("系统错误，惠赠商品信息无效！");
		}
		
		try {
			commonGiftBO.deleteCommonGifg(Long.valueOf(commonGiftId));
		} catch (Exception e) {
			return ResponseJsonData.responseError("系统错误，无法删除惠赠商品信息！") ;
		}
		
		return ResponseJsonData.responseSuccess("惠赠商品信息删除成功！") ;
	}
}

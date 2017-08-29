package cn.yj.market.module.common.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.yj.market.frame.bean.ResponseJsonData;
import cn.yj.market.frame.controller.BaseController;
import cn.yj.market.frame.exception.RunException;
import cn.yj.market.frame.util.CoreUtils;
import cn.yj.market.frame.vo.MarketOnceBuy;
import cn.yj.market.module.common.bo.OncebuyBO;

import com.alibaba.fastjson.JSONObject;


@Controller
@RequestMapping("/oncebuy")
public class OnceBuyController extends BaseController {
	
	@Autowired
	private OncebuyBO oncebuyBO;
	
	/**
     * index 页
     * 
     * @return
     */
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView index() {
        return new ModelAndView("oncebuy/oncebuy_index");
    }
    
	@RequestMapping(value = "/getOncebuyPageList", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public JSONObject getOncebuyPageList() {
		
		ResponseJsonData response = new ResponseJsonData() ;
		List<MarketOnceBuy> oncebuyPage = null;
		try {
			oncebuyPage = oncebuyBO.getList() ;
		} catch (Exception e) {
			response.setStatus(ERROR);
			response.setMessage("系统错误，无法查询单次购买优惠信息！");
		}
		if (oncebuyPage != null) {
			response.pagedGridData(oncebuyPage.size(), oncebuyPage);
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
	
	@RequestMapping(value = "/saveOncebuyInfo", method = {RequestMethod.GET,
	        RequestMethod.POST})
	@ResponseBody
	public JSONObject saveOncebuyInfo(String onceById ,String beginAmount , String endAmount, String perRate) {
		if (!NumberUtils.isNumber(beginAmount)) {
            return ResponseJsonData.responseError("购买额度起始值无效！");
        }
		if (!NumberUtils.isNumber(endAmount)) {
			return ResponseJsonData.responseError("购买额度结束值无效！");
		}
		
		if (!CoreUtils.isLong(perRate)) {
			return ResponseJsonData.responseError("优惠力度无效！") ;
		}
		
		MarketOnceBuy oncebuy = new MarketOnceBuy() ;
		oncebuy.setBeginAmount(new BigDecimal(beginAmount));
		oncebuy.setEndAmount(new BigDecimal(endAmount));
		oncebuy.setPerRate(Long.valueOf(perRate));
		
        if (CoreUtils.isLong(onceById)) {
        	oncebuy.setOnceById(Long.valueOf(onceById));
        	try {
        		oncebuyBO.updateOncebuy(oncebuy);
			} 
        	catch (RunException ex){
        		return ResponseJsonData.responseError(ex.getMessage()) ;
        	}
        	catch (Exception e) {
				return ResponseJsonData.responseError("系统错误，无法保存单次购买优惠信息！") ;
			}
        }
        else {
        	try {
        		oncebuyBO.saveOncebuy(oncebuy);
			} 
        	catch (RunException ex){
        		return ResponseJsonData.responseError(ex.getMessage()) ;
        	}
        	catch (Exception e) {
				return ResponseJsonData.responseError("系统错误，无法保存单次购买优惠信息！") ;
			}
		}
        return ResponseJsonData.responseSuccess("单次购买优惠信息保存成功！") ;
	}
}

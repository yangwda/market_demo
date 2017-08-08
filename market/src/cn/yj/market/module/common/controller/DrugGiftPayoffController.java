package cn.yj.market.module.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.yj.market.frame.bean.ResponseJsonData;
import cn.yj.market.frame.controller.BaseController;
import cn.yj.market.frame.page.Page;
import cn.yj.market.frame.util.CoreUtils;
import cn.yj.market.frame.util.SessionUtil;
import cn.yj.market.frame.vo.MarketGiftConfig;
import cn.yj.market.frame.vo.MarketGiftConfigLine;
import cn.yj.market.module.common.bean.GiftConfigFormBean;
import cn.yj.market.module.common.bean.GiftConfigSearchCondition;
import cn.yj.market.module.common.bo.GiftConfigBO;

import com.alibaba.fastjson.JSONObject;


@Controller
@RequestMapping("/drugGift")
public class DrugGiftPayoffController extends BaseController {
	
	@Autowired
	private GiftConfigBO giftConfigBO;
	
	/**
     * index 页
     * 
     * @return
     */
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView index() {
        return new ModelAndView("gift/drug_gift_payoff_index");
    }
    
	@RequestMapping(value = "/getDrugGiftPageList", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public JSONObject getDrugGiftPageList(GiftConfigSearchCondition condition) {
		
		ResponseJsonData response = new ResponseJsonData() ;
		Page<MarketGiftConfig> goodsPage = null;
		try {
			goodsPage = giftConfigBO.getPage(condition, SessionUtil.getPageRequestParams()) ;
		} catch (Exception e) {
			response.setStatus(ERROR);
			response.setMessage("系统错误，无法查询赠品信息！");
		}
		if (goodsPage != null) {
			response.pagedGridData(goodsPage.getTotal(), goodsPage.getRecords());
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
	
	@RequestMapping(value = "/saveDrugGiftPayoff", method = {RequestMethod.GET,
	        RequestMethod.POST})
	@ResponseBody
	public JSONObject saveDrugGiftPayoff(GiftConfigFormBean giftConfigForm) {
		if (giftConfigForm == null) {
            return ResponseJsonData.responseError("系统错误，无法获取药品赠品活动信息！");
        }
		//config
		if (StringUtils.isBlank(giftConfigForm.getGoodsName())) {
			return ResponseJsonData.responseError("请填写活动药品名称！") ;
		}
		if (StringUtils.isBlank(giftConfigForm.getGoodsNo())) {
			return ResponseJsonData.responseError("请填写活动药品编码！") ;
		}
		if (StringUtils.isBlank(giftConfigForm.getGoodsId())) {
			return ResponseJsonData.responseError("请填写活动药品ID！") ;
		}
		if (StringUtils.isBlank(giftConfigForm.getGiftConfigDesc())) {
			return ResponseJsonData.responseError("请填写药品赠品活动描述！") ;
		}
		if (StringUtils.isBlank(giftConfigForm.getGiftConfigEndTime())) {
			return ResponseJsonData.responseError("请填写药品赠品活动开始时间！") ;
		}
		if (StringUtils.isBlank(giftConfigForm.getGiftConfigEndTime())) {
			return ResponseJsonData.responseError("请填写药品赠品活动结束时间！") ;
		}
		if (StringUtils.isBlank(giftConfigForm.getBuyLimit())) {
			return ResponseJsonData.responseError("请填写药品赠品活动购买金额额度！") ;
		}
		// line 
		if (StringUtils.isBlank(giftConfigForm.getGiftGoodsNo()) || StringUtils.isBlank(giftConfigForm.getGiftGoodsId())) {
			return ResponseJsonData.responseError("请填写赠品信息！") ;
		}
		if (StringUtils.isBlank(giftConfigForm.getGiftGoodsCount())) {
			return ResponseJsonData.responseError("请填写赠品数量！") ;
		}
		
		//-- TODO 校验逻辑
		// config
		if (StringUtils.length(giftConfigForm.getGiftConfigDesc()) > 200) {
			return ResponseJsonData.responseError("药品赠品活动描述过长！") ;
		}
		if (StringUtils.isNotBlank(giftConfigForm.getGiftConfigRemarks())) {
			if (StringUtils.length(giftConfigForm.getGiftConfigRemarks()) > 2000) {
				return ResponseJsonData.responseError("药品赠品活动备注过长！") ;
			}
		}
		if (!NumberUtils.isNumber(giftConfigForm.getBuyLimit())) {
			return ResponseJsonData.responseError("药品赠品活动购买金额额度无效！") ;
		}
		if (!CoreUtils.isDate(giftConfigForm.getGiftConfigBeginTime())) {
			return ResponseJsonData.responseError("药品赠品活动开始时间无效！") ;
		}
		if (!CoreUtils.isDate(giftConfigForm.getGiftConfigEndTime())) {
			return ResponseJsonData.responseError("药品赠品活动结束时间无效！") ;
		}
		
		// line 
		if (!NumberUtils.isDigits(giftConfigForm.getGiftGoodsCount())) {
			return ResponseJsonData.responseError("赠品数量无效！") ;
		}
		
		MarketGiftConfig config = new MarketGiftConfig() ;
		if (StringUtils.isNotBlank(giftConfigForm.getGiftConfigId())) {
			config.setGiftConfigId(NumberUtils.toLong(giftConfigForm.getGiftConfigId()));
		}
		config.setGiftConfigDesc(giftConfigForm.getGiftConfigDesc());
		config.setGiftConfigType("按购买金额赠");
		config.setGiftConfigBeginTime(CoreUtils.parseDate(giftConfigForm.getGiftConfigBeginTime()));
		config.setGiftConfigEndTime(CoreUtils.parseDate(giftConfigForm.getGiftConfigEndTime()));
		config.setGiftConfigRemarks(giftConfigForm.getGiftConfigRemarks());
		config.setGoodsId(NumberUtils.toLong(giftConfigForm.getGoodsId()));
		config.setGoodsNo(giftConfigForm.getGoodsNo());
		config.setGoodsName(giftConfigForm.getGoodsName());
		config.setBuyLimit(NumberUtils.createBigDecimal(giftConfigForm.getBuyLimit()));
		
		MarketGiftConfigLine line = new MarketGiftConfigLine() ;
		if (StringUtils.isNotBlank(giftConfigForm.getGiftConfigLineId())) {
			line.setGiftConfigLineId(NumberUtils.toLong(giftConfigForm.getGiftConfigLineId()));
		}
		if (StringUtils.isNotBlank(giftConfigForm.getGiftConfigId())) {
			line.setGiftConfigId(NumberUtils.toLong(giftConfigForm.getGiftConfigId()));
		}
		line.setGiftGoodsId(NumberUtils.toLong(giftConfigForm.getGiftGoodsId()));
		line.setGiftGoodsName(giftConfigForm.getGiftGoodsName());
		line.setGiftGoodsNo(giftConfigForm.getGiftGoodsNo());
		line.setGiftGoodsCount(NumberUtils.toLong(giftConfigForm.getGiftGoodsCount()));
		line.setGiftGoodsCountUnit(giftConfigForm.getGiftGoodsCountUnit());
		
		
        if (config.getGiftConfigId() != null) {
        	try {
        		giftConfigBO.updateGiftConfig(config, line);
			} catch (Exception e) {
				return ResponseJsonData.responseError("系统错误，无法保存药品赠品活动信息！") ;
			}
        }
        else {
        	try {
        		giftConfigBO.saveGiftConfig(config, line) ;
			} catch (Exception e) {
				return ResponseJsonData.responseError("系统错误，无法保存药品赠品活动信息！") ;
			}
		}
        return ResponseJsonData.responseSuccess("药品赠品活动信息保存成功！") ;
	}
}

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
import cn.yj.market.frame.vo.MarketGiftCTConfig;
import cn.yj.market.module.common.bean.GiftConfigFormBean;
import cn.yj.market.module.common.bean.GiftConfigSearchCondition;
import cn.yj.market.module.common.bo.FeedGiftConfigBO;

import com.alibaba.fastjson.JSONObject;


@Controller
@RequestMapping("/feedGift")
public class FeedGiftPayoffController extends BaseController {
	
	@Autowired
	private FeedGiftConfigBO giftConfigBO;
	
	/**
     * index 页
     * 
     * @return
     */
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView index() {
        return new ModelAndView("gift/feed_gift_payoff_index");
    }
    
	@RequestMapping(value = "/getFeedGiftPageList", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public JSONObject getFeedGiftPageList(GiftConfigSearchCondition condition) {
		
		ResponseJsonData response = new ResponseJsonData() ;
		Page<MarketGiftCTConfig> goodsPage = null;
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
	
	@RequestMapping(value = "/saveFeedGiftPayoff", method = {RequestMethod.GET,
	        RequestMethod.POST})
	@ResponseBody
	public JSONObject saveFeedGiftPayoff(GiftConfigFormBean giftConfigForm) {
		if (giftConfigForm == null) {
            return ResponseJsonData.responseError("系统错误，无法获取饲料赠品活动信息！");
        }
		//config
		if (StringUtils.isBlank(giftConfigForm.getGoodsName())) {
			return ResponseJsonData.responseError("请填写活动饲料名称！") ;
		}
		if (StringUtils.isBlank(giftConfigForm.getGoodsNo())) {
			return ResponseJsonData.responseError("请填写活动饲料编码！") ;
		}
		if (StringUtils.isBlank(giftConfigForm.getGoodsId())) {
			return ResponseJsonData.responseError("请填写活动饲料ID！") ;
		}
		if (StringUtils.isBlank(giftConfigForm.getGiftConfigDesc())) {
			return ResponseJsonData.responseError("请填写饲料赠品活动描述！") ;
		}
		if (StringUtils.isBlank(giftConfigForm.getGiftConfigEndTime())) {
			return ResponseJsonData.responseError("请填写饲料赠品活动开始时间！") ;
		}
		if (!NumberUtils.isNumber(giftConfigForm.getGiftAmount())) {
			return ResponseJsonData.responseError("等值商品金额无效！") ;
		}
		
		
		// line 
//		if (giftConfigForm.getLineStrArr() == null || giftConfigForm.getLineStrArr().length < 1) {
//			return ResponseJsonData.responseError("请填写饲料赠品优惠明细！") ;
//		}
		
		//-- TODO 校验逻辑
		// config
		if (StringUtils.length(giftConfigForm.getGiftConfigDesc()) > 200) {
			return ResponseJsonData.responseError("饲料赠品活动描述过长！") ;
		}
		if (StringUtils.isNotBlank(giftConfigForm.getGiftConfigRemarks())) {
			if (StringUtils.length(giftConfigForm.getGiftConfigRemarks()) > 2000) {
				return ResponseJsonData.responseError("饲料赠品活动备注过长！") ;
			}
		}
		if (!CoreUtils.isDate(giftConfigForm.getGiftConfigBeginTime())) {
			return ResponseJsonData.responseError("饲料赠品活动开始时间无效！") ;
		}
		if (!CoreUtils.isDate(giftConfigForm.getGiftConfigEndTime())) {
			return ResponseJsonData.responseError("饲料赠品活动结束时间无效！") ;
		}
		
		// line 
//		var ss = checkType + "@@@" + buyLimit + "@@@" + buyLimitPunit + "@@@" 
//				+ goodsId + "@@@" + goodsName + "@@@" + goodsNo + "@@@" + giftGoodsCount + "@@@" + "giftGoodsCountUnit" ;
//		List<MarketGiftCTConfigLine> lineList = new ArrayList<MarketGiftCTConfigLine>() ;
//		for (String lineStr : giftConfigForm.getLineStrArr()) {
//			String[] lineProps = lineStr.split("@@@") ;
//			if (lineProps.length != 8) {
//				return ResponseJsonData.responseError("饲料赠品优惠明细无效！") ;
//			}
//			for (String ps : lineProps) {
//				if ("NULL".equalsIgnoreCase(ps)) {
//					return ResponseJsonData.responseError("饲料赠品优惠明细无效！") ;
//				}
//			}
//			if (!NumberUtils.isNumber(lineProps[1])) {
//				return ResponseJsonData.responseError("饲料赠品优惠明细无效！") ;
//			}
//			if (!NumberUtils.isNumber(lineProps[6])) {
//				return ResponseJsonData.responseError("饲料赠品优惠明细无效！") ;
//			}
//			
//			MarketGiftCTConfigLine line = new MarketGiftCTConfigLine() ;
//			if (StringUtils.isNotBlank(giftConfigForm.getGiftConfigId())) {
//				line.setGiftConfigId(NumberUtils.toLong(giftConfigForm.getGiftConfigId()));
//			}
//			line.setCheckType(lineProps[0]);
//			line.setBuyLimit(Long.valueOf(lineProps[1]));
//			line.setBuyLimitPunit(lineProps[2]);
//			line.setGiftGoodsId(Long.valueOf(lineProps[3]));
//			line.setGiftGoodsName(lineProps[4]);
//			line.setGiftGoodsNo(lineProps[5]);
//			line.setGiftGoodsCount(Long.valueOf(lineProps[6]));
//			line.setGiftGoodsCountUnit(lineProps[7]);
//			
//			lineList.add(line) ;
//		}
		
		
		MarketGiftCTConfig config = new MarketGiftCTConfig() ;
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
		config.setGiftAmount(NumberUtils.toDouble(giftConfigForm.getGiftAmount()));
		
        if (config.getGiftConfigId() != null) {
        	try {
        		giftConfigBO.updateGiftConfig(config, null);
			} catch (Exception e) {
				return ResponseJsonData.responseError("系统错误，无法保存饲料赠品活动信息！") ;
			}
        }
        else {
        	try {
        		giftConfigBO.saveGiftConfig(config, null) ;
			} catch (Exception e) {
				return ResponseJsonData.responseError("系统错误，无法保存饲料赠品活动信息！") ;
			}
		}
        return ResponseJsonData.responseSuccess("饲料赠品活动信息保存成功！") ;
	}
}

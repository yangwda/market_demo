package cn.yj.market.module.common.controller;

import java.util.Date;

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
import cn.yj.market.frame.page.Page;
import cn.yj.market.frame.util.SessionUtil;
import cn.yj.market.frame.vo.MarketGoods;
import cn.yj.market.module.common.bean.GoodsSearchCondition;
import cn.yj.market.module.common.bo.GoodsBO;

import com.alibaba.fastjson.JSONObject;


@Controller
@RequestMapping("/gift")
public class GiftGoodsController extends BaseController {
	
	@Autowired
	private GoodsBO goodsBO;
	
	/**
     * index 页
     * 
     * @return
     */
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView index() {
        return new ModelAndView("goods/gift_goods_index");
    }
    
	@RequestMapping(value = "/getGoodsPageList", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public JSONObject getGoodsPageList(GoodsSearchCondition condition) {
		
		ResponseJsonData response = new ResponseJsonData() ;
		Page<MarketGoods> goodsPage = null;
		try {
			goodsPage = goodsBO.getPage(condition, SessionUtil.getPageRequestParams()) ;
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
	
	@RequestMapping(value = "/saveGoodsInfo", method = {RequestMethod.GET,
	        RequestMethod.POST})
	@ResponseBody
	public JSONObject saveGoodsInfo(MarketGoods goods) {
		if (goods == null) {
            return ResponseJsonData.responseError("系统错误，无法获取赠品信息！");
        }
		if (StringUtils.isBlank(goods.getGoodsName())) {
			return ResponseJsonData.responseError("请填写赠品名称！") ;
		}
		if (StringUtils.isBlank(goods.getGoodsNo())) {
			return ResponseJsonData.responseError("请填写赠品编码！") ;
		}
		if (StringUtils.isBlank(goods.getGoodsManufacturer())) {
			return ResponseJsonData.responseError("请填写赠品厂商信息！") ;
		}
		if (StringUtils.isBlank(goods.getGoodsUsage())) {
			return ResponseJsonData.responseError("请填写赠品使用说明信息！") ;
		}
//		if (StringUtils.isBlank(goods.getGoodsType())) {
//			return ResponseJsonData.responseError("请填写赠品类型信息！") ;
//		}
		if (StringUtils.isBlank(goods.getGoodsStatus())) {
			return ResponseJsonData.responseError("请填写赠品销售状态信息！") ;
		}
		
		//-- TODO 校验逻辑
		if (StringUtils.length(goods.getGoodsName()) > 200) {
			return ResponseJsonData.responseError("赠品名称过长！") ;
		}
		if (StringUtils.length(goods.getCommon()) > 2000) {
			return ResponseJsonData.responseError("其他备注过长！") ;
		}
		if (StringUtils.length(goods.getGoodsNo()) > 200) {
			return ResponseJsonData.responseError("赠品编码过长！") ;
		}
		if (StringUtils.length(goods.getGoodsManufacturer()) > 200) {
			return ResponseJsonData.responseError("赠品厂商信息过长！") ;
		}
		if (StringUtils.length(goods.getGoodsUsage()) > 2000) {
			return ResponseJsonData.responseError("赠品使用说明信息过长！") ;
		}
		if (StringUtils.length(goods.getGoodsType()) > 100) {
			return ResponseJsonData.responseError("赠品类型信息过长！") ;
		}
		if (StringUtils.length(goods.getGoodsStatus()) > 100) {
			return ResponseJsonData.responseError("赠品销售状态信息过长！") ;
		}
		if (StringUtils.length(goods.getGoodsRemark()) > 200) {
			return ResponseJsonData.responseError("赠品特征信息备注过长！") ;
		}
		
		goods.setGoodsType("赠品");
		goods.setCreateTime(new Date());
		
        if (goods.getGoodsId() != null) {
        	try {
        		goodsBO.updateGoods(goods.getGoodsId(), goods.getGoodsName(), goods.getGoodsNo(), 
        				goods.getGoodsManufacturer(), goods.getGoodsUsage(), goods.getGoodsType(), 
        				goods.getGoodsRemark(), goods.getCommon());
			} catch (Exception e) {
				return ResponseJsonData.responseError("系统错误，无法保存赠品信息！") ;
			}
        }
        else {
        	try {
        		goodsBO.saveGoods(goods);
			} catch (Exception e) {
				return ResponseJsonData.responseError("系统错误，无法保存赠品信息！") ;
			}
		}
        return ResponseJsonData.responseSuccess("赠品信息保存成功！") ;
	}
}

package cn.yj.market.module.common.controller;

import java.util.Date;

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
import cn.yj.market.frame.vo.MarketGoods;
import cn.yj.market.module.common.bean.GoodsSearchCondition;
import cn.yj.market.module.common.bo.GoodsBO;

import com.alibaba.fastjson.JSONObject;


@Controller
@RequestMapping("/drug")
public class DrugGoodsController extends BaseController {
	
	@Autowired
	private GoodsBO goodsBO;
	
	/**
     * index 页
     * 
     * @return
     */
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView index() {
        return new ModelAndView("goods/drug_goods_index");
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
			response.setMessage("系统错误，无法查询药品信息！");
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
            return ResponseJsonData.responseError("系统错误，无法获取药品信息！");
        }
		if (StringUtils.isBlank(goods.getGoodsName())) {
			return ResponseJsonData.responseError("请填写药品名称！") ;
		}
		if (StringUtils.isBlank(goods.getGoodsNo())) {
			return ResponseJsonData.responseError("请填写药品编码！") ;
		}
		if (StringUtils.isBlank(goods.getGoodsManufacturer())) {
			return ResponseJsonData.responseError("请填写药品厂商信息！") ;
		}
		if (StringUtils.isBlank(goods.getGoodsUsage())) {
			return ResponseJsonData.responseError("请填写药品使用说明信息！") ;
		}
//		if (StringUtils.isBlank(goods.getGoodsType())) {
//			return ResponseJsonData.responseError("请填写药品类型信息！") ;
//		}
		if (StringUtils.isBlank(goods.getGoodsStatus())) {
			return ResponseJsonData.responseError("请填写药品销售状态信息！") ;
		}
		
		//-- TODO 校验逻辑
		if (StringUtils.length(goods.getGoodsName()) > 200) {
			return ResponseJsonData.responseError("药品名称过长！") ;
		}
		if (StringUtils.length(goods.getCommon()) > 2000) {
			return ResponseJsonData.responseError("其他备注过长！") ;
		}
		if (StringUtils.length(goods.getGoodsNo()) > 200) {
			return ResponseJsonData.responseError("药品编码过长！") ;
		}
		if (StringUtils.length(goods.getGoodsManufacturer()) > 200) {
			return ResponseJsonData.responseError("药品厂商信息过长！") ;
		}
		if (StringUtils.length(goods.getGoodsUsage()) > 2000) {
			return ResponseJsonData.responseError("药品使用说明信息过长！") ;
		}
		if (StringUtils.length(goods.getGoodsType()) > 100) {
			return ResponseJsonData.responseError("药品类型信息过长！") ;
		}
		if (StringUtils.length(goods.getGoodsStatus()) > 100) {
			return ResponseJsonData.responseError("药品销售状态信息过长！") ;
		}
		if (StringUtils.length(goods.getGoodsRemark()) > 200) {
			return ResponseJsonData.responseError("药品特征信息备注过长！") ;
		}
		if (StringUtils.isNotBlank(goods.getPunit1())
				|| StringUtils.isNotBlank(goods.getPunit2())
				|| StringUtils.isNotBlank(goods.getPunit3())) {
			if (StringUtils.isBlank(goods.getPunit1())) {
				return ResponseJsonData.responseError("药品规格/单价信息填写有误！") ;
			}
			if (StringUtils.isBlank(goods.getPunit2())) {
				return ResponseJsonData.responseError("药品规格/单价信息填写有误！") ;
			}
			if (StringUtils.isBlank(goods.getPunit3())) {
				return ResponseJsonData.responseError("药品规格/单价信息填写有误！") ;
			}
			String[] a1 = goods.getPunit1().split("\\*") ;
			String[] a2 = goods.getPunit2().split("\\*") ;
			String[] a3 = goods.getPunit3().split("\\*") ;
			if (a1.length != a2.length || a2.length != a3.length) {
				return ResponseJsonData.responseError("药品规格/单价信息填写有误！") ;
			}
			for (int i = 0; i < a3.length; i++) {
				if (!CoreUtils.isInteger(a1[i])) {
					return ResponseJsonData.responseError("药品规格/单价信息填写有误！") ;
				}
				if (!NumberUtils.isNumber(a3[i])) {
					return ResponseJsonData.responseError("药品规格/单价信息填写有误！") ;
				}
			}
		}
		
		goods.setGoodsType("药品");
		goods.setCreateTime(new Date());
		
        if (goods.getGoodsId() != null) {
        	try {
        		goodsBO.updateGoods(goods.getGoodsId(), goods.getGoodsName(), goods.getGoodsNo(), 
        				goods.getGoodsManufacturer(), goods.getGoodsUsage(), goods.getGoodsType(), 
        				goods.getGoodsRemark(), goods.getCommon(), goods.getPunit1(), goods.getPunit2(), goods.getPunit3());
			} catch (Exception e) {
				return ResponseJsonData.responseError("系统错误，无法保存药品信息！") ;
			}
        }
        else {
        	try {
        		goodsBO.saveGoods(goods);
			} catch (Exception e) {
				return ResponseJsonData.responseError("系统错误，无法保存药品信息！") ;
			}
		}
        return ResponseJsonData.responseSuccess("药品信息保存成功！") ;
	}
}

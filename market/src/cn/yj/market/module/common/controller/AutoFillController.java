package cn.yj.market.module.common.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.yj.market.frame.controller.BaseController;
import cn.yj.market.frame.vo.MarketGoods;
import cn.yj.market.module.common.bean.GoodsSearchCondition;
import cn.yj.market.module.common.bo.GoodsBO;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/autofill")
public class AutoFillController extends BaseController {
	
	@Autowired 
	private GoodsBO goodsBO;
	
	@RequestMapping(value = "/getGoodsAutofill", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String getGoodsAutofill(GoodsSearchCondition condition) {
		JSONArray ja = new JSONArray() ;
		if (StringUtils.isBlank(condition.getGoodsName())) {
			return ja.toJSONString() ;
		}
		
		List<MarketGoods> goodsList = goodsBO.getGoodsAutofill(StringUtils.trim(condition.getGoodsName())) ;
		
		if (goodsList != null) {
			for (MarketGoods marketGoods : goodsList) {
				JSONObject jo = new JSONObject() ;
				jo.put("goodsName", marketGoods.getGoodsName()) ;
				jo.put("goodsNo", marketGoods.getGoodsNo()) ;
				jo.put("goodsId", marketGoods.getGoodsId()) ;
				ja.add(jo) ;
			}
		}
		
		return ja.toJSONString() ;
	}
	
	@RequestMapping(value = "/getGiftGoodsAutofill", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String getGiftGoodsAutofill(GoodsSearchCondition condition) {
		JSONArray ja = new JSONArray() ;
		if (StringUtils.isBlank(condition.getGoodsName())) {
			return ja.toJSONString() ;
		}
		
		List<MarketGoods> goodsList = goodsBO.getGiftGoodsAutofill(StringUtils.trim(condition.getGoodsName())) ;
		
		if (goodsList != null) {
			for (MarketGoods marketGoods : goodsList) {
				JSONObject jo = new JSONObject() ;
				jo.put("goodsName", marketGoods.getGoodsName()) ;
				jo.put("goodsNo", marketGoods.getGoodsNo()) ;
				jo.put("goodsId", marketGoods.getGoodsId()) ;
				jo.put("punit2", marketGoods.getPunit2()) ;
				ja.add(jo) ;
			}
		}
		
		return ja.toJSONString() ;
	}
}

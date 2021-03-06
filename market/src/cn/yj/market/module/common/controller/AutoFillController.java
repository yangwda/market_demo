package cn.yj.market.module.common.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.yj.market.frame.controller.BaseController;
import cn.yj.market.frame.page.Page;
import cn.yj.market.frame.util.CoreUtils;
import cn.yj.market.frame.util.JsonUtils;
import cn.yj.market.frame.util.SessionUtil;
import cn.yj.market.frame.vo.MarketGoods;
import cn.yj.market.frame.vo.MarketMember;
import cn.yj.market.module.common.bean.GoodsSearchCondition;
import cn.yj.market.module.common.bean.MemberSearchCondition;
import cn.yj.market.module.common.bo.GoodsBO;
import cn.yj.market.module.common.bo.MemberBO;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/autofill")
public class AutoFillController extends BaseController {
	
	@Autowired 
	private GoodsBO goodsBO;
	@Autowired
	private MemberBO memberBO;
	
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
	
	@RequestMapping(value = "/getFeedGoodsAutofill", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String getFeedGoodsAutofill(GoodsSearchCondition condition) {
		JSONArray ja = new JSONArray() ;
		if (StringUtils.isBlank(condition.getGoodsName())) {
			return ja.toJSONString() ;
		}
		
		List<MarketGoods> goodsList = goodsBO.getFeedGoodsAutofill(StringUtils.trim(condition.getGoodsName())) ;
		
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
	
	@RequestMapping(value = "/getBuyGoodsAutofill", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String getBuyGoodsAutofill(GoodsSearchCondition condition) {
		JSONArray ja = new JSONArray() ;
		if (StringUtils.isBlank(condition.getGoodsName())) {
			return ja.toJSONString() ;
		}
		
		List<MarketGoods> goodsList = goodsBO.getBuyGoodsAutofill(StringUtils.trim(condition.getGoodsName())) ;
		
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
	
	@RequestMapping(value = "/loadGoodsPunitList", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String loadGoodsPunitList(String goodsId) {
		JSONArray ja = new JSONArray() ;
		if (CoreUtils.isLong(goodsId)) {
			MarketGoods goods = goodsBO.getGoodsById(Long.valueOf(goodsId)) ;
			
			if (goods != null) {
				String punit2 = goods.getPunit2() ;
				if (StringUtils.isNotBlank(punit2)) {
					String[] pua = punit2.split("\\*") ;
					for (String pu : pua) {
						JSONObject jo = new JSONObject() ;
						jo.put("punit", pu) ;
						ja.add(jo) ;
					}
				}
			}
		}
		return ja.toJSONString() ;
	}
	@RequestMapping(value = "/getMemberInfo", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String getMemberInfo(MemberSearchCondition condition) {
		JSONArray ja = new JSONArray() ;
		if (StringUtils.isNotBlank(condition.getLikeStr())) {
			Page<MarketMember> mp = memberBO.getPage(condition, SessionUtil.getPageRequestParams()) ;
			if (mp != null) {
				List<MarketMember> ml = mp.getRecords() ;
				if (ml != null && !ml.isEmpty()) {
					for (MarketMember marketMember : ml) {
						ja.add(JsonUtils.toJsonObject(marketMember)) ;
					}
				}
			}
		}
		return ja.toJSONString() ;
	}
}

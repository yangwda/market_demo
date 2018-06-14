package cn.yj.market.module.common.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import cn.yj.market.frame.exception.RunException;
import cn.yj.market.frame.page.Page;
import cn.yj.market.frame.util.CoreUtils;
import cn.yj.market.frame.util.SessionUtil;
import cn.yj.market.frame.vo.MarketCommonGift;
import cn.yj.market.frame.vo.MarketGiftCTConfig;
import cn.yj.market.frame.vo.MarketGoods;
import cn.yj.market.frame.vo.MarketGoodsUnitPrice;
import cn.yj.market.frame.vo.MarketMember;
import cn.yj.market.frame.vo.MarketMemberGiftAccumulation;
import cn.yj.market.frame.vo.MarketMemberVoucher;
import cn.yj.market.frame.vo.MarketOnceBuy;
import cn.yj.market.frame.vo.MarketOrder;
import cn.yj.market.frame.vo.MarketOrderGiftLine;
import cn.yj.market.frame.vo.MarketOrderLine;
import cn.yj.market.frame.vo.MarketPayoff;
import cn.yj.market.module.common.bean.OrderSearchCondition;
import cn.yj.market.module.common.bo.CommonGiftBO;
import cn.yj.market.module.common.bo.FeedGiftConfigBO;
import cn.yj.market.module.common.bo.GiftConfigBO;
import cn.yj.market.module.common.bo.GoodsBO;
import cn.yj.market.module.common.bo.MemberBO;
import cn.yj.market.module.common.bo.OncebuyBO;
import cn.yj.market.module.common.bo.OrderBO;

import com.alibaba.fastjson.JSONObject;


@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {
	
	@Autowired
	private OrderBO orderBO;
	@Autowired
	private GoodsBO goodsBO;
	@Autowired
	private GiftConfigBO giftConfigBO;
	@Autowired
	private FeedGiftConfigBO feedGiftConfigBO;
	@Autowired
	private OncebuyBO oncebuyBO;
	@Autowired
	private MemberBO memberBO;
	@Autowired
	private CommonGiftBO commonGiftBO;
	/**
     * index 页
     * 
     * @return
     */
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView index() {
        return new ModelAndView("order/order_index");
    }
    
    @RequestMapping(value = "/loadBuyInfoDetail", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView loadBuyInfoDetail(String ppp) {
    	ModelAndView r = new ModelAndView("order/order_buy_detail");
    	String[] rq = ppp.split("@@@") ;
    	String memberIdStr = rq[0].substring(rq[0].indexOf("=")+1) ;
//    	BigDecimal voucher = BigDecimal.ZERO ;
    	if(CoreUtils.isLong(memberIdStr)){
    		Long memberId = Long.valueOf(memberIdStr) ;
    		//-- TODO 查询会员的累积消费消息、代金券等信息
    	}
    	List<Long> pidList = new ArrayList<Long>() ;
    	List<JSONObject> pdiList = new ArrayList<JSONObject>() ;
    	if (rq.length > 1) {
			for (int i = 1; i < rq.length; i++) {
				String row = rq[i] ;
//				System.out.println("+++++++++++row==================" + row);
				String[] ra = row.split("___") ;
				if (ra.length != 5) {
					continue ;
				}
				if (!CoreUtils.isLong(ra[0])) {
					continue ;
				}
				if (!CoreUtils.isLong(ra[1])) {
					continue ;
				}
				Long pid = Long.valueOf(ra[0]) ;
				pidList.add(pid) ;
				JSONObject p = new JSONObject() ;
				p.put("pid", pid) ;
				p.put("pct", Long.valueOf(ra[1])) ;
				p.put("pctu", ra[2]) ;
				p.put("gftcfg", ra[3]) ;
				p.put("gamid", ra[4]) ;
//				p.put("payment", "88.88");
//				p.put("pname", "剁椒鱼头一大份儿") ;
				pdiList.add(p) ;
			}
		}
    	List<MarketGoods> goodsList = goodsBO.queryGoodsList(pidList) ;
    	Map<Long, MarketGoods> goodsMap = new HashMap<Long, MarketGoods>(); 
//    	Map<Long, MarketGiftConfig> ggM = new HashMap<Long, MarketGiftConfig>() ;
    	Map<Long, MarketGiftCTConfig> ggFM = new HashMap<Long, MarketGiftCTConfig>() ;
    	List<Long> giftGoodsIdList = new ArrayList<Long>() ;
    	List<Long> giftFeedGoodsIdList = new ArrayList<Long>() ;
    	BigDecimal totalPrice = BigDecimal.ZERO ;
    	BigDecimal totalGiftAmount = BigDecimal.ZERO ;
    	Long totalBuyCount = 0L ;
    	if (goodsList != null && !goodsList.isEmpty()) {
    		for (MarketGoods goods : goodsList) {
    			goodsMap.put(goods.getGoodsId(), goods) ;
    			String gt = goods.getGoodsType() ;
    			if ("药品".equals(gt)) {
    				giftGoodsIdList.add(goods.getGoodsId()) ;
				}
    			if ("饲料".equals(gt)) {
    				giftFeedGoodsIdList.add(goods.getGoodsId()) ;
				}
			}
//    		if(!giftGoodsIdList.isEmpty()){
//    			List<MarketGiftConfig> gcl = giftConfigBO.queryGiftConfigListByGoodsIdList(giftGoodsIdList) ;
//    			if (gcl != null && !gcl.isEmpty()) {
//					for (MarketGiftConfig gc : gcl) {
//						if(ggM.get(gc.getGoodsId()) != null){
//							continue ;
//						}
//						ggM.put(gc.getGoodsId(), gc) ;
//					}
//				}
//    		}
    		if (!giftFeedGoodsIdList.isEmpty()) {
				List<MarketGiftCTConfig> gctl = feedGiftConfigBO.queryFeedGiftConfigListByGoodsIdList(giftFeedGoodsIdList) ;
				if(gctl != null && !gctl.isEmpty()){
					for (MarketGiftCTConfig gct : gctl) {
						if (ggFM.get(gct.getGoodsId()) != null) {
							continue ;
						}
						ggFM.put(gct.getGoodsId(), gct) ;
					}
				}
			}
			for (Iterator<JSONObject> iterator=pdiList.iterator();iterator.hasNext();) {
				JSONObject pi = iterator.next() ;
				Long pid = pi.getLong("pid") ;
				MarketGoods goods = goodsMap.get(pid) ;
				if (goods == null) {
					continue ;
				}
				pi.put("pname", goods.getGoodsName()) ;
				long pct = pi.getLong("pct") ;
				if(pct < 1){
					continue ;
				}
				totalBuyCount += pct ;
				String pctu = pi.getString("pctu");
				if (StringUtils.isBlank(goods.getPunit1()) || StringUtils.isBlank(goods.getPunit2()) || StringUtils.isBlank(goods.getPunit3())) {
					iterator.remove();
					continue ;
				}
				pi.put("unitPrice", goods.getPunit1() + "<br>" + goods.getPunit2() + "<br>" + goods.getPunit3()) ;
				List<MarketGoodsUnitPrice> unitList = goods.getUnitPriceSet() ;
				if (unitList == null || unitList.isEmpty()) {
					iterator.remove();
					continue ;
				}
				BigDecimal price = new BigDecimal(0) ;
				for (MarketGoodsUnitPrice up : unitList) {
					if (pctu.equals(up.getGoodsUnitName())) {
						price = up.getGoodsUnitPrice() ;
					}
				}
				BigDecimal prc = price.multiply(new BigDecimal(pct)) ;
				prc.setScale(2, BigDecimal.ROUND_HALF_UP) ;
				pi.put("payment", CoreUtils.formatMoney(prc)) ;
				totalPrice = totalPrice.add(prc) ;
//				if ("药品".equals(goods.getGoodsType())) {
//					MarketGiftConfig gc = ggM.get(goods.getGoodsId()) ;
//					if(gc != null){
//						JSONObject gift = new JSONObject() ;
//						gift.put("common", gc.giftCommonStr()) ;
//						if (gc.getLine() != null) {
//							pi.put("giftConfigLineId", gc.getLine().getGiftConfigLineId()) ;
//						}
//						else {
//							pi.put("giftConfigLineId", 0) ;
//						}
//						pi.put("gift", gift) ;
//					}
//				}
				if ("饲料".equals(goods.getGoodsType())) {
					MarketGiftCTConfig gct = ggFM.get(goods.getGoodsId());
					if(gct != null){
//						List<MarketGiftCTConfigLine> ll = gct.getLineList() ;
//						if(ll != null && !ll.isEmpty()){
//							List<JSONObject> configLine = new ArrayList<JSONObject>() ;
////							boolean ck = true;
//							long gftcfg = pi.getLong("gftcfg") ;
//							long giftConfigLineId = 0L ;
//							for (MarketGiftCTConfigLine line : ll) {
//								JSONObject gift = new JSONObject() ;
//								gift.put("nm", gct.getGoodsId()) ;
//								gift.put("vl", line.getGiftConfigLineId()) ;
//								boolean ff =(gftcfg == line.getGiftConfigLineId()) ;
//								if (ff) {
//									giftConfigLineId = line.getGiftConfigLineId() ;
//								}
//								gift.put("ck", ff ? "checked" : "") ;
//								gift.put("common", line.giftCommonStr()) ;
////								ck = false ;
//								configLine.add(gift) ;
//							}
//							if (giftConfigLineId == 0) {
//								JSONObject gift = configLine.get(0) ;
//								gift.put("ck", "checked") ;
//								giftConfigLineId = ll.get(0).getGiftConfigLineId() ;
//							}
//							JSONObject giftL = new JSONObject() ;
//							giftL.put("configLine", configLine) ;
//							pi.put("gift", giftL) ;
//							pi.put("giftConfigLineId", giftConfigLineId) ;
//						}
						if ("桶".endsWith(pctu)) {
							BigDecimal giftAmount = new BigDecimal(gct.getGiftAmount()).multiply(new BigDecimal(pct)) ;
							pi.put("giftAmount", CoreUtils.formatMoney(giftAmount)) ;
							totalGiftAmount = totalGiftAmount.add(giftAmount) ;
						}
						else {
							pi.put("giftAmount", "0.00") ;
						}
					}
				}
				if (pi.get("giftConfigLineId") == null) {
					pi.put("giftConfigLineId", 0L) ;
				}
			}
		}
    	
    	totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
//    	voucher.setScale(2, BigDecimal.ROUND_HALF_UP) ;
//    	BigDecimal pay = totalPrice.subtract(voucher) ;
//    	if (pay.compareTo(BigDecimal.ZERO) < 0) {
//    		pay = BigDecimal.ZERO ;
//		}
//    	pay.setScale(2, BigDecimal.ROUND_HALF_UP) ;
    	r.addObject("totalPayment", CoreUtils.formatMoney(totalPrice)) ;
//    	r.addObject("voucher", CoreUtils.formatMoney(voucher)) ;
//    	r.addObject("pay", CoreUtils.formatMoney(pay)) ;
    	r.addObject("totalGiftAmount", CoreUtils.formatMoney(totalGiftAmount)) ;
    	r.addObject("totalBuyCount", totalBuyCount) ;
    	r.addObject("pdiList", pdiList) ;
    	List<MarketOnceBuy> onceBuyList = oncebuyBO.getList() ;
    	r.addObject("onceBuyList", onceBuyList) ;
    	return r ;
    }
    
    @RequestMapping(value = "/editOrder", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView editOrder(String orderId) {
    	if (!CoreUtils.isLong(orderId)) {
			return orderNew(null) ;
		}
    	MarketOrder order = orderBO.getByOrderId(Long.valueOf(orderId)) ;
    	if (order == null) {
    		return orderNew(null) ;
		}
    	if ("新建".equals(order.getOrderStatus())) {
			return orderNew(order) ;
		}
    	if ("生效".equals(order.getOrderStatus())) {
    		if ("未付款".equals(order.getPayOffStatus())) {
    			return orderNew(order) ;
			}
			if ("部分付款".equals(order.getPayOffStatus())) {
				return orderConfirm(order) ;
			}
			else {
				return orderPayoff(order) ;
			}
		}
    	return orderPayoff(order);
    }
    
    @RequestMapping(value = "/payOffHis", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView payOffHis(String orderId) {
    	if (!CoreUtils.isLong(orderId)) {
    		return orderNew(null) ;
    	}
    	ModelAndView view = new ModelAndView("order/order_payoff_his");
    	MarketOrder order = orderBO.getByOrderId(Long.valueOf(orderId)) ;
    	BigDecimal ttmny = BigDecimal.ZERO ;
    	if (order != null) {
    		List<MarketPayoff> payoffs = orderBO.getPayoffHis(order.getOrderId()) ;
    		if (payoffs != null && !payoffs.isEmpty()) {
    			for (MarketPayoff marketPayoff : payoffs) {
					ttmny = ttmny.add(marketPayoff.getPayOffMoney()) ;
				}
				view.addObject("payoffs", payoffs) ;
			}
    	}
    	view.addObject("ttmny", ttmny) ;
    	return view;
    }
    
    @RequestMapping(value = "/Order", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView Order(String orderId) {
    	if (!CoreUtils.isLong(orderId)) {
    		return orderNew(null) ;
    	}
    	MarketOrder order = orderBO.getByOrderId(Long.valueOf(orderId)) ;
    	if (order == null) {
    		return orderNew(null) ;
    	}
    	if ("新建".equals(order.getOrderStatus())) {
    		return orderNew(order) ;
    	}
    	if ("生效".equals(order.getOrderStatus())) {
    		if ("部分付款".equals(order.getPayOffStatus()) || "未付款".equals(order.getPayOffStatus())) {
    			return orderConfirm(order) ;
    		}
    		else {
    			return orderPayoff(order) ;
    		}
    	}
    	return orderPayoff(order);
    }
    
    private ModelAndView orderNew(MarketOrder order){
    	ModelAndView view = new ModelAndView("order/new_order_win");
    	if (order != null) {
			view.addObject("order", order ) ;
			MarketMember member = memberBO.getByMemberId(order.getMemberId()) ;
			view.addObject("member", member ) ;
			List<MarketOrderLine> orderLines = orderBO.getOrderLineList(order.getOrderId()) ;
			List<JSONObject> lineList = new ArrayList<JSONObject>() ;
			if (orderLines != null && !orderLines.isEmpty()) {
				for (MarketOrderLine line : orderLines) {
					JSONObject lObject = new JSONObject() ;
					//var rcd = {gid:gid,gn: gn,gct:gct,gctu:gctu, gftcfg:0, punitstr2:punitstr2} ;
					lObject.put("gid", line.getGoodsId()) ;
					lObject.put("gn", line.getGoodsName()) ;
					lObject.put("gct", line.getGoodsCount()) ;
					lObject.put("gctu", line.getGoodsCountUnit()) ;
					lObject.put("gftcfg", line.getGoodsGiftConfigId()) ;
					MarketGoods goods = goodsBO.getGoodsById(line.getGoodsId()) ;
					lObject.put("punitstr2", goods.getPunit2()) ;
					
					lineList.add(lObject) ;
				}
			}
			view.addObject("lineList", lineList) ;
		}
    	return view ;
    }
    
    private ModelAndView orderConfirm(MarketOrder order) {
    	if (order == null) {
    		throw new RunException("无法获取销售单据信息！") ;
    	}
    	ModelAndView view = new ModelAndView("order/order_buy_confirm");
    	view.addObject("order", order ) ;
		MarketMember member = memberBO.getByMemberId(order.getMemberId()) ;
		view.addObject("member", member ) ;
		BigDecimal memberGiftAcc = orderBO.getMemberAcmBuyInfo(member.getMemberId()) ;
		view.addObject("memberGiftAcc", CoreUtils.formatMoney(memberGiftAcc) ) ;
		BigDecimal ttGiftAcc = memberGiftAcc.add(order.getOrderTotalGiftAmount()) ;
		view.addObject("ttGiftAcc", CoreUtils.formatMoney(ttGiftAcc)) ;
		String totalVoucher = orderBO.getMemberAcmVoucherInfo(member.getMemberId()) ;
		view.addObject("totalVoucher", totalVoucher) ;
//		BigDecimal charge = order.getOrderChargeMoney().subtract(order.getPayOffCashTotalMoney()) ;
		BigDecimal charge = order.getOrderTotalMoney().subtract(order.getOrderCutMoney()).subtract(order.getPayOffVoucherTotalMoney()).subtract(order.getPayOffCashTotalMoney());
		view.addObject("charge", charge) ;
		if (BigDecimal.ZERO.compareTo(order.getOrderCutMoney()) == 0) {
			view.addObject("cutMoney", 0) ;
		}
		if (BigDecimal.ZERO.compareTo(order.getPayOffCashTotalMoney()) == 0) {
			view.addObject("firstPay", 1) ;
		}
		if (BigDecimal.ZERO.compareTo(order.getGiftCheckAmount()) == 0) {
			view.addObject("firstGiftCheck", 1) ;
		}
		List<MarketOrderLine> orderLines = orderBO.getOrderLineList(order.getOrderId()) ;
		List<JSONObject> lineList = new ArrayList<JSONObject>() ;
		BigDecimal drfDiffAmount = BigDecimal.ZERO ;
		if (orderLines != null && !orderLines.isEmpty()) {
			for (MarketOrderLine line : orderLines) {
				JSONObject lObject = new JSONObject() ;
				lObject.put("goodsName", line.getGoodsName()) ;
				lObject.put("goodsCount", line.getGoodsCount()) ;
				lObject.put("goodsCountUnit", line.getGoodsCountUnit()) ;
				lObject.put("goodsOrderPrice", line.getGoodsOrderPrice()) ;
				lObject.put("giftAmount", line.getGiftAmount()) ;
				drfDiffAmount = drfDiffAmount.add(line.getGoodsDrfDiffAmount()) ;
//				List<String> gl = new ArrayList<String>() ;
//				List<MarketOrderGiftLine> giftLines = line.getOrderGiftLineSet() ;
//				if (giftLines != null && !giftLines.isEmpty()) {
//					for (MarketOrderGiftLine gline : giftLines) {
//						gl.add(gline.giftInfo()) ; 
//					}
//				}
//				List<MarketMemberGiftAccumulation> mglines = line.getOrderGiftAccLineSet() ;
//				if (mglines != null && !mglines.isEmpty()) {
//					for (MarketMemberGiftAccumulation mgl : mglines) {
//						gl.add(mgl.giftInfo()) ;
//					}
//				}
//				if (!gl.isEmpty()) {
//					lObject.put("giftInfo", gl) ;
//				}
				lineList.add(lObject) ;
			}
		}
		view.addObject("drfDiffAmount", drfDiffAmount) ;
		view.addObject("lineList", lineList) ;
		List<MarketOnceBuy> onceBuyList = oncebuyBO.getList() ;
    	view.addObject("onceBuyList", onceBuyList) ;
    	
    	// 常用惠赠
    	List<MarketCommonGift> commonGifts = commonGiftBO.getAll() ;
    	if (commonGifts != null) {
			StringBuilder gb = new StringBuilder() ;
			for (MarketCommonGift gift : commonGifts) {
				gb.append("<tr><td style=\"width:100px;\" nowrap=\"false\" align=\"left\">");
				gb.append(gift.getCommonGiftName()) ;
				gb.append("</td><td>");
				String pu = gift.getCommonGiftUnit() ;
				if (StringUtils.isBlank(pu)) {
					pu = "个" ;
				}
				String[] ua = pu.split("\\*") ;
				for (String u : ua) {
					gb.append("<input class=\"ffffff\" type=\"text\" pn=\"");
					gb.append(gift.getCommonGiftName()) ;
					gb.append("\" pu=\"").append(u).append("\" size=\"3\" />").append(u) ;
				}
				gb.append("</td></tr>\n") ;
			}
			view.addObject("commonGiftLines", gb.toString()) ;
		}
		return view ;
    }
    
    private ModelAndView orderPayoff(MarketOrder order) {
    	if (order == null) {
    		throw new RunException("无法获取销售单据信息！") ;
    	}
    	ModelAndView view = new ModelAndView("order/order_buy_print");
    	view.addObject("printDate", CoreUtils.timeFormat(new Date())) ;
    	view.addObject("order", order ) ;
		MarketMember member = memberBO.getByMemberId(order.getMemberId()) ;
		view.addObject("member", member ) ;
//		BigDecimal charge = order.getOrderChargeMoney().subtract(order.getPayOffCashTotalMoney()) ;
		BigDecimal charge = order.getOrderTotalMoney().subtract(order.getOrderCutMoney()).subtract(order.getPayOffVoucherTotalMoney()).subtract(order.getPayOffCashTotalMoney());
		view.addObject("charge", charge) ;
		if (BigDecimal.ZERO.compareTo(order.getOrderCutMoney()) == 0) {
			view.addObject("cutMoney", 0) ;
		}
		List<MarketOrderLine> orderLines = orderBO.getOrderLineList(order.getOrderId()) ;
		List<JSONObject> lineList = new ArrayList<JSONObject>() ;
		List<String> gl = new ArrayList<String>() ;
		if (orderLines != null && !orderLines.isEmpty()) {
			for (MarketOrderLine line : orderLines) {
				JSONObject lObject = new JSONObject() ;
				lObject.put("goodsName", line.getGoodsName()) ;
				lObject.put("goodsCount", line.getGoodsCount()) ;
				lObject.put("goodsCountUnit", line.getGoodsCountUnit()) ;
				lObject.put("goodsOrderPrice", line.getGoodsOrderPrice()) ;
				List<MarketOrderGiftLine> giftLines = line.getOrderGiftLineSet() ;
				if (giftLines != null && !giftLines.isEmpty()) {
					for (MarketOrderGiftLine gline : giftLines) {
						gl.add(gline.giftInfo()) ; 
					}
				}
				List<MarketMemberGiftAccumulation> mglines = line.getOrderGiftAccLineSet() ;
				if (mglines != null && !mglines.isEmpty()) {
					for (MarketMemberGiftAccumulation mgl : mglines) {
						gl.add(mgl.giftInfo()) ;
					}
				}
				lineList.add(lObject) ;
			}
		}
		List<MarketMemberVoucher> voucherList = orderBO.getOrderVoucher(order.getOrderId()) ;
		if (voucherList != null) {
			for (MarketMemberVoucher voucher : voucherList) {
				gl.add(voucher.giftInfo())  ;
			}
		}
		
		if (StringUtils.isNotBlank(order.getGiftCheckRemark())) {
			gl.add("等值商品累积兑换，金额：" + CoreUtils.formatMoney(order.getGiftCheckAmount()) + "，兑换商品内容：" + order.getGiftCheckRemark()) ;
		}
		if (StringUtils.isNotBlank(order.getOrderCommonGiftRemark())) {
			gl.add(order.getOrderCommonGiftRemark()) ;
		}
		if (StringUtils.isNotBlank(order.getOrderRemark())) {
			gl.add(order.getOrderRemark()) ;
		}
		BigDecimal memberGiftAcc = orderBO.getMemberAcmBuyInfo(member.getMemberId()) ;
		view.addObject("memberGiftAcc", CoreUtils.formatMoney(memberGiftAcc) ) ;
		String totalVoucher = orderBO.getMemberAcmVoucherInfo(member.getMemberId()) ;
		view.addObject("totalVoucher", totalVoucher) ;
		view.addObject("lineList", lineList) ;
		view.addObject("giftLineList", gl) ;
    	return view ;
    }
    
//    private ModelAndView orderDetail(MarketOrder order) {
//    	return new ModelAndView("order/order_buy_print");
//    }
    
    @RequestMapping(value = "/orderPrint", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView orderPrint(String orderId) {
    	if (!CoreUtils.isLong(orderId)) {
			return orderNew(null) ;
		}
    	MarketOrder order = orderBO.getByOrderId(Long.valueOf(orderId)) ;
    	if (order == null) {
    		return orderNew(null) ;
		}
    	return orderPayoff(order);
    }
    
    @RequestMapping(value = "/saveOrder", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject saveOrder(String orderId ,String memberId,String orderLineInfo) {
    	ResponseJsonData response = new ResponseJsonData() ;
    	JSONObject r = new JSONObject() ;
    	response.setRetData(r);
    	r.put("flag", "NG") ;
    	MarketOrder order = new MarketOrder() ;
    	order.setOrderType("销售");
    	order.setOrderStatus("新建");
    	order.setCreateTime(new Date());
    	if (CoreUtils.isLong(orderId)) {
    		order = orderBO.getByOrderId(Long.valueOf(orderId)) ;
    		if (order == null) {
				r.put("msg", "数据有误，查询不到单据信息 ！") ;
				return response.getResult() ;
			}
    		if (!"未付款".equals(order.getPayOffStatus())) {
				r.put("msg", "单据已进行过付款操作，不能进行当前操作！") ;
				return response.getResult() ;
			}
    		order.setOrderRemark("");
		}
    	else {
			order.setOrderNo(CoreUtils.generateOrderNo(0L));
		}
    	if (!CoreUtils.isLong(memberId)) {
    		r.put("msg", "请输入会员信息！") ;
			return response.getResult() ;
		}
    	MarketMember member = memberBO.getByMemberId(Long.valueOf(memberId)) ;
    	order.setMemberId(member.getMemberId());
    	order.setMemberName(member.getMemberName());
    	order.setMemberNo(member.getMemberNo());
    	BigDecimal orderTotalMoney = BigDecimal.ZERO ;
    	BigDecimal totalGiftAmount = BigDecimal.ZERO ;
    	//明细  r.gid+"___" + r.gct + "___" + r.gctu + "___" + r.gftcfg;
    	if (StringUtils.isNotBlank(orderLineInfo)) {
			String[] lineStrArr = orderLineInfo.split("@@@") ;
			List<MarketOrderLine> orderLines = new ArrayList<MarketOrderLine>() ;
			List<Long> giftFeedGoodsIdList = new ArrayList<Long>() ;
			for (String lineStr : lineStrArr) {
				String[] fieldStrArr = lineStr.split("___") ;
				if (fieldStrArr.length != 7) {
					continue ;
				}
				if (!CoreUtils.isLong(fieldStrArr[0])) {
					continue ;
				}
				if (!CoreUtils.isLong(fieldStrArr[1])) {
					continue ;
				}
				if (StringUtils.isBlank(fieldStrArr[2])) {
					continue ;
				}
				if (!CoreUtils.isLong(fieldStrArr[3])) {
					continue ;
				}
				if (!CoreUtils.isDouble(fieldStrArr[4])) {
					continue ;
				}
				if (StringUtils.isBlank(fieldStrArr[5])) {
					continue ;
				}
				if (StringUtils.isBlank(fieldStrArr[6])) {
					continue ;
				}
				MarketOrderLine orderLine = new MarketOrderLine() ;
				orderLine.setGoodsId(Long.valueOf(fieldStrArr[0]));
				orderLine.setGoodsCount(Long.valueOf(fieldStrArr[1]));
				orderLine.setGoodsCountUnit(fieldStrArr[2]);
				orderLine.setGoodsGiftConfigId(Long.valueOf(fieldStrArr[3]));
				orderLine.setGoodsOrderPrice(new BigDecimal(fieldStrArr[4]));
				//detail的时候，已经算过了 
//				orderLine.setGoodsOrderPrice(orderLine.getGoodsOrderPrice().multiply(new BigDecimal(orderLine.getGoodsCount())));
				orderLine.setGoodsName(fieldStrArr[5]);
				orderLine.setGoodsPrice(fieldStrArr[6]);
				orderLines.add(orderLine) ;
				orderTotalMoney = orderTotalMoney.add(orderLine.getGoodsOrderPrice()) ;
				giftFeedGoodsIdList.add(orderLine.getGoodsId()) ;
			}
			if (!giftFeedGoodsIdList.isEmpty()) {
				Map<Long, MarketGiftCTConfig> ggFM = new HashMap<Long, MarketGiftCTConfig>() ;
				List<MarketGiftCTConfig> gctl = feedGiftConfigBO.queryFeedGiftConfigListByGoodsIdList(giftFeedGoodsIdList) ;
				if(gctl != null && !gctl.isEmpty()){
					for (MarketGiftCTConfig gct : gctl) {
						if (ggFM.get(gct.getGoodsId()) != null) {
							continue ;
						}
						ggFM.put(gct.getGoodsId(), gct) ;
					}
				}
				for (MarketOrderLine line : orderLines) {
					MarketGiftCTConfig gct = ggFM.get(line.getGoodsId());
					if(gct != null){
						if ("桶".endsWith(line.getGoodsCountUnit())) {
							BigDecimal giftAmount = new BigDecimal(gct.getGiftAmount()).multiply(new BigDecimal(line.getGoodsCount())) ;
							line.setGiftAmount(giftAmount);
							totalGiftAmount = totalGiftAmount.add(giftAmount) ;
						}
						else {
							line.setGiftAmount(new BigDecimal("0.00"));
						}
					}
				}
				order.setOrderLineSet(orderLines);
			}
		}
    	
    	order.setOrderTotalMoney(orderTotalMoney);
    	order.setOrderChargeMoney(orderTotalMoney);
    	order.setOrderTotalGiftAmount(totalGiftAmount);
    	order.setOrderCutMoney(BigDecimal.ZERO);
    	order.setYearAccumulationMoney(BigDecimal.ZERO);
    	order.setPayOffVoucherTotalMoney(BigDecimal.ZERO);
    	order.setPayOffCashTotalMoney(BigDecimal.ZERO);
    	order.setPayOffStatus("未付款");
    	order.setSalesReturn("无退货");
    	order.setGiftCheckAmount(BigDecimal.ZERO);
    	order.setOrderGiftVoucherTotalMoney(BigDecimal.ZERO);
    	orderBO.saveOrder(order) ;
    	r.put("orderId", order.getOrderId()) ;
//    	r.put("orderId", 1) ;
    	r.put("flag", "OK") ;
    	return response.getResult();
    }
    @RequestMapping(value = "/payOrder", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
	public JSONObject payOrder(String orderId, String cutMoney,
			String chargeMoney, String callBackRemarks, String orderRemarks, 
			String memberGiftCheckAmt, String memberGiftCheckRmk, String commonGiftCheckRmk,
			String onceBuyGift, String giftFlag, String voucherMoney) {
    	ResponseJsonData response = new ResponseJsonData() ;
    	JSONObject r = new JSONObject() ;
    	response.setRetData(r);
    	r.put("flag", "NG") ;
    	if (!CoreUtils.isLong(orderId)) {
    		r.put("msg", "无法获取单据信息！") ;
			return response.getResult() ;
		}
    	if (!CoreUtils.isDouble(cutMoney)) {
    		r.put("msg", "抹零信息无效！") ;
			return response.getResult() ;
		}
    	if (!CoreUtils.isDouble(chargeMoney)) {
    		r.put("msg", "付款信息无效！") ;
    		return response.getResult() ;
    	}
    	if (!CoreUtils.isDouble(voucherMoney)) {
    		r.put("msg", "代金券信息无效！") ;
    		return response.getResult() ;
    	}
    	if (!CoreUtils.isDouble(memberGiftCheckAmt)) {
    		r.put("msg", "等值商品兑换数量无效！") ;
    		return response.getResult() ;
    	}
    	MarketOrder order = orderBO.getByOrderId(Long.valueOf(orderId)) ;
    	if (order == null) {
    		r.put("msg", "无法获取单据信息！") ;
			return response.getResult() ;
		}
    	if (!"生效".equals(order.getOrderStatus())) {
    		r.put("msg", "单据状态不是生效状态，不能进行付款操作！") ;
    		return response.getResult() ;
		}
    	if (!"未付款".equals(order.getPayOffStatus()) && !"部分付款".equals(order.getPayOffStatus())) {
    		r.put("msg", "单据不是未付款单据，不能进行付款操作！") ;
    		return response.getResult() ;
		}
//    	if (BigDecimal.ZERO.compareTo(order.getOrderCutMoney()) != 0) {
//    		r.put("msg", "单据已做过抹零，不能再做抹零！") ;
//    		return response.getResult() ;
//		}
    	//如果不是第一次付款，忽略giftFlag=Y，置G
    	if (order.getPayOffCashTotalMoney() == null) {
			order.setPayOffCashTotalMoney(BigDecimal.ZERO);
		}
    	giftFlag = "V" ;
    	if (BigDecimal.ZERO.compareTo(order.getPayOffCashTotalMoney()) < 0) {
			giftFlag = "G" ;
		}
    	order.setOrderCutMoney(new BigDecimal(cutMoney));
    	order.setPayOffVoucherTotalMoney(new BigDecimal(voucherMoney));
    	order.setGiftCheckAmount(new BigDecimal(memberGiftCheckAmt));
    	BigDecimal pay = new BigDecimal(chargeMoney) ;
    	if (BigDecimal.ZERO.compareTo(pay) == 0) {
    		r.put("msg", "没有付款金额，不能进行付款操作！") ;
    		return response.getResult() ;
		}
    	BigDecimal shuldPay = order.getOrderTotalMoney().subtract(order.getOrderCutMoney()).subtract(order.getPayOffVoucherTotalMoney()).subtract(order.getPayOffCashTotalMoney()) ;
    	if (pay.compareTo(shuldPay) > 0) {
    		r.put("msg", "付款金额大于待付款金额！") ;
    		return response.getResult() ;
		}
    	if (pay.compareTo(shuldPay) < 0) {
    		if (BigDecimal.ZERO.compareTo(order.getPayOffVoucherTotalMoney()) < 0) {
    			r.put("msg", "部分付款，不能使用代金券！") ;
        		return response.getResult() ;
			}
			order.setPayOffStatus("部分付款");
		}
    	else {
			order.setPayOffStatus("已付款");
		}
    	if (StringUtils.isNotBlank(order.getOrderRemark())) {
    		order.setOrderRemark(order.getOrderRemark().replaceAll("null", ""));
		}
    	if (StringUtils.isNotBlank(orderRemarks)) {
    		order.setOrderRemark(order.getOrderRemark() + ";;" + orderRemarks);
		}
    	if (StringUtils.isNotBlank(memberGiftCheckRmk)) {
    		order.setGiftCheckRemark(memberGiftCheckRmk);
    	}
    	if (StringUtils.isNotBlank(commonGiftCheckRmk)) {
    		order.setOrderCommonGiftRemark(commonGiftCheckRmk);
    	}
    	order.setPayOffCashTotalMoney(pay.add(order.getPayOffCashTotalMoney()));
    	orderBO.doPayOrder(order, callBackRemarks, onceBuyGift,pay,giftFlag) ;
    	r.put("flag", "OK") ;
    	return response.getResult();
    }
    
	@RequestMapping(value = "/getOrderPageList", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public JSONObject getOrderPageList(OrderSearchCondition condition) {
		
		if (condition == null) {
			condition = new OrderSearchCondition() ;
		}
		condition.setOrderType("销售");
		
		ResponseJsonData response = new ResponseJsonData() ;
		Page<MarketOrder> goodsPage = null;
		try {
			goodsPage = orderBO.getPage(condition, SessionUtil.getPageRequestParams()) ;
		} catch (Exception e) {
			response.setStatus(ERROR);
			response.setMessage("系统错误，无法查询销售单据信息！");
		}
		if (goodsPage != null) {
			response.pagedGridData(goodsPage.getTotal(), goodsPage.getRecords());
		}
		else {
			response.pagedGridData(0, null);
		}
		return response.getResult() ;
	}
	
	@RequestMapping(value = "/getMemberAcmBuyInfo", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public JSONObject getMemberAcmBuyInfo(String memberId) {
		ResponseJsonData response = new ResponseJsonData() ;
		JSONObject retdata = new JSONObject() ;
		response.setRetData(retdata);
		if (!CoreUtils.isLong(memberId)) {
			retdata.put("info", "无") ;
		}
		else {
			try {
				BigDecimal acm = orderBO.getMemberAcmBuyInfo(Long.valueOf(memberId)) ;
				retdata.put("info", CoreUtils.formatMoney(acm) + " 元") ;
			} catch (Exception e) {
				retdata.put("info", "未知") ;
			}
		}
		
		return response.getResult() ;
	}
	@RequestMapping(value = "/getMemberAcmVoucherInfo", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public JSONObject getMemberAcmVoucherInfo(String memberId) {
		ResponseJsonData response = new ResponseJsonData() ;
		JSONObject retdata = new JSONObject() ;
		response.setRetData(retdata);
		if (!CoreUtils.isLong(memberId)) {
			retdata.put("info", "无") ;
		}
		else {
			try {
				String acmInfo = orderBO.getMemberAcmVoucherInfo(Long.valueOf(memberId)) ;
				retdata.put("info", acmInfo) ;
			} catch (Exception e) {
				retdata.put("info", "未知") ;
			}
		}
		
		return response.getResult() ;
	}
	
	@Override
	@RequestMapping(value = "/loadContent", method = { RequestMethod.GET,
			RequestMethod.POST })
	protected ModelAndView loadContent(HttpServletRequest request, String pn) {
		return super.loadContent(request, pn);
	}
	
}

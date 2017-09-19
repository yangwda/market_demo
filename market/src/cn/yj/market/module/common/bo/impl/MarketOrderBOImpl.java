package cn.yj.market.module.common.bo.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.yj.market.frame.bo.BaseBo;
import cn.yj.market.frame.exception.RunException;
import cn.yj.market.frame.page.Page;
import cn.yj.market.frame.page.PageRequestParams;
import cn.yj.market.frame.util.CoreUtils;
import cn.yj.market.frame.vo.MarketCallBack;
import cn.yj.market.frame.vo.MarketGiftCTConfigLine;
import cn.yj.market.frame.vo.MarketGiftConfig;
import cn.yj.market.frame.vo.MarketGiftConfigLine;
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
import cn.yj.market.module.common.bo.OrderBO;
import cn.yj.market.module.common.dao.CallbackDao;
import cn.yj.market.module.common.dao.FeedGiftConfigLineDao;
import cn.yj.market.module.common.dao.GiftConfigDao;
import cn.yj.market.module.common.dao.GiftConfigLineDao;
import cn.yj.market.module.common.dao.GoodsDao;
import cn.yj.market.module.common.dao.MemberDao;
import cn.yj.market.module.common.dao.MemberGiftAccumulationDao;
import cn.yj.market.module.common.dao.MemberVoucherDao;
import cn.yj.market.module.common.dao.OncebuyDao;
import cn.yj.market.module.common.dao.OrderDao;
import cn.yj.market.module.common.dao.OrderGiftLineDao;
import cn.yj.market.module.common.dao.OrderLineDao;
import cn.yj.market.module.common.dao.PayoffDao;

@Service
public class MarketOrderBOImpl extends BaseBo implements OrderBO {
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private OrderLineDao lineDao;
	@Autowired
	private OrderGiftLineDao giftLineDao;
	@Autowired
	private GoodsDao goodsDao;
	@Autowired
	private GiftConfigLineDao giftConfigLineDao;
	@Autowired
	private FeedGiftConfigLineDao feedGiftConfigLineDao;
	@Autowired
	private GiftConfigDao giftConfigDao;
	@Autowired
	private MemberGiftAccumulationDao memberGiftAccumulationDao;
	@Autowired
	private CallbackDao callbackDao;
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private OncebuyDao oncebuyDao;
	@Autowired
	private MemberVoucherDao memberVoucherDao;
	@Autowired
	private PayoffDao payoffDao;
	
	@Override
	public Page<MarketOrder> getPage(OrderSearchCondition condition,
			PageRequestParams pageRequestParams) {
		return orderDao.getPage(condition, pageRequestParams);
	}

	@Override
	public String getMemberAcmBuyInfo(Long memberId) {
		// TODO 具体逻辑待完善
		return "暂无";
	}

	@Override
	public MarketOrder getByOrderId(Long orderId) {
		return orderDao.load(orderId);
	}
	
	@Override
	public void doPayOrder(MarketOrder order, String callBackRemarks,
			String onceBuyGift, BigDecimal pay) {
		if (order == null) {
			throw new RunException("无法获取单据信息！") ;
		}
		MarketOrder orderOld = orderDao.load(order.getOrderId()) ;
		if (orderOld == null) {
			throw new RunException("无法获取原单据信息！") ;
		}
		orderOld.setOrderCutMoney(order.getOrderCutMoney());
		orderOld.setPayOffStatus(order.getPayOffStatus());
		orderOld.setOrderRemark(order.getOrderRemark());
		orderOld.setPayOffCashTotalMoney(order.getPayOffCashTotalMoney());
		if (StringUtils.isNotBlank(callBackRemarks)) {
			MarketCallBack callBack = new MarketCallBack() ;
			MarketMember member = memberDao.get(order.getMemberId()) ;
			if (member != null) {
				callBack.setCallBackRemarks(callBackRemarks);
				callBack.setCreateTime(new Date());
				callBack.setMemberId(order.getMemberId());
				callBack.setMemberName(order.getMemberName());
				callBack.setMemberTel(member.getMemberTel());
				callBack.setMemberPhone(member.getMemberPhone());
				callBack.setOrderId(order.getOrderId());
				Long callbackId = (Long) callbackDao.save(callBack) ;
				orderOld.setCallBackId(callbackId);
			}
		}
		if (StringUtils.isNotBlank(onceBuyGift)) {
			String[] obgca = onceBuyGift.split("__") ;
			if (obgca.length == 2) {
				if (CoreUtils.isLong(obgca[0])) {
					if ("D".equals(obgca[1])) {
						//-- TODO 单次购买的优惠逻辑，后续补充处理
						MarketOnceBuy onceBuy = oncebuyDao.load(Long.valueOf(obgca[0])) ;
						if (onceBuy != null) {
							BigDecimal vd = pay.multiply(new BigDecimal(onceBuy.getPerRate())).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP) ;
							MarketMemberVoucher voucher = new MarketMemberVoucher() ;
							voucher.setCreateTime(new Date());
							voucher.setMemberId(order.getOrderId());
							voucher.setRemainingMoney(vd);
							voucher.setSourceOrderId(order.getOrderId());
							voucher.setSourceVoucherMoney(vd);
							memberVoucherDao.save(voucher) ;
						}
					}
				}
			}
		}
		//付款流水
		MarketPayoff payoff = new MarketPayoff() ;
		payoff.setMemberId(order.getMemberId());
		payoff.setOrderId(order.getOrderId());
		payoff.setPayOffMoney(pay);
		payoff.setPayOffTime(new Date());
		payoff.setPayOffType("销售付款");
		payoff.setPayOffWay("现金");
		payoffDao.save(payoff) ;
		orderDao.update(orderOld) ;
		
	}

	@Override
	public void saveOrder(MarketOrder order) {
		if (order.getOrderId() == null) {
			order.setOrderNo(CoreUtils.generateOrderNo(0L));
			Long ordreId = (Long) orderDao.save(order) ;
			order.setOrderId(ordreId);
			order = orderDao.get(ordreId) ;
			order.setOrderNo(CoreUtils.generateOrderNo(ordreId));
			// 明细
			List<MarketOrderLine> lines = order.getOrderLineSet() ;
			if (lines != null && !lines.isEmpty()) {
				for (MarketOrderLine marketOrderLine : lines) {
					Long giftConfigLineId = marketOrderLine.getGoodsGiftConfigId() ;
					MarketOrderGiftLine giftLine = null ;
					MarketMemberGiftAccumulation giftAccumulation = null ;
					if(giftConfigLineId != null && giftConfigLineId.longValue() > 0){
						MarketGiftConfigLine giftConfigLine = giftConfigLineDao.get(marketOrderLine.getGoodsGiftConfigId()) ;
						if (giftConfigLine == null ) {
							MarketGiftCTConfigLine ctConfigLine = feedGiftConfigLineDao.get(marketOrderLine.getGoodsGiftConfigId()) ;
							MarketGoods goods = goodsDao.get(marketOrderLine.getGoodsId()) ;
							List<MarketGoodsUnitPrice> goodsUnitPrices = goods.getUnitPriceSet() ;
							long minUnitCountBuyLimit = CoreUtils.getGoodsMinUnitCount(goodsUnitPrices ,ctConfigLine.getBuyLimit() ,ctConfigLine.getBuyLimitPunit()) ;
							long minUnitCountBuy = CoreUtils.getGoodsMinUnitCount(goodsUnitPrices ,marketOrderLine.getGoodsCount() ,marketOrderLine.getGoodsCountUnit()) ;
							//System.out.println(minUnitCountBuyLimit + "===========" + minUnitCountBuy);
							int count = (int) (minUnitCountBuy / minUnitCountBuyLimit) ;
							if (count > 0) {
								marketOrderLine.setGoodsGift("赠送");
								marketOrderLine.setGoodsGiftCheck(ctConfigLine.getCheckType());
								giftLine = new MarketOrderGiftLine() ;
								giftLine.setOrderId(ordreId);
								giftLine.setGoodsName(ctConfigLine.getGiftGoodsName());
								giftLine.setGoodsId(ctConfigLine.getGiftGoodsId());
								giftLine.setGoodsCount(ctConfigLine.getGiftGoodsCount().longValue() * count);
								giftLine.setGoodsCountUnit(ctConfigLine.getGiftGoodsCountUnit());
								giftLine.setGiftLogic(ctConfigLine.giftCommonStr());
								if ("累积".equals(marketOrderLine.getGoodsGiftCheck())) {
									giftAccumulation = new MarketMemberGiftAccumulation() ;
									giftAccumulation.setOrderId(ordreId);
									giftAccumulation.setGoodsId(marketOrderLine.getGoodsId());
									giftAccumulation.setGoodsName(marketOrderLine.getGoodsName());
									giftAccumulation.setBuyTime(new Date());
									giftAccumulation.setBuyCount(minUnitCountBuy);
									giftAccumulation.setCheckCount(0L);
								}
							}
							else {
								marketOrderLine.setGoodsGift("无赠送");
								marketOrderLine.setGoodsGiftCheck("无赠送");
							}
						}
						else {
							MarketGiftConfig giftConfig = giftConfigDao.get(giftConfigLine.getGiftConfigId()) ;
//							BigDecimal ct = giftConfig.getBuyLimit().divide(marketOrderLine.getGoodsOrderPrice() ,BigDecimal.ROUND_HALF_UP) ;
							BigDecimal ct = marketOrderLine.getGoodsOrderPrice().divide(giftConfig.getBuyLimit() ,BigDecimal.ROUND_HALF_UP) ;
							int count = ct.intValue() ;
							if (count > 0) {
								marketOrderLine.setGoodsGift("赠送");
								marketOrderLine.setGoodsGiftCheck("当时兑现");
								giftLine = new MarketOrderGiftLine() ;
								giftLine.setOrderId(ordreId);
								giftLine.setGoodsName(giftConfigLine.getGiftGoodsName());
								giftLine.setGoodsId(giftConfigLine.getGiftGoodsId());
								giftLine.setGoodsCount(giftConfigLine.getGiftGoodsCount().longValue() * count);
								giftLine.setGoodsCountUnit(giftConfigLine.getGiftGoodsCountUnit());
								giftLine.setGiftLogic(giftConfig.giftCommonStr());
							}
							else {
								marketOrderLine.setGoodsGift("无赠送");
								marketOrderLine.setGoodsGiftCheck("无赠送");
							}
						}
					}
					else {
						marketOrderLine.setGoodsGift("无赠送");
						marketOrderLine.setGoodsGiftCheck("无赠送");
					}
					marketOrderLine.setOrderId(ordreId);
					Long lineId = (Long) lineDao.save(marketOrderLine) ;
					if (giftLine != null) {
						giftLine.setOrderLineId(lineId);
						giftLineDao.save(giftLine) ;
					}
					if (giftAccumulation != null) {
						giftAccumulation.setOrderLineId(lineId);
						memberGiftAccumulationDao.save(giftAccumulation) ;
					}
				}
			}
			order.setOrderStatus("生效");
			orderDao.update(order);
		}
		else {
			//orderDao.update(order);
			MarketOrder orderOld = orderDao.load(order.getOrderId()) ;
			if (orderOld == null) {
				throw new RunException("无法获取原单据信息！") ;
			}
			if (!"新建".equals(orderOld.getOrderStatus())) {
				throw new RunException("不是新建单据，无法修改！") ;
			}
			orderOld.setMemberId(order.getMemberId());
			orderOld.setMemberName(order.getMemberName());
			orderOld.setMemberNo(order.getMemberNo());
			orderOld.setOrderTotalMoney(order.getOrderTotalMoney());
			orderOld.setOrderStatus("生效");
			orderDao.update(orderOld);
			Long ordreId = order.getOrderId();
			lineDao.deleteByOrderId(ordreId);
			giftLineDao.deleteByOrderId(ordreId);
			memberGiftAccumulationDao.deleteByOrderId(ordreId);
			
			// 明细
			List<MarketOrderLine> lines = order.getOrderLineSet() ;
			if (lines != null && !lines.isEmpty()) {
				for (MarketOrderLine marketOrderLine : lines) {
					Long giftConfigLineId = marketOrderLine.getGoodsGiftConfigId() ;
					MarketOrderGiftLine giftLine = null ;
					MarketMemberGiftAccumulation giftAccumulation = null ;
					if(giftConfigLineId != null && giftConfigLineId.longValue() > 0){
						MarketGiftConfigLine giftConfigLine = giftConfigLineDao.get(marketOrderLine.getGoodsGiftConfigId()) ;
						if (giftConfigLine == null ) {
							MarketGiftCTConfigLine ctConfigLine = feedGiftConfigLineDao.get(marketOrderLine.getGoodsGiftConfigId()) ;
							MarketGoods goods = goodsDao.get(marketOrderLine.getGoodsId()) ;
							List<MarketGoodsUnitPrice> goodsUnitPrices = goods.getUnitPriceSet() ;
							long minUnitCountBuyLimit = CoreUtils.getGoodsMinUnitCount(goodsUnitPrices ,ctConfigLine.getBuyLimit() ,ctConfigLine.getBuyLimitPunit()) ;
							long minUnitCountBuy = CoreUtils.getGoodsMinUnitCount(goodsUnitPrices ,marketOrderLine.getGoodsCount() ,marketOrderLine.getGoodsCountUnit()) ;
							//System.out.println(minUnitCountBuyLimit + "===========" + minUnitCountBuy);
							int count = (int) (minUnitCountBuy / minUnitCountBuyLimit) ;
							if (count > 0) {
								marketOrderLine.setGoodsGift("赠送");
								marketOrderLine.setGoodsGiftCheck(ctConfigLine.getCheckType());
								giftLine = new MarketOrderGiftLine() ;
								giftLine.setOrderId(ordreId);
								giftLine.setGoodsName(ctConfigLine.getGiftGoodsName());
								giftLine.setGoodsId(ctConfigLine.getGiftGoodsId());
								giftLine.setGoodsCount(ctConfigLine.getGiftGoodsCount().longValue() * count);
								giftLine.setGoodsCountUnit(ctConfigLine.getGiftGoodsCountUnit());
								giftLine.setGiftLogic(ctConfigLine.giftCommonStr());
								if ("累积".equals(marketOrderLine.getGoodsGiftCheck())) {
									giftAccumulation = new MarketMemberGiftAccumulation() ;
									giftAccumulation.setOrderId(ordreId);
									giftAccumulation.setGoodsId(marketOrderLine.getGoodsId());
									giftAccumulation.setGoodsName(marketOrderLine.getGoodsName());
									giftAccumulation.setBuyTime(new Date());
									giftAccumulation.setBuyCount(minUnitCountBuy);
									giftAccumulation.setCheckCount(0L);
								}
							}
							else {
								marketOrderLine.setGoodsGift("无赠送");
								marketOrderLine.setGoodsGiftCheck("无赠送");
							}
						}
						else {
							MarketGiftConfig giftConfig = giftConfigDao.get(giftConfigLine.getGiftConfigId()) ;
//							BigDecimal ct = giftConfig.getBuyLimit().divide(marketOrderLine.getGoodsOrderPrice() ,BigDecimal.ROUND_HALF_UP) ;
							BigDecimal ct = marketOrderLine.getGoodsOrderPrice().divide(giftConfig.getBuyLimit() ,BigDecimal.ROUND_HALF_UP) ;
							int count = ct.intValue() ;
							if (count > 0) {
								marketOrderLine.setGoodsGift("赠送");
								marketOrderLine.setGoodsGiftCheck("当时兑现");
								giftLine = new MarketOrderGiftLine() ;
								giftLine.setOrderId(ordreId);
								giftLine.setGoodsName(giftConfigLine.getGiftGoodsName());
								giftLine.setGoodsId(giftConfigLine.getGiftGoodsId());
								giftLine.setGoodsCount(giftConfigLine.getGiftGoodsCount().longValue() * count);
								giftLine.setGoodsCountUnit(giftConfigLine.getGiftGoodsCountUnit());
								giftLine.setGiftLogic(giftConfig.giftCommonStr());
							}
							else {
								marketOrderLine.setGoodsGift("无赠送");
								marketOrderLine.setGoodsGiftCheck("无赠送");
							}
						}
					}
					else {
						marketOrderLine.setGoodsGift("无赠送");
						marketOrderLine.setGoodsGiftCheck("无赠送");
					}
					marketOrderLine.setOrderId(ordreId);
					Long lineId = (Long) lineDao.save(marketOrderLine) ;
					if (giftLine != null) {
						giftLine.setOrderLineId(lineId);
						giftLineDao.save(giftLine) ;
					}
					if (giftAccumulation != null) {
						giftAccumulation.setOrderLineId(lineId);
						memberGiftAccumulationDao.save(giftAccumulation) ;
					}
				}
			}
		}
	}

	@Override
	public List<MarketOrderLine> getOrderLineList(Long orderId) {
		return lineDao.queryOrderLines(orderId);
	}

	@Override
	public List<MarketMemberVoucher> getOrderVoucher(Long orderId) {
		return memberVoucherDao.queryOrderVoucherList(orderId);
	}
}

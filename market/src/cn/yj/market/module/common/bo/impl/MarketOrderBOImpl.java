package cn.yj.market.module.common.bo.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
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
import cn.yj.market.frame.vo.MarketMemberGift;
import cn.yj.market.frame.vo.MarketMemberGiftAccumulation;
import cn.yj.market.frame.vo.MarketMemberVoucher;
import cn.yj.market.frame.vo.MarketOnceBuy;
import cn.yj.market.frame.vo.MarketOrder;
import cn.yj.market.frame.vo.MarketOrderGiftLine;
import cn.yj.market.frame.vo.MarketOrderLine;
import cn.yj.market.frame.vo.MarketPayoff;
import cn.yj.market.module.common.bean.MemberGiftCheckSearchCondition;
import cn.yj.market.module.common.bean.OrderSearchCondition;
import cn.yj.market.module.common.bo.OrderBO;
import cn.yj.market.module.common.constants.RatioConstants;
import cn.yj.market.module.common.dao.CallbackDao;
import cn.yj.market.module.common.dao.FeedGiftConfigLineDao;
import cn.yj.market.module.common.dao.GiftConfigDao;
import cn.yj.market.module.common.dao.GiftConfigLineDao;
import cn.yj.market.module.common.dao.GoodsDao;
import cn.yj.market.module.common.dao.MemberDao;
import cn.yj.market.module.common.dao.MemberGiftAccumulationDao;
import cn.yj.market.module.common.dao.MemberGiftDao;
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
	@Autowired
	private MemberGiftDao memberGiftDao;
	
	@Override
	public Page<MarketOrder> getPage(OrderSearchCondition condition,
			PageRequestParams pageRequestParams) {
		return orderDao.getPage(condition, pageRequestParams);
	}

	@Override
	public BigDecimal getMemberAcmBuyInfo(Long memberId) {
//		BigDecimal act = memberGiftDao.getMemberAcm(memberId) ;
		List<MarketMemberGift> memberGifts = memberGiftDao.getMemberGiftList(memberId) ;
		BigDecimal r = BigDecimal.ZERO ;
		if (memberGifts != null) {
			for (MarketMemberGift mg : memberGifts) {
				r = r.add(mg.getRemainingMoney()) ;
			}
		}
		return r;
	}
	
	@Override
	public String getMemberAcmVoucherInfo(Long memberId) {
		if (memberId == null || memberId.longValue() < 1) {
			return "0.00" ;
		}
		BigDecimal voucherTotal = memberVoucherDao.getTotalAvlVoucher(memberId) ;
		return CoreUtils.formatMoney(voucherTotal);
	}

	@Override
	public MarketOrder getByOrderId(Long orderId) {
		return orderDao.load(orderId);
	}
	
	@Override
	public void doPayOrder(MarketOrder order, String callBackRemarks,
			String onceBuyGift, BigDecimal pay, String giftFlag) {
		if (order == null) {
			throw new RunException("无法获取单据信息！") ;
		}
		MarketOrder orderOld = orderDao.load(order.getOrderId()) ;
		if (orderOld == null) {
			throw new RunException("无法获取原单据信息！") ;
		}
		orderOld.setOrderCutMoney(order.getOrderCutMoney());
		orderOld.setPayOffStatus(order.getPayOffStatus());
		String remarks = order.getOrderRemark(); 
		if (remarks == null) {
			remarks = "" ;
		}
		remarks = remarks.replace("null;;", "") ;
		orderOld.setOrderRemark(remarks);
		orderOld.setPayOffCashTotalMoney(order.getPayOffCashTotalMoney());
		
		orderOld.setGiftCheckAmount(order.getGiftCheckAmount());
		orderOld.setGiftCheckRemark(order.getGiftCheckRemark());
		orderOld.setOrderCommonGiftRemark(order.getOrderCommonGiftRemark());
		
		BigDecimal payVoucher = order.getPayOffVoucherTotalMoney() ;
		if (payVoucher == null) {
			payVoucher = BigDecimal.ZERO ;
		}
		orderOld.setPayOffVoucherTotalMoney(payVoucher);
		if (payVoucher.compareTo(BigDecimal.ZERO) > 0) {
			List<MarketMemberVoucher> vouchers = memberVoucherDao.getMemberVoucherList(order.getMemberId()) ; 
			if (vouchers == null || vouchers.isEmpty()) {
				throw new RunException("当前客户没有代金券！") ;
			}
			for (MarketMemberVoucher voucher : vouchers) {
				if (voucher.getRemainingMoney() == null) {
					continue ;
				}
				if (payVoucher.compareTo(voucher.getRemainingMoney()) >= 0) {
					payVoucher = payVoucher.subtract(voucher.getRemainingMoney()) ;
					voucher.setRemainingMoney(BigDecimal.ZERO);
				}
				else {
					voucher.setRemainingMoney(voucher.getRemainingMoney().subtract(payVoucher));
					payVoucher = BigDecimal.ZERO ;
				}
				memberVoucherDao.update(voucher);
				if (payVoucher.compareTo(BigDecimal.ZERO) == 0) {
					break ;
				}
			}
		}
		//此时应该==0
		if (payVoucher.compareTo(BigDecimal.ZERO) > 0) {
			throw new RunException("当前客户代金券不够！") ;
		}
		
		
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
		if ("V".equals(giftFlag)) {
//			MarketMemberVoucher voucher = new MarketMemberVoucher() ;
//			voucher.setCreateTime(new Date());
//			voucher.setMemberId(order.getMemberId());
//			voucher.setRemainingMoney(order.getOrderTotalGiftAmount());
//			voucher.setSourceOrderId(order.getOrderId());
//			voucher.setSourceVoucherMoney(order.getOrderTotalGiftAmount());
//			voucher.setRemarks("等值产品金额生成的代金券");
//			memberVoucherDao.save(voucher) ;
			// 等值商品做累积
			MarketMemberGift gift = new MarketMemberGift() ;
			gift.setCreateTime(new Date());
			gift.setMemberId(order.getMemberId());
			gift.setRemainingMoney(order.getOrderTotalGiftAmount());
			gift.setRemarks("等值商品累积");
			gift.setSourceGiftMoney(order.getOrderTotalGiftAmount());
			gift.setSourceOrderId(order.getOrderId());
			memberGiftDao.save(gift) ;
		}
		if (StringUtils.isNotBlank(onceBuyGift)) {
			String[] obgca = onceBuyGift.split("__") ;
			if (obgca.length == 2) {
				if (CoreUtils.isLong(obgca[0])) {
					if ("D".equals(obgca[1])) {
						MarketOnceBuy onceBuy = oncebuyDao.load(Long.valueOf(obgca[0])) ;
						if (onceBuy != null) {
							List<MarketOrderLine> oll = orderOld.getOrderLineSet() ;
							if (oll != null) {
								for (MarketOrderLine ol : oll) {
									pay = pay.subtract(ol.getGoodsDrfDiffAmount()) ;
								}
							}
							
							BigDecimal vd = pay.multiply(new BigDecimal(onceBuy.getPerRate())).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP) ;
							MarketMemberVoucher voucher = new MarketMemberVoucher() ;
							voucher.setCreateTime(new Date());
							voucher.setMemberId(order.getMemberId());
							voucher.setRemainingMoney(vd);
							voucher.setSourceOrderId(order.getOrderId());
							voucher.setSourceVoucherMoney(vd);
							voucher.setRemarks("单次购买活动生成的代金券");
							memberVoucherDao.save(voucher) ;
							
							BigDecimal vc = orderOld.getOrderGiftVoucherTotalMoney() ;
							if (vc == null) {
								vc = BigDecimal.ZERO ;
							}
							orderOld.setOrderGiftVoucherTotalMoney(vc.add(voucher.getSourceVoucherMoney()));
						}
					}
				}
			}
		}
		
		//-- todo 仿照代金券的扣减，实现等值商品累积兑换记录
		BigDecimal gc = order.getGiftCheckAmount() ;
		if (gc == null) {
			gc = BigDecimal.ZERO ;
		}
		if (gc.compareTo(BigDecimal.ZERO) > 0) {
			List<MarketMemberGift> gifts = memberGiftDao.getMemberGiftList(order.getMemberId()) ; 
			if (gifts == null || gifts.isEmpty()) {
				throw new RunException("当前客户没有等值商品累积金额！") ;
			}
			for (MarketMemberGift gift : gifts) {
				if (gift.getRemainingMoney() == null) {
					continue ;
				}
				if (gc.compareTo(gift.getRemainingMoney()) >= 0) {
					gc = gc.subtract(gift.getRemainingMoney()) ;
					gift.setRemainingMoney(BigDecimal.ZERO);
				}
				else {
					gift.setRemainingMoney(gift.getRemainingMoney().subtract(gc));
					gc = BigDecimal.ZERO ;
				}
				memberGiftDao.update(gift);
				if (payVoucher.compareTo(BigDecimal.ZERO) == 0) {
					break ;
				}
			}
		}
		//此时应该==0
		if (gc.compareTo(BigDecimal.ZERO) > 0) {
			throw new RunException("当前客户等值商品累积金额不够！") ;
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
			order.setYearAccumulationMoney(BigDecimal.ZERO);
			// 明细
			List<MarketOrderLine> lines = order.getOrderLineSet() ;
			if (lines != null && !lines.isEmpty()) {
				for (MarketOrderLine marketOrderLine : lines) {
					
					if ("代乳粉".equals(marketOrderLine.getGoodsName())) {
						marketOrderLine.setGoodsDrfDiffAmount(marketOrderLine
								.getGoodsOrderPrice().multiply(
										RatioConstants
												.getDrfRate(marketOrderLine
														.getGoodsCountUnit())));
					}
					else {
						marketOrderLine.setGoodsDrfDiffAmount(BigDecimal.ZERO);
					}
					order.setYearAccumulationMoney(order
							.getYearAccumulationMoney()
							.add(marketOrderLine.getGoodsOrderPrice().subtract(
									marketOrderLine.getGoodsDrfDiffAmount())));
					
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
					if (marketOrderLine.getGiftAmount() == null) {
						marketOrderLine.setGiftAmount(BigDecimal.ZERO);
					}
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
			if (!"未付款".equals(orderOld.getPayOffStatus())) {
				throw new RunException("单据已进行过付款操作，无法修改！") ;
			}
			orderOld.setMemberId(order.getMemberId());
			orderOld.setMemberName(order.getMemberName());
			orderOld.setMemberNo(order.getMemberNo());
			orderOld.setOrderTotalMoney(order.getOrderTotalMoney());
			orderOld.setOrderTotalGiftAmount(order.getOrderTotalGiftAmount());
			orderOld.setOrderStatus("生效");
			orderOld.setYearAccumulationMoney(BigDecimal.ZERO);
			orderDao.update(orderOld);
			Long ordreId = order.getOrderId();
			lineDao.deleteByOrderId(ordreId);
			giftLineDao.deleteByOrderId(ordreId);
			memberGiftAccumulationDao.deleteByOrderId(ordreId);
			
			// 明细
			List<MarketOrderLine> lines = order.getOrderLineSet() ;
			if (lines != null && !lines.isEmpty()) {
				for (MarketOrderLine marketOrderLine : lines) {
					
					if ("代乳粉".equals(marketOrderLine.getGoodsName())) {
						marketOrderLine.setGoodsDrfDiffAmount(marketOrderLine
								.getGoodsOrderPrice().multiply(
										RatioConstants
												.getDrfRate(marketOrderLine
														.getGoodsCountUnit())));
					}
					else {
						marketOrderLine.setGoodsDrfDiffAmount(BigDecimal.ZERO);
					}
					order.setYearAccumulationMoney(order
							.getYearAccumulationMoney()
							.add(marketOrderLine.getGoodsOrderPrice().subtract(
									marketOrderLine.getGoodsDrfDiffAmount())));
					
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
					if (marketOrderLine.getGiftAmount() == null) {
						marketOrderLine.setGiftAmount(BigDecimal.ZERO);
					}
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
			
			orderOld.setYearAccumulationMoney(order.getYearAccumulationMoney());
			orderDao.update(orderOld);
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

	@Override
	public List<MarketPayoff> getPayoffHis(Long orderId) {
		if (orderId == null) {
			return null ;
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(MarketPayoff.class).add( Property.forName("orderId").eq(orderId) );   
		return payoffDao.criteriaQuery(criteria);
	}

	@Override
	public Page<MarketOrder> getMemberGiftCheckPageList(
			MemberGiftCheckSearchCondition condition,
			PageRequestParams pageRequestParams) {
		return orderDao.getMemberGiftCheckPageList(condition,pageRequestParams);
	}
}

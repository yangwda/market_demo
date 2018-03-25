package cn.yj.market.module.common.bo;

import java.math.BigDecimal;
import java.util.List;

import cn.yj.market.frame.page.Page;
import cn.yj.market.frame.page.PageRequestParams;
import cn.yj.market.frame.vo.MarketMemberVoucher;
import cn.yj.market.frame.vo.MarketOrder;
import cn.yj.market.frame.vo.MarketOrderLine;
import cn.yj.market.module.common.bean.OrderSearchCondition;

public interface OrderBO {
	Page<MarketOrder> getPage(OrderSearchCondition condition ,PageRequestParams pageRequestParams) ;

	String getMemberAcmBuyInfo(Long memberId);
	
	String getMemberAcmVoucherInfo(Long memberId);

	MarketOrder getByOrderId(Long orderId);

	void saveOrder(MarketOrder order);

	List<MarketOrderLine> getOrderLineList(Long orderId);

	void doPayOrder(MarketOrder order, String callBackRemarks, String onceBuyGift, BigDecimal pay, String giftFlag);

	List<MarketMemberVoucher> getOrderVoucher(Long orderId);

}

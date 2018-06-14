package cn.yj.market.module.common.dao;

import java.math.BigDecimal;
import java.util.List;

import cn.yj.market.frame.hibernate.Dao;
import cn.yj.market.frame.vo.MarketMemberVoucher;

public interface MemberVoucherDao extends Dao<MarketMemberVoucher> {

	List<MarketMemberVoucher> queryOrderVoucherList(Long orderId);

	BigDecimal getTotalAvlVoucher(Long memberId);

	List<MarketMemberVoucher> getMemberVoucherList(Long memberId);
}

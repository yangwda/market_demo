package cn.yj.market.module.common.dao;

import java.util.List;

import cn.yj.market.frame.hibernate.Dao;
import cn.yj.market.frame.vo.MarketMemberGift;

public interface MemberGiftDao extends Dao<MarketMemberGift> {

	List<MarketMemberGift> getMemberGiftList(Long memberId);
}

package cn.yj.market.module.common.dao;

import cn.yj.market.frame.hibernate.Dao;
import cn.yj.market.frame.vo.MarketMemberGiftAccumulation;

public interface MemberGiftAccumulationDao extends Dao<MarketMemberGiftAccumulation> {

	void deleteByOrderId(Long ordreId);
}

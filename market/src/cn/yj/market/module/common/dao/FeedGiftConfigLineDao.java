package cn.yj.market.module.common.dao;

import java.util.List;

import cn.yj.market.frame.hibernate.Dao;
import cn.yj.market.frame.vo.MarketGiftCTConfigLine;

public interface FeedGiftConfigLineDao extends Dao<MarketGiftCTConfigLine> {

	List<MarketGiftCTConfigLine> getLineListByGiftConfigId(Long giftConfigId);
}

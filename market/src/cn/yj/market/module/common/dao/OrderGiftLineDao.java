package cn.yj.market.module.common.dao;

import cn.yj.market.frame.hibernate.Dao;
import cn.yj.market.frame.vo.MarketOrderGiftLine;

public interface OrderGiftLineDao extends Dao<MarketOrderGiftLine> {

	void deleteByOrderId(Long ordreId);
}

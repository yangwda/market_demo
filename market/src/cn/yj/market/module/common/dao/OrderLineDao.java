package cn.yj.market.module.common.dao;

import java.util.List;

import cn.yj.market.frame.hibernate.Dao;
import cn.yj.market.frame.vo.MarketOrderLine;

public interface OrderLineDao extends Dao<MarketOrderLine> {

	void deleteByOrderId(Long ordreId);

	List<MarketOrderLine> queryOrderLines(Long orderId);
}

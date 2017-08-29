package cn.yj.market.module.common.dao;

import java.util.List;

import cn.yj.market.frame.hibernate.Dao;
import cn.yj.market.frame.vo.MarketOnceBuy;

public interface OncebuyDao extends Dao<MarketOnceBuy> {
	List<MarketOnceBuy> getList() ;
}

package cn.yj.market.module.common.bo;

import java.util.List;

import cn.yj.market.frame.vo.MarketOnceBuy;

public interface OncebuyBO {
	List<MarketOnceBuy> getList() ;
	MarketOnceBuy saveOncebuy(MarketOnceBuy animal) ;
	void updateOncebuy(MarketOnceBuy animal);
}

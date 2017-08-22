package cn.yj.market.module.common.dao;

import cn.yj.market.frame.hibernate.Dao;
import cn.yj.market.frame.page.Page;
import cn.yj.market.frame.page.PageRequestParams;
import cn.yj.market.frame.vo.MarketGiftCTConfig;
import cn.yj.market.module.common.bean.GiftConfigSearchCondition;

public interface FeedGiftConfigDao extends Dao<MarketGiftCTConfig> {
	Page<MarketGiftCTConfig> getPage(GiftConfigSearchCondition condition ,PageRequestParams pageRequestParams) ;
}

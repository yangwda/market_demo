package cn.yj.market.module.common.dao;

import java.util.List;

import cn.yj.market.frame.hibernate.Dao;
import cn.yj.market.frame.page.Page;
import cn.yj.market.frame.page.PageRequestParams;
import cn.yj.market.frame.vo.MarketGiftConfig;
import cn.yj.market.module.common.bean.GiftConfigSearchCondition;

public interface GiftConfigDao extends Dao<MarketGiftConfig> {
	Page<MarketGiftConfig> getPage(GiftConfigSearchCondition condition ,PageRequestParams pageRequestParams) ;
	
	List<MarketGiftConfig> queryGiftConfigListByGoodsIdList(
			List<Long> giftGoodsIdList);
}

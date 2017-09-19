package cn.yj.market.module.common.bo;

import java.util.List;

import cn.yj.market.frame.page.Page;
import cn.yj.market.frame.page.PageRequestParams;
import cn.yj.market.frame.vo.MarketGiftConfig;
import cn.yj.market.frame.vo.MarketGiftConfigLine;
import cn.yj.market.module.common.bean.GiftConfigSearchCondition;

public interface GiftConfigBO {
	Page<MarketGiftConfig> getPage(GiftConfigSearchCondition condition ,PageRequestParams pageRequestParams) ;
	MarketGiftConfig saveGiftConfig(MarketGiftConfig cfg ,MarketGiftConfigLine line) ;
	void updateGiftConfig(MarketGiftConfig cfg ,MarketGiftConfigLine line) ;
	List<MarketGiftConfig> queryGiftConfigListByGoodsIdList(
			List<Long> giftGoodsIdList);
}

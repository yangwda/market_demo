package cn.yj.market.module.common.bo;

import java.util.List;

import cn.yj.market.frame.page.Page;
import cn.yj.market.frame.page.PageRequestParams;
import cn.yj.market.frame.vo.MarketGiftCTConfig;
import cn.yj.market.frame.vo.MarketGiftCTConfigLine;
import cn.yj.market.module.common.bean.GiftConfigSearchCondition;

public interface FeedGiftConfigBO {
	Page<MarketGiftCTConfig> getPage(GiftConfigSearchCondition condition ,PageRequestParams pageRequestParams) ;
	MarketGiftCTConfig saveGiftConfig(MarketGiftCTConfig cfg ,List<MarketGiftCTConfigLine> lineList) ;
	void updateGiftConfig(MarketGiftCTConfig cfg ,List<MarketGiftCTConfigLine> lineList) ;
}

package cn.yj.market.module.common.dao;

import cn.yj.market.frame.hibernate.Dao;
import cn.yj.market.frame.page.Page;
import cn.yj.market.frame.page.PageRequestParams;
import cn.yj.market.frame.vo.MarketCommonGift;

public interface CommonGiftDao extends Dao<MarketCommonGift> {
	Page<MarketCommonGift> getPage(PageRequestParams pageRequestParams) ;
}

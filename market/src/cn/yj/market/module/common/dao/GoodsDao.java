package cn.yj.market.module.common.dao;

import java.util.List;

import cn.yj.market.frame.hibernate.Dao;
import cn.yj.market.frame.page.Page;
import cn.yj.market.frame.page.PageRequestParams;
import cn.yj.market.frame.vo.MarketGoods;
import cn.yj.market.module.common.bean.GoodsSearchCondition;

public interface GoodsDao extends Dao<MarketGoods> {
	Page<MarketGoods> getPage(GoodsSearchCondition condition ,PageRequestParams pageRequestParams) ;

	List<MarketGoods> getGoodsAutofill(String likeStr);
	
	List<MarketGoods> getGiftGoodsAutofill(String likeStr);
}

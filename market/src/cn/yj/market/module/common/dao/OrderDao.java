package cn.yj.market.module.common.dao;

import cn.yj.market.frame.hibernate.Dao;
import cn.yj.market.frame.page.Page;
import cn.yj.market.frame.page.PageRequestParams;
import cn.yj.market.frame.vo.MarketOrder;
import cn.yj.market.module.common.bean.OrderSearchCondition;

public interface OrderDao extends Dao<MarketOrder> {
	Page<MarketOrder> getPage(OrderSearchCondition condition ,PageRequestParams pageRequestParams) ;
}

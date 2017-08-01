package cn.yj.market.module.common.dao;

import cn.yj.market.frame.hibernate.Dao;
import cn.yj.market.frame.page.Page;
import cn.yj.market.frame.page.PageRequestParams;
import cn.yj.market.frame.vo.MarketAnimal;
import cn.yj.market.module.common.bean.AnimalSearchCondition;

public interface AnimalDao extends Dao<MarketAnimal> {
	Page<MarketAnimal> getPage(AnimalSearchCondition condition ,PageRequestParams pageRequestParams) ;
}

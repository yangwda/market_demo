package cn.yj.market.module.common.dao;

import cn.yj.market.frame.hibernate.Dao;
import cn.yj.market.frame.page.Page;
import cn.yj.market.frame.page.PageRequestParams;
import cn.yj.market.frame.vo.MarketIllness;
import cn.yj.market.module.common.bean.IllnessSearchCondition;

public interface IllnessDao extends Dao<MarketIllness> {
	Page<MarketIllness> getPage(IllnessSearchCondition condition ,PageRequestParams pageRequestParams) ;
}

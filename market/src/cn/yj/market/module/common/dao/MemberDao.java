package cn.yj.market.module.common.dao;

import cn.yj.market.frame.hibernate.Dao;
import cn.yj.market.frame.page.Page;
import cn.yj.market.frame.page.PageRequestParams;
import cn.yj.market.frame.vo.MarketMember;
import cn.yj.market.module.common.bean.MemberSearchCondition;

public interface MemberDao extends Dao<MarketMember> {
	Page<MarketMember> getPage(MemberSearchCondition condition ,PageRequestParams pageRequestParams) ;
}

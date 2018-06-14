package cn.yj.market.module.common.dao.hibernate;

import org.hibernate.Criteria;
import org.springframework.stereotype.Component;

import cn.yj.market.frame.hibernate.GenericDao;
import cn.yj.market.frame.page.Page;
import cn.yj.market.frame.page.PageRequestParams;
import cn.yj.market.frame.vo.MarketCommonGift;
import cn.yj.market.module.common.dao.CommonGiftDao;

@Component
public class CommonGiftDaoImpl extends GenericDao<MarketCommonGift> implements
		CommonGiftDao {

	@Override
	public Page<MarketCommonGift> getPage(PageRequestParams pageRequestParams) {
		Criteria criteria = getCurrentSession().createCriteria(MarketCommonGift.class) ;
		return pagedQuery(criteria, pageRequestParams.getPageNum(), pageRequestParams.getPageSize()) ;
	}

}

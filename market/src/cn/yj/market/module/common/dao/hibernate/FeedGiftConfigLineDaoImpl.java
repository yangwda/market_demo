package cn.yj.market.module.common.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.yj.market.frame.hibernate.GenericDao;
import cn.yj.market.frame.vo.MarketGiftCTConfigLine;
import cn.yj.market.module.common.dao.FeedGiftConfigLineDao;

@Repository
public class FeedGiftConfigLineDaoImpl extends GenericDao<MarketGiftCTConfigLine> implements FeedGiftConfigLineDao {

	@Override
	public List<MarketGiftCTConfigLine> getLineListByGiftConfigId(
			Long giftConfigId) {
		if (giftConfigId == null) {
			return null ;
		}
		Criteria criteria = getCurrentSession().createCriteria(MarketGiftCTConfigLine.class) ;
		criteria.add( Restrictions.eq("giftConfigId", giftConfigId)) ;
		return list(criteria) ;
	}

}

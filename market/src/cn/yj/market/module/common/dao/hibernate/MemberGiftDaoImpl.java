package cn.yj.market.module.common.dao.hibernate;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import cn.yj.market.frame.hibernate.GenericDao;
import cn.yj.market.frame.vo.MarketMemberGift;
import cn.yj.market.module.common.dao.MemberGiftDao;

@Component
public class MemberGiftDaoImpl extends GenericDao<MarketMemberGift> implements
		MemberGiftDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<MarketMemberGift> getMemberGiftList(Long memberId) {
		
		if (memberId == null) {
			return null ;
		}
		
		Criteria criteria = getSession().createCriteria(MarketMemberGift.class) ;
		criteria.add(Restrictions.eq("memberId", memberId)) ;
		criteria.add(Restrictions.gt("remainingMoney", BigDecimal.ZERO)) ;
		
		return criteria.list();
	}

}

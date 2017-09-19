package cn.yj.market.module.common.dao.hibernate;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cn.yj.market.frame.hibernate.GenericDao;
import cn.yj.market.frame.vo.MarketMemberGiftAccumulation;
import cn.yj.market.module.common.dao.MemberGiftAccumulationDao;

@Repository
public class MumberGiftAccumulationDaoImpl extends GenericDao<MarketMemberGiftAccumulation> implements MemberGiftAccumulationDao {

	@Override
	public void deleteByOrderId(Long orderId) {
		Query q = getSession().createSQLQuery("delete from yj_market_member_gift_accumulation where orderId = :orderId ") ;
		q.setParameter("orderId", orderId) ;
		q.executeUpdate() ;
	}

}

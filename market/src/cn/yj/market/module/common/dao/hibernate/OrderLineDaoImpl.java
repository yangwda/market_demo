package cn.yj.market.module.common.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.yj.market.frame.hibernate.GenericDao;
import cn.yj.market.frame.vo.MarketMemberGiftAccumulation;
import cn.yj.market.frame.vo.MarketOrderGiftLine;
import cn.yj.market.frame.vo.MarketOrderLine;
import cn.yj.market.module.common.dao.OrderLineDao;

@Repository
public class OrderLineDaoImpl extends GenericDao<MarketOrderLine> implements OrderLineDao {

	@Override
	public void deleteByOrderId(Long orderId) {
		Query q = getSession().createSQLQuery("delete from yj_market_order_line where orderId = :orderId ") ;
		q.setParameter("orderId", orderId) ;
		q.executeUpdate() ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MarketOrderLine> queryOrderLines(Long orderId) {
		Criteria criteria = getCurrentSession().createCriteria(MarketOrderLine.class) ;
		criteria.add(Restrictions.eq("orderId", orderId)) ;
		List<MarketOrderLine>  rLines = criteria.list();
		if (rLines != null && !rLines.isEmpty()) {
			for (MarketOrderLine line : rLines) {
				List<MarketMemberGiftAccumulation> mgl = line.getOrderGiftAccLineSet() ;
				if (mgl != null) {
					for (MarketMemberGiftAccumulation mg : mgl) {
						mg.giftInfo() ;
					}
				}
				List<MarketOrderGiftLine> ogl = line.getOrderGiftLineSet() ;
				if (ogl != null) {
					for (MarketOrderGiftLine og : ogl) {
						og.giftInfo() ;
					}
				}
			}
		}
		return rLines ;
	}

}

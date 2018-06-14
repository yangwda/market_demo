package cn.yj.market.module.common.dao.hibernate;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cn.yj.market.frame.hibernate.GenericDao;
import cn.yj.market.frame.vo.MarketOrderGiftLine;
import cn.yj.market.module.common.dao.OrderGiftLineDao;

@Repository
public class OrderGiftLineDaoImpl extends GenericDao<MarketOrderGiftLine> implements OrderGiftLineDao {

	@Override
	public void deleteByOrderId(Long orderId) {
		Query q = getSession().createSQLQuery("delete from yj_market_order_gift_line where orderId = :orderId ") ;
		q.setParameter("orderId", orderId) ;
		q.executeUpdate() ;
	}

}

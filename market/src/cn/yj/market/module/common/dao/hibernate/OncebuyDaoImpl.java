package cn.yj.market.module.common.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import cn.yj.market.frame.hibernate.GenericDao;
import cn.yj.market.frame.vo.MarketOnceBuy;
import cn.yj.market.module.common.dao.OncebuyDao;

@Repository
public class OncebuyDaoImpl extends GenericDao<MarketOnceBuy> implements OncebuyDao {

	@Override
	public List<MarketOnceBuy> getList() {
		Criteria criteria = getCurrentSession().createCriteria(MarketOnceBuy.class) ;
		criteria.addOrder(Order.asc("beginAmount")) ;
		return list(criteria) ;
	}

}

package cn.yj.market.module.common.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import cn.yj.market.frame.hibernate.GenericDao;
import cn.yj.market.frame.vo.MarketCallBack;
import cn.yj.market.module.common.dao.CallbackDao;

@Component
public class CallbackDaoImpl extends GenericDao<MarketCallBack> implements CallbackDao {

	@Override
	public List<MarketCallBack> getCallbackList() {
		Criteria criteria = getCurrentSession().createCriteria(MarketCallBack.class) ;
		criteria.add(Restrictions.isNull("callBackOverTime"));
		List<MarketCallBack>  rLines = criteria.list();
		return rLines ;
	}

}

package cn.yj.market.module.common.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Component;

import cn.yj.market.frame.hibernate.GenericDao;
import cn.yj.market.frame.vo.MarketCallBack;
import cn.yj.market.module.common.dao.CallbackDao;

@Component
public class CallbackDaoImpl extends GenericDao<MarketCallBack> implements CallbackDao {

	@Override
	public List<MarketCallBack> getCallbackList() {
		return null;
	}

}

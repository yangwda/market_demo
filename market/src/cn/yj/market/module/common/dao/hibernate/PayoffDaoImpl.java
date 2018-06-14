package cn.yj.market.module.common.dao.hibernate;

import org.springframework.stereotype.Component;

import cn.yj.market.frame.hibernate.GenericDao;
import cn.yj.market.frame.vo.MarketPayoff;
import cn.yj.market.module.common.dao.PayoffDao;

@Component
public class PayoffDaoImpl extends GenericDao<MarketPayoff> implements
		PayoffDao {

}

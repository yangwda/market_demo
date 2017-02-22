package cn.yj.market.module.common.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Component;

import cn.yj.market.frame.hibernate.GenericDao;
import cn.yj.market.frame.vo.MarketUser;
import cn.yj.market.module.common.dao.UserDao;

@Component
public class UserDaoImpl extends GenericDao<MarketUser> implements UserDao {

	@Override
	public List<MarketUser> getUserList() {
		return getAll();
	}

}

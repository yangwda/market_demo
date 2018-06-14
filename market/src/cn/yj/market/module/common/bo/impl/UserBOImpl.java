package cn.yj.market.module.common.bo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.yj.market.frame.bo.BaseBo;
import cn.yj.market.frame.vo.MarketUser;
import cn.yj.market.module.common.bo.UserBO;
import cn.yj.market.module.common.dao.UserDao;

@Service
public class UserBOImpl extends BaseBo implements UserBO {
	
	@Autowired
	private UserDao userDao;

	@Override
	public List<MarketUser> getUser() {
		return userDao.getUserList();
	}

}

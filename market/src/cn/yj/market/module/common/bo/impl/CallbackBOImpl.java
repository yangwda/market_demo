package cn.yj.market.module.common.bo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.yj.market.frame.bo.BaseBo;
import cn.yj.market.frame.vo.MarketCallBack;
import cn.yj.market.module.common.bo.CallbackBO;
import cn.yj.market.module.common.dao.CallbackDao;

@Service
public class CallbackBOImpl extends BaseBo implements CallbackBO {
	
	@Autowired
	private CallbackDao callbackDao;

	@Override
	public List<MarketCallBack> getCallBackList() {
		return callbackDao.getCallbackList();
	}
	
}

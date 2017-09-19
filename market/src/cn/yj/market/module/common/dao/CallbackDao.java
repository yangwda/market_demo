package cn.yj.market.module.common.dao;

import java.util.List;

import cn.yj.market.frame.hibernate.Dao;
import cn.yj.market.frame.vo.MarketCallBack;

public interface CallbackDao extends Dao<MarketCallBack> {
	List<MarketCallBack> getCallbackList() ;
}

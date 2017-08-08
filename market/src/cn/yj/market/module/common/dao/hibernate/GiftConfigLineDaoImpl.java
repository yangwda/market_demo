package cn.yj.market.module.common.dao.hibernate;

import org.springframework.stereotype.Repository;

import cn.yj.market.frame.hibernate.GenericDao;
import cn.yj.market.frame.vo.MarketGiftConfigLine;
import cn.yj.market.module.common.dao.GiftConfigLineDao;

@Repository
public class GiftConfigLineDaoImpl extends GenericDao<MarketGiftConfigLine> implements GiftConfigLineDao {

}

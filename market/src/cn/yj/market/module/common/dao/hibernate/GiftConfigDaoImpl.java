package cn.yj.market.module.common.dao.hibernate;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.yj.market.frame.hibernate.GenericDao;
import cn.yj.market.frame.page.Page;
import cn.yj.market.frame.page.PageRequestParams;
import cn.yj.market.frame.vo.MarketGiftConfig;
import cn.yj.market.module.common.bean.GiftConfigSearchCondition;
import cn.yj.market.module.common.dao.GiftConfigDao;

@Repository
public class GiftConfigDaoImpl extends GenericDao<MarketGiftConfig> implements GiftConfigDao {

	@Override
	public Page<MarketGiftConfig> getPage(GiftConfigSearchCondition condition,
			PageRequestParams pageRequestParams) {
		Criteria criteria = getCurrentSession().createCriteria(MarketGiftConfig.class) ;
		if (condition != null) {
			if (StringUtils.isNotBlank(condition.getGoodsName())) {
				String goodsName = null;
				try {
					goodsName = new String(condition.getGoodsName().getBytes(),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					LOGGER.error("## ERROR in search goods ,use name condition convert to utf8 string .. ", e);
				}
				criteria.add( Restrictions.like("goodsName", "%"+goodsName+"%")) ;
			}
			if (StringUtils.isNotBlank(condition.getGoodsNo())) {
				criteria.add( Restrictions.like("goodsNo", "%"+condition.getGoodsNo()+"%")) ;
			}
			if (StringUtils.isNotBlank(condition.getDateCondition())) {
				if ("在用".equals(condition.getDateCondition())) {
					criteria.add( Restrictions.sqlRestriction(" giftConfigBeginTime <= SYSDATE()  and SYSDATE() <= giftConfigEndTime  ")) ;
				}
				if ("未开始".equals(condition.getDateCondition())) {
					criteria.add( Restrictions.sqlRestriction(" SYSDATE() < giftConfigBeginTime ")) ;
				}
				if ("过期".equals(condition.getDateCondition())) {
					criteria.add( Restrictions.sqlRestriction(" SYSDATE() > giftConfigEndTime ")) ;
				}
			}
		}
		
		criteria.addOrder(Order.asc("goodsId")) ;
		Page<MarketGiftConfig> page =  pagedQuery(criteria, pageRequestParams.getPageNum(), pageRequestParams.getPageSize()) ;
		
		if (page.getRecords() != null) {
			for (MarketGiftConfig config : page.getRecords()) {
				config.getLine() ;
			}
		}
		
		return page ;
	}

	@Override
	public List<MarketGiftConfig> queryGiftConfigListByGoodsIdList(
			List<Long> giftGoodsIdList) {
		if (giftGoodsIdList == null || giftGoodsIdList.isEmpty()) {
			return null ;
		}
		Criteria criteria = getCurrentSession().createCriteria(MarketGiftConfig.class) ;
		criteria.add( Restrictions.in("goodsId", giftGoodsIdList)) ;
		criteria.add( Restrictions.sqlRestriction(" giftConfigBeginTime <= SYSDATE()  and SYSDATE() <= giftConfigEndTime  ")) ;
		criteria.addOrder(Order.asc("giftConfigBeginTime")) ;
		List<MarketGiftConfig> gl = criteria.list() ;
		if (gl != null) {
			for (MarketGiftConfig config : gl) {
				config.getLine() ;
			}
		}
		return gl;
	}
}

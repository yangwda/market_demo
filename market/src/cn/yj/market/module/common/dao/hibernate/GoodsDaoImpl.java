package cn.yj.market.module.common.dao.hibernate;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.yj.market.frame.hibernate.GenericDao;
import cn.yj.market.frame.page.Page;
import cn.yj.market.frame.page.PageRequestParams;
import cn.yj.market.frame.vo.MarketGoods;
import cn.yj.market.module.common.bean.GoodsSearchCondition;
import cn.yj.market.module.common.dao.GoodsDao;

@Repository
public class GoodsDaoImpl extends GenericDao<MarketGoods> implements GoodsDao {

	@Override
	public Page<MarketGoods> getPage(GoodsSearchCondition condition,
			PageRequestParams pageRequestParams) {
		Criteria criteria = getCurrentSession().createCriteria(MarketGoods.class) ;
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
			if (StringUtils.isNotBlank(condition.getCommon())) {
				String common = null;
				try {
					common = new String(condition.getCommon().getBytes(),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					LOGGER.error("## ERROR in search goods ,use common condition convert to utf8 string .. ", e);
				}
				criteria.add(Restrictions.disjunction()
						.add(Restrictions.like("goodsManufacturer", "%"+common+"%"))
						.add(Restrictions.like("goodsUsage", "%"+common+"%"))
						.add(Restrictions.like("goodsRemark", "%"+common+"%"))
						.add(Restrictions.like("common", "%"+common+"%"))
						) ;
			}
			if (StringUtils.isNotBlank(condition.getGoodsType())) {
				criteria.add(Restrictions.eq("goodsType", condition.getGoodsType())) ;
			}
			if (StringUtils.isNotBlank(condition.getGoodsStatus())) {
				criteria.add(Restrictions.eq("goodsStatus", condition.getGoodsStatus())) ;
			}
		}
		
		criteria.addOrder(Order.asc("goodsId")) ;
		Page<MarketGoods> page =  pagedQuery(criteria, pageRequestParams.getPageNum(), pageRequestParams.getPageSize()) ;
		
		if (page.getRecords() != null) {
			for (MarketGoods goods : page.getRecords()) {
				goods.getPunit1() ;
			}
		}
		
		return page ;
	}

}

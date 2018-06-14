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
import cn.yj.market.frame.vo.MarketAnimal;
import cn.yj.market.module.common.bean.AnimalSearchCondition;
import cn.yj.market.module.common.dao.AnimalDao;

@Repository
public class AnimalDaoImpl extends GenericDao<MarketAnimal> implements AnimalDao {

	@Override
	public Page<MarketAnimal> getPage(AnimalSearchCondition condition,
			PageRequestParams pageRequestParams) {
		Criteria criteria = getCurrentSession().createCriteria(MarketAnimal.class) ;
		if (condition != null) {
			if (StringUtils.isNotBlank(condition.getAnimalName())) {
				String animalName = null;
				try {
					animalName = new String(condition.getAnimalName().getBytes(),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					LOGGER.error("## ERROR in search goods ,use name condition convert to utf8 string .. ", e);
				}
				criteria.add( Restrictions.like("animalName", "%"+animalName+"%")) ;
			}
		}
		
		criteria.addOrder(Order.asc("animalId")) ;
		return pagedQuery(criteria, pageRequestParams.getPageNum(), pageRequestParams.getPageSize()) ;
	}

}

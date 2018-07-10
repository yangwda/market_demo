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
import cn.yj.market.frame.vo.MarketIllness;
import cn.yj.market.module.common.bean.IllnessSearchCondition;
import cn.yj.market.module.common.dao.IllnessDao;

@Repository
public class IllnessDaoImpl extends GenericDao<MarketIllness> implements IllnessDao {

	@Override
	public Page<MarketIllness> getPage(IllnessSearchCondition condition,
			PageRequestParams pageRequestParams) {
		Criteria criteria = getCurrentSession().createCriteria(MarketIllness.class) ;
		if (condition != null) {
			if (StringUtils.isNotBlank(condition.getIllnessName())) {
				String illnessName = null;
				try {
					illnessName = new String(condition.getIllnessName().getBytes(),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					LOGGER.error("## ERROR in search goods ,use name condition convert to utf8 string .. ", e);
				}
				criteria.add( Restrictions.like("illnessName", "%"+illnessName+"%")) ;
			}
			if (StringUtils.isNotBlank(condition.getCallBack())) {
				criteria.add(Restrictions.eq("callBack", condition.getCallBack())) ;
			}
		}
		
		criteria.addOrder(Order.asc("illnessId")) ;
		return pagedQuery(criteria, pageRequestParams.getPageNum(), pageRequestParams.getPageSize()) ;
	}

}

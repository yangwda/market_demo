package cn.yj.market.module.common.dao.hibernate;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.yj.market.frame.hibernate.GenericDao;
import cn.yj.market.frame.page.Page;
import cn.yj.market.frame.page.PageRequestParams;
import cn.yj.market.frame.util.CoreUtils;
import cn.yj.market.frame.vo.MarketOrder;
import cn.yj.market.module.common.bean.MemberGiftCheckSearchCondition;
import cn.yj.market.module.common.bean.OrderSearchCondition;
import cn.yj.market.module.common.dao.OrderDao;

@Repository
public class OrderDaoImpl extends GenericDao<MarketOrder> implements OrderDao {

	@Override
	public Page<MarketOrder> getPage(OrderSearchCondition condition,
			PageRequestParams pageRequestParams) {
		Criteria criteria = getCurrentSession().createCriteria(MarketOrder.class) ;
		if (condition != null) {
			if (StringUtils.isNotBlank(condition.getMemberNo())) {
				criteria.add( Restrictions.eq("memberNo", condition.getMemberNo())) ;
			}
			if (CoreUtils.isDate(condition.getBeginDate())) {
				criteria.add( Restrictions.ge("createTime", CoreUtils.parseDate(condition.getBeginDate()))) ;
			}
			if (CoreUtils.isDate(condition.getEndDate())) {
				criteria.add( Restrictions.ge("createTime", CoreUtils.parseDate(condition.getEndDate()))) ;
			}
			if (StringUtils.isNotBlank(condition.getOrderRemark())) {
				criteria.add( Restrictions.like("orderRemark", "%"+condition.getOrderRemark()+"%")) ;
			}
			if (StringUtils.isNotBlank(condition.getOrderType())) {
				criteria.add( Restrictions.eq("orderType", condition.getOrderType())) ;
			}
		}
		
		criteria.addOrder(Order.desc("orderId")) ;
		Page<MarketOrder> page =  pagedQuery(criteria, pageRequestParams.getPageNum(), pageRequestParams.getPageSize()) ;
//		if (page.getRecords() != null) {
//			for (MarketOrder order : page.getRecords()) {
//				order.getOrderLineSet();
//			}
//		}
		return page ;
	}

	@Override
	public Page<MarketOrder> getMemberGiftCheckPageList(MemberGiftCheckSearchCondition condition,
			PageRequestParams pageRequestParams) {
		Criteria criteria = getCurrentSession().createCriteria(MarketOrder.class) ;
		if (condition != null) {
			if (CoreUtils.isLong(condition.getMemberId())) {
				criteria.add( Restrictions.eq("memberId", Long.valueOf(condition.getMemberId()))) ;
			}
			if (StringUtils.isNotBlank(condition.getMemberNo())) {
				criteria.add( Restrictions.eq("memberNo", condition.getMemberNo())) ;
			}
			if (StringUtils.isNotBlank(condition.getMemberName())) {
				criteria.add( Restrictions.like("memberName", "%" + condition.getMemberName() + "%")) ;
			}
		}
		criteria.add( Restrictions.gt("giftCheckAmount", BigDecimal.ZERO)) ;
		
		criteria.addOrder(Order.desc("orderId")) ;
		Page<MarketOrder> page =  pagedQuery(criteria, pageRequestParams.getPageNum(), pageRequestParams.getPageSize()) ;
		return page ;
	}
}

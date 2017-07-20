package cn.yj.market.module.common.dao.hibernate;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import cn.yj.market.frame.hibernate.GenericDao;
import cn.yj.market.frame.page.Page;
import cn.yj.market.frame.page.PageRequestParams;
import cn.yj.market.frame.vo.MarketMember;
import cn.yj.market.module.common.bean.MemberSearchCondition;
import cn.yj.market.module.common.dao.MemberDao;

@Component
public class MemberDaoImpl extends GenericDao<MarketMember> implements
		MemberDao {

	@Override
	public Page<MarketMember> getPage(MemberSearchCondition condition,
			PageRequestParams pageRequestParams) {
		
		Criteria criteria = getCurrentSession().createCriteria(MarketMember.class) ;
		if (condition != null) {
			if (StringUtils.isNotBlank(condition.getMemberName())) {
				String memberName = null;
				try {
					memberName = new String(condition.getMemberName().getBytes(),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					LOGGER.error("## ERROR in search member ,use name condition convert to utf8 string .. ", e);
				}
				if (StringUtils.isNotBlank(memberName)) {
					criteria.add( Restrictions.like("memberName", "%"+memberName+"%")) ;
				}
			}
			if (StringUtils.isNotBlank(condition.getMemberNo())) {
				criteria.add( Restrictions.like("memberNo", "%"+condition.getMemberNo()+"%")) ;
			}
			if (StringUtils.isNotBlank(condition.getMemberAddress())) {
				String memberAddress = null;
				try {
					memberAddress = new String(condition.getMemberAddress().getBytes(),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					LOGGER.error("## ERROR in search member ,use address condition convert to utf8 string .. " ,e);
				}
				if (StringUtils.isNotBlank(memberAddress)) {
					criteria.add( Restrictions.like("memberAddress", "%"+memberAddress+"%")) ;
				}
			}
			if (condition.getMemberPhone() != null) {
				criteria.add(Restrictions.disjunction()
						.add(Restrictions.like("memberPhone", "%"+condition.getMemberPhone()+"%"))
						.add(Restrictions.like("memberTel", "%"+condition.getMemberPhone()+"%"))
				) ;
			}
		}
		
		criteria.addOrder(Order.asc("memberId")) ;
		return pagedQuery(criteria, pageRequestParams.getPageNum(), pageRequestParams.getPageSize()) ;
	}

}

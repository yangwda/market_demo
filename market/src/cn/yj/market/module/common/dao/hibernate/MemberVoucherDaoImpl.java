package cn.yj.market.module.common.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import cn.yj.market.frame.hibernate.GenericDao;
import cn.yj.market.frame.vo.MarketMemberVoucher;
import cn.yj.market.module.common.dao.MemberVoucherDao;

@Component
public class MemberVoucherDaoImpl extends GenericDao<MarketMemberVoucher> implements
		MemberVoucherDao {
	@Override
	public List<MarketMemberVoucher> queryOrderVoucherList(Long orderId) {
		Criteria criteria = getCurrentSession().createCriteria(MarketMemberVoucher.class) ;
		criteria.add(Restrictions.eq("sourceOrderId", orderId)) ;
		List<MarketMemberVoucher>  rLines = criteria.list();
		return rLines ;
	}
}

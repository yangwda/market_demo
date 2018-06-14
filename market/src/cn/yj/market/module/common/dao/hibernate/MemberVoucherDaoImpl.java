package cn.yj.market.module.common.dao.hibernate;

import java.math.BigDecimal;
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
		@SuppressWarnings("unchecked")
		List<MarketMemberVoucher>  rLines = criteria.list();
		return rLines ;
	}
	
	@Override
	public BigDecimal getTotalAvlVoucher(Long memberId) {
		BigDecimal rt = BigDecimal.ZERO ;
		if (memberId == null) {
			return rt;
		}
		Criteria criteria = getCurrentSession().createCriteria(MarketMemberVoucher.class) ;
		criteria.add(Restrictions.eq("memberId", memberId)) ;
		@SuppressWarnings("unchecked")
		List<MarketMemberVoucher>  rLines = criteria.list();
		if (rLines != null) {
			for (MarketMemberVoucher voucher : rLines) {
				if (voucher.getRemainingMoney() != null) {
					rt = rt.add(voucher.getRemainingMoney()) ;
				}
			}
		}
		return rt;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MarketMemberVoucher> getMemberVoucherList(Long memberId) {
		if (memberId == null) {
			return null ;
		}
		Criteria criteria = getCurrentSession().createCriteria(MarketMemberVoucher.class) ;
		criteria.add(Restrictions.eq("memberId", memberId)) ;
		criteria.add(Restrictions.gt("remainingMoney", BigDecimal.ZERO)) ;
		
		return criteria.list();
	}
}

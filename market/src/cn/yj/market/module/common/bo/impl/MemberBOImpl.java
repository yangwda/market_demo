package cn.yj.market.module.common.bo.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.yj.market.frame.bo.BaseBo;
import cn.yj.market.frame.exception.RunException;
import cn.yj.market.frame.page.Page;
import cn.yj.market.frame.page.PageRequestParams;
import cn.yj.market.frame.util.CoreUtils;
import cn.yj.market.frame.vo.MarketMember;
import cn.yj.market.frame.vo.MarketMemberNoSeq;
import cn.yj.market.module.common.bean.MemberCheckReportBean;
import cn.yj.market.module.common.bean.MemberSearchCondition;
import cn.yj.market.module.common.bo.MemberBO;
import cn.yj.market.module.common.dao.MemberDao;
import cn.yj.market.module.common.dao.MemberNoSeqDao;

@Service
public class MemberBOImpl extends BaseBo implements MemberBO {
	
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private MemberNoSeqDao noSeqDao;

	@Override
	public Page<MarketMember> getPage(MemberSearchCondition condition,
			PageRequestParams pageRequestParams) {
		return memberDao.getPage(condition, pageRequestParams);
	}

	@Override
	public MarketMember saveMember(MarketMember member) {
		MarketMemberNoSeq noSeq = noSeqDao.loadAndLockNowait(1L);
		Long seq = noSeq.getMemberNoSeq() ;
		noSeq.setMemberNoSeq(seq.longValue() + 1L);
		noSeqDao.update(noSeq);
		member.setMemberNo(CoreUtils.generateMemberNo(seq));
		member.setCreateTime(new Date());
		Long memberId = (Long) memberDao.save(member);
		member.setMemberId(memberId);
		return member ;
	}

	@Override
	public void updateMember(Long memberId ,String memberName, String memberTel,
			String memberPhone, String memberQQ, String memberWeixin,
			String memberAddress, String memberBusiRemark, String common) {
		if (memberId == null) {
			throw new RunException("请指定会员ID！") ;
		}
		MarketMember member = memberDao.get(memberId) ;
		if (member == null) {
			throw new RunException("指定会员["+memberId+"]不存在！") ;
		}
		if (StringUtils.isNotBlank(common)) {
			member.setCommon(common);
		}
		if (StringUtils.isNotBlank(memberBusiRemark)) {
			member.setMemberBusiRemark(memberBusiRemark);
		}
		if (StringUtils.isNotBlank(memberAddress)) {
			member.setMemberAddress(memberAddress);
		}
		if (StringUtils.isNotBlank(memberWeixin)) {
			member.setMemberWeixin(memberWeixin);
		}
		if (StringUtils.isNotBlank(memberName)) {
			member.setMemberName(memberName);
		}
		if (StringUtils.isNotBlank(memberQQ)) {
			member.setMemberQQ(memberQQ);
		}
		if (StringUtils.isNotBlank(memberPhone)) {
			member.setMemberPhone(memberPhone);
		}
		if (StringUtils.isNotBlank(memberTel)) {
			member.setMemberTel(memberTel);
		}
		memberDao.update(member);
	}

	@Override
	public MarketMember getByMemberId(Long memberId) {
		return memberDao.load(memberId);
	}
	
	@Override
	public List<MemberCheckReportBean> getMemberCheckReport(Long memberId) {
		StringBuilder sql = new StringBuilder() ;
		sql.append("select m.memberNo,m.memberName,m.memberAddress,m.memberPhone,m.memberTel,").append(" \n") ;
		sql.append("		IFNULL(o.ljxse,0) ljxse,IFNULL(o.ljfke,0) ljfke,IFNULL(o.ljml,0) ljml,").append(" \n");
		sql.append("		IFNULL(o.ljsydjq,0) ljsydjq,IFNULL(o.ljdjqze,0) ljdjqze,IFNULL(o.ljwsydjq,0) ljwsydjq,").append(" \n") ;
		sql.append("		IFNULL(o.nmlj,0) nmlj,IFNULL(o.dzspljze,0) dzspljze,IFNULL(o.dzspljydh,0) dzspljydh,IFNULL(o.dzspljwdh,0) dzspljwdh ").append(" \n") ;
		sql.append("from yj_market_member m left join (").append(" \n") ;
		sql.append("	select io.memberId,SUM(IFNULL(io.orderTotalMoney,0)) ljxse, SUM(ifnull(io.payOffCashTotalMoney,0)) ljfke ,").append(" \n") ;
		sql.append("				SUM(ifnull(io.orderCutMoney,0)) ljml ,SUM(IFNULL(io.payOffVoucherTotalMoney,0)) ljsydjq,").append(" \n") ;
		sql.append("				SUM(IFNULL(io.orderGiftVoucherTotalMoney,0)) ljdjqze,").append(" \n") ;
		sql.append("				SUM(IFNULL(io.orderGiftVoucherTotalMoney,0) - IFNULL(io.payOffVoucherTotalMoney,0)) ljwsydjq,").append(" \n") ;
		sql.append("				SUM(IFNULL(io.yearAccumulationMoney,0)) nmlj,SUM(IFNULL(io.orderTotalGiftAmount,0)) dzspljze ,").append(" \n") ;
		sql.append("				SUM(IFNULL(io.giftCheckAmount,0)) dzspljydh ,").append(" \n") ;
		sql.append("				SUM(IFNULL(io.orderTotalGiftAmount,0) - IFNULL(io.giftCheckAmount,0)) dzspljwdh").append(" \n") ;
		sql.append("	from yj_market_order io group by io.memberId").append(" \n") ;
		sql.append(") o on(o.memberId = m.memberId) ").append(" \n") ;
		if (memberId != null) {
			sql.append("where m.memberId = ").append(memberId).append(" \n") ;
		}
		System.out.println(sql.toString());
		return memberDao.getListBeanBySql(sql.toString(), MemberCheckReportBean.class);
	}
}

package cn.yj.market.module.common.bo.impl;

import java.util.Date;

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

}

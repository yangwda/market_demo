package cn.yj.market.module.common.bo;

import java.util.List;

import cn.yj.market.frame.page.Page;
import cn.yj.market.frame.page.PageRequestParams;
import cn.yj.market.frame.vo.MarketMember;
import cn.yj.market.module.common.bean.MemberCheckReportBean;
import cn.yj.market.module.common.bean.MemberSearchCondition;

public interface MemberBO {
	Page<MarketMember> getPage(MemberSearchCondition condition ,PageRequestParams pageRequestParams) ;
	MarketMember saveMember(MarketMember member) ;
	void updateMember(Long memberId ,String memberName ,String memberTel ,String memberPhone ,String memberQQ ,
			String memberWeixin ,String memberAddress ,String memberBusiRemark ,String common);
	MarketMember getByMemberId(Long memberId);
	List<MemberCheckReportBean> getMemberCheckReport(Long memberId);
}

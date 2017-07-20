package cn.yj.market.module.common.bo;

import cn.yj.market.frame.page.Page;
import cn.yj.market.frame.page.PageRequestParams;
import cn.yj.market.frame.vo.MarketIllness;
import cn.yj.market.module.common.bean.IllnessSearchCondition;

public interface IllnessBO {
	Page<MarketIllness> getPage(IllnessSearchCondition condition ,PageRequestParams pageRequestParams) ;
	MarketIllness saveIllness(MarketIllness illness) ;
	void updateIllness(Long illnessId ,String illnessName ,String callBack );
}

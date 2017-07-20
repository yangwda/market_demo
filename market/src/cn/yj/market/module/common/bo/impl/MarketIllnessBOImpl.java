package cn.yj.market.module.common.bo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.yj.market.frame.bo.BaseBo;
import cn.yj.market.frame.page.Page;
import cn.yj.market.frame.page.PageRequestParams;
import cn.yj.market.frame.vo.MarketIllness;
import cn.yj.market.module.common.bean.IllnessSearchCondition;
import cn.yj.market.module.common.bo.IllnessBO;
import cn.yj.market.module.common.dao.IllnessDao;

@Service
public class MarketIllnessBOImpl extends BaseBo implements IllnessBO {
	
	@Autowired
	private IllnessDao illnessDao;

	@Override
	public Page<MarketIllness> getPage(IllnessSearchCondition condition,
			PageRequestParams pageRequestParams) {
		return illnessDao.getPage(condition, pageRequestParams);
	}

	@Override
	public MarketIllness saveIllness(MarketIllness illness) {
		Long illnessId = (Long) illnessDao.save(illness) ;
		illness.setIllnessId(illnessId);
		return illness;
	}

	@Override
	public void updateIllness(Long illnessId, String illnessName,
			String callBack) {
		MarketIllness illness = illnessDao.loadAndLockNowait(illnessId) ;
		if (illness != null) {
			illness.setIllnessName(illnessName);
			illness.setCallBack(callBack);
			illnessDao.update(illness);
		}
	}
}

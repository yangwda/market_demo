package cn.yj.market.module.common.bo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.yj.market.frame.bo.BaseBo;
import cn.yj.market.frame.vo.MarketCommonGift;
import cn.yj.market.module.common.bo.CommonGiftBO;
import cn.yj.market.module.common.dao.CommonGiftDao;

@Service
public class CommonGiftBOImpl extends BaseBo implements CommonGiftBO {
	
	@Autowired
	private CommonGiftDao commonGiftDao;

	@Override
	public List<MarketCommonGift> getAll() {
		return commonGiftDao.getAll();
	}

	@Override
	public MarketCommonGift saveCommonGift(MarketCommonGift commonGift) {
		Long memberId = (Long) commonGiftDao.save(commonGift);
		commonGift.setCommonGiftId(memberId);
		return commonGift ;
	}

	@Override
	public void deleteCommonGifg(Long commonGiftId) {
		if (commonGiftId == null) {
			return ;
		}
		MarketCommonGift entity = commonGiftDao.get(commonGiftId) ;
		commonGiftDao.delete(entity);;
	}


}

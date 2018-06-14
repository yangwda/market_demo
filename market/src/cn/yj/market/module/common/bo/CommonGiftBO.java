package cn.yj.market.module.common.bo;

import java.util.List;

import cn.yj.market.frame.vo.MarketCommonGift;

public interface CommonGiftBO {
	List<MarketCommonGift> getAll() ;
	MarketCommonGift saveCommonGift(MarketCommonGift commonGift) ;
	void deleteCommonGifg(Long commonGiftId);
}

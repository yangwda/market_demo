package cn.yj.market.module.common.bo;

import java.util.List;

import cn.yj.market.frame.page.Page;
import cn.yj.market.frame.page.PageRequestParams;
import cn.yj.market.frame.vo.MarketGoods;
import cn.yj.market.module.common.bean.GoodsSearchCondition;

public interface GoodsBO {
	Page<MarketGoods> getPage(GoodsSearchCondition condition ,PageRequestParams pageRequestParams) ;
	MarketGoods saveGoods(MarketGoods goods) ;
	void updateGoods(Long goodsId ,String goodsName ,String goodsNo ,String goodsManufacturer ,String goodsUsage ,
			String goodsType ,String goodsRemark ,String common ,String punit1,String punit2,String punit3);
	void modifyGoodsStatus(Long goodsId ,String goodsStatus) ;
	List<MarketGoods> getGoodsAutofill(String likeStr);
	List<MarketGoods> getGiftGoodsAutofill(String likeStr);
	List<MarketGoods> getFeedGoodsAutofill(String likeStr);
	MarketGoods getGoodsById(Long goodsId);
	List<MarketGoods> getBuyGoodsAutofill(String likeStr);
	List<MarketGoods> queryGoodsList(List<Long> goodsIdList);
}

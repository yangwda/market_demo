package cn.yj.market.module.common.bo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.yj.market.frame.bo.BaseBo;
import cn.yj.market.frame.page.Page;
import cn.yj.market.frame.page.PageRequestParams;
import cn.yj.market.frame.vo.MarketGoods;
import cn.yj.market.module.common.bean.GoodsSearchCondition;
import cn.yj.market.module.common.bo.GoodsBO;
import cn.yj.market.module.common.dao.GoodsDao;

@Service
public class MarketGoodsBOImpl extends BaseBo implements GoodsBO {
	
	@Autowired
	private GoodsDao goodsDao;

	@Override
	public Page<MarketGoods> getPage(GoodsSearchCondition condition,
			PageRequestParams pageRequestParams) {
		return goodsDao.getPage(condition, pageRequestParams);
	}

	@Override
	public MarketGoods saveGoods(MarketGoods goods) {
		Long goodsId = (Long) goodsDao.save(goods) ;
		goods.setGoodsId(goodsId);
		return goods;
	}

	@Override
	public void updateGoods(Long goodsId, String goodsName, String goodsNo,
			String goodsManufacturer, String goodsUsage, String goodsType,
			String goodsRemark, String common) {
		MarketGoods goods = goodsDao.loadAndLockNowait(goodsId) ;
		if (goods != null) {
			goods.setCommon(common);
			goods.setGoodsRemark(goodsRemark);
			goods.setGoodsType(goodsType);
			goods.setGoodsUsage(goodsUsage);
			goods.setGoodsManufacturer(goodsManufacturer);
			goods.setGoodsNo(goodsNo);
			goods.setGoodsName(goodsName);
			goodsDao.update(goods);
		}
	}

	@Override
	public void modifyGoodsStatus(Long goodsId, String goodsStatus) {
		MarketGoods goods = goodsDao.loadAndLockNowait(goodsId) ;
		if (goods != null) {
			goods.setGoodsStatus(goodsStatus);
			goodsDao.update(goods);
		}
	}
}

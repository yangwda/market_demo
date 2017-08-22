package cn.yj.market.module.common.bo.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.yj.market.frame.bo.BaseBo;
import cn.yj.market.frame.page.Page;
import cn.yj.market.frame.page.PageRequestParams;
import cn.yj.market.frame.vo.MarketGoods;
import cn.yj.market.frame.vo.MarketGoodsUnitPrice;
import cn.yj.market.module.common.bean.GoodsSearchCondition;
import cn.yj.market.module.common.bo.GoodsBO;
import cn.yj.market.module.common.dao.GoodsDao;
import cn.yj.market.module.common.dao.GoodsUnitPriceDao;

@Service
public class MarketGoodsBOImpl extends BaseBo implements GoodsBO {
	
	@Autowired
	private GoodsDao goodsDao;
	
	@Autowired
	private GoodsUnitPriceDao unitPriceDao;

	@Override
	public Page<MarketGoods> getPage(GoodsSearchCondition condition,
			PageRequestParams pageRequestParams) {
		return goodsDao.getPage(condition, pageRequestParams);
	}

	@Override
	public MarketGoods saveGoods(MarketGoods goods) {
		Long goodsId = (Long) goodsDao.save(goods) ;
		goods.setGoodsId(goodsId);
		if (StringUtils.isNotBlank(goods.getPunit1())) {
			String[] a1 = goods.getPunit1().split("\\*") ;
			String[] a2 = goods.getPunit2().split("\\*") ;
			String[] a3 = goods.getPunit3().split("\\*") ;
			for (int i = 0; i < a3.length; i++) {
				MarketGoodsUnitPrice up = new MarketGoodsUnitPrice() ;
				up.setGoodsId(goodsId);
				up.setGoodsUnitName(a2[i]);
				up.setGoodsUnitPrice(new BigDecimal(a3[i]));
				up.setGoodsUnitRate(Integer.parseInt(a1[i]));
				unitPriceDao.save(up) ;
			}
		}
		return goods;
	}

	@Override
	public void updateGoods(Long goodsId, String goodsName, String goodsNo,
			String goodsManufacturer, String goodsUsage, String goodsType,
			String goodsRemark, String common,String punit1,String punit2,String punit3) {
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
			
			List<MarketGoodsUnitPrice> upl = unitPriceDao
					.find("from cn.yj.market.frame.vo.MarketGoodsUnitPrice where goodsId=?",
							goods.getGoodsId());
			if (upl != null) {
				for (MarketGoodsUnitPrice marketGoodsUnitPrice : upl) {
					unitPriceDao.remove(marketGoodsUnitPrice);
				}
			}
			
			if (StringUtils.isNotBlank(punit1)) {
				String[] a1 = punit1.split("\\*") ;
				String[] a2 = punit2.split("\\*") ;
				String[] a3 = punit3.split("\\*") ;
				for (int i = 0; i < a3.length; i++) {
					MarketGoodsUnitPrice up = new MarketGoodsUnitPrice() ;
					up.setGoodsId(goodsId);
					up.setGoodsUnitName(a2[i]);
					up.setGoodsUnitPrice(new BigDecimal(a3[i]));
					up.setGoodsUnitRate(Integer.parseInt(a1[i]));
					unitPriceDao.save(up) ;
				}
			}
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
	
	@Override
	public List<MarketGoods> getGiftGoodsAutofill(String likeStr) {
		return goodsDao.getGiftGoodsAutofill(likeStr);
	}
	
	@Override
	public List<MarketGoods> getGoodsAutofill(String likeStr) {
		return goodsDao.getGoodsAutofill(likeStr);
	}

	@Override
	public List<MarketGoods> getFeedGoodsAutofill(String likeStr) {
		return goodsDao.getFeedGoodsAutofill(likeStr);
	}

	@Override
	public MarketGoods getGoodsById(Long goodsId) {
		if (goodsId == null) {
			return null ;
		}
		MarketGoods r = goodsDao.get(goodsId);
		if (r != null) {
			r.getPunit2() ;
		}
		return r;
	}
}

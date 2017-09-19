package cn.yj.market.module.common.bo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.yj.market.frame.bo.BaseBo;
import cn.yj.market.frame.page.Page;
import cn.yj.market.frame.page.PageRequestParams;
import cn.yj.market.frame.vo.MarketGiftConfig;
import cn.yj.market.frame.vo.MarketGiftConfigLine;
import cn.yj.market.module.common.bean.GiftConfigSearchCondition;
import cn.yj.market.module.common.bo.GiftConfigBO;
import cn.yj.market.module.common.dao.GiftConfigDao;
import cn.yj.market.module.common.dao.GiftConfigLineDao;

@Service
public class MarketGiftConfigBOImpl extends BaseBo implements GiftConfigBO {
	
	@Autowired
	private GiftConfigDao configDao;
	
	@Autowired
	private GiftConfigLineDao lineDao;

	@Override
	public Page<MarketGiftConfig> getPage(GiftConfigSearchCondition condition,
			PageRequestParams pageRequestParams) {
		return configDao.getPage(condition, pageRequestParams);
	}

	@Override
	public MarketGiftConfig saveGiftConfig(MarketGiftConfig cfg,
			MarketGiftConfigLine line) {
		Long configId = (Long) configDao.save(cfg) ;
		cfg.setGiftConfigId(configId);
		line.setGiftConfigId(configId);
		lineDao.save(line) ;
		return cfg;
	}

	@Override
	public void updateGiftConfig(MarketGiftConfig cfg, MarketGiftConfigLine line) {
		configDao.update(cfg);
		lineDao.update(line);
	}

	@Override
	public List<MarketGiftConfig> queryGiftConfigListByGoodsIdList(
			List<Long> giftGoodsIdList) {
		return configDao.queryGiftConfigListByGoodsIdList(giftGoodsIdList);
	}

}

package cn.yj.market.module.common.bo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.yj.market.frame.bo.BaseBo;
import cn.yj.market.frame.page.Page;
import cn.yj.market.frame.page.PageRequestParams;
import cn.yj.market.frame.vo.MarketGiftCTConfig;
import cn.yj.market.frame.vo.MarketGiftCTConfigLine;
import cn.yj.market.module.common.bean.GiftConfigSearchCondition;
import cn.yj.market.module.common.bo.FeedGiftConfigBO;
import cn.yj.market.module.common.dao.FeedGiftConfigDao;
import cn.yj.market.module.common.dao.FeedGiftConfigLineDao;

@Service
public class MarketFeedGiftConfigBOImpl extends BaseBo implements FeedGiftConfigBO {
	
	@Autowired
	private FeedGiftConfigDao configDao;
	
	@Autowired
	private FeedGiftConfigLineDao lineDao;

	@Override
	public Page<MarketGiftCTConfig> getPage(GiftConfigSearchCondition condition,
			PageRequestParams pageRequestParams) {
		return configDao.getPage(condition, pageRequestParams);
	}

	@Override
	public MarketGiftCTConfig saveGiftConfig(MarketGiftCTConfig cfg,
			List<MarketGiftCTConfigLine> lineList) {
		Long configId = (Long) configDao.save(cfg) ;
		cfg.setGiftConfigId(configId);
		for (MarketGiftCTConfigLine line : lineList) {
			line.setGiftConfigId(configId);
			lineDao.save(line) ;
		}
		return cfg;
	}

	@Override
	public void updateGiftConfig(MarketGiftCTConfig cfg, List<MarketGiftCTConfigLine> lineList) {
		configDao.update(cfg);
		List<MarketGiftCTConfigLine> ll =  lineDao.getLineListByGiftConfigId(cfg.getGiftConfigId()) ;
		if (ll != null) {
			for (MarketGiftCTConfigLine line : ll) {
				lineDao.remove(line);
			}
		}
		for (MarketGiftCTConfigLine line : lineList) {
			line.setGiftConfigId(cfg.getGiftConfigId());
			lineDao.save(line) ;
		}
	}
}

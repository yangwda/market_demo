package cn.yj.market.module.common.bo.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.yj.market.frame.bo.BaseBo;
import cn.yj.market.frame.exception.RunException;
import cn.yj.market.frame.vo.MarketOnceBuy;
import cn.yj.market.module.common.bo.OncebuyBO;
import cn.yj.market.module.common.dao.OncebuyDao;

@Service
public class MarketOncebuyBOImpl extends BaseBo implements OncebuyBO {
	
	@Autowired
	private OncebuyDao oncebuyDao;

	@Override
	public List<MarketOnceBuy> getList() {
		return oncebuyDao.getList();
	}

	@Override
	public MarketOnceBuy saveOncebuy(MarketOnceBuy onceBuy) {
		Long key =  (Long) oncebuyDao.save(onceBuy);
		onceBuy.setOnceById(key);
		
		checkItem();
		
		return onceBuy ;
	}

	private void checkItem() {
		// check
		List<MarketOnceBuy> rl = getList() ;
		BigDecimal lastEnd = BigDecimal.ZERO ;
		for (MarketOnceBuy ob : rl) {
			if (lastEnd.compareTo(ob.getBeginAmount()) > 0) {
				throw new RunException("新增条目有误，条目之间不能有交叉!") ;
			}
			if (ob.getBeginAmount().compareTo(ob.getEndAmount()) > 0) {
				throw new RunException("新增条目有误，条目开始值必须小于结束值!") ;
			}
			lastEnd = ob.getEndAmount() ;
		}
	}

	@Override
	public void updateOncebuy(MarketOnceBuy onceBuy) {
		oncebuyDao.update(onceBuy);
		checkItem();
	}


}

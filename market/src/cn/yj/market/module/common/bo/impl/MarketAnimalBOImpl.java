package cn.yj.market.module.common.bo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.yj.market.frame.bo.BaseBo;
import cn.yj.market.frame.page.Page;
import cn.yj.market.frame.page.PageRequestParams;
import cn.yj.market.frame.vo.MarketAnimal;
import cn.yj.market.module.common.bean.AnimalSearchCondition;
import cn.yj.market.module.common.bo.AnimalBO;
import cn.yj.market.module.common.dao.AnimalDao;

@Service
public class MarketAnimalBOImpl extends BaseBo implements AnimalBO {
	
	@Autowired
	private AnimalDao animalDao;

	@Override
	public Page<MarketAnimal> getPage(AnimalSearchCondition condition,
			PageRequestParams pageRequestParams) {
		return animalDao.getPage(condition, pageRequestParams);
	}

	@Override
	public MarketAnimal saveAnimal(MarketAnimal animal) {
		Long animalId = (Long) animalDao.save(animal) ;
		animal.setAnimalId(animalId);
		return animal;
	}

	@Override
	public void updateAnimal(Long animalId, String animalName) {
		MarketAnimal animal = animalDao.loadAndLockNowait(animalId) ;
		if (animal != null) {
			animal.setAnimalName(animalName);
			animalDao.update(animal);
		}
	}
}

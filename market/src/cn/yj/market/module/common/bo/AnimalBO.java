package cn.yj.market.module.common.bo;

import cn.yj.market.frame.page.Page;
import cn.yj.market.frame.page.PageRequestParams;
import cn.yj.market.frame.vo.MarketAnimal;
import cn.yj.market.module.common.bean.AnimalSearchCondition;

public interface AnimalBO {
	Page<MarketAnimal> getPage(AnimalSearchCondition condition ,PageRequestParams pageRequestParams) ;
	MarketAnimal saveAnimal(MarketAnimal animal) ;
	void updateAnimal(Long animalId ,String animalName );
}

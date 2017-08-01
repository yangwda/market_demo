package cn.yj.market.module.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.yj.market.frame.bean.ResponseJsonData;
import cn.yj.market.frame.controller.BaseController;
import cn.yj.market.frame.page.Page;
import cn.yj.market.frame.util.SessionUtil;
import cn.yj.market.frame.vo.MarketAnimal;
import cn.yj.market.module.common.bean.AnimalSearchCondition;
import cn.yj.market.module.common.bo.AnimalBO;

import com.alibaba.fastjson.JSONObject;


@Controller
@RequestMapping("/animal")
public class AnimalController extends BaseController {
	
	@Autowired
	private AnimalBO animalBO;
	
	/**
     * index 页
     * 
     * @return
     */
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView index() {
        return new ModelAndView("animal/animal_index");
    }
    
	@RequestMapping(value = "/getAnimalPageList", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public JSONObject getAnimalPageList(AnimalSearchCondition condition) {
		
		ResponseJsonData response = new ResponseJsonData() ;
		Page<MarketAnimal> animalPage = null;
		try {
			animalPage = animalBO.getPage(condition, SessionUtil.getPageRequestParams()) ;
		} catch (Exception e) {
			response.setStatus(ERROR);
			response.setMessage("系统错误，无法查询会员信息！");
		}
		if (animalPage != null) {
			response.pagedGridData(animalPage.getTotal(), animalPage.getRecords());
		}
		else {
			response.pagedGridData(0, null);
		}
		return response.getResult() ;
	}
	
	@Override
	@RequestMapping(value = "/loadContent", method = { RequestMethod.GET,
			RequestMethod.POST })
	protected ModelAndView loadContent(HttpServletRequest request, String pn) {
		return super.loadContent(request, pn);
	}
	
	@RequestMapping(value = "/saveAnimalInfo", method = {RequestMethod.GET,
	        RequestMethod.POST})
	@ResponseBody
	public JSONObject saveAnimalInfo(MarketAnimal animal) {
		if (animal == null) {
            return ResponseJsonData.responseError("系统错误，无法获取兽种类信息！");
        }
		if (StringUtils.isBlank(animal.getAnimalName())) {
			return ResponseJsonData.responseError("请填写兽种类名称！") ;
		}
		
		//-- TODO 校验逻辑
		if (StringUtils.length(animal.getAnimalName()) > 200) {
			return ResponseJsonData.responseError("兽种类名称过长！") ;
		}
		
        if (animal.getAnimalId() != null) {
        	try {
        		animalBO.updateAnimal(animal.getAnimalId(), animal.getAnimalName());
			} catch (Exception e) {
				return ResponseJsonData.responseError("系统错误，无法保存兽种类信息！") ;
			}
        }
        else {
        	try {
        		animalBO.saveAnimal(animal);
			} catch (Exception e) {
				return ResponseJsonData.responseError("系统错误，无法保存兽种类信息！") ;
			}
		}
        return ResponseJsonData.responseSuccess("兽种类信息保存成功！") ;
	}
}

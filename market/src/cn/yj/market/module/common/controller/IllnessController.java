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
import cn.yj.market.frame.vo.MarketIllness;
import cn.yj.market.module.common.bean.IllnessSearchCondition;
import cn.yj.market.module.common.bo.IllnessBO;

import com.alibaba.fastjson.JSONObject;


@Controller
@RequestMapping("/illness")
public class IllnessController extends BaseController {
	
	@Autowired
	private IllnessBO illnessBO;
	
	/**
     * index 页
     * 
     * @return
     */
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView index() {
        return new ModelAndView("illness/illness_index");
    }
    
	@RequestMapping(value = "/getIllnessPageList", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public JSONObject getIllnessPageList(IllnessSearchCondition condition) {
		
		ResponseJsonData response = new ResponseJsonData() ;
		Page<MarketIllness> illnessPage = null;
		try {
			illnessPage = illnessBO.getPage(condition, SessionUtil.getPageRequestParams()) ;
		} catch (Exception e) {
			response.setStatus(ERROR);
			response.setMessage("系统错误，无法查询会员信息！");
		}
		if (illnessPage != null) {
			response.pagedGridData(illnessPage.getTotal(), illnessPage.getRecords());
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
	
	@RequestMapping(value = "/saveIllnessInfo", method = {RequestMethod.GET,
	        RequestMethod.POST})
	@ResponseBody
	public JSONObject saveIllnessInfo(MarketIllness illness) {
		if (illness == null) {
            return ResponseJsonData.responseError("系统错误，无法获取疾病信息！");
        }
		if (StringUtils.isBlank(illness.getIllnessName())) {
			return ResponseJsonData.responseError("请填写疾病名称！") ;
		}
		if (StringUtils.isBlank(illness.getCallBack())) {
			return ResponseJsonData.responseError("请填写疾病回访！") ;
		}
		
		//-- TODO 校验逻辑
		if (StringUtils.length(illness.getIllnessName()) > 500) {
			return ResponseJsonData.responseError("疾病名称过长！") ;
		}
		if (StringUtils.length(illness.getCallBack()) > 10) {
			return ResponseJsonData.responseError("疾病回访过长！") ;
		}
		
        if (illness.getIllnessId() != null) {
        	try {
        		illnessBO.updateIllness(illness.getIllnessId(), illness.getIllnessName(), illness.getCallBack());
			} catch (Exception e) {
				return ResponseJsonData.responseError("系统错误，无法保存疾病信息！") ;
			}
        }
        else {
        	try {
        		illnessBO.saveIllness(illness);
			} catch (Exception e) {
				return ResponseJsonData.responseError("系统错误，无法保存疾病信息！") ;
			}
		}
        return ResponseJsonData.responseSuccess("疾病信息保存成功！") ;
	}
}

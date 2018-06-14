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
import cn.yj.market.frame.vo.MarketMember;
import cn.yj.market.module.common.bean.MemberSearchCondition;
import cn.yj.market.module.common.bo.MemberBO;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/member")
public class MemberController extends BaseController {
	
	@Autowired
	private MemberBO memberBO;
	
	/**
     * index 页
     * 
     * @return
     */
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView index() {
        return new ModelAndView("member/member_index");
    }
    
	@RequestMapping(value = "/getMemberPageList", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public JSONObject getMemberPageList(MemberSearchCondition condition) {
		
		ResponseJsonData response = new ResponseJsonData() ;
		Page<MarketMember> memberPage = null;
		try {
			memberPage = memberBO.getPage(condition, SessionUtil.getPageRequestParams()) ;
		} catch (Exception e) {
			response.setStatus(ERROR);
			response.setMessage("系统错误，无法查询会员信息！");
		}
		if (memberPage != null) {
			response.pagedGridData(memberPage.getTotal(), memberPage.getRecords());
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
	
	@RequestMapping(value = "/saveMemberInfo", method = {RequestMethod.GET,
	        RequestMethod.POST})
	@ResponseBody
	public JSONObject saveMemberInfo(MarketMember member) {
		if (member == null) {
            return ResponseJsonData.responseError("系统错误，无法获取会员信息！");
        }
		if (StringUtils.isBlank(member.getMemberName())) {
			return ResponseJsonData.responseError("请填写会员姓名！") ;
		}
		if (StringUtils.isBlank(member.getMemberPhone())) {
			return ResponseJsonData.responseError("请填写会员手机号码！") ;
		}
		
		//-- TODO 校验逻辑
		if (StringUtils.length(member.getMemberName()) > 100) {
			return ResponseJsonData.responseError("会员姓名过长！") ;
		}
		if (StringUtils.length(member.getCommon()) > 2000) {
			return ResponseJsonData.responseError("会员姓名过长！") ;
		}
		if (StringUtils.length(member.getMemberAddress()) > 1000) {
			return ResponseJsonData.responseError("会员姓名过长！") ;
		}
		if (StringUtils.length(member.getMemberBusiRemark()) > 2000) {
			return ResponseJsonData.responseError("会员姓名过长！") ;
		}
		if (StringUtils.length(member.getMemberPhone()) > 200) {
			return ResponseJsonData.responseError("会员姓名过长！") ;
		}
		if (StringUtils.length(member.getMemberQQ()) > 200) {
			return ResponseJsonData.responseError("会员姓名过长！") ;
		}
		if (StringUtils.length(member.getMemberTel()) > 200) {
			return ResponseJsonData.responseError("会员姓名过长！") ;
		}
		if (StringUtils.length(member.getMemberWeixin()) > 200) {
			return ResponseJsonData.responseError("会员姓名过长！") ;
		}
		
		
        if (member.getMemberId() != null) {
        	try {
        		memberBO.updateMember(member.getMemberId(), member.getMemberName(), member.getMemberTel(), member.getMemberPhone(), 
        				member.getMemberQQ(), member.getMemberWeixin(), member.getMemberAddress(), member.getMemberBusiRemark(), member.getCommon());
			} catch (Exception e) {
				return ResponseJsonData.responseError("系统错误，无法保存会员信息！") ;
			}
        }
        else {
        	try {
        		memberBO.saveMember(member);
			} catch (Exception e) {
				return ResponseJsonData.responseError("系统错误，无法保存会员信息！") ;
			}
		}
        return ResponseJsonData.responseSuccess("会员信息保存成功！") ;
	}

}

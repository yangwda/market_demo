package cn.yj.market.module.common.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.yj.market.frame.bean.ResponseJsonData;
import cn.yj.market.frame.controller.BaseController;
import cn.yj.market.frame.util.CoreUtils;
import cn.yj.market.module.common.bean.MemberCheckReportBean;
import cn.yj.market.module.common.bo.MemberBO;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/memberCheckReport")
public class MemberCheckReportController extends BaseController {
	
	@Autowired
	private MemberBO memberBO;
	
	/**
     * index 页
     * 
     * @return
     */
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView index() {
        return new ModelAndView("report/member_checkreport_index");
    }
    
	@RequestMapping(value = "/getMemberCheckReport", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public JSONObject getMemberCheckReport(String memberId) {
		ResponseJsonData response = new ResponseJsonData() ;
		List<MemberCheckReportBean> memberPage = null;
		Long memberIdLong = null ;
		if (CoreUtils.isLong(memberId)) {
			memberIdLong = Long.valueOf(memberId) ;
		}
		try {
			memberPage = memberBO.getMemberCheckReport(memberIdLong) ;
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(ERROR);
			response.setMessage("系统错误，无法查询会员对账信息！");
		}
		if (memberPage != null) {
			MemberCheckReportBean ttb = new MemberCheckReportBean();
			ttb.setMemberTel("<strong> 合计：</strong>");
			ttb.setDzspljwdh(BigDecimal.ZERO);
			ttb.setDzspljydh(BigDecimal.ZERO);
			ttb.setDzspljze(BigDecimal.ZERO);
			ttb.setLjdjqze(BigDecimal.ZERO);
			ttb.setLjfke(BigDecimal.ZERO);
			ttb.setLjml(BigDecimal.ZERO);
			ttb.setLjsydjq(BigDecimal.ZERO);
			ttb.setLjwsydjq(BigDecimal.ZERO);
			ttb.setLjxse(BigDecimal.ZERO);
			ttb.setNmlj(BigDecimal.ZERO);
			for (MemberCheckReportBean bean : memberPage) {
				ttb.setDzspljwdh(ttb.getDzspljwdh().add(bean.getDzspljwdh()));
				ttb.setDzspljydh(ttb.getDzspljydh().add(bean.getDzspljydh()));
				ttb.setDzspljze(ttb.getDzspljze().add(bean.getDzspljze()));
				ttb.setLjdjqze(ttb.getLjdjqze().add(bean.getLjdjqze()));
				ttb.setLjfke(ttb.getLjfke().add(bean.getLjfke()));
				ttb.setLjml(ttb.getLjml().add(bean.getLjml()));
				ttb.setLjsydjq(ttb.getLjsydjq().add(bean.getLjsydjq()));
				ttb.setLjwsydjq(ttb.getLjwsydjq().add(bean.getLjwsydjq()));
				ttb.setLjxse(ttb.getLjxse().add(bean.getLjxse()));
				ttb.setNmlj(ttb.getNmlj().add(bean.getNmlj()));
			}
			memberPage.add(ttb) ;
			response.pagedGridData(memberPage.size(), memberPage);
		}
		else {
			response.pagedGridData(0, null);
		}
		return response.getResult() ;
	}
}

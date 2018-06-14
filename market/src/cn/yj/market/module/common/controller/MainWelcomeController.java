package cn.yj.market.module.common.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.yj.market.frame.constants.FrameConstants;
import cn.yj.market.frame.controller.BaseController;
import cn.yj.market.frame.util.SessionUtil;
import cn.yj.market.module.common.bo.SystemLoginBO;

import com.alibaba.fastjson.JSONObject;

@Controller
public class MainWelcomeController extends BaseController {
    @Autowired
    private SystemLoginBO systemLoginBO;
    
    @RequestMapping(value = "/", method = {RequestMethod.GET,
            RequestMethod.POST})
    public ModelAndView index() {
		ModelAndView mv = new ModelAndView("/redrectindex") ;
		return mv ;
	}

    /**
     * 登录验证
     * 
     * @param request
     * @param response
     * @return 登录验证信息
     */
    @RequestMapping(value = "/main/loginSystem", method = {RequestMethod.GET,
        RequestMethod.POST})
    @ResponseBody
    public JSONObject loginSystem(String username, String password,
        HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        json.put("loginStatus", systemLoginBO.getLoginSystem(username, password));
        /* 登录成功，设置用户的Session环境 */
        if (FrameConstants.LOGIN_OK.equals(json.get("loginStatus"))) {
			SessionUtil.initUserSession();
		}
        return json;
    }

    @RequestMapping(value = "/main/updatePassword", method = {
        RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject updatePassword(String oldpassword, String password,
        HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        json.put("status", systemLoginBO.updatePassword(oldpassword, password));

        return json;
    }
    
    /**
     * 主页面
     * 
     * @return 系统主页面
     */
    @RequestMapping(value = "/welcome", method = {RequestMethod.GET,
        RequestMethod.POST})
    public ModelAndView menuList(HttpServletRequest request,
        HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("user", SessionUtil.getCurrentUser());
        return new ModelAndView("main", map);
    }
    
    /**
     * portal页面
     * 
     * @return portal页面
     */
    @RequestMapping(value = "/portal", method = {RequestMethod.GET,
    		RequestMethod.POST})
    public ModelAndView portal() {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("user", SessionUtil.getCurrentUser());
    	return new ModelAndView("portal", map);
    }

    /**
     * 退出登录
     */
    @RequestMapping(value = "/main/logOut", method = {RequestMethod.GET,
        RequestMethod.POST})
    @ResponseBody
    public JSONObject logOut(HttpServletRequest request,
        HttpServletResponse response) {

        SessionUtil.invalidateUserSession();

        JSONObject json = new JSONObject();
        json.put("logOut", "logOut");
        return json;

    }
}

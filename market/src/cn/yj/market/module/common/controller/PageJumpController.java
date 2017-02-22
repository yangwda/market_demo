package cn.yj.market.module.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.yj.market.frame.controller.BaseController;

/**
 * 共通页面跳转
 */
@Controller
@RequestMapping("/jump")
public class PageJumpController extends BaseController{
    /**
     * 页面跳转
     * ** 此接口封闭，系统不再允许直接跳转到jsp，这样便于控制权限
     * @return 
     */
    @RequestMapping(value = "/jsp", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView jumpPage(HttpServletRequest request, HttpServletResponse response) {
//        String jumpJsp = request.getParameter("jumpJsp");
//        return new ModelAndView(jumpJsp);
    	return new ModelAndView("noAuth") ;
    }
    
    @RequestMapping(value = "/sysBuilding", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView sysBuilding(HttpServletRequest request, HttpServletResponse response) {
    	return new ModelAndView("common/sysBuiding") ;
    }
    
    /**
     * 测试使用
     */
    @RequestMapping(value = "/sysBuilding2", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView sysBuilding2(HttpServletRequest request, HttpServletResponse response) {
    	return new ModelAndView("common/sysBuiding2") ;
    }
    
    /**
     * 测试使用
     */
    @RequestMapping(value = "/rfIn", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView rfIn(HttpServletRequest request, HttpServletResponse response) {
    	return new ModelAndView("rf/in/rfIn") ;
    }

}

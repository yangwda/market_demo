package cn.yj.market.module.common.bo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.yj.market.frame.bo.BaseBo;
import cn.yj.market.frame.constants.FrameConstants;
import cn.yj.market.frame.util.CoreUtils;
import cn.yj.market.frame.util.UserContext;
import cn.yj.market.frame.vo.MarketUser;
import cn.yj.market.module.common.bo.SystemLoginBO;
import cn.yj.market.module.common.bo.UserBO;

/**
 * 登录验证相关接口实现类
 */
@Service("systemLoginBO")
public class SystemLoginBOImpl extends BaseBo implements SystemLoginBO {
    public SystemLoginBOImpl() {
    }

    /** 用户登陆信息DAO接口的Hibernate方式实现 */
//    @Resource
//    private WmsUserService userService;

    @Autowired
    private UserBO userBo;
    
    /**
     * 登录验证
     * 
     * @param username 登录名
     * @param password 登录密码
     * @return 登录信息 ok:成功登录, noName:该用户不存在, errPD:密码错误, lock:该用户被锁定, limit:限制登录
     */
    @Override
    public String getLoginSystem(String username, String password) {
        String status = null;
//        BaseResponse response = userService.getUserByLoginName(username);
//        // if (!response.isSuccessful()) {
//        // status = "noName";
//        // } else {
//        // WmsUser user = (WmsUser) response.getRecord() ;
//        // if (password.equals(user.getPassword()) && user.getStatus() != null) {
//        // if (user.getStatus().intValue() == 0) {
//        // status = "lock";
//        // } else if (user.getStatus().intValue() == 1) {
//        // /* 设置登录成功状态 */
//        // status = FrameConstants.LOGIN_OK;
//        // /* 将用户放到用户上下文中 */
//        // UserContext.init(user);
//        // } else if (user.getStatus().intValue() == 4) {
//        // status = "limit";
//        // }
//        // } else {
//        // status = "errPD";
//        // }
//        // }
//
//        if (!response.isSuccessful()) {
//            throw new RunException("系统异常，无法获取用户信息。");
//        }
//        WmsUser user = (WmsUser) response.getRecord();
//        if (user == null) {
//            throw new RunException("系统异常，无法获取用户信息。");
//        }
        
        
        List<MarketUser> userList = userBo.getUser() ;
        System.out.println("========== hib user list =" + userList);
        if (userList != null) {
			for (MarketUser marketUser : userList) {
				System.out.println("----------->> user name is :" + marketUser.getUserName());
			}
		}
        
        /* 将用户放到用户上下文中 */
        //-- TODO 现在直接返回，后续做dao处理
        if (!"9F00EA63A35753DC3DD509F69B4E44F7".equals(password) || !"lige".equals(username)) {
        	status = "errPD";
		}
        else {
        	MarketUser user = new MarketUser();
            user.setComm("业主");
            user.setCreateTime(CoreUtils.parseDate("2017-01-18 22:33:03"));
            user.setLoginName("lige");
            user.setPasswd("9F00EA63A35753DC3DD509F69B4E44F7");
            user.setPhone("13888888888");
            user.setRole("saler");
            user.setSex("男");
            user.setUserId(2l);
            user.setUserName("李哥");
            UserContext.init(user);
            status = FrameConstants.LOGIN_OK;
		}
        return status;
    }

    public String updatePassword(String oldpassword, String password) {
        String status = null;
        MarketUser user = UserContext.getUser();
        Long userId = user.getUserId();
        if (!user.getPasswd().equals(oldpassword)) {
            status = "erroPsw";
        } else {
//            WmsUser u = userService.getUserByID(id);
//            u.setPassword(password);
//            WmsUserUpateRequest dbRequest =
//                dubboRequest(WmsUserUpateRequest.class);
//            dbRequest.setUser(u);
//            BaseResponse dbResponse = userService.updateWmsUser(dbRequest);
//            if (dbResponse.isSuccessful()) {
                status = "ok";
//            } else {
//                logger.error("## ERROR in update user password..."
//                    + dbResponse.getException());
//                status = "unknowErr";
//            }
        }

        return status;
    }
}

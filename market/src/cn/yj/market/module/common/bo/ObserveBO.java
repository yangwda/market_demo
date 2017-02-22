package cn.yj.market.module.common.bo;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

import cn.yj.market.frame.bean.ResponseJsonData;
import cn.yj.market.frame.util.DateUtil;
import cn.yj.market.frame.util.UserContext;
import cn.yj.market.frame.vo.MarketUser;

/**
 * 观察器日志记录
 * 
 */
@Aspect
@Service("observeBO")
public class ObserveBO {
    private static final Log log = LogFactory.getLog(ObserveBO.class);

    public ObserveBO() {
       // log.info("日志观察器初始化 is ok!");
    }

    //    @Before("execution(* *..bo.*BO.*(..))")
    public void logAll(JoinPoint point) throws Throwable {
        //        log.info("日志观察器 Before:  " + className + "/" + methodName);
    }

    //@After("execution(* *..bo.*BO.*(..))")
    public void after() {
        //        log.info("日志观察器 After");
    }

    // BO接口执行数据库操作记录
    @Around("execution(* *..bo.*BO.get*(..)) || execution(* *..bo.*BO.do*(..))")
    public Object logDoMethod(ProceedingJoinPoint point) throws Throwable {

        String className = point.getTarget().getClass().getName();
        String methodName = point.getSignature().getName();

        MarketUser user = null;
        if (UserContext.isOk()) {
			user = UserContext.getUser() ;
		}
        String userName = null;
        if (user != null) {
            userName = user.getUserName();
        }else{
            userName = "登录验证";
        }

        log.info("日志观察器 @Around:  " + className + "/" + methodName+ "/" + userName);
        //        Object[] arguments = point.getArgs();
        //        Class targetClass = Class.forName(className);
        //        Method[] method = targetClass.getMethods(); // 取该类的所有方法
        //        for (Method m : method) {
        //            if (m.getName().equals(methodName)) {
        //                Class[] tmpCs = m.getParameterTypes();
        //            }
        //        }

        Object obj;
        
        try {
            obj = point.proceed();
            if (obj instanceof ResponseJsonData) {
//                ((BoData) obj).setStatus(FrameConstants.ERROR);
//                ((BoData) obj).setMessage("无访问权限!");
                // TODO 实际业务处理
                //                if (FrameConstants.SUCCESS.equals(((BoData) obj).getStatus())) {
                //                    log.info("执行成功: " + DateUtil.getDFDateTime().format(new Date()) + " : " + userName + " 执行: "
                //                            + className + " - " + methodName + " - " + ((BoData) obj).getMessage());
                //                }
//                if (FrameConstants.ERROR.equals(((BoData) obj).getStatus())) {
//                    log.error("执行失败: " + DateUtil.getDFDateTime().format(new Date()) + " : " + userName + " 执行: "
//                            + className + " - " + methodName + " - " + ((BoData) obj).getMessage());
//                }

            }

        } catch (Exception e) {
            log.warn(DateUtil.getDFDateTime().format(new Date()) + " : " + userName + " 执行: " + className + " - "
                    + methodName + " 出现系统异常!");
            throw e;
        }
        return obj;
    }

    //方法运行出现异常时调用  
    @AfterThrowing(pointcut = "execution(* *..bo.*BO.*(..))", throwing = "ex")
    public void afterThrowing(Exception ex) {
        log.info(ex.getMessage());
    }

}

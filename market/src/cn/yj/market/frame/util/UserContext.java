package cn.yj.market.frame.util;

import java.io.Serializable;

import cn.yj.market.frame.constants.SysRoleEnum;
import cn.yj.market.frame.exception.RunException;
import cn.yj.market.frame.vo.MarketUser;

/**
 * 系统登录用户Context <br>
 * 为了非web请求线程使用bo和dao，在bo和dao中将RequestContext进行隔离 <br>
 * 在bo和dao中不允许出现springmvc和javaee相关api <br>
 * 这样bo和dao脱离了web请求环境，同样可以正常运行 <br>
 * 由于在bo和dao中会频繁使用到操作者相关信息 <br>
 * 故封装UserContext，当web请求时，将用户信息放在UserContext中，方便bo和dao使用 <br>
 * 非web请求线程，线程启动时，需要设置UserContext <br>
 */
public final class UserContext implements Serializable {

    private static final long serialVersionUID = 5930882832862353896L;

    private UserContext() {
    }

    private MarketUser user;

    private MarketUser getContextUser() {
        return user;
    }

    private static ThreadLocal<UserContext> USER_CONTEXT =
        new ThreadLocal<UserContext>();

    /**
     * 用户上下文初始化
     * 
     * @param user
     */
    public static void init(MarketUser user) {
        if (!isOk()) {
            UserContext ctx = new UserContext();
            USER_CONTEXT.set(ctx);
        }
        if (user == null) {
            return;
        }
        get().setUser(user);
    }

    private void setUser(MarketUser user) {
    	this.user = user;
	}

	public static boolean isOk() {
        return (USER_CONTEXT.get() != null);
    }

    /**
     * 获取用户上下文
     * 
     * @return
     */
    public static UserContext get() {
        return USER_CONTEXT.get();
    }

    /**
     * 设置用户上下文
     * 
     * @return
     */
    public static void set(UserContext uctx) {
        USER_CONTEXT.set(uctx);
    }

    /**
     * 清空用户上下文
     */
    public static void invalidate() {
        USER_CONTEXT.remove();
    }

    /**
     * 获取当前操作用户
     * 
     * @return
     */
    public static MarketUser getUser() {
        if (get() == null) {
            return null;
        }
        return get().getContextUser();
    }

    /**
     * 判断当前用户是否是系统管理员
     * 
     * @return
     */
    public static boolean isAdmin() {
        check();
        return SysRoleEnum.ADMIN.getRole().equalsIgnoreCase(getUser().getRole());
    }

    /* ==================================================== */

    private static void check() {
        if (!isOk()) {
            throw new RunException("未登录或登录超时，请先登录系统！");
        }
        if (getUser() == null) {
            throw new RunException("未登录或登录超时，请先登录系统！");
        }
    }
}

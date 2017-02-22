package cn.yj.market.module.common.bo;


/**
 * 登录验证相关接口
 */
public interface SystemLoginBO {

    /**
     * 登录验证
     * 
     * @param username 登录名
     * @param password 登录密码
     * @return 登录信息 ok:成功登录, noName:该用户不存在, errPD:密码错误, lock:该用户被锁定, limit:限制登录
     */
    public String getLoginSystem(String username, String password);

    /**
     * 修改密码
     * 
     * @param oldpassword
     * @param password
     * @return
     */
    public String updatePassword(String oldpassword , String password);

}

package cn.yj.market.frame.bo;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.yj.market.frame.bean.ResponseJsonData;
import cn.yj.market.frame.constants.FrameConstants;

/**
 * BO基类
 */
@Deprecated
public class WebappBaseBO {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public WebappBaseBO() {
    }

    public ResponseJsonData validator(Object obj) {
        ResponseJsonData boData = new ResponseJsonData();
        if (obj == null) {
            return boData;
        }
        return boData;
    }

    // /**
    // * 获取web请求用户，或者后台启动程序的操作者
    // * @return
    // */
    // public TmsUser getUser() {
    // return UserContext.getUser() ;
    // }

    /**
     * bo处理异常返回
     * 
     * @param errInfo
     * @return
     */
    protected ResponseJsonData error(String errInfo) {
        ResponseJsonData rjd = new ResponseJsonData();
        rjd.setStatus(FrameConstants.RESPONSE_DATA_FLAG_ERROR);
        if (StringUtils.isNotBlank(errInfo)) {
            rjd.setMessage(errInfo);
        } else {
            rjd.setMessage("未知错误，请联系系统管理员。");
        }
        return rjd;
    }

    /**
     * bo处理成功返回
     * 
     * @param info
     * @return
     */
    protected ResponseJsonData success(String info) {
        ResponseJsonData rjd = new ResponseJsonData();
        rjd.setStatus(FrameConstants.RESPONSE_DATA_FLAG_SUCCESS);
        if (StringUtils.isNotBlank(info)) {
            rjd.setMessage(info);
        } else {
            rjd.setMessage("操作成功。");
        }
        return rjd;
    }
}

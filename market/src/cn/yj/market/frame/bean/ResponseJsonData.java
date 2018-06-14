package cn.yj.market.frame.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.ObjectError;

import cn.yj.market.frame.constants.FrameConstants;
import cn.yj.market.frame.util.JsonUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * do方法返回的数据类型封装,用于放置BO的执行状态
 */
public class ResponseJsonData implements Serializable {

    private static final long serialVersionUID = 8142746586873763528L;

    public ResponseJsonData() {
        setStatus(FrameConstants.RESPONSE_DATA_FLAG_SUCCESS);
        setErrors(new ArrayList<ObjectError>());
        setErrorMsgList(new ArrayList<String>());
    }

    private boolean isPagedGridData = false;

    private String status = null; // success / error

    private String message = null; // 成功或错误时的提示信息

    private Object retData = null; // 返回的数据

    @SuppressWarnings("unused")
    private Object columns = null; // 表头信息 生成动态列的时候用

    private Object pagedGridData = null; // 返回的数据

    private List<ObjectError> errors = null; // 验证错误信息

    private List<String> errorMsgList = null; // 普通String型错误消息列表

    public static JSONObject responseError(String errMsg) {
        ResponseJsonData rjd = new ResponseJsonData();
        rjd.setStatus(FrameConstants.RESPONSE_DATA_FLAG_ERROR);
        if (StringUtils.isBlank(errMsg)) {
            errMsg = "系统异常，请联系管理员！";
        }
        rjd.setMessage(errMsg);
        return rjd.getResult();
    }

    /**
     * 方法功能描述
     * 
     * @param msg
     * 
     */
    public static JSONObject responseSuccess(String msg) {
        ResponseJsonData rjd = new ResponseJsonData();
        rjd.setStatus(FrameConstants.RESPONSE_DATA_FLAG_SUCCESS);
        if (StringUtils.isBlank(msg)) {
            msg = "相关操作成功！";
        }
        rjd.setMessage(msg);
        return rjd.getResult();
    }

    @SuppressWarnings("rawtypes")
    public void pagedGridData(long total, List gridDataList) {
        JSONObject jObject = new JSONObject();
        if (total <= 0) {
            jObject.put("total", 0);
            jObject.put("rows", new JSONArray());
        } else {
            jObject.put("total", total);
            JSONArray rl = new JSONArray();
            if (gridDataList != null && !gridDataList.isEmpty()) {
                for (Object object : gridDataList) {
                    rl.add(JsonUtils.toJsonObject(object));
                }
            }
            jObject.put("rows", rl);
        }
        pagedGridData = jObject;
        isPagedGridData = true;
    }

    public void setDataList(Collection<?> dc) {
        if (dc == null) {
            return;
        }
        this.retData = JsonUtils.toJsonArray(dc);
    }

    public JSONObject getResult() {
        JSONObject retObj = new JSONObject();

        if ((getErrors() != null && !getErrors().isEmpty())
            || (getErrorMsgList() != null && !getErrorMsgList().isEmpty())) { // 错误信息封装
            setStatus(FrameConstants.RESPONSE_DATA_FLAG_ERROR);
            StringBuffer err = new StringBuffer();
            if (getErrors() != null && !getErrors().isEmpty()) {
                for (ObjectError er : getErrors()) {
                    err.append(er.getObjectName() + " - "
                        + er.getDefaultMessage() + "</BR>");
                }
            }
            if (getErrorMsgList() != null && !getErrorMsgList().isEmpty()) {
                for (String errMsg : getErrorMsgList()) {
                    err.append(errMsg + "<BR>");
                }
            }

            setMessage(err.toString());
        }

        retObj.put("status", getStatus());
        retObj.put("message", getMessage());
        retObj.put("retData", (isPagedGridData ? getPagedGridData()
            : getRetData()));
        retObj.put("errors", getErrors());
        return retObj;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getRetData() {
        return retData;
    }

    public Object getPagedGridData() {
        return pagedGridData;
    }

    public void setRetData(Object retData) {
        if (retData == null) {
            return;
        }
        this.retData = JsonUtils.toJsonObject(retData);
    }

    public List<ObjectError> getErrors() {
        return errors;
    }

    public void setErrors(List<ObjectError> errors) {
        this.errors = errors;
    }

    public List<String> getErrorMsgList() {
        return errorMsgList;
    }

    public void setErrorMsgList(List<String> errorMsgList) {
        this.errorMsgList = errorMsgList;
    }

}

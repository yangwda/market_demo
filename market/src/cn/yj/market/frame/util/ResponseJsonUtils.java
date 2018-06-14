package cn.yj.market.frame.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;

public final class ResponseJsonUtils {

    private static final Log logger = LogFactory
        .getLog(ResponseJsonUtils.class);

    public static void responseJson(HttpServletResponse response,
        JSONObject responseVal) {
        if (response == null) {
            return;
        }
        if (responseVal == null) {
            return;
        }
        PrintWriter out = null;
        try {
            response.setContentType("application/json;charset=utf-8");
            response.setHeader("Cache-Control", "no-cache, must-revalidate");
            out = response.getWriter();
            out.write(responseVal.toString());
            out.flush();
        } catch (Exception e) {
            logger.error("== ERROR IN RESPONSE JSON ==", e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}

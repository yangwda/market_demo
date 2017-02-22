package cn.yj.market.frame.util;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import cn.yj.market.frame.bean.JsonBean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

/**
 * 
 * com.alibaba.fastjson的工具类.
 * 
 * <p>
 * 1. Java Bean转json，包含默认的日期格式处理. <br>
 * 2. 详细说明2. <br>
 * 3. 详细说明3. <br>
 * 4. 详细说明4. <br>
 * 5. 详细说明5. <br>
 *
 * @author weidong.yang@eascs.com
 * @date 2016年7月21日 下午6:45:43
 * @version 2016年7月21日 weidong.yang@eascs.com TODO简要描述修改内容，修改原因
 *
 */
public final class JsonUtils {
    private JsonUtils() {
    }

    // 日志
    // private static final Log logger = LogFactory.getLog(JsonUtils.class);
    private static final ObjectSerializer DATE_CONFIG =
        new SimpleDateFormatSerializer("yyyy-MM-dd");;

    private static final ObjectSerializer DATE_TIME_CONFIG =
        new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss");;

    private static final SerializeConfig DEFAULT_JSON_CONFIG =
        new SerializeConfig();
    static {
        DEFAULT_JSON_CONFIG.put(java.util.Date.class, DATE_TIME_CONFIG);
        DEFAULT_JSON_CONFIG.put(java.sql.Date.class, DATE_TIME_CONFIG);
    }

    /**
     * 
     * 通用的将java bean转换成json object的方法
     * 
     * @param obj JavaBean对象
     * @return
     *
     */
    public static final JSONObject toJsonObject(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof JSONObject) {
            return (JSONObject) obj;
        }
        if (obj instanceof JsonBean) {
            JsonBean jsonBean = (JsonBean) obj;
            return jsonBean.toJson();
        }
        return toJsonObject(obj, DEFAULT_JSON_CONFIG);
    }

    /**
     * 
     * 通用的将java bean转换成json object的方法
     * 
     * @param obj JavaBean对象
     * @param cfg 序列化配置，配置各数据类型转换为json的规则
     * @return
     *
     */
    public static final JSONObject toJsonObject(Object obj, SerializeConfig cfg) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof JSONObject) {
            return (JSONObject) obj;
        }
        if (cfg != null) {
            return JSON.parseObject(JSON.toJSONString(obj, cfg));
        }
        return (JSONObject) JSON.toJSON(obj);
    }

    /**
     * 
     * 通用的将java bean转换成json string的方法
     * 
     * @param obj JavaBean对象
     * @return
     *
     */
    public static final String toJsonString(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof JSONObject) {
            return ((JSONObject) obj).toJSONString();
        }
        if (obj instanceof JsonBean) {
            JsonBean jsonBean = (JsonBean) obj;
            return jsonBean.toJsonString();
        }
        return toJsonString(obj, DEFAULT_JSON_CONFIG);
    }

    /**
     * 
     * 通用的将java bean转换成json string的方法
     * 
     * @param obj JavaBean对象
     * @param cfg 序列化配置，配置各数据类型转换为json的规则
     * @return
     *
     */
    public static final String toJsonString(Object obj, SerializeConfig cfg) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof JSONObject) {
            return ((JSONObject) obj).toJSONString();
        }
        if (cfg != null) {
            return JSON.toJSONString(obj, cfg);
        }
        return JSON.toJSONString(obj, DEFAULT_JSON_CONFIG);
    }

    /**
     * 
     * 通用的将java Collection转换成 JSONArray的方法
     * 
     * @param dc java Collection对象
     * @return
     *
     */
    public static final JSONArray toJsonArray(Collection<?> dc) {
        if (dc == null) {
            return null;
        }
        if (dc instanceof JSONArray) {
            return (JSONArray) dc;
        }
        JSONArray jArray = new JSONArray();
        for (Object object : dc) {
            jArray.add(toJsonObject(object));
        }
        return jArray;
    }

    /**
     * 
     * json日期转换配置
     * 
     * @return
     *
     */
    public static final ObjectSerializer dateConfig() {
        return DATE_CONFIG;
    }

    /**
     * 
     * json日期+时间转换配置
     * 
     * @return
     *
     */
    public static final ObjectSerializer dateTimeConfig() {
        return DATE_TIME_CONFIG;
    }

    /**
     * 将json string 转换为json对象
     * 
     * @param jsonStr
     * @return
     * 
     */
    public static JSONObject parseJson(String jsonStr) {
        if (StringUtils.isBlank(jsonStr)) {
            return new JSONObject();
        }
        return JSON.parseObject(jsonStr);
    }

    /**
     * 
     * 将json字符串转换成java bean
     * 
     * @param jsonStr
     * @param clazz
     * @return
     *
     */
    public static final <T> T toJavaBean(String jsonStr, Class<T> clazz) {
        if (StringUtils.isBlank(jsonStr)) {
            return null;
        }
        return JSON.toJavaObject(parseJson(jsonStr), clazz);
    }

    /**
     * 
     * 将json字符串转换成java bean
     * 
     * @param json
     * @param clazz
     * @return
     *
     */
    public static final <T> T toJavaBean(JSONObject json, Class<T> clazz) {
        if (json == null) {
            return null;
        }
        return JSON.toJavaObject(json, clazz);
    }
}

package cn.yj.market.frame.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Application 级 properties 及常量容器 ；variable.properties配置文件中的内容
 */
public final class ConstantsContainer {

    /** 用于放置所有有效用户  KEY: personId */
    private static Map<String, String> VARIABLE = null;

    /**
     * 转换property配置文件值
     */
    public static void setVariable(Properties props) {
        VARIABLE = new HashMap<String, String>();
        Enumeration<String> enums = (Enumeration<String>) props.propertyNames();
        while (enums.hasMoreElements()) {
            String v = enums.nextElement();
            VARIABLE.put(v, props.getProperty(v));
        }
    }

    /**
     * 根据proKey取得property配置文件值
     * 
     * @param proKey 
     * @return property配置文件值
     */
    public static String getVariable(String proKey) {
        return VARIABLE.get(proKey);
    }
    
}

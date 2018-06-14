/*
 * 项目名称:wmsapi
 * 文件名称:JsonBean.java
 * 包名:com.dsp.framework.bean
 * 日期:2016年7月22日上午9:37:08
 * Copyright (c) 2016, 怡亚通 All Rights Reserved.
 */
package cn.yj.market.frame.bean;

import com.alibaba.fastjson.JSONObject;

/**
 * 接口，提供bean转换json的方法.
 * 
 * <p>
 * 1. 详细说明1. <br>
 * 2. 详细说明2. <br>
 * 3. 详细说明3. <br>
 * 4. 详细说明4. <br>
 * 5. 详细说明5. <br>
 *
 * @author weidong.yang@eascs.com
 * @date 2016年7月22日 上午9:37:08
 * @version 2016年7月22日 weidong.yang@eascs.com 新建
 * 
 */

public interface JsonBean {

    /**
     * 
     * 提供javabean转换json的方法.
     * 
     * @return
     *
     */
    JSONObject toJson();

    String toJsonString();
}

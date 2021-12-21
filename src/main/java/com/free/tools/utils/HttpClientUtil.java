package com.free.tools.utils;

import com.free.tools.http.BaseHttpUtil;
import com.free.tools.model.CustomHttpMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * httpclient请求工具类
 *
 * @Author: dinghao
 * @Date: 2021-12-19 21:09:22
 */
public class HttpClientUtil extends BaseHttpUtil {

    private final static Logger logger = LogManager.getLogger(HttpClientUtil.class);

    /**
     * exec请求，通过以下参数获取数据
     *
     * @param url 请求地址
     * @return
     */
    public static String exec(CustomHttpMethod customHttpMethod, String url) {
        return exec(customHttpMethod, url, null, getDefaultContentType(), null);
    }

    /**
     * exec请求，通过以下参数获取数据
     *
     * @param url    请求地址
     * @param header 请求头部参数
     * @return
     */
    public static String exec(CustomHttpMethod customHttpMethod, String url, Map<String, String> header) {
        return exec(customHttpMethod, url, header, getDefaultContentType(), null);
    }


    /**
     * exec请求，通过以下参数获取数据 content-Type 默认 application/json
     *
     * @param url  请求地址
     * @param body 请求内容
     * @return
     */
    public static String exec(CustomHttpMethod customHttpMethod, String url, String body) {
        return exec(customHttpMethod, url, null, getDefaultContentType(), body);
    }

    /**
     * exec请求，通过以下参数获取数据
     *
     * @param url         请求地址
     * @param contentType 请求内容体类型
     * @param body        请求内容
     * @return
     */
    public static String exec(CustomHttpMethod customHttpMethod, String url, String contentType, String body) {
        return exec(customHttpMethod, url, null, contentType, body);
    }

    /**
     * exec请求，通过以下参数获取数据 content-Type 默认 application/json
     *
     * @param url    请求地址
     * @param header 请求头部参数
     * @param body   请求内容
     * @return
     */
    public static String exec(CustomHttpMethod customHttpMethod, String url, Map<String, String> header, String body) {
        return exec(customHttpMethod, url, header, getDefaultContentType(), body);
    }

    /**
     * exec请求，通过以下参数获取数据
     *
     * @param url         请求地址
     * @param header      请求头部参数
     * @param contentType 请求内容体类型
     * @param body        请求内容
     * @return
     */
    public static String exec(CustomHttpMethod customHttpMethod, String url, Map<String, String> header, String contentType, String body) {
        return exec(url, customHttpMethod, header, contentType, body);
    }

    public static void main(String[] args) {
        String result = HttpClientUtil.exec(CustomHttpMethod.GET, "http://127.0.0.1:8088/api/v3/login-log?page=1&limit=1");
        System.out.println(result);
    }
}

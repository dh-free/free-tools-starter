package com.free.tools.http;

import com.alibaba.fastjson.JSON;
import com.free.tools.constant.HttpConstants;
import com.free.tools.http.core.HttpClientConnectFactory;
import com.free.tools.model.CustomHttpMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * http抽象类，继承工具类的公共函数
 *
 * @author dinghao
 */
public class BaseHttpUtil extends HttpRequestConfigUtil {

    private final static Logger logger = LogManager.getLogger(BaseHttpUtil.class);

    /**
     * 创建请求对象 httpRequestBase
     *
     * @param Method
     * @param url
     * @return
     */
    protected static HttpRequestBase createHttpRequestBase(CustomHttpMethod Method, String url) {
        switch (Method) {
            case POST:
                return new HttpPost(url);
            case PUT:
                return new HttpPut(url);
            case DELETE:
                return new HttpDelete(url);
            case GET:
            default:
                return new HttpGet(url);
        }
    }

    /**
     * send请求
     *
     * @param url
     * @param method
     * @param header
     * @param contentType
     * @param body
     * @return
     */
    protected static String exec(String url, CustomHttpMethod method, Map<String, String> header, String contentType, String body) {
        CloseableHttpClient httpClient = HttpClientConnectFactory.getHttpClient();
        HttpRequestBase httpBase = createHttpRequestBase(method, url);
        setHeader(httpBase, header);
        setContentType(httpBase, contentType);
        if (httpBase instanceof HttpEntityEnclosingRequestBase) {
            setHttpBody((HttpEntityEnclosingRequestBase) httpBase, body);
        }
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpBase);
            response.getStatusLine().getStatusCode();
            HttpEntity httpEntity = response.getEntity();
            return EntityUtils.toString(httpEntity, HttpConstants.CHARSET_UTF_8);
        } catch (Exception e) {
            logger.error("request method : {} , httpUtil error : {}", method, e.getMessage());
            e.printStackTrace();
        }
        return JSON.toJSONString(response);
    }


    /**
     * 处理响应值
     *
     * @param apiResult
     * @param defaultResult
     * @param type
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T handleResponse(String apiResult, T defaultResult, Type type) {
        if (StringUtils.isBlank(apiResult)) {
            return defaultResult;
        }
        try {
            return (T) JSON.parseObject(apiResult, type);
        } catch (Exception e) {
            return defaultResult;
        }
    }
}

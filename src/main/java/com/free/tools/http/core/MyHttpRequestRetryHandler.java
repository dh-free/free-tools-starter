package com.free.tools.http.core;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.protocol.HttpContext;

/**
 * 定义重试处理机制
 */
public class MyHttpRequestRetryHandler extends DefaultHttpRequestRetryHandler {

    private int retry_times = 3;

    public MyHttpRequestRetryHandler() {
        super();
    }

    /**
     * 覆盖默认的重试次数及重试标志
     * @param retry_times
     */
    public MyHttpRequestRetryHandler(int retry_times) {
        super();
        this.retry_times = retry_times;
    }

    /**
     * 检查重试次数 检查连接异常原因
     */
    @Override
    public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
        // 如果已经重试了3次，就放弃
        if (executionCount >= retry_times) {
            return false;
        }
        // 如果服务器丢掉了连接，那么就重试
        if (exception instanceof NoHttpResponseException) {
            return true;
        }
        // 超时
        if (exception instanceof InterruptedIOException) {
            return true;
        }
        // 不要重试SSL握手异常
        if (exception instanceof SSLHandshakeException) {
            return false;
        }
        // 目标服务器不可达
        if (exception instanceof UnknownHostException) {
            return false;
        }
        // ssl握手异常
        if (exception instanceof SSLException) {
            return false;
        }
        HttpClientContext clientContext = HttpClientContext.adapt(context);
        HttpRequest request = clientContext.getRequest();
        // 如果请求是幂等的，就再次尝试
        return !(request instanceof HttpEntityEnclosingRequest);
    }

}

package com.free.tools.http.core;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.free.tools.constant.HttpConstants;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * httpclient连接工厂
 */
public class HttpClientConnectFactory {

    private final static Logger logger = LogManager.getLogger(HttpClientConnectFactory.class);

    private static PoolingHttpClientConnectionManager cm = null;

    /**
     * 初始化连接池
     */
    static {
        SSLContext sslcontext;
        try {
            sslcontext = createIgnoreVerifySSL();
            ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register(HttpConstants.HTTP_PROTOCOL, plainsf)
                    .register(HttpConstants.HTTPS_PROTOCOL, getSSLConnectionSocketFactory(sslcontext))
                    .build();
            cm = new PoolingHttpClientConnectionManager(registry);
            cm.setMaxTotal(HttpConstants.MAX_TOTAL_POOL);
            cm.setDefaultMaxPerRoute(HttpConstants.MAX_CONPERROUTE);
        } catch (Exception e) {
            logger.error("Failed to initialize the connection pool : {}", e.getMessage());
            e.getStackTrace();
        }
    }

    /**
     * 获取httpclient连接
     *
     * @return
     */
    public static CloseableHttpClient getHttpClient() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(HttpConstants.CONNECTION_REQUEST_TIMEOUT)
                .setConnectTimeout(HttpConstants.CONNECT_TIMEOUT)
                .setSocketTimeout(HttpConstants.SOCKET_TIMEOUT)
                .build();
        return HttpClients.custom().setConnectionManager(cm)
                .setDefaultRequestConfig(requestConfig)
                .setRetryHandler(new MyHttpRequestRetryHandler())
                .setConnectionManagerShared(true)
                .build();
    }

    /**
     * 创建sslcontext
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    private static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance(HttpConstants.SSL_CONTEXT);
        X509TrustManager trustManager = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {

            }

            public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {

            }

            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        sc.init(null, new TrustManager[]{trustManager}, null);
        return sc;
    }

    /**
     * getSSLConnectionSocketFactory
     *
     * @param sslcontext
     * @return
     */
    private static ConnectionSocketFactory getSSLConnectionSocketFactory(SSLContext sslcontext) {
        return new SSLConnectionSocketFactory(sslcontext, NoopHostnameVerifier.INSTANCE);
    }

}

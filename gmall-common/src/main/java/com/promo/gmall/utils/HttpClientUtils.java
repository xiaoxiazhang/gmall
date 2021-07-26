package com.promo.gmall.utils;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.MapUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.*;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
public class HttpClientUtils {

    /**
     * ms毫秒,从池中获取链接超时时间
     */
    private static final int CONNECTION_REQUEST_TIMEOUT = 5000;

    /**
     * ms毫秒,建立链接超时时间
     */
    private static final int CONNECT_TIMEOUT = 1000;

    /**
     * ms毫秒,读取超时时间
     */
    private static final int SOCKET_TIMEOUT = 10000;

    /**
     * 最大总并发
     */
    private static final int MAX_TOTAL = 200;

    /**
     * 每路并发[每个域名并发最大个数-默认是2]
     */
    private static final int MAX_PER_ROUTE = 10;


    private static CloseableHttpClient httpClient;


    static {
        try {
            //enable ssl
            TrustManager x509m = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };

            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new TrustManager[]{x509m}, null);
            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(context, NoopHostnameVerifier.INSTANCE);
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("https", socketFactory)
                    .register("http", PlainConnectionSocketFactory.INSTANCE).build();


            PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            // http连接池设置socket属性
            SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
            connManager.setDefaultSocketConfig(socketConfig);

            //http连接池设置connection属性
            MessageConstraints messageConstraints = MessageConstraints.custom()
                    .setMaxHeaderCount(200)
                    .setMaxLineLength(2000)
                    .build();
            ConnectionConfig connectionConfig = ConnectionConfig.custom().setMalformedInputAction(CodingErrorAction.IGNORE).setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8).setMessageConstraints(messageConstraints).build();
            connManager.setDefaultConnectionConfig(connectionConfig);

            // http连接池设置并发属性
            connManager.setMaxTotal(MAX_TOTAL);
            connManager.setDefaultMaxPerRoute(MAX_PER_ROUTE);

            //默认请求设置
            RequestConfig requestConfig = RequestConfig.custom()
                    // 连接目标服务器超时时间：连接一个url的连接等待时间
                    .setConnectTimeout(CONNECT_TIMEOUT)
                    // 读取目标服务器数据超时时间：连接上一个url，获取response的返回等待时间
                    .setSocketTimeout(SOCKET_TIMEOUT)
                    // 从连接池获取连接的超时时间
                    .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
                    .build();
            httpClient = HttpClients.custom()
                    .setConnectionManager(connManager)
                    .setDefaultRequestConfig(requestConfig)
                    .build();

        } catch (Exception e) {
            throw new IllegalStateException("HttpClient init error", e);
        }

    }

    private HttpClientUtils() {
        // blank
    }


    public static String getRequest(String url) {
        return getRequest(url, null);
    }


    public static String getRequest(String url, Map<String, Object> paramMap) {
        URI uri;
        try {
            URIBuilder builder = new URIBuilder(url);
            Optional.ofNullable(paramMap).ifPresent(map -> {
                map.forEach((key, value) -> {
                    builder.addParameter(key, value.toString());
                });
            });
            uri = builder.build();
        } catch (URISyntaxException e) {
            throw new UnsupportedOperationException("Uri syntax error", e);
        }

        return executeRequest(new HttpGet(uri));
    }


    public static String postFormRequest(String url, Map<String, Object> paramMap) {
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> paramList = Lists.newArrayList();
        if (MapUtils.isNotEmpty(paramMap)) {
            paramMap.forEach((key, value) -> {
                paramList.add(new BasicNameValuePair(key, value.toString()));
            });
        }

        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, StandardCharsets.UTF_8);
        httpPost.setEntity(entity);
        return executeRequest(httpPost);
    }


    public static String postJsonRequest(String url, String json) {
        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
        httpPost.setEntity(entity);
        return executeRequest(httpPost);
    }

    /**
     * 执行http请求
     *
     * @param request httpUri请求参数
     * @return 返回服务器响应内容
     */
    private static String executeRequest(HttpUriRequest request) {
        try (CloseableHttpResponse response = httpClient.execute(request);) {
            HttpEntity entity = response.getEntity();
            try {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    throw new IllegalStateException("request server error: " + response.getStatusLine().getReasonPhrase());
                }
                return EntityUtils.toString(entity, StandardCharsets.UTF_8);
            } finally {
                if (entity != null) {
                    entity.getContent().close();
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("io error:", e);
        }
    }


    public static void main(String[] args) throws Exception {


    }

}

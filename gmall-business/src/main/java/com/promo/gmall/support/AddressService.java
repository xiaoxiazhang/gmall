package com.promo.gmall.support;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.promo.gmall.domain.OverseaAddressDO;
import com.promo.gmall.mapper.OverseaAddressMapper;
import com.promo.gmall.utils.MessageTemplate;
import com.promo.gmall.utils.taobao.TBDomainConstant;
import com.promo.gmall.utils.taobao.request.HttpGetWithEntity;
import com.promo.gmall.utils.taobao.request.StateCodeQueryRequest;
import com.promo.gmall.utils.taobao.request.StateQueryRequest;
import com.promo.gmall.utils.taobao.response.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
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
import org.apache.http.util.EntityUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.util.UriEncoder;

import javax.annotation.Resource;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.promo.gmall.utils.taobao.TBDomainConstant.STATE_CODE_QUERY_APP_KEY;
import static com.promo.gmall.utils.taobao.TBDomainConstant.STATE_QUERY_APP_KEY;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Slf4j
@Component
public class AddressService {


    @Resource
    private OverseaAddressMapper overseaAddressMapper;


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


    public List<OverseaAddressDO> create() {
        List<OverseaAddressDO> overseaAddressDOS = Lists.newArrayList();

        // 根节点
        OverseaAddressDO overseaAddressDO = new OverseaAddressDO();
        overseaAddressDO.setResourceId(228L);
        overseaAddressDO.setPResourceId(0L);
        overseaAddressDO.setPostCode("");
        overseaAddressDO.setLevel(1);
        overseaAddressDO.setName("US");
        overseaAddressMapper.insertSelective(overseaAddressDO);


        long timestamp = System.currentTimeMillis();
        Gson gson = new GsonBuilder().create();

        // 构造请求 - 参数,cookie
        StateQueryRequest request = new StateQueryRequest();
        request.setPid(TBDomainConstant.US_ID);
        String requestData = gson.toJson(request);
        String sign = getSign(STATE_QUERY_APP_KEY, requestData, timestamp);

        Map<String, Object> map01 = new HashMap<>();
        map01.put("appKey", STATE_QUERY_APP_KEY);
        map01.put("pid", TBDomainConstant.US_ID);
        map01.put("sign", sign);
        map01.put("timestamp", timestamp);
        String parentUrl = MessageTemplate.processTemplate(TBDomainConstant.STATE_QUERY_URL, map01);

        HttpGetWithEntity httpGet = null;
        try {
            httpGet = new HttpGetWithEntity(new URIBuilder(parentUrl).build());
        } catch (URISyntaxException e) {

        }
        httpGet.setEntity(new StringEntity(requestData, ContentType.APPLICATION_JSON));
        httpGet.setHeader("cookie", TBDomainConstant.COOKIE);


        AtomicInteger count = new AtomicInteger(0);

        // 发送请求
        String jsonResponse = jsonp2JSON(executeRequest(httpGet));
        ResultDTO<DivisionGroupData> divisionGroupDataResultDTO = JSON.parseObject(jsonResponse, new TypeReference<ResultDTO<DivisionGroupData>>() {
        });
        divisionGroupDataResultDTO.getData().getDivisionGroups().stream()
                .flatMap(e -> e.getItems().stream())
                .forEach(stateItem -> {
                    OverseaAddressDO overseaAddressDO02 = new OverseaAddressDO();
                    overseaAddressDO02.setLevel(2);
                    overseaAddressDO02.setPostCode("");
                    overseaAddressDO02.setName(stateItem.getName());
                    overseaAddressDO02.setPResourceId(228L);
                    overseaAddressDO02.setResourceId(Long.parseLong(stateItem.getId()));

                    try {
                        overseaAddressMapper.insertSelective(overseaAddressDO02);
                    } catch (DuplicateKeyException e) {
                        throw new RuntimeException("重复的resourceId", e);
                    }

                    // sub数据
                    StateQueryRequest subRequest = new StateQueryRequest();
                    subRequest.setPid(stateItem.getId());
                    String subRequestData = gson.toJson(subRequest);
                    String subSign = getSign(STATE_QUERY_APP_KEY, subRequestData, timestamp);

                    Map<String, Object> map02 = new HashMap<>();
                    map02.put("appKey", STATE_QUERY_APP_KEY);
                    map02.put("pid", stateItem.getId());
                    map02.put("sign", subSign);
                    map02.put("timestamp", timestamp);
                    String subUrl = MessageTemplate.processTemplate(TBDomainConstant.STATE_QUERY_URL, map02);
                    HttpGetWithEntity subHttpGet = null;
                    try {
                        subHttpGet = new HttpGetWithEntity(new URIBuilder(subUrl).build());
                    } catch (URISyntaxException e) {
                        throw new RuntimeException("url解析失败", e);
                    }
                    StringEntity entity02 = new StringEntity(JSON.toJSONString(new StateQueryRequest().setPid(stateItem.getId())),
                            ContentType.APPLICATION_JSON);
                    subHttpGet.setEntity(entity02);
                    subHttpGet.setHeader("cookie", TBDomainConstant.COOKIE);
                    String jsonResponse02 = jsonp2JSON(executeRequest(subHttpGet));
                    ResultDTO<DivisionGroupData> subDivisionGroupDataResultDTO = JSON.parseObject(jsonResponse02, new TypeReference<ResultDTO<DivisionGroupData>>() {
                    });

                    subDivisionGroupDataResultDTO.getData().getDivisionGroups().stream()
                            .flatMap(e -> e.getItems().stream())
                            .parallel()
                            .forEach(stateItem02 -> {
                                OverseaAddressDO overseaAddressDO03 = new OverseaAddressDO();
                                overseaAddressDO03.setLevel(3);
                                overseaAddressDO03.setPostCode("");
                                overseaAddressDO03.setName(stateItem02.getName());
                                overseaAddressDO03.setPResourceId(Long.parseLong(stateItem.getId()));
                                overseaAddressDO03.setResourceId(Long.parseLong(stateItem02.getId()));

                                // sub数据
                                StateCodeQueryRequest codeQueryRequest = new StateCodeQueryRequest();
                                codeQueryRequest.setSearchName(stateItem02.getName());
                                String codeQueryData = gson.toJson(codeQueryRequest);
                                String codeQuerySign = getSign(STATE_CODE_QUERY_APP_KEY, codeQueryData, timestamp);

                                HttpGetWithEntity codeQueryHttpGet = null;
                                try {
                                    Map<String, Object> map03 = new HashMap<>();
                                    map03.put("appKey", STATE_CODE_QUERY_APP_KEY);
                                    map03.put("sign", codeQuerySign);
                                    map03.put("searchName", UriEncoder.encode(stateItem02.getName()));
                                    map03.put("timestamp", timestamp);
                                    String codeQueryUrl = MessageTemplate.processTemplate(TBDomainConstant.STATE_CODE_QUERY_URL, map03);
                                    codeQueryHttpGet = new HttpGetWithEntity(new URIBuilder(codeQueryUrl).build());
                                } catch (Exception e) {
                                    throw new RuntimeException("构建请求失败啦", e);
                                }
                                StringEntity entity03 = new StringEntity(JSON.toJSONString(new StateCodeQueryRequest().setSearchName(stateItem02.getName())),
                                        ContentType.APPLICATION_JSON);
                                codeQueryHttpGet.setEntity(entity03);
                                codeQueryHttpGet.setHeader("cookie", TBDomainConstant.COOKIE);
                                String jsonResponse03 = jsonp2JSON(executeRequest(codeQueryHttpGet));
                                ResultDTO<RegionDataGroup> regionDataGroupResultDTO = JSON.parseObject(jsonResponse03, new TypeReference<ResultDTO<RegionDataGroup>>() {
                                });


                                List<RegionData> regionDatas = regionDataGroupResultDTO.getData().getRegionDatas();
                                if (regionDatas != null) {
                                    for (RegionData regionData : regionDatas) {
                                        if (regionData.getId().equals(stateItem02.getId())) {
                                            String code = regionData.getPcList().stream().map(CodeInfo::getPostCode).collect(Collectors.joining(","));
                                            overseaAddressDO03.setPostCode(code);
                                            break;
                                        }
                                    }
                                }
                                overseaAddressMapper.insertSelective(overseaAddressDO03);
                                if (count.incrementAndGet() % 1000 == 0) {
                                    log.info("已处理的国外地址数量: {}", overseaAddressDOS.size());
                                }

                            });
//                    for (DivisionGroup divisionGroup : subDivisionGroupDataResultDTO.getData().getDivisionGroups()) {
//                        for (StateItem stateItem02 : divisionGroup.getItems()) {

//
//                        }
//                    }
                });

        return overseaAddressDOS;
    }


    private static String jsonp2JSON(String jsonp) {
        int start = jsonp.indexOf("(");
        int end = jsonp.lastIndexOf(")");
        return jsonp.substring(start + 1, end);
    }


    private static String getSign(String appKey, String data, long timestamp) {
        String req = TBDomainConstant.TOKEN + "&" + timestamp + "&" + appKey + "&" + data;
        return DigestUtils.md5Hex(req);
    }


    private static URI getURI(String urlTemplate, String sign, String pid) {
        Map<String, Object> map = new HashMap<>();
        map.put("pid", Long.parseLong(pid));
        try {
            String uri = MessageTemplate.processTemplate(urlTemplate, map);
            System.out.println("请求地址：" + uri);
            return new URIBuilder(uri).build();
        } catch (URISyntaxException e) {
            return null;
        }
    }


}

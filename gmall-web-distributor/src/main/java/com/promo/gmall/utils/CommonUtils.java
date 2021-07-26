package com.promo.gmall.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 通用工具类
 *
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
public class CommonUtils {


    // 客户端IP相关HTTP头信息
    private static final String[] HEADERS_TO_TRY = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR",
            "X-Real-IP"
    };


    private CommonUtils() {
        // blank

    }


    /**
     * 获取客户端ip地址(可以穿透代理)
     *
     * @param request http请求对象
     * @return 返回真实客户端IP
     */
    public static String getClientIpAddress(HttpServletRequest request) {
        for (String header : HEADERS_TO_TRY) {
            String ip = request.getHeader(header);
            if (StringUtils.isNotEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {

                // X-Forwarded-For的值并不止一个，而是一串IP值，
                // 取X-Forwarded-For中第一个非unknown的有效IP字符串。
                if ("X-Forwarded-For".equals(header)) {
                    int index = ip.indexOf(',');
                    if (index != -1) {
                        return ip.substring(0, index);
                    }
                }
                return ip;
            }
        }
        return request.getRemoteAddr();
    }

}

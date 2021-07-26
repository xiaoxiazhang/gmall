package com.promo.gmall.support;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * token管理器
 *
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Slf4j
public class TokenUtils {

    /**
     * token过期时间
     */
    private static final long TOKEN_EXPIRATION_TIME = 24 * 60 * 60 * 1000;

    /**
     * token编码签名
     */
    private static final String TOKEN_SIGN_KEY = "099b3b060154898840f0ebdfb46ec78f";


    /**
     * 根据用户名生成对应的token信息
     */
    public static String createToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, TOKEN_SIGN_KEY)
                .compressWith(CompressionCodecs.GZIP).compact();
    }


    /**
     * 通过token获取到用户信息
     */
    public static String getUserFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(TOKEN_SIGN_KEY).
                            parseClaimsJws(token).getBody().getSubject();
        } catch (Exception e) {
            log.error("token格式有问题. token: {}", token, e);
            return StringUtils.EMPTY;
        }

    }


    /**
     * 获取token, 先从cookie中获取，cookie中没有从请求头中获取
     *
     * @return 返回token, 可能为空哦。
     */
    public static String getToken(HttpServletRequest request) {
        // 优先从cookie中获取token
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return request.getHeader("token");
    }
}

package com.promo.gmall.security.filter;

import com.alibaba.fastjson.JSON;
import com.promo.gmall.common.APIResult;
import com.promo.gmall.constants.CacheConstants;
import com.promo.gmall.redis.CacheHelper;
import com.promo.gmall.request.acl.AdminLoginRequest;
import com.promo.gmall.security.DefaultUserDetails;
import com.promo.gmall.support.TokenUtils;
import com.promo.gmall.utils.CodeMsgHelper;
import com.promo.gmall.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.promo.gmall.constants.CacheConstants.SECURITY_TOKEN_KEY;

/**
 * 认证请求过滤器
 *
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Slf4j
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private CacheHelper cacheHelper;


    public TokenLoginFilter(AuthenticationManager authenticationManager, CacheHelper cacheHelper) {
        this.authenticationManager = authenticationManager;
        this.cacheHelper = cacheHelper;

        this.setPostOnly(false);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/auth/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String requestBody;
        try {
            InputStream inputStream = request.getInputStream();
            requestBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        } catch (IOException ex) {
            throw new AuthenticationServiceException("获取输入流失败", ex);
        }

        if (StringUtils.isEmpty(requestBody)) {
            throw new AuthenticationServiceException("认证请求参数不能为空");
        }

        AdminLoginRequest param = JSON.parseObject(requestBody, AdminLoginRequest.class);
        if (StringUtils.isEmpty(param.getUsername())) {
            throw new AuthenticationServiceException("用户名不能为空");
        }

        if (StringUtils.isEmpty(param.getPassword())) {
            throw new AuthenticationServiceException("用户密码不能为空");
        }

        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(param.getUsername(), param.getPassword()));
    }

    /**
     * 认证成功回调方法
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) {
        DefaultUserDetails user = (DefaultUserDetails) auth.getPrincipal();
        String token = TokenUtils.createToken(user.getUsername());
        cacheHelper.set(String.format(SECURITY_TOKEN_KEY, user.getUsername()), user, (8 * CacheConstants.ONE_HOURS));

        // 将token数据写入cookie
        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(30);
        response.addCookie(cookie);

        log.info("Auth login success. userName: {}", user.getUsername());
        ResponseUtils.out(response, APIResult.success(token));
    }


    /**
     * 认证失败回调方法
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        log.error("Auth login error", e);
        ResponseUtils.out(response, APIResult.fail(CodeMsgHelper.buildBusinessError(e.getMessage())));
    }


}

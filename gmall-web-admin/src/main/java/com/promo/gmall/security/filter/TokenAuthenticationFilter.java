package com.promo.gmall.security.filter;

import com.promo.gmall.common.APIResult;
import com.promo.gmall.exception.BizErrorCodeEnum;
import com.promo.gmall.redis.CacheHelper;
import com.promo.gmall.security.DefaultUserDetails;
import com.promo.gmall.support.TokenUtils;
import com.promo.gmall.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.promo.gmall.constants.CacheConstants.SECURITY_TOKEN_KEY;
import static com.promo.gmall.constants.MallCommonConstants.TOKEN_VALID_TIME;

/**
 * 认证授权过滤器
 *
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Slf4j
public class TokenAuthenticationFilter extends BasicAuthenticationFilter {

    private CacheHelper cacheHelper;


    public TokenAuthenticationFilter(AuthenticationManager authenticationManager, CacheHelper cacheHelper) {
        super(authenticationManager);
        this.cacheHelper = cacheHelper;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        // 认证校验
        String token = TokenUtils.getToken(request);
        if (StringUtils.isEmpty(token)) {
            ResponseUtils.out(response, APIResult.fail(BizErrorCodeEnum.UN_LOGIN_ERROR.getCodeMsg()));
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(token);
        if (authentication == null) {
            log.warn("用户token认证失败. token: {}", token);
            ResponseUtils.out(response, APIResult.fail(BizErrorCodeEnum.UN_LOGIN_ERROR.getCodeMsg()));
            return;
        }

        // 将认证信息写入上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }


    /**
     * 通过token获取认证信息
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if (StringUtils.isNotEmpty(token)) {
            String userName = TokenUtils.getUserFromToken(token);

            if (StringUtils.isNotEmpty(userName)) {
                DefaultUserDetails userDetails = cacheHelper.get(String.format(SECURITY_TOKEN_KEY, userName));
                if (userDetails != null) {

                    // 更新token有效时间
                    cacheHelper.expired(String.format(SECURITY_TOKEN_KEY, userName), TOKEN_VALID_TIME);
                    return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
                }
            }
        }

        return null;
    }
}

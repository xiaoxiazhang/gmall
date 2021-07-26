package com.promo.gmall.security;

import com.promo.gmall.common.APIResult;
import com.promo.gmall.redis.CacheHelper;
import com.promo.gmall.support.TokenUtils;
import com.promo.gmall.utils.ResponseUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.promo.gmall.constants.CacheConstants.SECURITY_TOKEN_KEY;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Component
public class TokenLogoutHandler implements LogoutHandler {

    @Autowired
    private CacheHelper cacheHelper;


    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = TokenUtils.getToken(request);
        if (StringUtils.isNotBlank(token)) {
            String userName = TokenUtils.getUserFromToken(token);
            if (StringUtils.isNotEmpty(userName)) {
                cacheHelper.delete(String.format(SECURITY_TOKEN_KEY, userName));
            }
        }

        // 响应体中写入结果
        ResponseUtils.out(response, APIResult.success());
    }


}

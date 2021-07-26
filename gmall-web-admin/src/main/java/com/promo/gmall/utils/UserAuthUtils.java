package com.promo.gmall.utils;

import com.promo.gmall.security.DefaultUserDetails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
public class UserAuthUtils {


    /**
     * 获取登录用户信息
     */
    public static DefaultUserDetails getAclUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }

        return (DefaultUserDetails) authentication.getPrincipal();
    }

    /**
     * 获取登录用户名
     */
    public static String getUserName() {
        DefaultUserDetails userDetails = getAclUser();
        if (userDetails == null) {
            return StringUtils.EMPTY;
        }

        return userDetails.getUsername();
    }

    /**
     * 获取登录用户ID
     */
    public static Long getUserId() {
        DefaultUserDetails userDetails = getAclUser();
        if (userDetails == null) {
            return 0L;
        }

        return userDetails.getUserId();
    }


}

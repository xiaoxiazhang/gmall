package com.promo.gmall.service.acl;

import com.promo.gmall.manager.acl.AclUserManager;
import com.promo.gmall.model.acl.AclUserBO;
import com.promo.gmall.redis.CacheHelper;
import com.promo.gmall.support.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import static com.promo.gmall.constants.CacheConstants.SECURITY_TOKEN_KEY;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Service
public class AclUserService {

    @Autowired
    private CacheHelper cacheHelper;

    @Autowired
    private AclUserManager aclUserManager;


    public AclUserBO getByUsername(String username) {
        return aclUserManager.getByUsername(username);
    }

    public void login() {


    }

    public void logout(HttpServletRequest request) {

        String token = TokenUtils.getToken(request);

        if (StringUtils.isNotBlank(token)) {
            String userName = TokenUtils.getUserFromToken(token);
            if (StringUtils.isNotEmpty(userName)) {
                cacheHelper.delete(String.format(SECURITY_TOKEN_KEY, userName));
            }
        }
    }
}

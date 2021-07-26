package com.promo.gmall.security;

import com.promo.gmall.exception.BizErrorCodeEnum;
import com.promo.gmall.model.acl.AclUserBO;
import com.promo.gmall.service.acl.AclUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Component("userDetailsService")
public class DefaultUserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private AclUserService aclUserService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AclUserBO aclUserBO = aclUserService.getByUsername(username);

        if (aclUserBO == null) {
            throw new UsernameNotFoundException(BizErrorCodeEnum.NO_EXISTS_USER_ERROR_CODE.getMessage());
        }

        return new DefaultUserDetails(aclUserBO);
    }


}

package com.promo.gmall.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.promo.gmall.model.acl.AclUserBO;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Data
public class DefaultUserDetails implements UserDetails {

    private static final long serialVersionUID = -4901784245917860573L;

    /**
     * 用户信息
     */
    private AclUserBO aclUserBO;


    public DefaultUserDetails(AclUserBO aclUserBO) {
        this.aclUserBO = aclUserBO;
    }

    public Long getUserId() {
        return aclUserBO.getId();
    }


    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return aclUserBO.getPassword();
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return aclUserBO.getUsername();
    }


    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}

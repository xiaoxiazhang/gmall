package com.promo.gmall.config;

import com.promo.gmall.common.APIResult;
import com.promo.gmall.exception.BizErrorCodeEnum;
import com.promo.gmall.redis.CacheHelper;
import com.promo.gmall.security.TokenLogoutHandler;
import com.promo.gmall.security.filter.TokenAuthenticationFilter;
import com.promo.gmall.security.filter.TokenLoginFilter;
import com.promo.gmall.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Slf4j
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private TokenLogoutHandler tokenLogoutHandler;

    @Autowired
    private CacheHelper cacheHelper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                // 未认证访问错误处理器
                .authenticationEntryPoint((request, response, authException) -> {
                    log.error("用户认证失败.", authException);
                    ResponseUtils.out(response, APIResult.fail(BizErrorCodeEnum.UN_LOGIN_ERROR.getCodeMsg()));
                })
                .and().csrf().disable()

                .authorizeRequests()
                // .antMatchers("/user/**").hasAuthority("user")
                .anyRequest().authenticated()

                // 退出登录配置
                .and().logout().logoutUrl("/auth/logout")
                .addLogoutHandler(tokenLogoutHandler)
                .permitAll()

                // 登录请求过滤器 和 认证过滤器
                .and()
                .addFilter(new TokenLoginFilter(authenticationManager(), cacheHelper))
                .addFilter(new TokenAuthenticationFilter(authenticationManager(), cacheHelper))
                .httpBasic();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        // 忽略认证授权URL地址
        web.ignoring().antMatchers("/doc.html", "/swagger-resources/**", "/v2/**", "/webjars/**", "/error", "/**/favicon.ico", "/test/**");
    }


}

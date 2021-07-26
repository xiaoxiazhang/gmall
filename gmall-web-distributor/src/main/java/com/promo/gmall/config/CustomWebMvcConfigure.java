package com.promo.gmall.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web 配置
 *
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Component
public class CustomWebMvcConfigure implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // C端用户登录拦截器
        registry.addInterceptor(new LoginHandlerInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/address")
                .excludePathPatterns("/user/register", "/user/login")
                .excludePathPatterns("/...");


    }


}

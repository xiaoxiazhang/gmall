package com.promo.gmall.controller.acl;

import com.promo.gmall.common.APIResult;
import com.promo.gmall.request.acl.AdminLoginRequest;
import com.promo.gmall.security.filter.TokenLoginFilter;
import com.promo.gmall.service.acl.AclUserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("auth")
@Api("服务登录登出认证接口")
public class AuthController {


    @Autowired
    private AclUserService aclUserService;

    /**
     * 用户登录接口目前只是定义【供前端发送请求参看】
     * 实际请求调用会被拦截器拦截，不会被DispatchServlet处理。具体参数下面类
     *
     * @see TokenLoginFilter
     */
    @PostMapping("/login")
    public APIResult<String> login(@RequestBody AdminLoginRequest request) {

        aclUserService.login();
        return APIResult.success();
    }


    /**
     * 登出操作，删除缓存
     */
    @PostMapping("/logout")
    public APIResult<String> logout(HttpServletRequest httpRequest) {
        aclUserService.logout(httpRequest);
        return APIResult.success();
    }


}

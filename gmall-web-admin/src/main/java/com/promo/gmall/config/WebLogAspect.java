package com.promo.gmall.config;


import com.alibaba.fastjson.JSON;
import com.promo.gmall.utils.CommonUtils;
import com.promo.gmall.utils.UserAuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuji
 * @since 1.0.0
 */
@Slf4j
@Aspect
@Component
public class WebLogAspect {

    @Pointcut("execution(public * com.promo.gmall.controller..*(..))")
    public void action() {
        // blank
    }


    @Around("action()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取URL
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();

        // 获取IP
        String clientIpAddress = CommonUtils.getClientIpAddress(request);

        // 获取请求参数
        String param = "";
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            List<Object> paramList = Arrays.stream(args).
                    filter(this::isNotSpringMvcParam)
                    .collect(Collectors.toList());
            param = JSON.toJSONString(paramList);
            request.setAttribute("WEB_ARGS", param);
        }

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long cost = System.currentTimeMillis() - start;
        log.info("用户请求日志:url==>【{}】, ip==> {}, userId==>{}, args==> {}, result==> {}, cost==>{} ms",
                url, clientIpAddress, UserAuthUtils.getUserId(), param, JSON.toJSONString(result), cost);

        return result;
    }


    /**
     * 非SpringMVC参数
     */
    private boolean isNotSpringMvcParam(Object obj) {
        return !(obj instanceof HttpServletRequest)
                && !(obj instanceof HttpServletResponse)
                && !(obj instanceof HttpSession)
                && !(obj instanceof Errors);
    }


}

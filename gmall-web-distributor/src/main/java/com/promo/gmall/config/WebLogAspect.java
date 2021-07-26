package com.promo.gmall.config;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.promo.gmall.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();

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
            List<Object> paramList = Arrays.stream(args).filter(e -> isNotSpringMvcParam(e))
                    .collect(Collectors.toList());
            param = JSON.toJSONString(paramList, SerializerFeature.IgnoreErrorGetter);
            request.setAttribute("WEB_ARGS", param);
        }

        // TODO: 2020/9/17 获取用户ID
        Long userId = 0L;

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long cost = System.currentTimeMillis() - start;
        log.info("用户请求日志:url==>【{}】, ip==> {}, userId==>{}, param==> {}, result==> {}, cost==>{}ms",
                url, clientIpAddress, userId, param, JSON.toJSONString(result), cost);

        return result;
    }


    /**
     * 非SpringMVC参数
     */
    private boolean isNotSpringMvcParam(Object obj) {
        return !(obj instanceof HttpServletRequest)
                && !(obj instanceof HttpServletResponse)
                && !(obj instanceof Errors);
    }


}

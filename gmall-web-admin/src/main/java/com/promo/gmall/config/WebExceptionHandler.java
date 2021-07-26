package com.promo.gmall.config;

import com.alibaba.fastjson.JSON;
import com.promo.gmall.common.APIResult;
import com.promo.gmall.exception.BizErrorCodeEnum;
import com.promo.gmall.exception.MallBusinessException;
import com.promo.gmall.utils.CodeMsgHelper;
import com.promo.gmall.utils.CommonUtils;
import com.promo.gmall.utils.UserAuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 全局异常处理.
 *
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Slf4j
@RestControllerAdvice
public class WebExceptionHandler {


    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public APIResult<Object> handleHttpMessageNotReadableException(HttpServletRequest request, HttpMessageNotReadableException e) {
        String clientIpAddress = CommonUtils.getClientIpAddress(request);
        log.error("请求错误日志: url==>【{}】, ip==>{}", request.getRequestURI(), clientIpAddress, e);
        return APIResult.fail(CodeMsgHelper.buildParamError("参数解析失败【400】"));
    }


    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public APIResult<Object> handleHttpRequestMethodNotSupportedException(HttpServletRequest request, HttpRequestMethodNotSupportedException e) {
        String clientIpAddress = CommonUtils.getClientIpAddress(request);
        log.error("请求错误日志: url==>【{}】, ip==>{}, userId==> {}, errorMsg==>{}", request.getRequestURI(), clientIpAddress, "不支持当前请求方法", e);
        return APIResult.fail(CodeMsgHelper.buildServerError("请求方法不匹配"));
    }


    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public APIResult<Object> handleHttpMediaTypeNotSupportedException(HttpServletRequest request, HttpMediaTypeNotSupportedException e) {
        String clientIpAddress = CommonUtils.getClientIpAddress(request);

        log.error("请求错误日志: url==>【{}】, ip==>{}, userId==> {}, errorMsg==>{}", request.getRequestURI(), clientIpAddress, "不支持当前请求方法", e);
        return APIResult.fail(CodeMsgHelper.buildServerError("请求媒体类型不匹配"));
    }


    /**
     * JSR303校验异常统一处理
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public APIResult<Object> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
        String clientIpAddress = CommonUtils.getClientIpAddress(request);
        Long userId = UserAuthUtils.getUserId();

        Map<String, String> errorMap = e.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        log.warn("参数异常日志: url==>【{}】, ip==>{}, userId==> {}, errorMsg==>{}", request.getRequestURI(), clientIpAddress, userId, JSON.toJSON(errorMap));
        return APIResult.fail(CodeMsgHelper.buildParamError(JSON.toJSONString(errorMap)));
    }


    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(MallBusinessException.class)
    public APIResult<Object> handleBizException(HttpServletRequest request, MallBusinessException e) {
        Object args = request.getAttribute("WEB_ARGS");
        String clientIpAddress = CommonUtils.getClientIpAddress(request);
        Long userId = UserAuthUtils.getUserId();

        log.warn("业务异常日志: url==>【{}】, ip==>{}, userId==> {}, param==> {}, errorMsg==>{}", request.getRequestURI(), clientIpAddress, userId, args, e.getCodeMsg().getMessage());
        return APIResult.fail(e);
    }


    /**
     * MethodSecurityInterceptor拦截器拦截认证信息失败
     *
     * @see org.springframework.security.access.intercept.aopalliance.MethodSecurityInterceptor
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public APIResult<Object> handleAccessDeniedException(HttpServletRequest request, Throwable throwable) {
        Object args = request.getAttribute("WEB_ARGS");
        String clientIpAddress = CommonUtils.getClientIpAddress(request);
        Long userId = UserAuthUtils.getUserId();

        log.error("用户授权失败: url==>【{}】, ip==>{}, userId==> {}, param==> {}", request.getRequestURI(), clientIpAddress, userId, args);
        return APIResult.fail(BizErrorCodeEnum.UN_AUTH_ERROR.getCodeMsg());
    }


    @ExceptionHandler(Throwable.class)
    @ResponseStatus(value = HttpStatus.OK)
    public APIResult<Object> handleException(HttpServletRequest request, Throwable throwable) {
        Object args = request.getAttribute("WEB_ARGS");
        String clientIpAddress = CommonUtils.getClientIpAddress(request);
        Long userId = UserAuthUtils.getUserId();

        log.error("系统异常日志: url==>【{}】, ip==>{}, userId==> {}, param==> {}", request.getRequestURI(), clientIpAddress, userId, args, throwable);
        return APIResult.fail(CodeMsgHelper.buildServerError("服务器开小差~"));
    }


}

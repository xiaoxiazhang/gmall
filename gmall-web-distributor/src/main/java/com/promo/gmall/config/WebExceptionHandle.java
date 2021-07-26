package com.promo.gmall.config;

import com.promo.gmall.utils.CodeMsgHelper;
import com.promo.gmall.utils.CommonUtils;
import com.promo.gmall.common.APIResult;
import com.promo.gmall.exception.MallBusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理.
 *
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Slf4j
@RestControllerAdvice
public class WebExceptionHandle {


    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public APIResult<Object> handleHttpMessageNotReadableException(HttpServletRequest request, HttpMessageNotReadableException e) {

        // TODO: 2020/9/17 获取userId
        Long userId = 0L;
        Object param = request.getAttribute("WEB_ARGS");
        String clientIpAddress = CommonUtils.getClientIpAddress(request);

        log.error("用户错误日志:url==>【{}】, ip==>{}, userId==> {}, param==> {}, errorMsg==>{}",
                request.getRequestURI(), clientIpAddress, userId, param, "参数解析失败", e);
        return APIResult.fail(CodeMsgHelper.buildParamError("参数解析失败【400】"));
    }


    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public APIResult<Object> handleHttpRequestMethodNotSupportedException(HttpServletRequest request, HttpRequestMethodNotSupportedException e) {
        Long userId = 0L;
        Object param = request.getAttribute("WEB_ARGS");
        String clientIpAddress = CommonUtils.getClientIpAddress(request);


        log.error("用户错误日志:url==>【{}】, ip==>{}, userId==> {}, param==> {}, errorMsg==>{}",
                request.getRequestURI(), clientIpAddress, userId, param, "不支持当前请求方法", e);
        return APIResult.fail(CodeMsgHelper.buildServerError("请求方法不匹配"));
    }


    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public APIResult<Object> handleHttpMediaTypeNotSupportedException(HttpServletRequest request, Exception e) {
        Long userId = 0L;
        Object param = request.getAttribute("WEB_ARGS");
        String clientIpAddress = CommonUtils.getClientIpAddress(request);

        log.error("用户错误日志:url==>【{}】, ip==>{}, userId==> {}, param==> {}, errorMsg==>{}",
                request.getRequestURI(), clientIpAddress, userId, param, "不支持当前请求方法", e);
        return APIResult.fail(CodeMsgHelper.buildServerError("请求媒体类型不匹配"));
    }


    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(MallBusinessException.class)
    public APIResult<Object> handleException(HttpServletRequest request, MallBusinessException e) {
        Long userId = 0L;
        Object param = request.getAttribute("WEB_ARGS");
        String clientIpAddress = CommonUtils.getClientIpAddress(request);

        log.error("用户错误日志:url==>【{}】, ip==>{}, userId==> {}, param==> {}, errorMsg==>{}",
                request.getRequestURI(), clientIpAddress, userId, param, e.getCodeMsg().getMessage(), e);
        return APIResult.fail(e);
    }


    @ExceptionHandler(Throwable.class)
    @ResponseStatus(value = HttpStatus.OK)
    public APIResult<Object> handleException(HttpServletRequest request, Throwable throwable) {
        Long userId = 0L;
        Object param = request.getAttribute("WEB_ARGS");
        String clientIpAddress = CommonUtils.getClientIpAddress(request);

        log.error("用户错误日志:url==>【{}】, ip==>{}, userId==> {}, param==> {}",
                request.getRequestURI(), clientIpAddress, userId, param, throwable);
        return APIResult.fail(CodeMsgHelper.buildServerError("服务器开小差~"));
    }


}

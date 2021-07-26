package com.promo.gmall.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
public class BindingResultUtils {

    public static <T> void handle(BindingResult result) {
        // 抛出参数异常
        if (result.hasErrors()) {
            throw new IllegalArgumentException(assembleFiledError(result.getFieldErrors()));
        }
    }


    private static String assembleFiledError(List<FieldError> fieldErrors) {
        if (CollectionUtils.isEmpty(fieldErrors)) {
            return StringUtils.EMPTY;
        }

        return fieldErrors.stream()
                .map(e -> e.getField() + ":" + e.getDefaultMessage())
                .collect(Collectors.joining(";"));
    }


}

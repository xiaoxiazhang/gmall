package com.promo.gmall.annotation;

import java.lang.annotation.*;

/**
 * 列元数据注解：定义列索引和列名
 * <p> 列索引从0开始
 *
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnMeta {


    /**
     * 列名
     */
    String name() default "";

    /**
     * 列索引
     */
    int index() default -1;

}

package com.promo.gmall.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

/**
 * 重试接口工具类
 *
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Slf4j
public abstract class RetryUtils {

    /**
     * 重试调度方法
     *
     * @param supplier     返回数据方法执行体
     * @param retryCount   重试次数
     * @param intervalTime 重试间隔时间/ms(注意：阻塞当前线程)
     * @return R
     */
    public static <R> R invoke(Supplier<R> supplier, int retryCount, long intervalTime) {
        for (int i = 1; i <= retryCount; i++) {
            try {
                if (intervalTime > 0) {
                    Thread.sleep(intervalTime);
                }
                return supplier.get();
            } catch (InterruptedException e) {
                log.error("InterruptedException Error.", e);

                Thread.currentThread().interrupt();
                break;  // 线程中断直接退出重试
            } catch (Throwable throwable) {
                log.error("Retry error【retryCount={}, 】", i, throwable);
            }
        }

        return null;
    }

}

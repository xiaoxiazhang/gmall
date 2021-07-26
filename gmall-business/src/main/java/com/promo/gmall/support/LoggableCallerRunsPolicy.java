package com.promo.gmall.support;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Slf4j
public class LoggableCallerRunsPolicy extends ThreadPoolExecutor.CallerRunsPolicy {
    private String name;

    public LoggableCallerRunsPolicy(String name) {
        this.name = name;
    }

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        super.rejectedExecution(r, e);

        // 记录日志，以便分析线程数够不够
        log.warn(this.name + " run in caller's thread");
    }
}

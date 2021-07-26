package com.promo.gmall;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.Arrays;

/**
 * 应用启动类
 *
 * @author wuji
 * @since 1.0.0
 */
@Slf4j
@MapperScan("com.promo.gmall.mapper")
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(AdminApplication.class);
        ApplicationContext ctx = application.run(args);
        String[] profiles = ctx.getEnvironment().getActiveProfiles();
        log.info("Application active profile: {}", Arrays.toString(profiles));
    }

}

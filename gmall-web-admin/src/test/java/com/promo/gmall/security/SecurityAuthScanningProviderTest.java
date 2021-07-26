package com.promo.gmall.security;


import com.alibaba.fastjson.JSON;
import com.promo.gmall.model.acl.SecurityAuthNodeBO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Slf4j
@SpringBootTest
public class SecurityAuthScanningProviderTest {

    @Autowired
    private SecurityAuthScanningProvider securityAuthScanningProvider;

    @Test
    public void scan() {
        List<SecurityAuthNodeBO> securityAuthNodeBOList = securityAuthScanningProvider.scan();
        log.info("securityAuthNodeBOList==> {}", JSON.toJSONString(securityAuthNodeBOList));

    }
}
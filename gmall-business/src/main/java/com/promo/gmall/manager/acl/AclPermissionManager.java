package com.promo.gmall.manager.acl;

import com.promo.gmall.mapper.AclPermissionMapper;
import com.promo.gmall.redis.CacheHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Slf4j
@Component
public class AclPermissionManager {

    @Resource
    private CacheHelper cacheHelper;

    @Resource
    private AclPermissionMapper aclPermissionMapper;


}

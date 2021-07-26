package com.promo.gmall.service.acl;

import com.promo.gmall.manager.acl.AclRoleManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Slf4j
@Service
public class AclRoleService {


    @Autowired
    private AclRoleManager aclRoleManager;


}

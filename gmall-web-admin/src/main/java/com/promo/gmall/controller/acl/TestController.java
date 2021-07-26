package com.promo.gmall.controller.acl;

import com.promo.gmall.mapper.OverseaAddressMapper;
import com.promo.gmall.support.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.x.x
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {


    @Resource
    private OverseaAddressMapper overseaAddressMapper;

    @Autowired
    private AddressService addressService;


    @GetMapping("create")
    public String create() {
        return "success";
    }

    @GetMapping("get")
    public String hello() {
        overseaAddressMapper.selectByPrimaryKey(1L);
        return "success";
    }


}

package com.promo.gmall.controller.acl;

import com.promo.gmall.common.APIResult;
import com.promo.gmall.common.PageResponse;
import com.promo.gmall.request.acl.PermissionListRequest;
import com.promo.gmall.request.acl.PermissionSaveRequest;
import com.promo.gmall.request.acl.PermissionUpdateRequest;
import com.promo.gmall.response.acl.AclPermissionVO;
import com.promo.gmall.response.acl.MenuVO;
import com.promo.gmall.service.acl.AclPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Slf4j
@RestController
@Api("权限菜单管理模块")
@RequestMapping("permission")
public class PermissionController {

    @Autowired
    private AclPermissionService aclPermissionService;


    @ApiOperation("权限列表")
    @PostMapping(value = "/listPermissionPage")
    public APIResult<PageResponse<AclPermissionVO>> listPermissionPage(@RequestBody PermissionListRequest request) {

        return APIResult.success();
    }


    @ApiOperation("保存权限项或菜单项")
    @PostMapping(value = "/save")
    public APIResult<String> savePermission(@RequestBody PermissionSaveRequest request) {

        return APIResult.success();
    }


    @ApiOperation("更新权限项")
    @PreAuthorize("hasAuthority('permission@update')")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public APIResult<String> updatePermission(@RequestBody PermissionUpdateRequest request) {

        return APIResult.success();
    }


    @ApiOperation("删除权限项")
    @PostMapping(value = "/delete/{id}")
    public APIResult<String> delete(Long id) {

        return APIResult.success();
    }


    @ApiOperation("获取权限菜单项")
    @PostMapping(value = "/listMenu")
    public APIResult<MenuVO> get(Long id) {

        return APIResult.success();
    }


}

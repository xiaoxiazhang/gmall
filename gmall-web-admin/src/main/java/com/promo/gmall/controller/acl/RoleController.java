package com.promo.gmall.controller.acl;

import com.promo.gmall.common.APIResult;
import com.promo.gmall.common.PageResponse;
import com.promo.gmall.request.acl.RoleListRequest;
import com.promo.gmall.request.acl.RoleSaveRequest;
import com.promo.gmall.request.acl.RoleUpdateRequest;
import com.promo.gmall.response.acl.AclRoleVO;
import com.promo.gmall.service.acl.AclRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Slf4j
@RestController
@Api("角色管理模块")
@RequestMapping("role")
public class RoleController {

    @Autowired
    private AclRoleService aclRoleService;


    @ApiOperation("角色列表")
    @PostMapping(value = "/listRolePage")
    public APIResult<PageResponse<AclRoleVO>> listRolePage(@RequestBody RoleListRequest request) {

        return APIResult.success();
    }


    @ApiOperation("保存角色")
    @PostMapping(value = "/saveRole")
    public APIResult<String> saveRole(@RequestBody RoleSaveRequest request) {

        return APIResult.success();
    }


    @ApiOperation("更新角色")
    @RequestMapping(value = "/updateRole")
    public APIResult<String> updateRole(@RequestBody RoleUpdateRequest request) {

        return APIResult.success();
    }


    @ApiOperation("删除角色")
    @PostMapping(value = "/delete/{id}")
    public APIResult<String> deleteRole(@PathVariable Long id) {

        return APIResult.success();
    }


}

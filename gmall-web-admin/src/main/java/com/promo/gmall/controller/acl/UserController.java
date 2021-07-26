package com.promo.gmall.controller.acl;

import com.promo.gmall.common.APIResult;
import com.promo.gmall.common.PageResponse;
import com.promo.gmall.request.acl.UserListRequest;
import com.promo.gmall.request.acl.UserSaveRequest;
import com.promo.gmall.request.acl.UserUpdateRequest;
import com.promo.gmall.response.acl.AclUserVO;
import com.promo.gmall.response.acl.MenuVO;
import com.promo.gmall.service.acl.AclUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * acl用户管理模块
 *
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@RestController
@RequestMapping("/user")
@Api("用户管理模块")
public class UserController {


    @Autowired
    private AclUserService aclUserService;


    @ApiOperation("用户列表")
    @PostMapping(value = "/listAclUser")
    public APIResult<PageResponse<AclUserVO>> listAuthUser(@RequestBody UserListRequest request) {

        return APIResult.success();
    }


    @ApiOperation("保存用户")
    @PostMapping(value = "/saveUser")
    public APIResult<String> saveUser(@RequestBody UserSaveRequest request) {

        return APIResult.success();
    }


    @ApiOperation("用户详情")
    @RequestMapping(value = "/get/{id}")
    public APIResult<AclUserVO> getAclUserInfo(@PathVariable Long id) {

        return APIResult.success();
    }


    @ApiOperation("更新用户")
    @PostMapping(value = "/updateUser")
    public APIResult<String> updateUser(@RequestBody UserUpdateRequest request) {

        return APIResult.success();

    }


    @ApiOperation("删除用户")
    @PostMapping(value = "/delete/{id}")
    public APIResult<String> deleteUser(@PathVariable Long id) {

        return APIResult.success();
    }


    @ApiOperation("重置密码")
    @PostMapping(value = "/resetPassword/{id}")
    public APIResult<String> resetPassword(@PathVariable Long id) {

        return APIResult.success();
    }


    @ApiOperation("获取用户菜单")
    @PostMapping(value = "/getUserMenu")
    public APIResult<MenuVO> getUserMenu() {

        return APIResult.success();
    }

}

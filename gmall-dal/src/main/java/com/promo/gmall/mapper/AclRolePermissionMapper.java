package com.promo.gmall.mapper;

import com.promo.gmall.domain.AclRolePermissionDO;

public interface AclRolePermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AclRolePermissionDO record);

    int insertSelective(AclRolePermissionDO record);

    AclRolePermissionDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AclRolePermissionDO record);

    int updateByPrimaryKey(AclRolePermissionDO record);
}
package com.promo.gmall.mapper;

import com.promo.gmall.domain.AclUserRoleDO;

public interface AclUserRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AclUserRoleDO record);

    int insertSelective(AclUserRoleDO record);

    AclUserRoleDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AclUserRoleDO record);

    int updateByPrimaryKey(AclUserRoleDO record);
}
package com.promo.gmall.mapper;

import com.promo.gmall.domain.AclRoleDO;

public interface AclRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AclRoleDO record);

    int insertSelective(AclRoleDO record);

    AclRoleDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AclRoleDO record);

    int updateByPrimaryKey(AclRoleDO record);
}
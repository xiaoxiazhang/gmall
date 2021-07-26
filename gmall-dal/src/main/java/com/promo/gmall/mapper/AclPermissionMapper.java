package com.promo.gmall.mapper;

import com.promo.gmall.domain.AclPermissionDO;

public interface AclPermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AclPermissionDO record);

    int insertSelective(AclPermissionDO record);

    AclPermissionDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AclPermissionDO record);

    int updateByPrimaryKey(AclPermissionDO record);
}
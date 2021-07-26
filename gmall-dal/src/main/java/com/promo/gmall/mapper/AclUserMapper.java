package com.promo.gmall.mapper;

import com.promo.gmall.domain.AclUserDO;

public interface AclUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AclUserDO record);

    int insertSelective(AclUserDO record);

    AclUserDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AclUserDO record);

    int updateByPrimaryKey(AclUserDO record);


    /**
     * 通过用户获取用户信息
     */
    AclUserDO getByUsername(String username);


}
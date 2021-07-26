package com.promo.gmall.mapper;

import com.promo.gmall.domain.PropertyNameDO;

public interface PropertyNameMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PropertyNameDO record);

    int insertSelective(PropertyNameDO record);

    PropertyNameDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PropertyNameDO record);

    int updateByPrimaryKey(PropertyNameDO record);
}
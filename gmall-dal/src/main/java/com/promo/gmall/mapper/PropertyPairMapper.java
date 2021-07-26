package com.promo.gmall.mapper;

import com.promo.gmall.domain.PropertyPairDO;

public interface PropertyPairMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PropertyPairDO record);

    int insertSelective(PropertyPairDO record);

    PropertyPairDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PropertyPairDO record);

    int updateByPrimaryKey(PropertyPairDO record);
}
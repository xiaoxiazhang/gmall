package com.promo.gmall.mapper;

import com.promo.gmall.domain.PropertyValueDO;

public interface PropertyValueMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PropertyValueDO record);

    int insertSelective(PropertyValueDO record);

    PropertyValueDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PropertyValueDO record);

    int updateByPrimaryKey(PropertyValueDO record);
}
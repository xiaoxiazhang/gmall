package com.promo.gmall.mapper;

import com.promo.gmall.domain.CategoryPropertyValueDO;

public interface CategoryPropertyValueMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CategoryPropertyValueDO record);

    int insertSelective(CategoryPropertyValueDO record);

    CategoryPropertyValueDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CategoryPropertyValueDO record);

    int updateByPrimaryKey(CategoryPropertyValueDO record);
}
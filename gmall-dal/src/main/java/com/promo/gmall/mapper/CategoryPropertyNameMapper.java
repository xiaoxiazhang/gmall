package com.promo.gmall.mapper;

import com.promo.gmall.domain.CategoryPropertyNameDO;

public interface CategoryPropertyNameMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CategoryPropertyNameDO record);

    int insertSelective(CategoryPropertyNameDO record);

    CategoryPropertyNameDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CategoryPropertyNameDO record);

    int updateByPrimaryKey(CategoryPropertyNameDO record);
}
package com.promo.gmall.mapper;

import com.promo.gmall.domain.FrontCategoryDO;

public interface FrontCategoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FrontCategoryDO record);

    int insertSelective(FrontCategoryDO record);

    FrontCategoryDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FrontCategoryDO record);

    int updateByPrimaryKey(FrontCategoryDO record);
}
package com.promo.gmall.mapper;

import com.promo.gmall.domain.HomeResourceDO;

public interface HomeResourceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HomeResourceDO record);

    int insertSelective(HomeResourceDO record);

    HomeResourceDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HomeResourceDO record);

    int updateByPrimaryKey(HomeResourceDO record);
}
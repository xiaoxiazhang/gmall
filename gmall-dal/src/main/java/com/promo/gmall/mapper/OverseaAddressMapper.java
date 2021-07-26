package com.promo.gmall.mapper;

import com.promo.gmall.domain.OverseaAddressDO;

public interface OverseaAddressMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OverseaAddressDO record);

    int insertSelective(OverseaAddressDO record);

    OverseaAddressDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OverseaAddressDO record);

    int updateByPrimaryKey(OverseaAddressDO record);
}
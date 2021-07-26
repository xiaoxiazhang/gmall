package com.promo.gmall.mapper;

import com.promo.gmall.domain.ItemPriceDO;

public interface ItemPriceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ItemPriceDO record);

    int insertSelective(ItemPriceDO record);

    ItemPriceDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ItemPriceDO record);

    int updateByPrimaryKey(ItemPriceDO record);
}